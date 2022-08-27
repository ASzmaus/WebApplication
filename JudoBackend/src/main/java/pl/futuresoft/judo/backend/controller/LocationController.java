package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.service.LocationService;

import java.util.List;

@RestController

public class LocationController {

	@Autowired
	LocationService locationService;

	@PostMapping("/location")
	public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto)  {
		locationDto = locationService.addLocation(locationDto);
		return new ResponseEntity<LocationDto>(locationDto, HttpStatus.OK);
	}

	@PutMapping("/location/{locationId}")
	public ResponseEntity<Void> editLocation(@RequestBody LocationDto locationDto,  @PathVariable int locationId) {
			locationService.editLocation(locationDto);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/location")
	public ResponseEntity<List<LocationDto>> locationList(){
		List<LocationDto> locationListDto = locationService.locationList();
		return new ResponseEntity<List<LocationDto>>(locationListDto, HttpStatus.OK);
	}

	@GetMapping("/location/{locationId}")
	public ResponseEntity<LocationDto> getLocation(@PathVariable int locationId){
		LocationDto locationDto = locationService.getLocation(locationId);
		return new ResponseEntity<LocationDto>(locationDto,HttpStatus.OK);
	}
}
