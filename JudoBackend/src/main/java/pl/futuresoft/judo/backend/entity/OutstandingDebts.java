package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Data
@Entity(name = "OutstandingDebts")
@Table(name = "outstanding_debts", schema = "administration")

public class OutstandingDebts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "debts_id", nullable = false)
    private Integer debtsId;
    @Column( name = "payment_deadline", nullable = false)
    private LocalDate paymentDeadline;
    @Column( name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "amount_of_debt", nullable = true)
    private BigDecimal amountOfDebt;
    @Column( name = "payment_date", nullable = true)
    private LocalDate paymentDate;
    @Column( name = "paid_amount", nullable = true)
    private BigDecimal paidAmount;
}
