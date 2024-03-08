import 'dart:convert';

import 'package:voice_chat/websocket/model/websocket_data.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:web_socket_channel/status.dart' as status;

class WebSocketHelper {
  //websocket
  late WebSocketChannel _websocketChannel;

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
    var message = new WebsocketData(WebsocketDataType.OFFER, jsonSession);
    addMessage(WebsocketDataType.OFFER, message);
  }

  void sendCandidateToSignalingServer(String candidateStr) {
    var message = new WebsocketData(WebsocketDataType.CANDIDATE, candidateStr);
    addMessage(WebsocketDataType.CANDIDATE, message);
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