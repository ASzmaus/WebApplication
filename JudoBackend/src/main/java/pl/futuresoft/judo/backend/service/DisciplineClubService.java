package pl.futuresoft.judo.backend.service;

import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.DisciplineClubDto;
import pl.futuresoft.judo.backend.dto.DisciplineDto;
import pl.futuresoft.judo.backend.entity.Discipline;
import pl.futuresoft.judo.backend.entity.DisciplineClub;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.exception.IdAlreadyAddedException;
import pl.futuresoft.judo.backend.repository.ClubRepository;
import pl.futuresoft.judo.backend.repository.DisciplineClubRepository;
import pl.futuresoft.judo.backend.repository.DisciplineRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DisciplineClubService {
	private final DisciplineClubRepository disciplineClubRepository;
	private final ClubRepository clubRepository;
	private final DisciplineRepository disciplineRepository;
	private final DisciplineService disciplineService;
	public DisciplineClubService(DisciplineClubRepository disciplineClubRepository,
								 ClubRepository clubRepository,
								 DisciplineRepository disciplineRepository,
								 DisciplineService disciplineService) {
		this.disciplineClubRepository = disciplineClubRepository;
		this.clubRepository = clubRepository;
		this.disciplineRepository = disciplineRepository;
		this.disciplineService = disciplineService;
	}

	public void writeEntityToDto(DisciplineClub disciplineClub, DisciplineClubDto disciplineClubDto) {
		disciplineClubDto.setDisciplineClubId(disciplineClub.getDisciplineClubId());
		disciplineClubDto.setClubId(disciplineClub.getClubId());
		disciplineClubDto.setDisciplineId(disciplineClub.getDisciplineId());
	}

	@Transactional
	public DisciplineClubDto addDisciplineToClub(Integer clubId, Integer disciplineId) {
		Preconditions.checkNotNull(clubId,"ClubId can not be null");
		Preconditions.checkNotNull(disciplineId, "DisciplineId can not be null");
		if (!clubRepository.findById(clubId).isPresent())
			throw new EntityNotFoundException();
		if (!disciplineRepository.findById(disciplineId).isPresent())
			throw new EntityNotFoundException();
		DisciplineClub element= disciplineClubRepository.findAllByClubIdByDisciplineId(clubId, disciplineId);
		if (element!=null)
		    throw new IdAlreadyAddedException();
		DisciplineClubDto disciplineClubDto = new DisciplineClubDto();
		DisciplineClub disciplineClub = new DisciplineClub();
		disciplineClub.setClubId(clubId);
		disciplineClub.setDisciplineId(disciplineId);

		writeEntityToDto(disciplineClub, disciplineClubDto);
		disciplineClubRepository.save(disciplineClub);
		return disciplineClubDto;
	}

	@Transactional
	public void deleteDisciplineFromClub(int clubId, int disciplineId) {
		if (disciplineClubRepository.findAllByClubIdByDisciplineId(clubId, disciplineId)==null)
			throw new EntityNotFoundException();
		disciplineClubRepository.delete(clubId, disciplineId);
	}

	@Transactional
	public List<DisciplineDto> disciplineListByClub(int clubId) {
		if (disciplineClubRepository.findAllByClubId(clubId).isEmpty())
			throw new EntityNotFoundException();
		List<Discipline> list= disciplineClubRepository.findAllByClubId(clubId);
		List<DisciplineDto> listDisciplineDto=StreamSupport
					.stream(list.spliterator(),false)
					.map(p->{
						DisciplineDto ddto = new DisciplineDto();
						disciplineService.writeEntityToDto(p, ddto);
						String disciplineName =disciplineRepository.findById(p.getDisciplineId()).get().getName();
						ddto.setName(disciplineName);
					return ddto;})
					.collect(Collectors.toList());
				return listDisciplineDto;
	}
}