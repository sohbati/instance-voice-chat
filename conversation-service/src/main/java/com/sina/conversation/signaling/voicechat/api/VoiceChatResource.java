package com.sina.conversation.signaling.voicechat.api;

import com.sina.conversation.signaling.voicechat.service.VoiceChatSignalingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/voice-chat-signaling")
@Consumes("application/json")
@Produces("application/json")
public class VoiceChatResource {

    @Inject
    VoiceChatSignalingService voiceChatSignalingService;

    @POST
    @Path("/add-user-offer-session-description")
    @Operation(summary = "Figma Code: api#????", description = "Add User offer to available list of users")
    public String getUserInfo(String userId, String sessionDescription) {
        voiceChatSignalingService.setUserOfferSessionDescription(userId, sessionDescription);
        return userId;
    }


}
