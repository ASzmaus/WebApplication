package pl.futuresoft.judo.backend.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.command.AgreementCommand;
import pl.futuresoft.judo.backend.command.OutstandingDebtsCommand;
import pl.futuresoft.judo.backend.configuration.MailConfiguration;
import pl.futuresoft.judo.backend.configuration.ScheduleConfiguration;
import pl.futuresoft.judo.backend.dto.OutstandingDebtsDto;
import pl.futuresoft.judo.backend.entity.Agreement;
import pl.futuresoft.judo.backend.entity.OutstandingDebts;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.entity.WorkGroup;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.mapper.OutstandingDebtsMapper;
import pl.futuresoft.judo.backend.quee.PaymentTaskProducer;
import pl.futuresoft.judo.backend.repository.AgreementRepository;
import pl.futuresoft.judo.backend.repository.OutstandingDebtsRepository;
import pl.futuresoft.judo.backend.repository.UserRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@ConditionalOnProperty(value="scheduling.enabled", havingValue="true", matchIfMissing = true)
public class OutstandingDebtsSchedulerService {

    private final ScheduleConfiguration scheduleConfiguration;
    private final OutstandingDebtsRepository outstandingDebtsRepository;
    private final OutstandingDebtsMapper outstandingDebtsMapper;
    private final AgreementRepository agreementRepository;
    private final MailService mailService;
    private final MailConfiguration mailConfiguration;
    private final ClubService clubService;
    private final UserRepository userRepository;
    private final WorkGroupService workGroupService;
    private  final PaymentTaskProducer paymentTaskProducer;


    public OutstandingDebtsSchedulerService(ScheduleConfiguration scheduleConfiguration, OutstandingDebtsRepository outstandingDebtsRepository, OutstandingDebtsMapper outstandingDebtsMapper, AgreementRepository agreementRepository, MailService mailService, MailConfiguration mailConfiguration, ClubService clubService, UserRepository userRepository, WorkGroupService workGroupService, PaymentTaskProducer paymentTaskProducer) {
        this.scheduleConfiguration = scheduleConfiguration;
        this.outstandingDebtsRepository = outstandingDebtsRepository;
        this.outstandingDebtsMapper = outstandingDebtsMapper;
        this.agreementRepository = agreementRepository;
        this.mailService = mailService;
        this.mailConfiguration = mailConfiguration;
        this.clubService = clubService;
        this.userRepository = userRepository;
        this.workGroupService = workGroupService;
        this.paymentTaskProducer = paymentTaskProducer;
    }

    @Transactional
    @Scheduled(cron = "${scheduling.cron}")
     public void trackOverduePayment() {
        log.info("Beginning of scheduler");

        outstandingDebtsDtoList()
                .stream()
                .forEach(d -> {
                    User user = userRepository.findById(d.getUserId())
                            .orElseThrow(() -> new RuntimeException("No user for this Id"));
                    WorkGroup workGroup = userRepository.findWorkGroupByUserId(d.getUserId());
                    String mailBody = "Outstanding debts for clients " + user.getFirstName() + " " + user.getLastName() + " the amount of " + d.getAmountOfDebt() + " .Payment should be done as soon as possible to the following account: " + workGroup.getBankAccountNumber();
                    paymentTaskProducer.createPaymentSentTask(user.getEmail(), mailBody);
                });

        clubService.addClubList()
                .forEach(e -> {
                    Iterable<User> iterable = userRepository.findAll();
                    StreamSupport.stream(iterable.spliterator(), true)
                            .filter(s -> s.getClubId()!=null && s.getClubId().equals(e.getClubId()) )
                            .forEach(admin -> {
                                List<Object> listOutstandingDebts = new LinkedList<>();
                                outstandingDebtsDtoListByClubIdForClients(e.getClubId())
                                        .forEach(r -> {
                                            Integer workGroupId = userRepository.findWorkGroupByUserId(r.getUserId()).getWorkGroupId();
                                            OutstandingDebtsCommand outstandingDebtsCommand = outstandingDebtsMapper.mapClubUserOutstandingDebtsToOutstandingDebtsCommand(e.getClubId(), workGroupId, r.getUserId(), r.getDebtsId());
                                            listOutstandingDebts.add(outstandingDebtsCommand.toString());
                                        });
                                if (!listOutstandingDebts.isEmpty() && admin.getRoleId().equals("a")) {
                                    String str = listOutstandingDebts.stream().map(Object::toString)
                                            .collect(Collectors.joining(","));
                                   paymentTaskProducer.createPaymentSentTask(admin.getEmail(), str);
                                }
                            });
                });
    }

