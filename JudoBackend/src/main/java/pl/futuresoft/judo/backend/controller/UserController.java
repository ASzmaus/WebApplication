package pl.futuresoft.judo.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.LoginAvailableDto;
import pl.futuresoft.judo.backend.dto.UserDto;
import pl.futuresoft.judo.backend.exception.*;
import pl.futuresoft.judo.backend.service.UserService;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ROLE_o')")
    @GetMapping(value="/club/{clubId}/administrator/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable(value="clubId") Integer clubId,@PathVariable(value="userId") Integer userId)
    {
            UserDto user = userService.getUser(clubId, userId);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/checkLoginAvailable")
    public ResponseEntity<LoginAvailableDto[]> checkLoginAvailable(@RequestBody LoginAvailableDto login){
        UserDto user = userService.getUserByEmail(login.getLogin());
        LoginAvailableDto ldd = new LoginAvailableDto();
        ldd.setLogin(login.getLogin());
        ldd.setAvailable(user==null);
        LoginAvailableDto [] page = new LoginAvailableDto[1];
        page[0] = ldd;
        return new ResponseEntity<LoginAvailableDto[]>(page, HttpStatus.OK);

    }

    @PostMapping(value = "/activate")
    public ResponseEntity<UserDto> activate(@RequestParam String token) throws Exception {
        try
        {
            UserDto user = userService.activate(token);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        }catch(TokenNotFoundException z)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(TokenExpiredException z)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping(value = "/remainder")
    public ResponseEntity<UserDto> remainder(@RequestBody UserDto user) throws Exception {
        try
        {
            user = userService.remindPassword(user);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        }catch(LoginNotFoundException l)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto user) throws Exception {
        try
        {
            user = userService.register(user);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        }catch(LoginIsUsedException l)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/remainderEnterPassword")
    public ResponseEntity<UserDto> remainderEnterPassword(@RequestBody UserDto user,@RequestParam String token) throws Exception {
        try
        {
            user = userService.setNewPassword(token,user);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        }catch(TokenNotFoundException z)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(TokenExpiredException z)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping(value = "/club/{clubId}/administrator")
    @PreAuthorize("hasAuthority('ROLE_o')")
    public ResponseEntity<UserDto> addAdministrator(@RequestBody UserDto userDto, @PathVariable int clubId) {
        userDto = userService.addAdmin(userDto, clubId);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }
    @DeleteMapping("/administrator/{userId}")
    @PreAuthorize("hasAuthority('ROLE_o')")
    public ResponseEntity<Void> softDeleteAdministrator(@PathVariable int userId) {
        userService.softDeleteAdmin(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/club/{clubId}/administrator")
    @PreAuthorize("hasAuthority('ROLE_o')")
    public ResponseEntity<List<UserDto>> list(@PathVariable int clubId){
        List<UserDto> adminListDto = userService.adminList(clubId);
        return new ResponseEntity<List<UserDto>>(adminListDto, HttpStatus.OK);
    }

    @PutMapping(value = "/club/{clubId}/administrator/{userId}")
    @PreAuthorize("hasAuthority('ROLE_o')")
    public ResponseEntity<String> edit(@RequestBody UserDto userDto,  @PathVariable int clubId, @PathVariable int userId) {
        userService.editAdmin(userDto, clubId, userId);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value="/club/{clubId}/staff")
    @PreAuthorize("hasAuthority('ROLE_a')")
    public ResponseEntity<UserDto> addStaffByAdmin(@RequestBody UserDto staffDto, @PathVariable int clubId){
        staffDto = userService.addStaffByAdmin(staffDto, clubId);
        return new ResponseEntity<UserDto>(staffDto, HttpStatus.OK);
    }

    @PutMapping(value = "/club/{clubId}/staff/{staffId}")
    @PreAuthorize("hasAuthority('ROLE_a')")
    public ResponseEntity<Void> editStaffByAdmin(@RequestBody UserDto staffDto, @PathVariable int clubId, @PathVariable int staffId) {
       userService.editStaffByAdmin(staffDto, clubId, staffId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value="/staff/{staffId}")
    @PreAuthorize("hasAuthority('ROLE_a')")
    public ResponseEntity<Void> softDeleteStaffByAdmin(@PathVariable int staffId) {
        userService.softDeleteStaffByAdmin(staffId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/club/{clubId}/staff")
    @PreAuthorize("hasAuthority('ROLE_a')")
    public ResponseEntity<List<UserDto>> staffList(@PathVariable int clubId){
        List<UserDto> staffListDto = userService.staffList(clubId);
        return new ResponseEntity<List<UserDto>>(staffListDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_a')")
    @GetMapping(value="/club/{clubId}/staff/{userId}")
    public ResponseEntity<UserDto> getStaff(@PathVariable(value="clubId") Integer clubId, @PathVariable(value="userId") Integer userId)
    {
            UserDto user = userService.getUser(clubId, userId);
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }
}
