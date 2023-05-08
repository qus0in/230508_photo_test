package io.playdata.photo.service;


import io.playdata.photo.model.Photo;
import io.playdata.photo.model.PhotoForm;
import io.playdata.photo.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    // Photo 엔티티를 PhotoForm DTO로 변환하는 메소드
    private PhotoForm mapToForm(Photo photo) {
        PhotoForm photoForm = new PhotoForm();
        photoForm.setId(photo.getId());
        photoForm.setTitle(photo.getTitle());
        photoForm.setDescription(photo.getDescription());
        photoForm.setImageUrl(photo.getImageUrl());
        return photoForm;
    }

    // 모든 사진 정보를 조회하는 메소드
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    // PhotoForm으로 사진 정보를 추가하는 메소드
    public void addPhoto(PhotoForm photoForm) {
        Photo photo = new Photo();
        photo.setTitle(photoForm.getTitle());
        photo.setDescription(photoForm.getDescription());
        photo.setImageUrl(photoForm.getImageUrl());
        photoRepository.save(photo);
    }

    // id로 사진 정보를 조회하는 메소드
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid photo id: " + id));
    }

    // id로 PhotoForm으로 변환된 사진 정보를 조회하는 메소드
    public PhotoForm getPhotoFormById(Long id) {
        Photo photo = getPhotoById(id);
        return mapToForm(photo);
    }

    // PhotoForm으로 사진 정보를 수정하는 메소드
    public void updatePhoto(PhotoForm photoForm) {
        Photo photo = getPhotoById(photoForm.getId());
        photo.setTitle(photoForm.getTitle());
        photo.setDescription(photoForm.getDescription());
        photo.setImageUrl(photoForm.getImageUrl());
        photoRepository.save(photo);
    }

    // id로 사진 정보를 삭제하는 메소드
    public void deletePhotoById(Long id) {
        photoRepository.deleteById(id);
    }

}
