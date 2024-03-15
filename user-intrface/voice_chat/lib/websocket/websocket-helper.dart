import 'dart:convert';

import 'package:get_it/get_it.dart';
import 'package:local_storage/local_storage.dart';
import 'package:voice_chat/webrtc/webrtc-helper.dart';
import 'package:voice_chat/websocket/model/websocket_data.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;

class WebSocketHelper {
  //websocket
  late WebSocketChannel _websocketChannel;
  final LocalStorage _localStorage = GetIt.instance<LocalStorage>();
  late WebRTCHelper _webRTCHelper;

  setWebRTCHelper(WebRTCHelper webRTCHelper) {
    _webRTCHelper = webRTCHelper;
  }

  void init(String userId) {
    //websocket
    final wsUrl = Uri.parse('ws://localhost:8082/users/$userId/voice-chat-signaling');
    _websocketChannel = WebSocketChannel.connect(wsUrl);
    webSocketListener();
  }

  void dispose() {
    _websocketChannel.sink.close(status.goingAway);
  }

  void sendOfferToSignalingServer(String jsonSession) {
    var message = getWebsocketDataInstance(WebsocketDataType.OFFER, jsonSession);
    addMessage(message);
  }

  void sendCandidateToSignalingServer(String candidateStr) {
    var message = getWebsocketDataInstance(WebsocketDataType.CANDIDATE, candidateStr);
    addMessage(message);
  }

  WebsocketData getWebsocketDataInstance(WebsocketDataType type, String str) {
    String userId = _localStorage.getLocalStoredUserInfo().userId;
    var message = WebsocketData(type: type, userId: userId, value: str);
    return message;
  }


  void addMessage(WebsocketData message) {
    //print(message);
    _websocketChannel.sink.add(message);
  }

  //websocket
  webSocketListener() async {
      //websocket
      await _websocketChannel.ready;
      _websocketChannel.stream.listen((message) {
      clientMessageDispatcher(message);
    },
      onDone: () {
        // Handle the connection closing
        print('Connection closed');
      },
      onError: (error) {
        // Handle any errors that occur
        print('Error: $error');
      },
      cancelOnError: true,);
  }

  void clientMessageDispatcher(String message) async {
    Map<String, dynamic> map =  json.decode(message);
    WebsocketData data = WebsocketData.fromJson(map);

    if (data.type == WebsocketDataType.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION) {
      _webRTCHelper.setRemoteDescription(data.value).then((_) {
        // _webRTCHelper.createAndSendTheAnswer();
      });
    }
  }

  void sendAnswerSdp(String answerSdp) async {
    var message = getWebsocketDataInstance(WebsocketDataType.ANSWER_SDP, answerSdp);
    addMessage(message);
  }
}