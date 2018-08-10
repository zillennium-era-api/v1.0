package com.eracambodia.era.exception;

import com.eracambodia.era.configuration.fileupload.FileStorageProperty;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message){
        super(message);
    }
    public FileStorageException(String message,Throwable throwable){
        super(message,throwable);
    }
}
