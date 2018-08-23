package com.eracambodia.era.configuration.fileupload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperty {
    private String uploadDir;
    private String uploadBuildingDir;

    public String getUploadBuildingDir() {
        return uploadBuildingDir;
    }

    public void setUploadBuildingDir(String uploadBuildingDir) {
        this.uploadBuildingDir = uploadBuildingDir;
    }

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
                ", uploadBuildingDir='" + uploadBuildingDir + '\'' +
                '}';
    }
}
