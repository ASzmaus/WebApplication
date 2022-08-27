package pl.futuresoft.judo.backend.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OutstandingDebtsCommand {
        private Integer debtsId;
        private LocalDate paymentDeadline;
        private Integer userId;
        private String firstName;
        private String lastName;
        private BigDecimal amountOfDebt;
        private LocalDate paymentDate;
        private BigDecimal paidAmount;
        private String bankAccountNumber;
        private Integer clubId;
        private String name;

        @Override
        public String toString() {
                return   concat("Nazwa klubu: ", this.name,", termin zapłaty: ",this.paymentDeadline.toString(),", imię: ", this.firstName,", nazwisko: ",this.lastName,
                        ", kwota do zapłaty z umowy ",this.amountOfDebt,", data zapłaty: ", this.paymentDate, ", kwota zapłacona: ", this.paidAmount, ", numer konta bankowego: ", this.bankAccountNumber );
        }

        public static String concat(Object... arr) {
                StringBuilder sb = new StringBuilder();
                for (Object s : arr)
                        if (s != null){

                                sb.append( s.toString());}
                        else{
                                sb.append("");
                         }
                return sb.toString();
        }
}