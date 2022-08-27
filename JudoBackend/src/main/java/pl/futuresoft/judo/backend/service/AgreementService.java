package pl.futuresoft.judo.backend.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.Jasper.JasperService;
import pl.futuresoft.judo.backend.command.AgreementCommand;
import pl.futuresoft.judo.backend.configuration.JasperConfiguration;
import pl.futuresoft.judo.backend.dto.AgreementDto;
import pl.futuresoft.judo.backend.entity.*;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.mapper.AgreementMapper;
import pl.futuresoft.judo.backend.repository.AgreementRepository;
import pl.futuresoft.judo.backend.repository.DocumentRepository;
import pl.futuresoft.judo.backend.repository.UserAddressRepository;
import pl.futuresoft.judo.backend.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class AgreementService {

    private final HashMap<String, Object> parametersMap;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final AgreementRepository agreementRepository;
    private final JasperService jasperService;
    private final JasperConfiguration jasperConfiguration;
    private final AgreementMapper agreementMapper;
    private final OutstandingDebtsSchedulerService outstandingDebtsSchedulerService;

    public AgreementService(HashMap<String, Object> parametersMap, DocumentRepository documentRepository, UserRepository userRepository, UserAddressRepository userAddressRepository, AgreementRepository agreementRepository, JasperService jasperService, JasperConfiguration jasperConfiguration, AgreementMapper agreementMapper, OutstandingDebtsSchedulerService outstandingDebtsSchedulerService) {
        this.parametersMap = parametersMap;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.agreementRepository = agreementRepository;
        this.jasperService = jasperService;
        this.jasperConfiguration = jasperConfiguration;
        this.agreementMapper = agreementMapper;
        this.outstandingDebtsSchedulerService = outstandingDebtsSchedulerService;
    }

    @Transactional
    public AgreementDto saveAgreement(AgreementCommand agreementCommand) {
        User user = userRepository.findById(agreementCommand.getUserId())
                .orElseThrow(EntityNotFoundException::new);

        Document document = documentRepository.findById(jasperConfiguration.getDocumentId())
                .orElseThrow(EntityNotFoundException::new);

        List<UserAddress> userAddressList = userAddressRepository.findAllByUserId(agreementCommand.getUserId());
        UserAddress userAddress = Lists.reverse(userAddressList).stream()
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        createParametersMap(user,userAddress,agreementCommand);

        Agreement agreement = Agreement
                .builder()
                .agreementDate(LocalDate.now())
                .dateFrom(LocalDate.now())
                .dateTo(calculateDataTo(agreementCommand))
                .grossAmount(calculateGrossAmount(agreementCommand))
                .name(jasperConfiguration.getAgreementName())
                .type(agreementCommand.getType())
                .userId(agreementCommand.getUserId())
                .content(jasperService.createDocument(parametersMap,document))
                .documentId(jasperConfiguration.getDocumentId())
                .build();
        agreementRepository.save(agreement);

        outstandingDebtsSchedulerService.createOutstandingDebts(agreementCommand);

        return agreementMapper.mapAgreementToAgreementDto(agreement.getAgreementId());
    }

    private void createParametersMap(User user, UserAddress userAddress, AgreementCommand agreementCommand) {
        parametersMap.put("last_name",user.getFirstName());
        parametersMap.put("first_name",user.getLastName());
        parametersMap.put("date_of_agreement",LocalDate.now().toString());
        parametersMap.put("city",userAddress.getCity());
        parametersMap.put("street",userAddress.getStreet());
        parametersMap.put("house_no",userAddress.getHouseNumber().toString());
        parametersMap.put("postcode",userAddress.getPostcode());
        parametersMap.put("date_from",LocalDate.now().toString());
        parametersMap.put("date_to",LocalDate.now().toString());
        parametersMap.put("gross_amount",calculateGrossAmount(agreementCommand).toString());
        parametersMap.put("company_name",jasperConfiguration.getCompanyName());
        parametersMap.put("company_address",jasperConfiguration.getCompanyAddress());
        parametersMap.put("agreement_place",jasperConfiguration.getAgreementPlace());
}

    private BigDecimal calculateGrossAmount(AgreementCommand agreementCommand){
        WorkGroup workGroup = userRepository.findWorkGroupByUserId(agreementCommand.getUserId());
        if(agreementCommand.getType().toString().equals("MONTHLY")){
            return workGroup.getMonthlyCost();
        } else{
            return workGroup.getMonthlyCost().multiply( BigDecimal.valueOf(12));
        }
    }

    private LocalDate calculateDataTo(AgreementCommand agreementCommand){
       if(agreementCommand.getType().toString().equals("MONTHLY")){
            return LocalDate.now().plusMonths(1);
       } else{
            return LocalDate.now().plusMonths(12);
       }
    }
}
