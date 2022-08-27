package pl.futuresoft.judo.backend.quee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentSentTask {
        private String toEmail;
        private String bodyEmail;
}