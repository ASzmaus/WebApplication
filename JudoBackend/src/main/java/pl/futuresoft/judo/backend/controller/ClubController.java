package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.ClubDto;
import pl.futuresoft.judo.backend.service.ClubService;

import java.util.List;

@RestController

public class  ClubController {

	@Autowired
	ClubService clubService;

	@PostMapping("/club")
	@PreAuthorize("hasAuthority('ROLE_o')")
		public ResponseEntity<ClubDto> add(@RequestBody ClubDto clubDto) {
				clubDto = clubService.addClub(clubDto);
				return new ResponseEntity<ClubDto>(clubDto, HttpStatus.OK);
		}

	@PutMapping("/club/{clubId}")
	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	public ResponseEntity<ClubDto> edit(@RequestBody ClubDto clubDto,  @PathVariable int clubId) {
			clubDto.setClubId(clubId);
			clubDto = clubService.editClub(clubDto);
			return new ResponseEntity<ClubDto>(clubDto, HttpStatus.OK);
	}
	@GetMapping("/club/{clubId}")
	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	public ResponseEntity<ClubDto> getClub(@PathVariable int clubId){
		ClubDto club = clubService.getClub(clubId);
		return new ResponseEntity<ClubDto>(club, HttpStatus.OK);
	}

	@GetMapping("/club")
	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	public ResponseEntity<List<ClubDto>> list(){
		List<ClubDto> clubListDto = clubService.addClubList();
		return new ResponseEntity<List<ClubDto>>(clubListDto, HttpStatus.OK);
	}

    @DeleteMapping("/club/{clubId}")
	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
    public ResponseEntity<Void> deleteClub(@PathVariable int clubId) {
    		clubService.softDeleteClub(clubId);
    		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}