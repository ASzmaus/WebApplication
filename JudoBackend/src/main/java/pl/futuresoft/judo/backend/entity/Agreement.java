package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.futuresoft.judo.backend.Enum.AgreementType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name="Agreement")
@Table(name="agreement", schema = "administration")

public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id", nullable = false)
    private Integer agreementId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name =  "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgreementType type;
    @Column(name = "agreement_date", nullable = false)
    private LocalDate agreementDate;
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;
    @Column(name = "gross_amount",nullable = false)
    private BigDecimal grossAmount;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "content", nullable = false)
    private byte[] content;
    @Column(name="document_id", nullable = false)
    private Integer documentId;
}