    @Transactional
    public OutstandingDebtsDto createOutstandingDebts(AgreementCommand agreementCommand){
        List<Agreement> agreementList = agreementRepository.findAllByUserId(agreementCommand.getUserId());
        Agreement lastAgreement = Lists.reverse(agreementList).stream()
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

         OutstandingDebts outstandingDebts = OutstandingDebts
                .builder()
                .paymentDeadline(countPaymentDeadline(agreementCommand))
                .userId(agreementCommand.getUserId())
                .amountOfDebt(lastAgreement.getGrossAmount())
                .build();
        outstandingDebtsRepository.save(outstandingDebts);
        return outstandingDebtsMapper.mapOutstandingDebtsToOutstandingDebtsDto(outstandingDebts.getDebtsId());
    }

    private LocalDate countPaymentDeadline(AgreementCommand agreementCommand) {
        if (agreementCommand.getAgreementDate().isAfter(LocalDate.of(agreementCommand.getAgreementDate().getYear(), agreementCommand.getAgreementDate().getMonth(), scheduleConfiguration.getPaymentDayInScheduler()))) {
            return LocalDate.of(agreementCommand.getAgreementDate().getYear(), agreementCommand.getAgreementDate().getMonth().plus(1), scheduleConfiguration.getPaymentDayInScheduler());
        } else {
            return LocalDate.of(agreementCommand.getAgreementDate().getYear(), agreementCommand.getAgreementDate().getMonth(), scheduleConfiguration.getPaymentDayInScheduler());
        }
    }

    public List<OutstandingDebtsDto> outstandingDebtsDtoListByClubIdForClients(Integer clubId){
        List<OutstandingDebts> iterator = outstandingDebtsRepository.findAllOutstandingDebtsByClubId(clubId);
        if( iterator == null)
             return null;
        return StreamSupport
                .stream(iterator.spliterator(), true)
                .filter(e->(e.getPaidAmount()==null || (e.getAmountOfDebt().compareTo(e.getPaidAmount())==1)) && LocalDate.now().isAfter(e.getPaymentDeadline()) )
                .map(p->outstandingDebtsMapper.mapOutstandingDebtsToOutstandingDebtsDto(p.getDebtsId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OutstandingDebtsDto> outstandingDebtsDtoList(){
        Iterable<OutstandingDebts> iterator = outstandingDebtsRepository.findAll();
        if( iterator == null)
            return null;
        return StreamSupport
                .stream(iterator.spliterator(), true)
                .filter(e->(e.getPaidAmount()==null || (e.getAmountOfDebt().compareTo(e.getPaidAmount())==1)) && LocalDate.now().isAfter(e.getPaymentDeadline()))
                .map(p->outstandingDebtsMapper.mapOutstandingDebtsToOutstandingDebtsDto(p.getDebtsId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_a')")
    public OutstandingDebtsDto editOutstandingDebts(OutstandingDebtsCommand outstandingDebtsCommand, Integer debtsId){
        OutstandingDebts outstandingDebts = outstandingDebtsRepository.findById(debtsId)
                .orElseThrow(EntityNotFoundException::new);
        return OutstandingDebtsDto
                .builder()
                .debtsId(outstandingDebtsCommand.getDebtsId())
                .amountOfDebt(outstandingDebtsCommand.getAmountOfDebt())
                .paidAmount(outstandingDebtsCommand.getPaidAmount())
                .paymentDate(outstandingDebtsCommand.getPaymentDate())
                .paymentDeadline(outstandingDebtsCommand.getPaymentDeadline())
                .userId(outstandingDebtsCommand.getUserId())
                .build();
    }
}