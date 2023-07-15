package com.itsol.recruit.service;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;

public interface TextBookService {
    PageExtDTO<TextBookDTO> searchAll(String keyword,int pageNumber,int pageSize,String sortBy,String sortDir);
    void createTextBook(TextBookDTO textBookDTO);
    void delete(String id);
}
