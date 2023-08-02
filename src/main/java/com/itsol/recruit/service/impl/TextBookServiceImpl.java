package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;
import com.itsol.recruit.entity.TextBook;
import com.itsol.recruit.repository.TextBookRepoCustom;
import com.itsol.recruit.repository.TextBookRepository;
import com.itsol.recruit.service.TextBookService;
import com.itsol.recruit.utils.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextBookServiceImpl implements TextBookService {

    @Autowired
    private TextBookRepository textBookRepository;

    @Autowired
    private TextBookRepoCustom textBookRepoCustom;

    @Override
    public PageExtDTO<TextBookDTO> searchAll(String keyword, int pageNumber, int pageSize,String sortBy,String sortDir) {
        return textBookRepoCustom.search(keyword,pageNumber,pageSize,sortBy,sortDir);
    }

    @Override
    public void createTextBook(TextBookDTO textBookDTO) {
        TextBook textBook = new TextBook();
        BeanUtils.copyProperties(textBookDTO,textBook);
        textBookRepository.save(textBook);
    }

    @Override
    public void delete(String id) {
        Optional<TextBook> textBook = textBookRepository.findById(id);
        if(!textBook.isPresent())
            throw new BusinessException("999","Sổ văn bản không tồn tại");
        textBookRepository.delete(textBook.get());
    }
}
