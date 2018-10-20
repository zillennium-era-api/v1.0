package com.eracambodia.era.model.api_building_files;

import com.eracambodia.era.setting.Default;

import java.util.regex.Pattern;

public class File {
    private String fileName;
    private String path;

    public String getFileName() {
        String[] s=fileName.split(Pattern.quote("."));
        return s[0];
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        if(path!=null){
            return Default.buildingFile+path;
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
