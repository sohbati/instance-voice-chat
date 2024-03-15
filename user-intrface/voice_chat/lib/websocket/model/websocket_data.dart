import 'dart:convert';

class WebsocketData {
  WebsocketDataType type;
  String userId;
  String value;

  WebsocketData({required this.type,required this.userId, required this.value});

  factory WebsocketData.fromJson(Map<String, dynamic> json) {
    WebsocketDataType getWebsocketDataType(String typeStr) {
      switch (typeStr) {
        case 'OFFER':
          return WebsocketDataType.OFFER;
        case 'CANDIDATE':
          return WebsocketDataType.CANDIDATE;
        case 'SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION':
          return WebsocketDataType.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION;
        default:
          return WebsocketDataType.NONE;
      }
    }

    return WebsocketData(
      type: getWebsocketDataType(json['type']),
      userId: json['userId'],
      value: json['value'],
    );
  }

  @override
  String toString() {
    String typeStr = type.name;
    String encodedJson = jsonEncode(value);
    return '{"type": "$typeStr","userId": "$userId", "value": $encodedJson}';
  }
}
enum WebsocketDataType {
  NONE("NONE"),
  OFFER("OFFER"),
  CANDIDATE("CANDIDATE"),
  SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION("SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION"),
  ANSWER_SDP("ANSWER_SDP");

  final String name;
  const WebsocketDataType(this.name);
}