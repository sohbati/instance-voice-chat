package com.sina.conversation.signaling.voicechat.service;

import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
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
        return users.stream().toList();
    }

    public ReadyToVoiceChatUserModel getUserInfo(String userId) {
        return onlineUserManagement.getReadyToVoiceChatUser(userId);
    }
}
