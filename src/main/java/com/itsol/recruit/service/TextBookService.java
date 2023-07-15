package com.itsol.recruit.service;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;

public interface TextBookService {
    PageExtDTO searchAll(String keyword,int pageNumber,int pageSize);
    void createTextBook(TextBookDTO textBookDTO);
    void delete(String id);
}
