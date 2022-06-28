package com.triple.mileage.domain.photo;

import com.triple.mileage.domain.BasicEntity;
import com.triple.mileage.domain.review.Review;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo extends BasicEntity {
    @Id
    @Column(length = 36)
    private String photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
