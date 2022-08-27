package pl.futuresoft.judo.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.futuresoft.judo.backend.dto.ClubLocationDto;
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.service.ClubLocationService;

@RestController
public class ClubLocationController {

	@Autowired
	ClubLocationService clubLocationService;

	@PostMapping("/club/{clubId}/location/{locationId}")
	public ResponseEntity<ClubLocationDto> addLocationToClub(@PathVariable int clubId, @PathVariable int locationId) {
		ClubLocationDto clubLocationDto = clubLocationService.addLocationToClub(clubId, locationId);
		return new ResponseEntity<ClubLocationDto>(clubLocationDto, HttpStatus.OK);
	}

	@DeleteMapping("/club/{clubId}/location/{locationId}")
	public ResponseEntity<Void> deleteLocationFromClub(@PathVariable int clubId, @PathVariable int locationId) {
		clubLocationService.deleteLocationFromClub(clubId, locationId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/club/{clubId}/location")
	public ResponseEntity<List<LocationDto>> ClubList(@PathVariable int clubId) {
		List<LocationDto> locationDtoList = clubLocationService.LocationListByClub(clubId);
		return new ResponseEntity<List<LocationDto>>(locationDtoList, HttpStatus.OK);
	}
}
