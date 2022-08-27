package pl.futuresoft.judo.backend.configuration;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "scheduling")

public class ScheduleConfiguration {

   private Integer paymentDayInScheduler;
   private String cron;
   private Boolean enabled;


}
