package com.sina.conversation.signaling.voicechat.onlineuser;

import com.sina.conversation.infrastructure.pubsub.MessagePublisher;
import com.sina.conversation.signaling.voicechat.model.UserPartneringStatus;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class OnlineUserManagement {
    private static Map<java.lang.String, ReadyToVoiceChatUserModel> onlineUsers = new HashMap<>();

    @Inject
    MessagePublisher messagePublisher;

    public List<ReadyToVoiceChatUserModel> getNotPartneredUsersList() {
        return onlineUsers.values().stream().filter(p ->
                p.getPartneringStatus() == UserPartneringStatus.NOT_PARTNERED).toList();
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
        Log.info(java.lang.String.format("User removed from online users: %s", userId));
        onlineUsers.remove(userId);
        Log.info(String.format("Online users: %s", onlineUsers.size()));
    }

    public Set<java.lang.String> getAllOnlineUsers() {
        return onlineUsers.keySet();
    }

    public List<ReadyToVoiceChatUserModel> getAllPartneredUsers() {
        return onlineUsers.values().stream().filter(
                userModel -> userModel.getPartneringStatus().equals(UserPartneringStatus.PARTNERED))
                .toList();
    }

    public void setAnswerSdp(java.lang.String secondUserId, java.lang.String answerSdp) {
        ReadyToVoiceChatUserModel secondUserModel = onlineUsers.get(secondUserId);
        if (secondUserModel == null) {
            //Todo what if secondUserModel does not exists in the list?
            return;
        }
        secondUserModel.setUserAnswerSessionDescriptionProtocol(answerSdp);
        messagePublisher.sendAnswerSdpToFirstUser(secondUserId);

        try {
            //TODO  need to provide better solution to get both partners successful connection status
            // be patient til the answer sdp settle in first partner
            Thread.sleep(4000); // 2 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        secondUserModel.setPartneringStauts(UserPartneringStatus.PARTNERED);
        onlineUsers.get(secondUserModel.getPartnerUserId()).setPartneringStauts(UserPartneringStatus.PARTNERED);
        //after this status the sendCandidate scheduler will pick up users and send the candidates to other partner
        try {
            //be patient til the answer sdp settle in first partner
            Thread.sleep(3000); // 2 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void changeUserStatusToConnected(String userId) {
        ReadyToVoiceChatUserModel userModel = onlineUsers.get(userId);
        userModel.setPartneringStauts(UserPartneringStatus.CONNECTED);
    }
}
