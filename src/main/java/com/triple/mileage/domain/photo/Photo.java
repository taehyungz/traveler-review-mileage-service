package com.triple.mileage.domain.photo;

import com.triple.mileage.domain.BasicEntity;
import com.triple.mileage.domain.review.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BasicEntity {
    @Id
    @Column(length = 36)
    private String photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    public Photo(
            String photoId,
            Review review) {
        this.photoId = photoId;
        this.review = review;
    }

    public void attatchTo(Review review) {
        this.review = review;
    }
}
