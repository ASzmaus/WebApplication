package pl.futuresoft.judo.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.DisciplineDto;
import pl.futuresoft.judo.backend.entity.Discipline;
import pl.futuresoft.judo.backend.repository.DisciplineRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DisciplineService {

	private final DisciplineRepository DisciplineRepository;
	public DisciplineService(DisciplineRepository DisciplineRepository){
		this.DisciplineRepository = DisciplineRepository;
	}

	public void writeDtoToEntity(Discipline discipline, DisciplineDto disciplineDto ) {
		discipline.setDisciplineId(disciplineDto.getDisciplineId());
		discipline.setName(disciplineDto.getName());
		discipline.setDescription(disciplineDto.getDescription());
	}

	public void writeEntityToDto(Discipline disciplineDb, DisciplineDto disciplineDto) {
		disciplineDto.setDisciplineId(disciplineDb.getDisciplineId());
		disciplineDto.setName(disciplineDb.getName());
		disciplineDto.setDescription(disciplineDb.getDescription());
	}

	@Transactional
	public List<DisciplineDto> disciplineList() {
		Iterable<Discipline> iterable= DisciplineRepository.findAll();
		if (iterable==null)
			return null;
		List<DisciplineDto> listDisciplineDto=StreamSupport
				 .stream(iterable.spliterator(), false)
				 .map(p->{
				DisciplineDto disciplineDto = new DisciplineDto();
				writeEntityToDto(p,disciplineDto);
				return disciplineDto;})
				 .collect(Collectors.toList());
			return listDisciplineDto;
	}
}
