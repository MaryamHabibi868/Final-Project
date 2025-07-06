package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    public static final String FEEDBACK_Range = "feedback_range";
    public static final String FEEDBACK_Type = "feedback_type";

    @Column(name = FeedBack.FEEDBACK_Range, nullable = false)
    private Integer feedbackRange;

    @Column(name = FeedBack.FEEDBACK_Type)
    private String feedbackType;

    @OneToOne
    private OrderOfCustomer orderOfCustomer;
}
