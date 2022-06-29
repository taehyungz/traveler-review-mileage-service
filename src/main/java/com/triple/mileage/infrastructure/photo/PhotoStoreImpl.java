package com.triple.mileage.infrastructure.photo;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PhotoStoreImpl implements PhotoStore {

    private final PhotoRepository photoRepository;

    @Override
    public void save(List<String> attachedPhotoIds, Review savedReview) {
        for (String photoId : attachedPhotoIds) {
            Photo photo = new Photo(photoId, savedReview);
            savedReview.addPhoto(photo);
            photoRepository.save(photo);
        }
    }
}
