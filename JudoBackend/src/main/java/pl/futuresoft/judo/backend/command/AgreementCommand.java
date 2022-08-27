package pl.futuresoft.judo.backend.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.futuresoft.judo.backend.Enum.AgreementType;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgreementCommand {
    private AgreementType type;
    private Integer userId;
    private LocalDate agreementDate;

}
