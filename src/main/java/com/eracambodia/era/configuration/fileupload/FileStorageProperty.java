package com.eracambodia.era.configuration.fileupload;

import org.springframework.boot.context.properties.ConfigurationProperties;
// for upload file to server
@ConfigurationProperties(prefix = "file")
public class FileStorageProperty {
    private String uploadDir;
    private String uploadBuildingDir;
    private String uploadFile;

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

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    @Override
    public String toString() {
        return "FileStorageProperty{" +
                "uploadDir='" + uploadDir + '\'' +
                ", uploadBuildingDir='" + uploadBuildingDir + '\'' +
                ", uploadFile='" + uploadFile + '\'' +
                '}';
    }
}
