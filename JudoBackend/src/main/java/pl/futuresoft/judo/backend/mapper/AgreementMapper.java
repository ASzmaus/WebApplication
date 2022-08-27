package pl.futuresoft.judo.backend.mapper;

import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.dto.AgreementDto;
import pl.futuresoft.judo.backend.entity.Agreement;
import pl.futuresoft.judo.backend.repository.AgreementRepository;

@Component
public class AgreementMapper {
    private final AgreementRepository agreementRepository;

    public AgreementMapper(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    public AgreementDto mapAgreementToAgreementDto(Integer agreementId){
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(()->new RuntimeException("No agreement for this Id"+agreementId));
        return AgreementDto
                .builder()
                .agreementId(agreement.getAgreementId())
                .agreementDate(agreement.getAgreementDate())
                .dateFrom(agreement.getDateFrom())
                .dateTo(agreement.getDateTo())
                .name(agreement.getName())
                .grossAmount(agreement.getGrossAmount())
                .content(agreement.getContent())
                .type(agreement.getType())
                .userId(agreement.getUserId())
                .documentId(agreement.getDocumentId())
                .build();
    }
}
