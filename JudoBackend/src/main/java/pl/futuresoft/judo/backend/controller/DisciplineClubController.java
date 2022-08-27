package pl.futuresoft.judo.backend.controller;

import java.util.List;

import pl.futuresoft.judo.backend.dto.DisciplineDto;
import pl.futuresoft.judo.backend.dto.DisciplineClubDto;
import pl.futuresoft.judo.backend.service.DisciplineClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DisciplineClubController {

	@Autowired
	DisciplineClubService disciplineClubService;

	@PostMapping("/club/{clubId}/discipline/{disciplineId}")
	public ResponseEntity<DisciplineClubDto> addDisciplineToClub(@PathVariable int clubId, @PathVariable int disciplineId) {
			DisciplineClubDto disciplineClubDto = new DisciplineClubDto();
			disciplineClubDto = disciplineClubService.addDisciplineToClub(clubId,disciplineId);
			return new ResponseEntity<DisciplineClubDto>(disciplineClubDto, HttpStatus.OK);
		}

	 @DeleteMapping("/club/{clubId}/discipline/{disciplineId}")
	    public ResponseEntity<Void> deleteDiscipline(@PathVariable int clubId, @PathVariable int disciplineId) {
						disciplineClubService.deleteDisciplineFromClub(clubId, disciplineId);
	    		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

	@GetMapping("/club/{clubId}/discipline")
	public ResponseEntity< List<DisciplineDto>> disciplineList(@PathVariable int clubId) throws Exception {
		 List<DisciplineDto> disciplineListDto = disciplineClubService.disciplineListByClub(clubId);
			return new ResponseEntity< List<DisciplineDto>>(disciplineListDto, HttpStatus.OK);
	}
}