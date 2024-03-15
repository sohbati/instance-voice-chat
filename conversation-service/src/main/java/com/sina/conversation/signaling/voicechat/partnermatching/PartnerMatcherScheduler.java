package com.sina.conversation.signaling.voicechat.partnermatching;

import com.sina.conversation.infrastructure.pubsub.MessagePublisher;
import com.sina.conversation.signaling.voicechat.model.UserPartneringStatus;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;

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
        List<ReadyToVoiceChatUserModel> list = onlineUserManagement.getNotPartneredUsersList();
        Log.info(java.lang.String.format("Ready to connect users = %s", list.size()));
        while (list.size() >= 0) {
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

            messagePublisher.matchTowPartnersMessage(first.getUserId(), second.getUserId());

        }
    }
}
