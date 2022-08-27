package pl.futuresoft.judo.backend.quee;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.configuration.MailConfiguration;
import pl.futuresoft.judo.backend.service.MailService;

@Component
@EnableBinding(PaymentChannel.class)
public class PaymentTaskConsumer {

    private final MailService mailService;
    private final MailConfiguration mailConfiguration;

    public PaymentTaskConsumer(MailService mailService, MailConfiguration mailConfiguration){
        this.mailService = mailService;
        this.mailConfiguration = mailConfiguration;
    }

    @StreamListener(PaymentChannel.PAYMENT_SENT_INPUT)
    public void consumePaymentSentTask( PaymentSentTask paymentSentTask){
       mailService.confSmtpHostAndSendEmail(paymentSentTask.getToEmail(), mailConfiguration.getMailTitle(), paymentSentTask.getBodyEmail());
    }
}
