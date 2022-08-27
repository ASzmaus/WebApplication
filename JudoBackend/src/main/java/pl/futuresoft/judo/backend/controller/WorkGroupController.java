package pl.futuresoft.judo.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.WorkGroupDto;
import pl.futuresoft.judo.backend.repository.ClubLocationRepository;
import pl.futuresoft.judo.backend.repository.ClubRepository;
import pl.futuresoft.judo.backend.repository.DisciplineClubRepository;
import pl.futuresoft.judo.backend.repository.WorkGroupRepository;
import pl.futuresoft.judo.backend.service.SubscriptionService;
import pl.futuresoft.judo.backend.service.WorkGroupService;

import java.util.List;

@RestController

public class WorkGroupController {

	WorkGroupRepository workGroupRepository;
	ClubRepository clubRepository;
	DisciplineClubRepository disciplineClubRepository;
	SubscriptionService subscriptionService;
	WorkGroupService workGroupService;
	ClubLocationRepository clubLocationRepository;

	public WorkGroupController(WorkGroupRepository workGroupRepository, ClubLocationRepository clubLocationRepository, ClubRepository clubRepository, DisciplineClubRepository disciplineClubRepository, SubscriptionService subscriptionService, WorkGroupService workGroupService) {
	this.workGroupRepository = workGroupRepository;
	this.disciplineClubRepository = disciplineClubRepository;
	this.clubRepository=clubRepository;
	this.subscriptionService = subscriptionService;
	this.workGroupService = workGroupService;
	this.clubLocationRepository=clubLocationRepository;
	}

	@PostMapping("/club/{clubId}/discipline/{disciplineId}/workGroup")
	@PreAuthorize("hasAuthority('ROLE_a')")
	public ResponseEntity<WorkGroupDto> addGroupToClubToDiscipline(@RequestBody WorkGroupDto workGroupDto, @PathVariable int clubId, @PathVariable int disciplineId ) {
			workGroupDto = workGroupService.addWorkGroupToClubToDiscipline(workGroupDto, clubId, disciplineId);
			return new ResponseEntity<WorkGroupDto>(workGroupDto,HttpStatus.OK);
	}

	@PutMapping("/club/{clubId}/discipline/{disciplineId}/workGroup/{workGroupId}")
	public ResponseEntity<Void> editGroup(@RequestBody WorkGroupDto workGroupDto, @PathVariable int clubId, @PathVariable int disciplineId, @PathVariable int workGroupId ) {
			workGroupService.editWorkGroup(workGroupDto, clubId, disciplineId, workGroupId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/club/{clubId}/workGroup")
	public ResponseEntity<List<WorkGroupDto>> groupListByClub(@PathVariable int clubId) {
		List<WorkGroupDto> workGroupListDto = workGroupService.workGroupListByClub(clubId);
		return new ResponseEntity<List<WorkGroupDto>>(workGroupListDto, HttpStatus.OK);
	}

	@GetMapping("/club/{clubId}/workGroup/{workGroupId}")
	public ResponseEntity<WorkGroupDto> getWorkGroup(@PathVariable(value="clubId") int clubId, @PathVariable(value="workGroupId") int workGroupId) {
			WorkGroupDto workGroupDto = workGroupService.getWorkGroup(clubId, workGroupId);
			return new ResponseEntity<WorkGroupDto>(workGroupDto, HttpStatus.OK);
	}
}
