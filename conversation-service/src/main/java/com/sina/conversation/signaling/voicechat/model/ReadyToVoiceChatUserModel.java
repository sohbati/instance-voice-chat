package com.sina.conversation.signaling.voicechat.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ReadyToVoiceChatUserModel {
           //ReadyToVoiceChatUsersModel
    private java.lang.String userId;
    private java.lang.String userOfferSessionDescriptionProtocol;
    private java.lang.String userAnswerSessionDescriptionProtocol;
    private List<java.lang.String> candidates = new ArrayList<>();

    private UserPartneringStatus partneringStatus = UserPartneringStatus.NOT_PARTNERED;

    public static ReadyToVoiceChatUserModel newWithSessionDescription(java.lang.String userId, java.lang.String sessionDescription) {
        ReadyToVoiceChatUserModel model = new ReadyToVoiceChatUserModel();
        model.setUserId(userId);
        model.setUserOfferSessionDescriptionProtocol(sessionDescription);
        return model;
    }
    public java.lang.String getUserId() {
        return userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getUserOfferSessionDescriptionProtocol() {
        return userOfferSessionDescriptionProtocol;
    }

    public void setUserOfferSessionDescriptionProtocol(java.lang.String userOfferSessionDescriptionProtocol) {
        this.userOfferSessionDescriptionProtocol = userOfferSessionDescriptionProtocol;
    }

    public List<java.lang.String> getCandidates() {
        return candidates;
    }

    public void addCandidate(java.lang.String candidate) {
        candidates.add(candidate);
    }

    public UserPartneringStatus getPartneringStatus() {
        return partneringStatus;
    }

    public void setPartneringStauts(UserPartneringStatus partnered) {
        partneringStatus = partnered;
    }

    public java.lang.String getUserAnswerSessionDescriptionProtocol() {
        return userAnswerSessionDescriptionProtocol;
    }

    public void setUserAnswerSessionDescriptionProtocol(java.lang.String userAnswerSessionDescriptionProtocol) {
        this.userAnswerSessionDescriptionProtocol = userAnswerSessionDescriptionProtocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReadyToVoiceChatUserModel other = (ReadyToVoiceChatUserModel) obj;
        return userId == other.userId;
    }

    @Override
    public java.lang.String toString() {
        return "user={" +
                "userId='" + userId + '\'' +
                ", userOfferSessionDescriptionProtocol='" + userOfferSessionDescriptionProtocol + '\'' +
                ", userAnswerSessionDescriptionProtocol='" + userAnswerSessionDescriptionProtocol + '\'' +
                ", candidates=" + candidates +
                '}';
    }
}
