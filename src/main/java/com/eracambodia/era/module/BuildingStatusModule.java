package com.eracambodia.era.module;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.eracambodia.era.model.api_building_status_update.response.BuildingUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingStatusModule {
    private SocketIONamespace socketIONamespace;
    @Autowired
    public BuildingStatusModule(SocketIOServer socketIOServer){
        socketIOServer.start();
        socketIONamespace=socketIOServer.addNamespace("/update");
        socketIONamespace.addConnectListener(onConnected());
        socketIONamespace.addDisconnectListener(onDisconnected());
        socketIONamespace.addEventListener("update", BuildingUpdate.class,onChatRetrieve());

    }
    private DataListener<BuildingUpdate> onChatRetrieve(){
        return new DataListener<BuildingUpdate>() {
            @Override
            public void onData(SocketIOClient socketIOClient, BuildingUpdate buildingUpdate, AckRequest ackRequest) throws Exception {
                System.out.println("ZZZZZ" + socketIOClient.getSessionId().toString() + "ZZZZZ" + buildingUpdate);
                socketIONamespace.getBroadcastOperations().sendEvent("update", buildingUpdate);
            }
        };
    }
    private ConnectListener onConnected(){
        return new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                HandshakeData handshakeData=socketIOClient.getHandshakeData();
                System.out.println("XXXXX"+socketIOClient.getSessionId().toString()+"XXXXX"+handshakeData.getUrl());
            }
        };
    }
    private DisconnectListener onDisconnected(){
        return new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("YYYYY" + socketIOClient.getSessionId().toString());
            }
        };
    }
    public void broadcastEvent(String event, Object data){
        this.socketIONamespace.getBroadcastOperations().sendEvent(event,data);
    }
}
