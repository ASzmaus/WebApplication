package pl.futuresoft.judo.backend.configuration;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Slf4j
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "judo")
public class JasperConfiguration {
    private String AgreementName;
    private String companyName;
    private String companyAddress;
    private String agreementPlace;
    private Integer documentId;
    private BigDecimal grossAmount;

}
