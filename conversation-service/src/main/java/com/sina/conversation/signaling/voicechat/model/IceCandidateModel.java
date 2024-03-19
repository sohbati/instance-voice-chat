package com.sina.conversation.signaling.voicechat.model;

public class IceCandidateModel {

    private String iceCandidateJsonStr;
    private boolean isSentToPartner;

    public String getIceCandidateJsonStr() {
        return iceCandidateJsonStr;
    }

    public void setIceCandidateJsonStr(String iceCandidateJsonStr) {
        this.iceCandidateJsonStr = iceCandidateJsonStr;
    }

    public boolean isSentToPartner() {
        return isSentToPartner;
    }

    public void setSentToPartner(boolean sentToPartner) {
        isSentToPartner = sentToPartner;
    }
}
