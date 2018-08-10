package com.eracambodia.era.service;

import com.eracambodia.era.configuration.fileupload.FileStorageProperty;
import com.eracambodia.era.exception.FileNotFoundException;
import com.eracambodia.era.exception.FileStorageException;
import com.eracambodia.era.exception.ImageExtensionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private Path path;

    @Autowired
    public FileStorageService(FileStorageProperty fileStorageProperties) {
        this.path = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.path);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String imageExtension[]={"jpg","png","jpge"};
        String extension=com.google.common.io.Files.getFileExtension(file.getOriginalFilename());
        for(int i=0;i<imageExtension.length;i++){
            if(!extension.equalsIgnoreCase(imageExtension[i])){
                return null;
            }
        }
        String fileName = UUID.randomUUID()+StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri()) ;
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}
