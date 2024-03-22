package com.sina.conversation.signaling.voicechat.partnermatching;

import com.sina.conversation.infrastructure.pubsub.MessagePublisher;
import com.sina.conversation.signaling.voicechat.model.IceCandidateModel;
import com.sina.conversation.signaling.voicechat.model.UserPartneringStatus;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class PartnerMatcherScheduler {

    @Inject
    MessagePublisher messagePublisher;

    @Inject
    OnlineUserManagement onlineUserManagement;
    private Random random = new Random();


    @Scheduled(every = "5s")
    public void partnerMatchFinder() {
        List<ReadyToVoiceChatUserModel> list = new ArrayList<>(onlineUserManagement.getNotPartneredUsersList());
        if (!list.isEmpty())
           Log.info(java.lang.String.format("Ready to connect users = %s", list.size()));
        while (!list.isEmpty()) {
            if (list.size() < 2) {
                break;
            }
            int partnerFirstIndex = 0;
            int partnerSecondIndex = random.nextInt(list.size() - 1) + 1;

            ReadyToVoiceChatUserModel first = list.get(partnerFirstIndex);
            ReadyToVoiceChatUserModel second = list.get(partnerSecondIndex);

            list.remove(first);
            list.remove(second);
            onlineUserManagement.setUserPartneringStatus(
                    first.getUserId(), UserPartneringStatus.WAITING_TO_GET_PARTNERED);
            onlineUserManagement.setUserPartneringStatus(
                    second.getUserId(), UserPartneringStatus.WAITING_TO_GET_PARTNERED);

            matchTowPartnersMessage(first.getUserId(), second.getUserId());

        }
    }

    public void matchTowPartnersMessage(java.lang.String firstUser, java.lang.String secondUser) {
        ReadyToVoiceChatUserModel firstUserModel = onlineUserManagement.getReadyToVoiceChatUser(firstUser);
        ReadyToVoiceChatUserModel secondUserModel = onlineUserManagement.getReadyToVoiceChatUser(secondUser);

        setAsPartners(firstUserModel, secondUserModel);
        messagePublisher.sendOfferSdpToSecondUser(
                secondUserModel.getUserId(), firstUserModel.getUserOfferSessionDescriptionProtocol());
        Log.info(String.format("Match users[first:%s, second:%s]",
                firstUserModel.getUserId(), secondUserModel.getUserId()));

    }

    private void setAsPartners(ReadyToVoiceChatUserModel firstUser, ReadyToVoiceChatUserModel secondUser) {
        firstUser.setPartnerUserId(secondUser.getUserId());
        secondUser.setPartnerUserId(firstUser.getUserId());
    }

    @Scheduled(every = "5s")
    public void sendIceCandidateToPartnerScheduler() {
        List<ReadyToVoiceChatUserModel> partneredUsers = onlineUserManagement.getAllPartneredUsers();

        for (ReadyToVoiceChatUserModel userModel: partneredUsers) {
            List<IceCandidateModel> candidateModelList =
                    userModel.getCandidates().stream().filter(
                            p -> !p.isSentToPartner()).toList();
            for(IceCandidateModel iceCandidateModel: candidateModelList) {
                messagePublisher.sendCandidateToPartnerUser(
                        userModel.getPartnerUserId(), iceCandidateModel.getIceCandidateJsonStr());
                iceCandidateModel.setSentToPartner(true);
            }
        }
    }
}
