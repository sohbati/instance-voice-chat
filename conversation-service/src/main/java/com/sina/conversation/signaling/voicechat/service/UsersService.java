package com.sina.conversation.signaling.voicechat.service;

import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UsersService {

    @Inject
    OnlineUserManagement onlineUserManagement;
    public List<String> getAllOnlineUsers() {
        Set<String> users = onlineUserManagement.getAllOnlineUsers();
        Log.info(String.format("users list size :%s and list items are:" , users.stream().toList().toString()));
        return users.stream().toList();
    }

    public ReadyToVoiceChatUserModel getUserInfo(String userId) {
        return onlineUserManagement.getReadyToVoiceChatUser(userId);
    }
}
