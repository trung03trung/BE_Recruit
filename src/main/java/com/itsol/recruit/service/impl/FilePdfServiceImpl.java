package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.FilePdf;
import com.itsol.recruit.repository.FilePdfRepository;
import com.itsol.recruit.service.FilePdfService;
import com.itsol.recruit.web.vm.FilePdfVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FilePdfServiceImpl implements FilePdfService {

    public final FilePdfRepository filePdfRepository;

    public FilePdfServiceImpl(FilePdfRepository filePdfRepository) {
        this.filePdfRepository = filePdfRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO> addFilePdf(FilePdfVM filePdfvm) {
        try {
            FilePdf filePdf = new FilePdf();
            filePdf.setFile_name(filePdfvm.getFile_name());
            filePdf.setDownload_uri(filePdfvm.getDownload_uri());
            filePdf.setSize_url(filePdfvm.getSize_url());
            filePdf.setData(filePdfvm.getData());
            filePdfRepository.save(filePdf);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.OK, "ok"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
