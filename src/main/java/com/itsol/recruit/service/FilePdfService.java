package com.itsol.recruit.service;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.FilePdf;
import com.itsol.recruit.web.vm.FilePdfVM;
import org.springframework.http.ResponseEntity;

public interface FilePdfService {
    public FilePdf findById(Long id);
    public ResponseEntity<ResponseDTO>  addFilePdf(FilePdfVM filePdfVM);
}
