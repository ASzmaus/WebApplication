package pl.futuresoft.judo.backend.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubCommand {
    private Integer clubId;
    private String name;
    private String domain;
    private String phoneNumber;
    private String addressEmail;
}
