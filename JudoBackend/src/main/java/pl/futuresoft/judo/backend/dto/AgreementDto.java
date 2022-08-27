package pl.futuresoft.judo.backend.dto;

import lombok.Builder;
import lombok.Data;
import pl.futuresoft.judo.backend.Enum.AgreementType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class AgreementDto {
    private Integer agreementId;
    private String name;
    private AgreementType type;
    private LocalDate agreementDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal grossAmount;
    private Integer userId;
    private byte[] content;
    private Integer documentId;
}
