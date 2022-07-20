package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.Image;
import com.itsol.recruit.repository.ImageRepository;
import com.itsol.recruit.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ResponseDTO uploadImage(MultipartFile file) throws IOException {
        Image image = Image.builder().name(file.getOriginalFilename())
                                     .type(file.getContentType())
                                     .image(ImageUtils.compressImage(file.getBytes())).build();
        imageRepository.save(image);
        return new ResponseDTO("Upload image success");
    }

    public Image getImageByName(String name) throws IOException{
        Image image=imageRepository.findImageByName(name);
        image.setImage(ImageUtils.decompressImage(image.getImage()));
        return image;
    }
}
