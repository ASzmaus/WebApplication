package pl.futuresoft.judo.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.ClubDto;
import pl.futuresoft.judo.backend.entity.Club;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.repository.ClubRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClubService {

	@Autowired
    ClubRepository clubRepository;

	public void writeDtoToEntity(Club club, ClubDto clubDto ) {
		club.setClubId(clubDto.getClubId());
		club.setName(clubDto.getName());
		club.setDomain(clubDto.getDomain());
		club.setPhoneNumber(clubDto.getPhoneNumber());
		club.setAddressEmail(clubDto.getAddressEmail());
		club.setDeleted(false);
	}

	public void writeEntityToDto(Club club, ClubDto clubDto) {
		clubDto.setClubId(club.getClubId());
		clubDto.setName(club.getName());
		clubDto.setDomain(club.getDomain());
		clubDto.setPhoneNumber(club.getPhoneNumber());
		clubDto.setAddressEmail(club.getAddressEmail());
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_o')")
	public ClubDto addClub(ClubDto clubDto) {
			Club club = new Club();
    		writeDtoToEntity(club, clubDto);
    		clubRepository.save(club);
			return clubDto;
	}


	@Transactional
	public ClubDto editClub(ClubDto clubDto) {
		if (!clubRepository.findById(clubDto.getClubId()).isPresent()) {
			throw new EntityNotFoundException();
		} else {
			Club clubToEdition = clubRepository.findById(clubDto.getClubId()).get();
			clubToEdition.setName(clubDto.getName());
			clubToEdition.setDomain(clubDto.getDomain());
			clubToEdition.setAddressEmail(clubDto.getAddressEmail());
			clubToEdition.setPhoneNumber(clubDto.getPhoneNumber());
			writeEntityToDto(clubToEdition,clubDto);
			clubRepository.save(clubToEdition);
			return clubDto;
		}
	}

	@Transactional
	public ClubDto getClub(Integer clubId){
		Club club = clubRepository.findById(clubId).get();
		ClubDto clubDto = new ClubDto();
		writeEntityToDto(club, clubDto);
		return clubDto;
	}


	@Transactional
	public List<ClubDto> addClubList(){
		Iterable<Club> iterable= clubRepository.findAll();
		if (iterable==null)
			return null;
		List<ClubDto> listClubDto=StreamSupport
				 .stream(iterable.spliterator(), false)
				 .filter(e -> e.getDeleted().equals(false))
				 .map(p->{
				ClubDto dto = new ClubDto();
				writeEntityToDto(p, dto);
				return dto;})
				 .collect(Collectors.toList());
			return listClubDto;
	}

	@Transactional
	public void softDeleteClub(int clubId) {
		if (!clubRepository.findById(clubId).isPresent()) {
				throw new EntityNotFoundException();
		} else {
		clubRepository.delete(clubId,true);
		}
	}
}