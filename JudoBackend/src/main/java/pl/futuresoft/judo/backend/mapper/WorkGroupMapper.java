package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.WorkGroupCommand;
import pl.futuresoft.judo.backend.entity.WorkGroup;
import pl.futuresoft.judo.backend.repository.*;

@Component
public class WorkGroupMapper {

    private final WorkGroupRepository workGroupRepository;

    public WorkGroupMapper(WorkGroupRepository workGroupRepository){
             this.workGroupRepository=workGroupRepository;
    }

    public WorkGroupCommand mapWorkGroupToWorkGroupCommand(Integer workGroupId){
        String  nameWorkGroup = workGroupRepository
                .findById(workGroupId)
                .map(WorkGroup::getName)
                .orElseThrow(()-> new RuntimeException("No work group for Id" + workGroupId ));

        WorkGroup  workGroup = workGroupRepository.findById(workGroupId)
                .orElseThrow(()-> new RuntimeException("No work group for Id" + workGroupId ));
        return WorkGroupCommand
                .builder()
                .name(nameWorkGroup)
                .bankAccountNumber(workGroup.getBankAccountNumber())
                .build();
    }
}

