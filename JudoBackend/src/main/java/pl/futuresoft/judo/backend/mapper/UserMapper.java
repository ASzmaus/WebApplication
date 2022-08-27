package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.command.UserCommand;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.repository.*;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper( UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserCommand mapUserToUserCommand(Integer userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow(()->new RuntimeException("No user for this Id"+userId));
        return UserCommand
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
