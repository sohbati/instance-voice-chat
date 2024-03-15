package com.sina.conversation.signaling.voicechat.model;

import java.util.Objects;

public class PartneredUsersModel {
    private String offeredUser;
    private String answeredUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartneredUsersModel that = (PartneredUsersModel) o;
        return Objects.equals(offeredUser, that.offeredUser) && Objects.equals(answeredUser, that.answeredUser);
    }

    public String getOfferedUser() {
        return offeredUser;
    }

    public void setOfferedUser(String offeredUser) {
        this.offeredUser = offeredUser;
    }

    public String getAnsweredUser() {
        return answeredUser;
    }

    public void setAnsweredUser(String answeredUser) {
        this.answeredUser = answeredUser;
    }
}
