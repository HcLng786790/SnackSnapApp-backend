package com.huuduc.snacksnap.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface FileService {

    String uploadFile(MultipartFile file);

    Resource downloadFile(String filename);

    String uploadFileToDrive(File file);

}
