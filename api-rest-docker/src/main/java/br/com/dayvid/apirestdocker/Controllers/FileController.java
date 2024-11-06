package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.data.vo.v1.UploadFileResponseVO;
import br.com.dayvid.apirestdocker.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService service;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk");

        var filename = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(filename)
                .toUriString();

        return
                new UploadFileResponseVO(
                        filename, fileDownloadUri, file.getContentType(), file.getSize());
    }
}