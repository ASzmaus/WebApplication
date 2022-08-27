package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.ClubCommand;
import pl.futuresoft.judo.backend.entity.Club;
import pl.futuresoft.judo.backend.repository.ClubRepository;

@Component
public class ClubMapper {

    private final ClubRepository clubRepository;

    public ClubMapper(ClubRepository clubRepository){

        this.clubRepository=clubRepository;
    }

    public ClubCommand mapClubToClubCommand(Integer clubId){
        Club club = clubRepository
                .findById(clubId)
                .orElseThrow(()->new RuntimeException("No club for this Id"+clubId));
        return ClubCommand
                .builder()
                .clubId(club.getClubId())
                .name(club.getName())
                .domain(club.getDomain())
                .addressEmail(club.getAddressEmail())
                .phoneNumber(club.getPhoneNumber())
                .build();
    }
}
