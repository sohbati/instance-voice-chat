import 'dart:convert';

import 'package:flutter_webrtc/flutter_webrtc.dart';
import 'package:sdp_transform/sdp_transform.dart';
// import 'package:voice_chat/infrastructure/PermissionRequest.dart';
import 'package:voice_chat/websocket/websocket-helper.dart';

import '../infrastructure/log-helper.dart';

class WebRTCHelper {

  late WebSocketHelper _webSocketHelper;

  setWebSocketHelper(WebSocketHelper webSocketHelper) {
    _webSocketHelper = webSocketHelper;
  }

  //webrtc
  late RTCPeerConnection _peerConnection;
  late MediaStream _localStream;

  late final RTCVideoRenderer _localRenderer;
  late final RTCVideoRenderer _remoteRenderer;

  RTCVideoRenderer getLocalRenderer() {
    return _localRenderer;
  }

  RTCVideoRenderer getRemoteRenderer() {
    return _remoteRenderer;
  }

  //webrtc
  void initRenderer() async {
    _localRenderer = RTCVideoRenderer();
    _remoteRenderer = RTCVideoRenderer();
    await _localRenderer.initialize();
    await _remoteRenderer.initialize();
  }

  RTCPeerConnection getRTCPeerConnection() {
    return _peerConnection;
  }

  //webrtc
  Future<RTCPeerConnection>createWebRtcPeerConnection() async {
    final Map<String, dynamic> configuration = {
      'iceServers': [
        // {'urls': 'stun:stun.l.google.com:19302'},
        {'urls': 'stun:194.5.175.224:3478'},
        // Add more ICE servers if needed
      ]
    };
    final Map<String, dynamic> offerSdpConstraints = {
      "mandatory": {
        "OfferToReceiveAudio": true,
        "OfferToReceiveVideo": false,
      },
      "optinal":[],
    };
    _localStream = await _getUserMedia();
    RTCPeerConnection pc = await createPeerConnection(
        configuration, offerSdpConstraints);
    // pc.addStream(_localStream);
    // final videoTrack = _localStream.getVideoTracks().first;
    // pc.addTrack(videoTrack, _localStream);
    _localStream?.getTracks().forEach((track) { pc?.addTrack(track, _localStream!); });

    pc.onIceCandidate = (e) {
      if (e.candidate != null) {
        String candidate = (json.encode({
          'candidate': e.candidate.toString(),
          'sdpMid': e.sdpMid.toString(),
          'sdpMLineIndex': e.sdpMLineIndex,
        }));
        _webSocketHelper.sendCandidateToSignalingServer(candidate);
       // LogHelper.log(candidate);
      }
    };

    pc.onIceConnectionState = (state) {
      if(state == RTCIceConnectionState.RTCIceConnectionStateConnected) {
        LogHelper.log("CONNECTED **************************");
        _webSocketHelper.sendConnected();
      }

      if(state == RTCIceConnectionState.RTCIceConnectionStateNew) {
        LogHelper.log("NEW ****************************");
      }
      LogHelper.log(state.toString());
    };


    // pc.onAddStream = (stream) {
    //   LogHelper.log('addStream:' + stream.id);
    //   _remoteRenderer.srcObject = stream;
    // };
    pc.onTrack = (event) {
      _remoteRenderer.srcObject = event.streams.first;
      // if (event.track.kind == 'video') {
      //   // Handle incoming video track
      //   // You can render the video track using a VideoRendererWidget or any other method
      // } else if (event.track.kind == 'audio') {
      //   // Handle incoming audio track
      // }
    };
    _peerConnection = pc;
    LogHelper.log("The peer connection created");
    return pc;
  }

  void setPeerConnection(RTCPeerConnection peerConnection) {
    _peerConnection = peerConnection;
  }

  //webrtc
  _getUserMedia() async {
    final Map<String, dynamic> mediaConstraints = {
      'audio': true,
      // 'video': {
      //   'facingMode': 'user'
      // }
    };
    // await requestPermissions();
    MediaStream stream = await navigator.mediaDevices.getUserMedia(mediaConstraints);

    _localRenderer.srcObject = stream;
    // _localRenderer.mirror = true;
    return stream;
  }

  //webrtc
  void createOfferAndSendToSignalingServer() async {
    RTCSessionDescription description =
      await getRTCPeerConnection().createOffer({'offerToReceiveVideo': 1});
    var sdp = description.sdp;
    var session = parse(sdp!);
    String jsonSession = json.encode(session);
    _webSocketHelper.sendOfferToSignalingServer(jsonSession);
    // LogHelper.log('Offer sdp sent to signaling server');
    _peerConnection.getIceConnectionState().then((value) => LogHelper.log("RTC ICE STATE :$value"));

    getRTCPeerConnection().setLocalDescription(description!);
  }

  //webrtc
  Future<void> setOfferRemoteDescription(String remoteSessionDescription) async {
    String jsonString = remoteSessionDescription.replaceFirst('\"setup\":\"actpass\"', '\"setup\":\"active\"');
    dynamic session = await jsonDecode('$jsonString');
    String sdp = write(session, null);
    RTCSessionDescription description = new RTCSessionDescription(sdp, 'offer');
    await _peerConnection.setRemoteDescription(description).then((_) {
      LogHelper.log('Offer set in  Remote description successfully');
      _peerConnection.getIceConnectionState().then((value) => LogHelper.log("RTC ICE STATE :$value"));

      createAndSendTheAnswer();
    });
  }

  Future<void> setAnswerRemoteDescription(String remoteSessionDescription) async {
    // String jsonString = remoteSessionDescription.replaceFirst('\"setup\":\"actpass\"', '\"setup\":\"active\"');
    dynamic session = await jsonDecode('$remoteSessionDescription');
    String sdp = write(session, null);
    RTCSessionDescription description = new RTCSessionDescription(sdp, 'answer');
    await _peerConnection.setRemoteDescription(description).then((_) {
      LogHelper.log('Answer Remote description set successfully');
      _peerConnection.getIceConnectionState().then((value) => LogHelper.log("RTC ICE STATE :$value"));
    });
  }


  //webrtc
  void createAndSendTheAnswer() async{
    RTCSessionDescription description = await _peerConnection.createAnswer({'offerToReceiveVideo': 1});
    String sdp = description.sdp!;
    var session = parse(sdp);
    var answerSdp = json.encode(session);
    _peerConnection.setLocalDescription(description);
    _webSocketHelper.sendAnswerSdp(answerSdp);
    LogHelper.log("Answer sdp sent");
    _peerConnection.getIceConnectionState().then((value) => LogHelper.log("RTC ICE STATE :$value"));

  }

  //webrtc
  void setCandidate(String candidateJsonString) async {
    dynamic session = await jsonDecode(candidateJsonString);
    LogHelper.log(session['candidate']);
    dynamic candidate =
      new RTCIceCandidate(session['candidate'], session['sdpMid'], session['sdpMLineIndex']);

    LogHelper.log(_peerConnection.getIceConnectionState().toString());
    _peerConnection.getIceConnectionState().then((value) => LogHelper.log(value.toString()));
    await _peerConnection.addCandidate(candidate).then((_) {
        LogHelper.log('Candidate set successfully');
    }).onError((error, stackTrace) {
       LogHelper.log(error.toString());
       print(stackTrace);
    });

  }

  void dispose() {
    //webrtc
    _localRenderer.dispose();
    _remoteRenderer.dispose();
    _peerConnection.close();

  }
}