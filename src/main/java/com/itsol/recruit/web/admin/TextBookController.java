package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;
import com.itsol.recruit.service.TextBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@CrossOrigin("*")
public class TextBookController {

    private final TextBookService textBookService;

    public TextBookController(TextBookService textBookService) {
        this.textBookService = textBookService;
    }

    @GetMapping(value = "/text-book")
    public ResponseEntity<PageExtDTO<TextBookDTO>> getAllJob(
            @RequestParam(value = "keyword",required = false)  String keyword,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "dueDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir ){
        return ResponseEntity.ok().body(textBookService.searchAll(keyword,pageNumber,pageSize,sortBy,sortDir));
    }

}
