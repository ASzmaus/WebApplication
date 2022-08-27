package pl.futuresoft.judo.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException;
import pl.futuresoft.judo.backend.repository.ClubLocationRepository;
import pl.futuresoft.judo.backend.repository.ClubRepository;
import pl.futuresoft.judo.backend.repository.LocationRepository;
import pl.futuresoft.judo.backend.dto.ClubLocationDto;
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.entity.ClubLocation;
import pl.futuresoft.judo.backend.entity.Location;

@Service
public class ClubLocationService {

	private final ClubLocationRepository clubLocationRepository;
	private final ClubRepository clubRepository;
	private final LocationRepository locationRepository;
	private final LocationService locationService;

	public ClubLocationService(ClubLocationRepository clubLocationRepository, ClubRepository clubRepository, LocationRepository locationRepository, LocationService locationService) {
		this.clubLocationRepository=clubLocationRepository;
		this.clubRepository=clubRepository;
		this.locationRepository=locationRepository;
		this.locationService = locationService;
	}

	public void writeEntityToDto(ClubLocation clubLocation, ClubLocationDto clubLocationDto) {
		clubLocationDto.setClubLocationId(clubLocation.getClubLocationId());
		clubLocationDto.setClubId(clubLocation.getClubId());
		clubLocationDto.setLocationId(clubLocation.getLocationId());
	}

	@Transactional
	public ClubLocationDto addLocationToClub(Integer clubId, Integer locationId){

		Preconditions.checkNotNull(clubId,"ClubId can not be null");
		Preconditions.checkNotNull(locationId, "LocationId can not be null");
		if(!clubRepository.findById(clubId).isPresent())
			throw new EntityNotFoundException();
		if(!locationRepository.findById(locationId).isPresent())
			throw new EntityNotFoundException();
		ClubLocation clubLocation1=clubLocationRepository.findAllByClubIdLocationId(clubId, locationId);
		if(clubLocation1!=null)
			throw new IdAlreadyAddedException();
		ClubLocationDto clubLocationDto = new ClubLocationDto();
		ClubLocation clubLocation = new ClubLocation();
		clubLocation.setClubId(clubId);
		clubLocation.setLocationId(locationId);
		writeEntityToDto(clubLocation, clubLocationDto);
		clubLocationRepository.save(clubLocation);
		return clubLocationDto;
		}

	@Transactional
	public void deleteLocationFromClub( Integer clubId, Integer locationId){
		Preconditions.checkNotNull(clubId,"ClubId can not be null");
		Preconditions.checkNotNull(locationId, "LocationId can not be null");
		if (clubLocationRepository.findAllByClubIdLocationId(clubId,locationId)==null)
			throw new EntityNotFoundException();
		clubLocationRepository.delete(clubId, locationId);
	}

	@Transactional
	public List<LocationDto> LocationListByClub(Integer clubId){
		Preconditions.checkNotNull(clubId,"ClubId can not be null");
		if(clubLocationRepository.findAllByClubId(clubId).isEmpty())
			throw new EntityNotFoundException();
		List<Location> locationList = clubLocationRepository.findAllByClubId(clubId);
		List<LocationDto> locationListDto = StreamSupport
				.stream(locationList.spliterator(),false)
				.map(p->{
					LocationDto locationDto = new LocationDto();
					locationService.writeEntityToDto(p,locationDto);
				return locationDto;
				})
				.collect(Collectors.toList());
		return locationListDto;
	}
}
