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
       case 'SEND_ANSWER_SDP_TO_FIRST_USER':
          return WebsocketDataType.SEND_ANSWER_SDP_TO_FIRST_USER;
       case 'SEND_CANDIDATE_TO_PARTNER_USER':
          return WebsocketDataType.SEND_CANDIDATE_TO_PARTNER_USER;
       case 'CONNECTED':
          return WebsocketDataType.CONNECTED;
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
  ANSWER_SDP("ANSWER_SDP"),
  SEND_ANSWER_SDP_TO_FIRST_USER("SEND_ANSWER_SDP_TO_FIRST_USER"),
  SEND_CANDIDATE_TO_PARTNER_USER("SEND_CANDIDATE_TO_PARTNER_USER"),
  CONNECTED("CONNECTED");

  final String name;
  const WebsocketDataType(this.name);
}