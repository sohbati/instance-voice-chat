import 'dart:convert';

import 'package:get_it/get_it.dart';
import 'package:local_storage/local_storage.dart';
import 'package:voice_chat/infrastructure/log-helper.dart';
import 'package:voice_chat/webrtc/webrtc-helper.dart';
import 'package:voice_chat/websocket/model/websocket_data.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;

class WebSocketHelper {
  //websocket
  late WebSocketChannel _websocketChannel;
  final LocalStorage _localStorage = GetIt.instance<LocalStorage>();
  late WebRTCHelper? _webRTCHelper;

  List<WebsocketData> _messages = [];
  bool _isConnected = false;
  bool _isSending = false;

  setWebRTCHelper(WebRTCHelper webRTCHelper) {
    _webRTCHelper = webRTCHelper;
  }

  void connect(String userId) {
    //websocket
    // final wsUrl = Uri.parse('ws://194.5.175.224:8888/ws/voice-chat-signaling/users/$userId/voice-chat-signaling');
    final wsUrl = Uri.parse('ws://194.5.175.224:8082/users/$userId/voice-chat-signaling');
    // final wsUrl = Uri.parse('ws://localhost:8082/users/$userId/voice-chat-signaling');
    // final wsUrl = Uri.parse('ws://localhost:8888/ws/voice-chat-signaling/users/$userId/voice-chat-signaling');
    _websocketChannel = WebSocketChannel.connect(wsUrl);
    print("Connection established: $wsUrl");
    webSocketListener();
    _isConnected = true;
  }

  void dispose() {
    _websocketChannel.sink.close(status.goingAway);
    _isConnected = false;
  }

  bool isOpenSocket() {
    return _isConnected;
  }

  void sendOfferToSignalingServer(String jsonSession) {
    var message = getWebsocketDataInstance(WebsocketDataType.OFFER, jsonSession);
    if (isOpenSocket()) {
      addMessage(message);
      LogHelper.log(WebsocketDataType.OFFER.toString() + ' sent');
    }
  }

  void sendCandidateToSignalingServer(String candidateStr) {
    var message = getWebsocketDataInstance(WebsocketDataType.CANDIDATE, candidateStr);
    addMessage(message);
    LogHelper.log('A ' + WebsocketDataType.CANDIDATE.toString() + ' sent');

  }

  WebsocketData getWebsocketDataInstance(WebsocketDataType type, String str) {
    String userId = _localStorage.getLocalStoredUserInfo().userId;
    var message = WebsocketData(type: type, userId: userId, value: str);
    return message;
  }

  void addMessage(WebsocketData message) {
    _messages.add(message);
    if (_isConnected && !_isSending) {
      sendMessages();
    }
  }

  void sendMessages() {
    if (_messages.isEmpty) {
      _isSending = false;
      return;
    }

    _isSending = true;
    WebsocketData messageToSend = _messages.first;

    // Simulate network request delay
    Future.delayed(Duration(seconds: 2), () {
      // Send message to server via WebSocket
      // print('Sending message to server: $messageToSend');
      _websocketChannel.sink.add(messageToSend);
      _messages.removeAt(0);
      sendMessages();
    });
  }


  //websocket
  webSocketListener() async {
      //websocket
      await _websocketChannel.ready;
      _websocketChannel.stream.listen((message) {
        try {
          clientMessageDispatcher(message);
        }catch(e) {
          LogHelper.log("error in dispatching the message:");
          LogHelper.log(message);
          throw e;
        }
    },
      onDone: () {
        // Handle the connection closing
        LogHelper.log('Connection closed');
      },
      onError: (error) {
        // Handle any errors that occur
        LogHelper.log('Error: $error');
      },
      cancelOnError: true,);
  }

  void clientMessageDispatcher(String message) async {
    // LogHelper.log(printmessage);
    Map<String, dynamic> map =  json.decode(message);
    WebsocketData data = WebsocketData.fromJson(map);
    LogHelper.log(data.type.toString() + " Received");

    if (data.type == WebsocketDataType.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION) {
      LogHelper.log("offer sdp in second user");
      _webRTCHelper?.setOfferRemoteDescription(data.value).then((_) {});
    }else if (data.type == WebsocketDataType.SEND_ANSWER_SDP_TO_FIRST_USER) {
       LogHelper.log("answer sdp in first user");
       _webRTCHelper?.setAnswerRemoteDescription(data.value).then((_) {
       });
    }else if (data.type == WebsocketDataType.SEND_CANDIDATE_TO_PARTNER_USER) {
       LogHelper.log("set candidate in partner user");
       _webRTCHelper?.setCandidate(data.value);
    }
  }

  void sendAnswerSdp(String answerSdp) async {
    var message = getWebsocketDataInstance(WebsocketDataType.ANSWER_SDP, answerSdp);
    addMessage(message);
  }

  void sendConnected() {
    var message = getWebsocketDataInstance(WebsocketDataType.CONNECTED, "");
    addMessage(message);
  }
}