package pl.futuresoft.judo.backend.quee;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class PaymentTaskProducer {
    MessageChannel messageChannel;

    public PaymentTaskProducer(PaymentChannel paymentChannel){
        messageChannel = paymentChannel.paymentSentOutput();
    }
    public void createPaymentSentTask( String toEmail, String emailBody){
        PaymentSentTask paymentSentTask = new PaymentSentTask(toEmail, emailBody);
        messageChannel.send(MessageBuilder.withPayload(paymentSentTask).build());
    }
}
