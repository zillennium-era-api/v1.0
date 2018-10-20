package com.eracambodia.era.configuration.socketio;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/*@org.springframework.context.annotation.Configuration*/
public class SocketServer {
    @Value("0.0.0.0")
    private String host;

    @Value("9090")
    private Integer port;

    //@Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }

    //@Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer ssrv) {
        return new SpringAnnotationScanner(ssrv);
    }

}
