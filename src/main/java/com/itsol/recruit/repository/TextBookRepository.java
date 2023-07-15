package com.itsol.recruit.repository;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.entity.TextBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TextBookRepository extends JpaRepository<TextBook,String> {
    @Query(value="select count(a.id) as totalElements,a.id, a.name, a.start_number startNumber" +
            "a.apply_date applyDate" +
            " from text_book a where a.name= ?1 or a.manage_user=?1 " +
            "offset ?2 limit ?3",
            nativeQuery = true)
    PageExtDTO searchAll(String keyword, int pageNumber ,int pageSize);
}
