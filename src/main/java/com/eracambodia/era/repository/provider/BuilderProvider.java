package com.eracambodia.era.repository.provider;

import org.apache.ibatis.jdbc.SQL;

public class BuilderProvider {
    public String buildingStatusUpdate(){
        return new SQL(){
            {

            }
        }.toString();
    }
}
