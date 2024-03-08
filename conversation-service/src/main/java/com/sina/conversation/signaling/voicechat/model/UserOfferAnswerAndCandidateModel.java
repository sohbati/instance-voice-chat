package com.sina.conversation.signaling.voicechat.model;


import java.util.List;


public class UserOfferAnswerAndCandidateModel {

    private String userId;
    private String userOfferSessionDescription;
    private String userAnswerSessionDescription;
    private List<String> candidates;

    public static UserOfferAnswerAndCandidateModel withOfferAndCandidates(String userId, String offerSdp, List<String> candidates) {
        UserOfferAnswerAndCandidateModel model = new UserOfferAnswerAndCandidateModel();
        model.setUserId(userId);
        model.setUserOfferSessionDescription(offerSdp);
        model.setCandidates(candidates);
        return model;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserOfferAnswerAndCandidateModel other = (UserOfferAnswerAndCandidateModel) obj;
        return userId == other.userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOfferSessionDescription() {
        return userOfferSessionDescription;
    }

    public void setUserOfferSessionDescription(String userOfferSessionDescription) {
        this.userOfferSessionDescription = userOfferSessionDescription;
    }

    public String getUserAnswerSessionDescription() {
        return userAnswerSessionDescription;
    }

    public void setUserAnswerSessionDescription(String userAnswerSessionDescription) {
        this.userAnswerSessionDescription = userAnswerSessionDescription;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }
}
