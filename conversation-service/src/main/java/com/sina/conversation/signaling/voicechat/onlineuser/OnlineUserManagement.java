package com.sina.conversation.signaling.voicechat.onlineuser;

import com.sina.conversation.signaling.voicechat.model.UserPartneringStatus;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class OnlineUserManagement {
    private static Map<java.lang.String, ReadyToVoiceChatUserModel> onlineUsers = new HashMap();

    public List<ReadyToVoiceChatUserModel> getNotPartneredUsersList() {
        return onlineUsers.values().stream().filter(p ->
                p.getPartneringStatus() == UserPartneringStatus.NOT_PARTNERED).collect(Collectors.toList());
    }
    public void addOfferSDP(java.lang.String userId, java.lang.String offerSDP) {
        ReadyToVoiceChatUserModel user = onlineUsers.get(userId);
        if (user == null) {
            user = addUserToList(userId);
        }
        if(isUserPartnered(user)) {
            Log.warn("This could be an error that a partnered user's device is sending a offerSDP to the server");
            Log.warn("By this time I am going to ignore this offerSDP, hope for the future");
            return;
        }
        user.setUserOfferSessionDescriptionProtocol(offerSDP);
        onlineUsers.put(userId, user);
        Log.info(java.lang.String.format("user with id:%s added to the online users", user.getUserId()));
    }

    public void addCandidate(java.lang.String userId, java.lang.String candidate) {
        ReadyToVoiceChatUserModel user = onlineUsers.get(userId);
        if(user == null) {
            user = addUserToList(userId);
        }
        user.addCandidate(candidate);
        Log.info(java.lang.String.format("A candidate added to user : %s", userId));
        if(Log.isDebugEnabled()) {
            Log.debug(java.lang.String.format("Candidate: %s", candidate));
        }
    }

    public void setUserPartneringStatus(java.lang.String userId, UserPartneringStatus status) {
        onlineUsers.get(userId).setPartneringStauts(status);
    }
    private boolean isUserPartnered(ReadyToVoiceChatUserModel user) {
        if (onlineUsers.get(user.getUserId()) != null) {
            return onlineUsers.get(user.getUserId()).getPartneringStatus() != UserPartneringStatus.NOT_PARTNERED;
        }
        return false;
    }


    private ReadyToVoiceChatUserModel addUserToList(java.lang.String userId) {
        synchronized (onlineUsers) {
            ReadyToVoiceChatUserModel userModel = new ReadyToVoiceChatUserModel();
            userModel.setUserId(userId);
            onlineUsers.put(userId, userModel);
            return userModel;
        }
    }

    public ReadyToVoiceChatUserModel getReadyToVoiceChatUser(java.lang.String userId) {
        return onlineUsers.get(userId);
    }

    public void removeUser(java.lang.String userId) {
        Log.info(java.lang.String.format("User %s remove from online users!", userId));
        onlineUsers.remove(userId);
    }

    public Set<java.lang.String> getAllOnlineUsers() {
        return onlineUsers.keySet();
    }

    public void setAnswerSdp(java.lang.String userId, java.lang.String answerSdp) {
        ReadyToVoiceChatUserModel user = onlineUsers.get(userId);
        if (user == null) {
            //Todo what if user does not exists in the list?
            return;
        }
        user.setUserAnswerSessionDescriptionProtocol(answerSdp);

    }
}
