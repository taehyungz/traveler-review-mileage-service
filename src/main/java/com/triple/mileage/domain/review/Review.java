package com.triple.mileage.domain.review;

import com.triple.mileage.domain.BasicEntity;
import com.triple.mileage.domain.photo.Photo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "review",
        indexes = {
                @Index(name="idx_review_user_id", columnList = "userId"),
                @Index(name="idx_review_place_id", columnList = "placeId"),
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BasicEntity {
    @Id
    @Column(length = 36)
    private String reviewId;

    @Column(nullable = false, length = 36)
    private String userId;

    @Column(nullable = false, length = 36)
    private String placeId;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<Photo> photoList = new ArrayList<>();

    public Review(
            String reviewId,
            String userId,
            String placeId) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.placeId = placeId;
    }

    public void addPhoto(Photo photo) {
        photo.attatchTo(this);
        this.photoList.add(photo);
    }
}
