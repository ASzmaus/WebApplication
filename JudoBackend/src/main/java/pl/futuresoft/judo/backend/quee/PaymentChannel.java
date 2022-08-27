package pl.futuresoft.judo.backend.quee;

import org.springframework.cloud.stream.annotation.*;
import org.springframework.messaging.*;

public interface PaymentChannel {
    String PAYMENT_SENT_OUTPUT = "paymentSentOutput";
    String PAYMENT_SENT_INPUT = "paymentSentInput";

    @Output(PAYMENT_SENT_OUTPUT)
    MessageChannel paymentSentOutput();
    @Input(PAYMENT_SENT_INPUT)
    SubscribableChannel paymentSentInput();

}
