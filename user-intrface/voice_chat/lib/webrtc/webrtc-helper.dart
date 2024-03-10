import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_webrtc/flutter_webrtc.dart';
import 'package:sdp_transform/sdp_transform.dart';
import 'package:voice_chat/websocket/websocket-helper.dart';

class WebRTCHelper {


  late WebSocketHelper _webSocketHelper;

  //webrtc
  bool _offer = false;
  late RTCPeerConnection _peerConnection;
  late MediaStream _localStream;

  final _localRenderer = new RTCVideoRenderer();
  final _remoteRenderer = new RTCVideoRenderer();
  final sdpController = TextEditingController();

  void setWebsocketHelper(WebSocketHelper webSocketHelper) {
    _webSocketHelper = webSocketHelper;
  }

  //webrtc
  void initRenderer() async {
    await _localRenderer.initialize();
    await _remoteRenderer.initialize();
  }

  RTCPeerConnection getRTCPeerConnection() {
    return _peerConnection;
  }

  void setOffer(bool offer) {
    _offer = offer;
  }

  //webrtc
  Future<RTCPeerConnection>createWebRtcPeerConnection() async {
    Map<String, dynamic> configuration = {
      "iceServers": [
        {"url": "stun:stun.l.google.com:19302"}
      ]
    };
    final Map<String, dynamic> offerSdpConstraints = {
      "mandatory": {
        "OfferToReceiveAudio": true,
        "OfferToReceivevideo": true,
      },
      "optinal":[],
    };
    _localStream = await _getUserMedia();
    RTCPeerConnection pc = await createPeerConnection(
        configuration, offerSdpConstraints);
    pc.addStream(_localStream);
    pc.onIceCandidate = (e) {
      if (e.candidate != null) {
        //TODO send candidates, sdpmid, sdpMLineIndex to the server
        String candidate = (json.encode({
          'candidate': e.candidate.toString(),
          'sdpMid': e.sdpMid.toString(),
          'sdpMLineIndex': e.sdpMLineIndex,
        }));
        _webSocketHelper.sendCandidateToSignalingServer(candidate);
        print(candidate);
      }
    };

    pc.onIceConnectionState = (e) {
      print(e);
    };
    pc.onAddStream = (stream) {
      print('addStream:' + stream.id);
      _remoteRenderer.srcObject = stream;
    };
    _peerConnection = pc;
    return pc;
  }

  void setPeerConnection(RTCPeerConnection peerConnection) {
    _peerConnection = peerConnection;
  }

  //webrtc
  _getUserMedia() async {
    final Map<String, dynamic> mediaConstraints = {
      'audio': false,
      'video': {
        'facingMode': 'user'
      }
    };
    MediaStream stream = await navigator.mediaDevices.getUserMedia(mediaConstraints);

    _localRenderer.srcObject = stream;
    _localRenderer.mirror = true;
    return stream;
  }

  //webrtc
  void createOffer() async {
    RTCSessionDescription description =
    await getRTCPeerConnection().createOffer({'offerToReceiveVideo': 1});
    var sdp = description.sdp;
    var session = parse(sdp!);
    String jsonSession = json.encode(session);
    _webSocketHelper.sendOfferToSignalingServer(jsonSession);

    setOffer(true);
    getRTCPeerConnection().setLocalDescription(description!);

  }

  //webrtc
  void _setRemoteDescription() async {
    String jsonString = sdpController.text;
    dynamic session = await jsonDecode('$jsonString');
    String sdp = write(session, null);
    RTCSessionDescription description =
    new RTCSessionDescription(sdp, _offer? 'answer' : 'offer');
    print(description.toMap());
    await _peerConnection.setRemoteDescription(description);
  }

  //webrtc
  void _createAnswer() async {
    RTCSessionDescription description =
    await _peerConnection.createAnswer({'offerToReceiveVideo': 1});
    String sdp = description.sdp!;
    var session = parse(sdp);
    print(json.encode(session));
    _peerConnection.setLocalDescription(description);

  }
  //webrtc
  void _setCandidate() async {
    String jsonString = sdpController.text;
    dynamic session = await jsonDecode(jsonString);
    print(session['candidate']);
    dynamic candidate =
    new RTCIceCandidate(session['candidate'], session['sdpMid'], session['sdpMLineIndex']);
    await _peerConnection.addCandidate(candidate);

  }

  void dispose() {
    //webrtc
    _localRenderer.dispose();
    _remoteRenderer.dispose();
    sdpController.dispose();

  }

}