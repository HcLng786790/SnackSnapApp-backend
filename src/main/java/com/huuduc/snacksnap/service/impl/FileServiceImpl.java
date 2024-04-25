package com.huuduc.snacksnap.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class FileServiceImpl implements FileService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoodeCredentials();


    @Override
    public String uploadFileToDrive(File file) {

        String response;

        try{
            String folderId ="18DcFWvMNzqkUhTmW4WplDLsh7cAYYkhD";
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileData = new com.google.api.services.drive.model.File();
            fileData.setName(file.getName());
            fileData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg",file);
            com.google.api.services.drive.model.File uploadFile = drive.files().create(fileData,mediaContent)
                    .setFields("id").execute();
            String imgUrl = "https://drive.google.com/uc?export=view&id="+uploadFile.getId();
            System.out.println("IMG URL: "+imgUrl);
            file.delete();

            response = imgUrl;

        }catch (Exception e){
            e.printStackTrace();
            throw new NotFoundException(Collections.singletonMap("Upload error: ",e.getMessage()));
        }

        return response;

    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credentials =GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credentials)
                .build();
    }

    private static String getPathToGoodeCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory,"cred.json");
        return filePath.toString();
    }

    @Value("${file.path}")
    private String UPLOAD_DIR;


    @Override
    public String uploadFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        // Generate a unique file name to avoid overwriting existing files
        String fileName = "file" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Create the directory for storing uploaded files if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Copy the uploaded file to the upload directory
        Path targetPath = Paths.get(UPLOAD_DIR, fileName);
        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    @Override
    public Resource downloadFile(String filename) {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Resource resource = null;

        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                new NotFoundException(Collections.singletonMap("fileName", filename));
            }
        } catch (MalformedURLException e) {
            new NotFoundException(Collections.singletonMap("fileName", filename));
        }

        return resource;
    }

}
