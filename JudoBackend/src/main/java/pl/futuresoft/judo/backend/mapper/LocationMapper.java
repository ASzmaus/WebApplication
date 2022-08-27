package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.LocationCommand;
import pl.futuresoft.judo.backend.entity.Location;
import pl.futuresoft.judo.backend.repository.*;

@Component
public class LocationMapper {

    private final LocationRepository locationRepository;

    public LocationMapper(LocationRepository locationRepository){
            this.locationRepository=locationRepository;
    }

    public LocationCommand mapLocationToLocationCommand(Integer locationId){
        Location location = locationRepository
                .findById(locationId)
                .orElseThrow(()->new RuntimeException("No location for this Id"+locationId));
        return LocationCommand
                .builder()
                .description(location.getDescription())
                .city(location.getCity())
                .street(location.getStreet())
                .houseNumber(location.getHouseNumber())
                .build();
    }
}
