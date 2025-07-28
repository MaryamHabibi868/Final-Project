package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity<Long> {

    public static final String TABLE_NAME = "feedbacks";
    public static final String RANGE = "range";
    public static final String DESCRIPTION = "description";
    public static final String OFFER_ID = "offer_id";

    @Column(name = Feedback.RANGE, nullable = false)
    private Integer range;

    @Column(name = Feedback.DESCRIPTION)
    private String description;

    @JoinColumn(name = Feedback.OFFER_ID, nullable = false)
    @OneToOne
    private Offer offer;
}
