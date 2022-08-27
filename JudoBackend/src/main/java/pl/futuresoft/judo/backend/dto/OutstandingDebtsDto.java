package pl.futuresoft.judo.backend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class OutstandingDebtsDto {
    private Integer debtsId;
    private LocalDate paymentDeadline;
    private Integer userId;
    private BigDecimal amountOfDebt;
    private LocalDate paymentDate;
    private BigDecimal paidAmount;
  }

