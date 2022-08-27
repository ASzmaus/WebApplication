package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.UserAddressDto;
import pl.futuresoft.judo.backend.service.UserAddressService;

import java.util.List;

@RestController

public class UserAddressController {

	@Autowired
	UserAddressService userAddressService;
	@PostMapping("/userAddress")
	public ResponseEntity<UserAddressDto> addUserAddress(@RequestBody UserAddressDto userAddressDto)  {
		userAddressDto = userAddressService.addUserAddress(userAddressDto);
		return new ResponseEntity<UserAddressDto>(userAddressDto, HttpStatus.OK);
	}

	@PutMapping("/userAddress/{userAddressId}")
	public ResponseEntity<UserAddressDto> editUserAddress(@RequestBody UserAddressDto userAddressDto,  @PathVariable int userAddressId) {
			userAddressDto = userAddressService.editUserAddress(userAddressDto);
			return new ResponseEntity<UserAddressDto>(userAddressDto, HttpStatus.OK);
	}

	@GetMapping("/userAddress")
	public ResponseEntity<List<UserAddressDto>> userAddressList(){
		List<UserAddressDto> userAddressListDto = userAddressService.userAddressList();
		return new ResponseEntity<List<UserAddressDto>>(userAddressListDto, HttpStatus.OK);
	}
}
