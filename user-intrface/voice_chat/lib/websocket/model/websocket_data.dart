import 'dart:convert';

class WebsocketData {
  WebsocketDataType type;
  String? value;
  String userId;
  WebsocketData(this.type, this.userId, this.value);

  @override
  String toString() {
    String typeStr = type.name;
    String encodedJson = jsonEncode(value);
    return '{"type": "$typeStr","userId": "$userId", "value": $encodedJson}';
  }
}
enum WebsocketDataType {
  OFFER("OFFER"),
  CANDIDATE("CANDIDATE");

  final String name;
  const WebsocketDataType(this.name);
}