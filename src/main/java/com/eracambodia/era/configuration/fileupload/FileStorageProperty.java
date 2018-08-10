package com.eracambodia.era.configuration.fileupload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperty {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public String toString() {
        return "FileStorageProperty{" +
                "uploadDir='" + uploadDir + '\'' +
                '}';
    }
}
