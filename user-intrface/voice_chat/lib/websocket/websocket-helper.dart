import 'dart:convert';

import 'package:get_it/get_it.dart';
import 'package:local_storage/local_storage.dart';
import 'package:voice_chat/websocket/model/websocket_data.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;

class WebSocketHelper {
  //websocket
  late WebSocketChannel _websocketChannel;
  final LocalStorage _localStorage = GetIt.instance<LocalStorage>();

  void init() {
    //websocket
    final wsUrl = Uri.parse('ws://localhost:8082/users/123/voice-chat-signaling');
    _websocketChannel = WebSocketChannel.connect(wsUrl);
    webSocketListener();
  }

  void dispose() {
    _websocketChannel.sink.close(status.goingAway);
  }

  void sendOfferToSignalingServer(String jsonSession) {
    var message = getWebsocketDataInstance(WebsocketDataType.OFFER, jsonSession);
    addMessage(WebsocketDataType.OFFER, message);
  }

  void sendCandidateToSignalingServer(String candidateStr) {
    var message = getWebsocketDataInstance(WebsocketDataType.CANDIDATE, candidateStr);
    addMessage(WebsocketDataType.CANDIDATE, message);
  }

  WebsocketData getWebsocketDataInstance(WebsocketDataType type, String str) {
    String userId = _localStorage.getLocalStoredUserInfo().userId;
    var message = WebsocketData(type, userId, str);
    return message;
  }


  void addMessage(WebsocketDataType type , WebsocketData message) {
    _websocketChannel.sink.add(message);
  }

  //websocket
  webSocketListener() async {
    //websocket
    await _websocketChannel.ready;
    _websocketChannel.stream.listen((message) {
      _websocketChannel.sink.add('received!');
      print(message);
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
}