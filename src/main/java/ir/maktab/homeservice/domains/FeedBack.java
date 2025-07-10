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
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBack extends BaseEntity<Long> {

    public static final String TABLE_NAME = "feedback";
    public static final String FEEDBACK_RANGE = "feedback_range";
    public static final String FEEDBACK_DESCRIPTION = "feedback_description";
    public static final String OFFER_OF_SPECIALIST_ID = "offer_of_specialist_id";

    @Column(name = FeedBack.FEEDBACK_RANGE, nullable = false)
    private Integer feedbackRange;

    @Column(name = FeedBack.FEEDBACK_DESCRIPTION)
    private String feedbackDescription;

    @JoinColumn(name = FeedBack.OFFER_OF_SPECIALIST_ID, nullable = false)
    @OneToOne
    private OfferOfSpecialist offerOfSpecialist;
}
