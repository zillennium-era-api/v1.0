package com.eracambodia.era.exception;

public class ImageExtensionException extends RuntimeException {
    public ImageExtensionException(String message){
        super(message);
    }
    public ImageExtensionException(String message,Throwable throwable){
        super(message,throwable);
    }
}
