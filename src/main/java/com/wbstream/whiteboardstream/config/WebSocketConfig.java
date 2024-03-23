package com.wbstream.whiteboardstream.config;

import com.wbstream.whiteboardstream.websockets.WSHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler(), "/streampath")
                .setAllowedOrigins("http://localhost:8000");
    }

    @Bean
    public WSHandler wsHandler(){
        return new WSHandler();
    }
}
