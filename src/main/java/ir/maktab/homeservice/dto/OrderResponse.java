package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import lombok.*;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {


    private Long id;

    private String description;

    private BigDecimal suggestedPrice;

    private ZonedDateTime startDate;

    private OrderStatus status;

    private Long addressId;

    private Long homeServiceId;

    private Long customerId;


    public OrderResponse(Offer offer) {
        Order order = offer.getOrderInformation();
        this.id = order.getId();
        this.description = order.getDescription();
        this.suggestedPrice = order.getSuggestedPrice();
        this.startDate = order.getStartDate();
        this.homeServiceId = order.getHomeService().getId();
        this.customerId = order.getCustomer().getId();

        if (offer.getStatus() == OfferStatus.DONE
                || offer.getStatus() == OfferStatus.PAID
                || offer.getStatus() == OfferStatus.ACCEPTED) {
            this.status = order.getStatus();
            this.addressId = order.getAddress().getId();
        }
    }
}
