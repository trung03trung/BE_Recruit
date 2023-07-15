package com.itsol.recruit.repository;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.entity.TextBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TextBookRepository extends JpaRepository<TextBook,String> {

}
