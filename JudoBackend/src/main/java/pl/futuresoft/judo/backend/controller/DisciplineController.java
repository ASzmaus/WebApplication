package pl.futuresoft.judo.backend.controller;

import java.util.List;

import pl.futuresoft.judo.backend.dto.DisciplineDto;
import pl.futuresoft.judo.backend.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DisciplineController {

	@Autowired
	DisciplineService disciplineService;

	@GetMapping("/discipline")
	public ResponseEntity<List<DisciplineDto>> list(){
		List<DisciplineDto> disciplineListDto = disciplineService.disciplineList();
		return new ResponseEntity<List<DisciplineDto>>(disciplineListDto, HttpStatus.OK);
	}
}
