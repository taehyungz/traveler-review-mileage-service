package com.triple.mileage.infrastructure.photo;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.photo.PhotoStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoStoreImpl implements PhotoStore {

    private final PhotoRepository photoRepository;

    @Override
    public void save(Photo photo) {
        photoRepository.save(photo);
    }
}
