package com.eracambodia.era.configuration.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.eracambodia.era.model.socket.BuildingUpdate;
import com.eracambodia.era.model.socket.ResponseBuildingUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ChangeStatusEvent {

    private final SocketIOServer server;

    //@Autowired
    public ChangeStatusEvent(SocketIOServer server) {
        this.server = server;
        server.start();
    }

    //@OnEvent(value = "listenChange")
    public void onEvent(SocketIOClient client, AckRequest request, BuildingUpdate data) {
        ResponseBuildingUpdate responseBuildingUpdate=new ResponseBuildingUpdate();
        responseBuildingUpdate.setData(data);
        server.getBroadcastOperations().sendEvent("eventChange", responseBuildingUpdate);
    }
    /*@OnConnect
    public void onConnect(SocketIOClient socketIOClient){
        System.out.println("Connected");
    }*/
}
