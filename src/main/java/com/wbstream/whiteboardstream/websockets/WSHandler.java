package com.wbstream.whiteboardstream.websockets;

import com.wbstream.whiteboardstream.exceptions.WebSocketException;
import com.wbstream.whiteboardstream.pojo.Board;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.repo.BoardRepo;
import com.wbstream.whiteboardstream.repo.BoardUsersRepo;
import com.wbstream.whiteboardstream.service.BoardService;
import com.wbstream.whiteboardstream.util.CommonUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WSHandler extends AbstractWebSocketHandler {


    private static final HashMap<Long, List<WebSocketSession>> boardSessions = new HashMap<>();
    private static final HashMap<String, Long> sessionBoardId = new HashMap<>();
    public WSHandler() {

    }

    @Autowired
    private BoardService boardService;




    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        String query = uri.getQuery();
        String[] paramKeyValues = query.split("&");

        HashMap<String, String> params = CommonUtil.getParamMap(paramKeyValues);
        Boolean create = Boolean.valueOf(params.get("create"));
        Long userId = Long.valueOf(params.get("userId"));
        Long boardId = Long.valueOf(params.get("boardId"));

        Board b = boardService.getBoard(boardId);
        if(b == null){

            String message = "Board not found";
            JSONObject res = new JSONObject().put("message", message);
            session.sendMessage(new TextMessage(res.toString()));
            session.close();
            throw new WebSocketException(message);
        }
        List<User> users = boardService.getBoardUsers(boardId);
        boolean validUser = false;
        for(User u : users){
            if(u.getId().equals(userId)){
                validUser = true;
                break;
            }
        }
        if(!validUser){

            String message = "User not part of the board";
            JSONObject res = new JSONObject().put("message", message);
            session.sendMessage(new TextMessage(res.toString()));
            session.close();
            throw new WebSocketException(message);
        }

        boardSessions.putIfAbsent(boardId, new ArrayList<>());
        boardSessions.get(boardId).add(session);

        sessionBoardId.put(session.getId(), boardId);





//        System.out.println(session.getAttributes().get("userId"));
//        System.out.println(session.getAttributes().get("boardId"));
        System.out.println("Connection established :: ");
        JSONObject res = new JSONObject().put("message", "Connection established");
        session.sendMessage(new TextMessage(res.toString()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        if(sessionBoardId.get(session.getId()) == null){
            System.out.println("baord not founf in map");
            return;
        }
        Long boardId = sessionBoardId.get(session.getId());
        System.out.println(boardId+" found in memory");

        List<WebSocketSession> list = boardSessions.get(boardId);
        for(WebSocketSession sess : list){
            if(session.getId().equals(sess.getId())){
                continue;
            }
            System.out.println("Sebding message to clieny");
            if(sess.isOpen()){
                sess.sendMessage(new TextMessage(message.getPayload()));
            }
        }

//        String payload = message.getPayload();
//        System.out.println("Message received from client");
//        session.sendMessage(new TextMessage(payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection closed :: ");
        // Handle session close actions
    }
}
