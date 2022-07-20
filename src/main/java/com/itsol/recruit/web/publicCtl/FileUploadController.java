package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.FilePdf;
import com.itsol.recruit.file_util.FileUploadUtil;
import com.itsol.recruit.service.FilePdfService;
import com.itsol.recruit.web.vm.FilePdfVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class FileUploadController {
    private final FilePdfService filePdfService;

    public FileUploadController(FilePdfService filePdfService) {
        this.filePdfService = filePdfService;
    }


    @PostMapping("/uploadFile")
    public ResponseEntity<FilePdf> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
        FilePdfVM filePdfVM = new FilePdfVM();
        filePdfVM.setFile_name(fileName);
        filePdfVM.setDownload_uri("/downloadFile/" + filecode);
        filePdfVM.setSize_url(size);
        filePdfVM.setData(multipartFile.getBytes());
        filePdfService.addFilePdf(filePdfVM);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}