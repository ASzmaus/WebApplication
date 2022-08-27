package pl.futuresoft.judo.backend.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.LocationDto;
import pl.futuresoft.judo.backend.dto.WorkGroupDto;
import pl.futuresoft.judo.backend.entity.ClubLocation;
import pl.futuresoft.judo.backend.entity.DisciplineClub;
import pl.futuresoft.judo.backend.entity.WorkGroup;
import pl.futuresoft.judo.backend.exception.*;
import pl.futuresoft.judo.backend.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkGroupService {

	private final WorkGroupRepository workGroupRepository;
	private final ClubRepository clubRepository;
	private final DisciplineClubRepository disciplineClubRepository;
	private final SubscriptionService subscriptionService;
	private final ClubLocationRepository clubLocationRepository;
	private final LocationRepository locationRepository;
	private final LocationService locationService;
	private static final String REGEX_PATERN = "PL[0-9]{2}(\\s[0-9]{4}){6}";

	public WorkGroupService(WorkGroupRepository workGroupRepository, ClubLocationRepository clubLocationRepository, ClubRepository clubRepository, DisciplineClubRepository disciplineClubRepository, SubscriptionService subscriptionService, LocationRepository locationRepository, LocationService locationService) {
		this.workGroupRepository = workGroupRepository;
		this.disciplineClubRepository = disciplineClubRepository;
		this.clubRepository=clubRepository;
		this.subscriptionService = subscriptionService;
		this.clubLocationRepository=clubLocationRepository;
		this.locationRepository = locationRepository;
		this.locationService = locationService;
	}

	public void writeDtoToEntity(WorkGroup workGroupDb, WorkGroupDto workGroupDto, int clubId, int disciplineId ){
		workGroupDb.setName(workGroupDto.getName());
		workGroupDb.setClubId(clubId);
		workGroupDb.setDisciplineId(disciplineId);
		workGroupDb.setLimitOfPlaces(workGroupDto.getLimitOfPlaces());
		workGroupDb.setStartingDate(workGroupDto.getStartingDate());
		workGroupDb.setEndDate(workGroupDto.getEndDate());
		workGroupDb.setClubLocation(getClubLocationFromWorkGroupDto(workGroupDto, clubId));
		workGroupDb.setMonthlyCost(workGroupDto.getMonthlyCost());
		workGroupDb.setBankAccountNumber(workGroupDto.getBankAccountNumber());
	}

	private void writeEntityToDto(WorkGroup workGroup, WorkGroupDto workGroupDto) {
		workGroupDto.setWorkGroupId(workGroup.getWorkGroupId());
		workGroupDto.setName(workGroup.getName());
		workGroupDto.setDisciplineId(workGroup.getDisciplineId());
		workGroupDto.setClubId(workGroup.getClubId());
		workGroupDto.setLimitOfPlaces(workGroup.getLimitOfPlaces());
		workGroupDto.setStartingDate(workGroup.getStartingDate());
		workGroupDto.setEndDate(workGroup.getEndDate());
		workGroupDto.setLocationDto(getLocationDtoByClubLocationId(workGroup));
		workGroupDto.setMonthlyCost(workGroup.getMonthlyCost());
		workGroupDto.setBankAccountNumber(workGroup.getBankAccountNumber());
	}

	public LocationDto getLocationDtoByClubLocationId(WorkGroup workGroup){
		Integer locationId = workGroup.getClubLocation().getLocationId();
		LocationDto locationDto = new LocationDto();
		locationService.writeEntityToDto(locationRepository.findById(locationId).get(),locationDto);
		return locationDto;
	}

	@Transactional
	public WorkGroupDto saveWorkGroup(WorkGroupDto workGroupDto, int disciplineId, int clubId) {
		if (patternMatches(workGroupDto.getBankAccountNumber(), REGEX_PATERN) == false)
			throw new WrongDataFormatException();
		WorkGroup workGroupByBankAccountNumber = workGroupRepository.findByName(workGroupDto.getBankAccountNumber());
		if (workGroupByBankAccountNumber != null)
			throw new BankAccountAlreadyAddedException();
		WorkGroup workGroupDb = new WorkGroup();
		writeDtoToEntity(workGroupDb, workGroupDto, clubId, disciplineId);
		workGroupRepository.save(workGroupDb);
		return workGroupDto;
		}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_a')")
	public WorkGroupDto addWorkGroupToClubToDiscipline(WorkGroupDto workGroupDto,int clubId,  int disciplineId) {
		DisciplineClub disciplineClub = disciplineClubRepository.findAllByClubIdByDisciplineId(clubId, disciplineId);
		if(disciplineClub==null)
			throw new DisciplineNotFoundInClubException(clubId, disciplineId );
		WorkGroup element2 = workGroupRepository.findByName(workGroupDto.getName());
		if (element2!=null)
			throw new IdAlreadyAddedException();
		List<WorkGroup> workGroupClub = workGroupRepository.findAllByClubId(clubId);
		if (workGroupClub.size()<1) {
			workGroupDto = saveWorkGroup(workGroupDto, clubId, disciplineId);
			return workGroupDto;
		}else if ((workGroupClub.size()>=1) && (subscriptionService.ifClubHasPaidSubscription(clubId)== true)) {
				workGroupDto = saveWorkGroup(workGroupDto, clubId, disciplineId);
				return workGroupDto;
		}else {
				throw new ClubDoseNotHavePaidSubscriptionException();
		}
	}

	@Transactional
	public void editWorkGroup(WorkGroupDto workGroupDto, int clubId, int disciplineId, int workGroupId) {
		Optional<WorkGroup> workGroupOptionalDb = workGroupRepository.findById(workGroupId);
		if (!workGroupOptionalDb.isPresent())
			throw new EntityNotFoundException();
		WorkGroup workGroupDb = workGroupOptionalDb.get();
		writeDtoToEntity(workGroupDb, workGroupDto, clubId, disciplineId);
		workGroupRepository.save(workGroupDb);
	}
	public ClubLocation getClubLocationFromWorkGroupDto(WorkGroupDto workGroupDto, int clubId ){
		Integer workGroupDtoLocationId = workGroupDto.getLocationDto().getLocationId();
		return clubLocationRepository.findAllByClubIdLocationId(clubId,workGroupDtoLocationId);
	}

	public WorkGroupDto getWorkGroup(Integer clubId, Integer workGroupId)
	{
		WorkGroup workGroup = workGroupRepository.findByClubIdAndWorkGroupId(clubId,workGroupId);
		if (workGroup==null)
			throw new WorkGroupNotFoundException(workGroupId);
		WorkGroupDto dto = new WorkGroupDto();
		writeEntityToDto(workGroup, dto);
		return dto;
	}

	@Transactional
	public List<WorkGroupDto> workGroupListByClub(int clubId) {
		Iterable<WorkGroup> iterable= workGroupRepository.findAllByClubId(clubId);
		if (iterable==null)
			return null;
		List<WorkGroupDto> listWorkGoupDto=StreamSupport
				 .stream(iterable.spliterator(), false)
				 .map(p->{
				WorkGroupDto workGroupDto = new WorkGroupDto();
				writeEntityToDto(p, workGroupDto);
				return workGroupDto;})
				 .collect(Collectors.toList());
			return listWorkGoupDto;
	}

	private boolean patternMatches(String numberAccount, String regexPattern) {
		return Pattern.compile(regexPattern)
				.matcher(numberAccount)
				.matches();
	}
}
