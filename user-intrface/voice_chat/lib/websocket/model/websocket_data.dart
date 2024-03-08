class WebsocketData {
  WebsocketDataType type;
  String? value;

  WebsocketData(this.type, this.value);

  @override
  String toString() {
    return 'WebsocketData={type: "$type", value: "$value"}';
  }
}
enum WebsocketDataType {OFFER, CANDIDATE}
