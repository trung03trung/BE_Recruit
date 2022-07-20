package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findImageByName(String name);
}
