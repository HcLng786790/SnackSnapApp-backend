package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

/**
 * File Controller
 *
 * @author huuduc
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadFile(file));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {

        Resource resource = fileService.downloadFile(fileName);
        String originalFilename = resource.getFilename().split("_", 2)[1];
        String mediaType = URLConnection.guessContentTypeFromName(originalFilename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
                .body(resource);
    }

    @PostMapping("/uploadToDrive")
    public ResponseEntity<?> uploadToDrive(@RequestParam("file") MultipartFile file) throws IOException {

        //Check file null
        if (file.isEmpty()){
            return ResponseEntity.ok("File is empty");
        }

        //Transfer Multipart file to File
        File tempFile = File.createTempFile(file.getName(),null);
        file.transferTo(tempFile);

        return ResponseEntity.ok(this.fileService.uploadFileToDrive(tempFile));

    }
}
