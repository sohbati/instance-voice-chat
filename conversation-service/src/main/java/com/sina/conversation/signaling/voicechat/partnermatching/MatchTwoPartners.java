package com.sina.conversation.signaling.voicechat.partnermatching;

import com.sina.conversation.infrastructure.pubsub.MessagePublisher;
import com.sina.conversation.signaling.voicechat.model.PartneredUsersModel;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MatchTwoPartners {

    private static Map<java.lang.String, PartneredUsersModel> partneredUsers = new HashMap<>();

    @Inject
    OnlineUserManagement onlineUserManagement;
    @Inject
    MessagePublisher messagePublisher;
    public void makeTwoUserAsPartners(java.lang.String firstUser, java.lang.String secondUser) {
        ReadyToVoiceChatUserModel firstUserModel = onlineUserManagement.getReadyToVoiceChatUser(firstUser);
        ReadyToVoiceChatUserModel secondUserModel = onlineUserManagement.getReadyToVoiceChatUser(secondUser);

        messagePublisher.sendOfferSdpToSecondUser(
                secondUserModel.getUserId(), firstUserModel.getUserOfferSessionDescriptionProtocol());

        addToPartneredList(firstUserModel, secondUserModel);
    }

    private void addToPartneredList(ReadyToVoiceChatUserModel firstUser, ReadyToVoiceChatUserModel secondUser) {
        PartneredUsersModel model = new PartneredUsersModel();
        model.setOfferedUser(firstUser.getUserId());
        model.setAnsweredUser(secondUser.getUserId());

//        partneredUsers.put()
    }
}
