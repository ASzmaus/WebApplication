package pl.futuresoft.judo.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.UserDto;
import pl.futuresoft.judo.backend.dto.WorkGroupUserDto;
import pl.futuresoft.judo.backend.repository.UserRepository;
import pl.futuresoft.judo.backend.repository.WorkGroupRepository;
import pl.futuresoft.judo.backend.repository.WorkGroupUserRepository;
import pl.futuresoft.judo.backend.service.WorkGroupUserService;

import java.util.List;

@RestController
public class WorkGroupUserController {
    private final WorkGroupRepository workGroupRepository;
    private final WorkGroupUserRepository workGroupUserRepository;
    private final UserRepository userRepository;
    private final WorkGroupUserService workGroupUserService;

    public WorkGroupUserController(WorkGroupRepository workGroupRepository, WorkGroupUserRepository workGroupUserRepository, UserRepository userRepository, WorkGroupUserService workGroupUserService) {
        this.workGroupRepository = workGroupRepository;
        this.workGroupUserRepository = workGroupUserRepository;
        this.userRepository = userRepository;
        this.workGroupUserService = workGroupUserService;
    }
    @PostMapping("/workGroup/{workGroupId}/user/{userId}")
    public ResponseEntity<WorkGroupUserDto> addUserToWorkGroup(@PathVariable int userId, @PathVariable int workGroupId)  {
            WorkGroupUserDto workGroupUserDto = new WorkGroupUserDto();
            workGroupUserDto=workGroupUserService.addUserToWorkGroup(userId, workGroupId);
            return new ResponseEntity<WorkGroupUserDto>(workGroupUserDto, HttpStatus.OK);
    }
    @DeleteMapping("workGroup/{workGroupId}/user/{userId}")
    public ResponseEntity<Void> deleteUserFromWOrkGroup(@PathVariable int userId, @PathVariable int workGroupId){
            workGroupUserService.deleteUserFromWorkGroup(workGroupId,userId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/workGroup/{workGroupId}/user")
    public ResponseEntity<List<UserDto>> getListUsersByWorkGroup(@PathVariable int workGroupId) {
            List<UserDto> listUserDto = workGroupUserService.userListByWorkGroup(workGroupId);
            return new ResponseEntity<List<UserDto>>(listUserDto,HttpStatus.OK);
    }
}
