package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.OutstandingDebtsCommand;
import pl.futuresoft.judo.backend.dto.OutstandingDebtsDto;
import pl.futuresoft.judo.backend.entity.OutstandingDebts;
import pl.futuresoft.judo.backend.repository.OutstandingDebtsRepository;

@Component
public class OutstandingDebtsMapper {

    private final OutstandingDebtsRepository outstandingDebtsRepository;
    private final UserMapper userMapper;
    private final ClubMapper clubMapper;
    private final WorkGroupMapper workGroupMapper;

      public OutstandingDebtsMapper(OutstandingDebtsRepository outstandingDebtsRepository, UserMapper userMapper, ClubMapper clubMapper, WorkGroupMapper workGroupMapper) {
        this.outstandingDebtsRepository = outstandingDebtsRepository;
        this.userMapper = userMapper;
        this.clubMapper = clubMapper;
        this.workGroupMapper = workGroupMapper;
      }

    public OutstandingDebtsDto mapOutstandingDebtsToOutstandingDebtsDto(Integer debtsId){
       OutstandingDebts outstandingDebts = outstandingDebtsRepository.findById(debtsId)
                .orElseThrow(()-> new RuntimeException("No outstandingDebts for this id" + debtsId));

       return OutstandingDebtsDto
                .builder()
                .debtsId(outstandingDebts.getDebtsId())
                .paymentDeadline(outstandingDebts.getPaymentDeadline())
                .userId(outstandingDebts.getUserId())
                .amountOfDebt(outstandingDebts.getAmountOfDebt())
                .paymentDate(outstandingDebts.getPaymentDate())
                .paidAmount(outstandingDebts.getPaidAmount())
                .build();
    }

    public OutstandingDebtsCommand mapClubUserOutstandingDebtsToOutstandingDebtsCommand(Integer clubId, Integer workGroupId, Integer userId, Integer debtsId){
        OutstandingDebts outstandingDebts = outstandingDebtsRepository.findById(debtsId)
                .orElseThrow(()-> new RuntimeException("No outstandingDebts for this id" + debtsId));


        return OutstandingDebtsCommand
                .builder()
                .clubId(clubId)
                .name(clubMapper.mapClubToClubCommand(clubId).getName())
                .debtsId(outstandingDebts.getDebtsId())
                .paymentDeadline(outstandingDebts.getPaymentDeadline())
                .userId(outstandingDebts.getUserId())
                .firstName(userMapper.mapUserToUserCommand(userId).getFirstName())
                .lastName(userMapper.mapUserToUserCommand(userId).getLastName())
                .amountOfDebt(outstandingDebts.getAmountOfDebt())
                .paymentDate(outstandingDebts.getPaymentDate())
                .paidAmount(outstandingDebts.getPaidAmount())
                .bankAccountNumber(workGroupMapper.mapWorkGroupToWorkGroupCommand(workGroupId).getBankAccountNumber())
                .build();
    }
}
