package pl.futuresoft.judo.backend.service;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.UserDto;
import pl.futuresoft.judo.backend.dto.WorkGroupUserDto;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.entity.WorkGroupUser;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException;
import pl.futuresoft.judo.backend.repository.UserRepository;
import pl.futuresoft.judo.backend.repository.WorkGroupRepository;
import pl.futuresoft.judo.backend.repository.WorkGroupUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkGroupUserService {
    private final WorkGroupRepository workGroupRepository;
    private final WorkGroupUserRepository workGroupUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public WorkGroupUserService(WorkGroupRepository workGroupRepository, WorkGroupUserRepository workGroupUserRepository, UserRepository userRepository, UserService userService) {
        this.workGroupRepository = workGroupRepository;
        this.workGroupUserRepository = workGroupUserRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    public void writeEntityToDto(WorkGroupUser workGroupUser, WorkGroupUserDto workGroupUserDto){
        workGroupUserDto.setWorkGroupUserId(workGroupUser.getWorkGroupUserId());
        workGroupUserDto.setWorkGroupId(workGroupUser.getWorkGroupId());
        workGroupUserDto.setUserId(workGroupUser.getUserId());
    }
    @Transactional
    public WorkGroupUserDto addUserToWorkGroup(Integer userId, Integer workGroupId ){
        Preconditions.checkNotNull(userId,"UserId could not be null");
        Preconditions.checkNotNull(workGroupId,"WorkGroupId could not be null");
        if(!userRepository.findById(userId).isPresent())
            throw new EntityNotFoundException();
        if(!workGroupRepository.findById(workGroupId).isPresent())
            throw new EntityNotFoundException();
        WorkGroupUser workGroupUser1=workGroupUserRepository.findAllByUserIdAndWorkGroupId(workGroupId,userId);
        if(workGroupUser1!=null)
            throw new IdAlreadyAddedException();
        WorkGroupUser workGroupUser = new WorkGroupUser();
        WorkGroupUserDto workGroupUserDto =new WorkGroupUserDto();
        workGroupUser.setWorkGroupId(workGroupId);
        workGroupUser.setUserId(userId);
        writeEntityToDto(workGroupUser,workGroupUserDto);
        workGroupUserRepository.save(workGroupUser);
        workGroupUserDto.setWorkGroupUserId(workGroupUser.getWorkGroupUserId());
        return workGroupUserDto;
    }

    @Transactional
    public void deleteUserFromWorkGroup(Integer workGroupId, Integer userId){
        Preconditions.checkNotNull(userId,"UserId could not be null");
        Preconditions.checkNotNull(workGroupId,"WorkGroupId could not be null");
       if(workGroupUserRepository.findAllByUserIdAndWorkGroupId(workGroupId,userId)==null)
            throw new EntityNotFoundException();
        workGroupUserRepository.delete(workGroupId,userId);
    }

  @Transactional
  public List<UserDto> userListByWorkGroup(Integer workGroupId) {
      Preconditions.checkNotNull(workGroupId,"ClubId can not be null");
      if(workGroupUserRepository.findAllByWorkGroupId(workGroupId).isEmpty())
          throw new EntityNotFoundException();
      List<User> userList = workGroupUserRepository.findAllByWorkGroupId(workGroupId);
      List<UserDto> userListDto = StreamSupport
              .stream(userList.spliterator(),false)
              .map(p->{
                  UserDto userDto = new UserDto();
                  userService.writeEntityToDto(p,userDto);
                  return userDto;
              })
              .collect(Collectors.toList());
      return userListDto;
  }
}
