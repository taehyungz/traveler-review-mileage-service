package com.triple.mileage.infrastructure.photo;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.review.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PhotoStoreImplTest {

    @Mock
    PhotoRepository photoRepository;

    @InjectMocks
    PhotoStoreImpl sut;

    @Test
    void Each_photos_attached_on_review() {
        List<String> attachedPhotoIds = List.of("first", "second", "third");
        Review review = new Review("reviewId", "userId", "placeId");

        sut.save(attachedPhotoIds, review);

        verify(photoRepository, times(attachedPhotoIds.size())).save(any(Photo.class));
    }
}