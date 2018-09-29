package com.eracambodia.era.module;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.eracambodia.era.model.socket.BuildingUpdate;
import com.eracambodia.era.model.socket.ResponseBuildingUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*@Component*/
public class BuildingStatusModule {
    private SocketIONamespace socketIONamespace;
    /*@Autowired*/
    public BuildingStatusModule(SocketIOServer socketIOServer){
        socketIOServer.start();
        socketIONamespace=socketIOServer.addNamespace("/era");
        socketIONamespace.addConnectListener(onConnected());
        socketIONamespace.addDisconnectListener(onDisconnected());
        socketIONamespace.addEventListener("listenChange", BuildingUpdate.class,onStatusChange());
    }
    private DataListener<BuildingUpdate> onStatusChange(){
        return new DataListener<BuildingUpdate>() {
            @Override
            public void onData(SocketIOClient socketIOClient, BuildingUpdate buildingUpdate , AckRequest ackRequest) throws Exception {
                ResponseBuildingUpdate responseBuildingUpdate=new ResponseBuildingUpdate();
                responseBuildingUpdate.setData(buildingUpdate);
                socketIONamespace.getBroadcastOperations().sendEvent("eventChange", responseBuildingUpdate);
            }
        };
    }
    private ConnectListener onConnected(){
        return new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("onConnected");
            }
        };
    }
    private DisconnectListener onDisconnected(){
        return new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("onDisconnected");
            }
        };
    }
}
