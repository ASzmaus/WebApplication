package pl.futuresoft.judo.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.entity.Location;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocationService {
	private final LocationRepository locationRepository;
	public LocationService(LocationRepository locationRepository){

		this.locationRepository=locationRepository;
	}
	public void writeDtoToEntity(Location location, LocationDto locationDto ) {
		location.setLocationId(locationDto.getLocationId());
		location.setStreet(locationDto.getStreet());
		location.setHouseNumber(locationDto.getHouseNumber());
		location.setCity(locationDto.getCity());
		location.setPostcode(locationDto.getPostcode());
		location.setDescription(locationDto.getDescription());
	}

	public void writeEntityToDto(Location location, LocationDto locationDto) {
		locationDto.setLocationId(location.getLocationId());
		locationDto.setStreet(location.getStreet());
		locationDto.setHouseNumber(location.getHouseNumber());
		locationDto.setCity(location.getCity());
		locationDto.setPostcode(location.getPostcode());
		locationDto.setDescription(location.getDescription());
	}

	@Transactional
	public LocationDto addLocation(LocationDto locationDto) {
		Location location = new Location();
		writeDtoToEntity(location, locationDto);
		locationRepository.save(location);
		return locationDto;
	}

	@Transactional
	public LocationDto editLocation(LocationDto locationDto) {
		if (!locationRepository.findById(locationDto.getLocationId()).isPresent()) {
			throw new EntityNotFoundException();
		} else {
			Location location = locationRepository.findById(locationDto.getLocationId()).get();
			location.setStreet(locationDto.getStreet());
			location.setHouseNumber(locationDto.getHouseNumber());
			location.setCity(locationDto.getCity());
			location.setPostcode(locationDto.getPostcode());
			location.setDescription(locationDto.getDescription());
			writeEntityToDto(location, locationDto);
			locationRepository.save(location);
			return locationDto;
		}
	}

	@Transactional
	public List<LocationDto> locationList(){
		Iterable<Location> iterable = locationRepository.findAll();
		if (iterable==null)
			return null;
		List<LocationDto> listLocationDto=StreamSupport
				 .stream(iterable.spliterator(), false)
				 .map(p->{
					 LocationDto locationDto = new LocationDto();
				writeEntityToDto(p, locationDto);
				return locationDto;})
				 .collect(Collectors.toList());
			return listLocationDto;
	}
	@Transactional
	public LocationDto getLocation(int locationId){
		Optional<Location> locationOptional = locationRepository.findById(locationId);
		if(!locationOptional.isPresent()){
			throw new EntityNotFoundException();
		}
		LocationDto locationDto = new LocationDto();
		writeEntityToDto(locationOptional.get(),locationDto);
		return locationDto;
	}
}
