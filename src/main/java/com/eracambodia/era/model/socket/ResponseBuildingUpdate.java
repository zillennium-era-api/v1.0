package com.eracambodia.era.model.socket;

public class ResponseBuildingUpdate {
    private int code=200;
    private String message="Success.";
    private BuildingUpdate data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BuildingUpdate getData() {
        return data;
    }

    public void setData(BuildingUpdate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBuildingUpdate{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
