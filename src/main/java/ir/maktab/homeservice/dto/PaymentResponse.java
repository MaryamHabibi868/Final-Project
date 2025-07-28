package ir.maktab.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

        private Long id;

        private BigDecimal suggestedPrice;

        private Long specialistId;

        private Long orderId;

        private String message = "Offer paid successfully";
    }
