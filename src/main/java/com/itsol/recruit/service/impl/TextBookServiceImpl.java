package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;
import com.itsol.recruit.repository.TextBookRepository;
import com.itsol.recruit.service.TextBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextBookServiceImpl implements TextBookService {

    @Autowired
    private TextBookRepository textBookRepository;

    @Override
    public PageExtDTO searchAll(String keyword, int pageNumber, int pageSize) {
        return textBookRepository.searchAll(keyword,pageNumber,pageSize);
    }

    @Override
    public void createTextBook(TextBookDTO textBookDTO) {

    }

    @Override
    public void delete(String id) {

    }
}
