import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:voice_chat/webrtc/webrtc-helper.dart';
import 'package:voice_chat/websocket/websocket-helper.dart';

import 'package:flutter_webrtc/flutter_webrtc.dart';
import 'package:sdp_transform/sdp_transform.dart';

class FindChatPartnerForm extends StatefulWidget {
  const FindChatPartnerForm({super.key});

  @override
  State<FindChatPartnerForm> createState() => _FindChatPartnerFormState();
}

class _FindChatPartnerFormState extends State<FindChatPartnerForm> {

  WebSocketHelper _webSocketHelper = new WebSocketHelper();
  WebRTCHelper _webRTCHelper = new WebRTCHelper();

  @override
  void initState() {

    _webRTCHelper.setWebsocketHelper(_webSocketHelper);

    //webrtc
    _webRTCHelper.initRenderer();
    (() async {
      RTCPeerConnection webRtcPeerConnection = await _webRTCHelper.createWebRtcPeerConnection();
      _webRTCHelper.setPeerConnection(webRtcPeerConnection);
      _createOffer();
    })();
    _webSocketHelper.init();

    super.initState();
  }

  //webrtc
  void _createOffer() async {
    RTCSessionDescription description =
      await _webRTCHelper.getRTCPeerConnection().createOffer({'offerToReceiveVideo': 1});
    var sdp = description.sdp;
    var session = parse(sdp!);
    String jsonSession = json.encode(session);
    _webSocketHelper.sendOfferToSignalingServer(jsonSession);

    _webRTCHelper.setOffer(true);
    _webRTCHelper.getRTCPeerConnection().setLocalDescription(description!);

  }


  @override
  void dispose() {
    //webrtc
    _webRTCHelper.dispose();
    //websocket
    _webSocketHelper.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {

    Color backgroundColor = Colors.lightGreen;
    // _createOffer();

    return Scaffold(
        appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
    title: Text("tt"),
    ),
    body: Center(

      child:  Container(
        margin: EdgeInsets.all(0),
        padding: EdgeInsets.all(0),
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.zero,
          border: Border.all(color: Color(0x4d9e9e9e), width: 1),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.max,

          children: [
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisSize: MainAxisSize.max,
                children: [
                  Center(
                    // alignment: Alignment(-0.2, -0.1),
                    child: Container(
                      margin: EdgeInsets.all(0),
                      padding: EdgeInsets.all(0),
                      width: 230,
                      height: MediaQuery.of(context).size.height,
                      decoration: BoxDecoration(
                        color: Color(0x1f000000),
                        shape: BoxShape.rectangle,
                        borderRadius: BorderRadius.zero,
                        border: Border.all(color: Color(0x4d9e9e9e), width: 1),
                      ),
                      child: Align(
                          alignment: Alignment(0.7, 0.0),
                          child: Container(
                            margin: EdgeInsets.all(0),
                            padding: EdgeInsets.all(0),
                            width: 30,
                            height: 30,
                            decoration: BoxDecoration(
                              color: Color(0xaa5d3838),
                              shape: BoxShape.circle,
                              border: Border.all(
                                  color: Color(0x4d9e9e9e), width: 1),
                            ),
                          )),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.all(0),
                    padding: EdgeInsets.all(0),
                    width: 200,
                    height: 100,
                    decoration: BoxDecoration(
                      color: Color(0x1f000000),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.zero,
                      border: Border.all(color: Color(0x4d9e9e9e), width: 1),
                    ),
                    child: Align(
                      alignment: Alignment(-0.8, 0.0),
                      child: Text(
                        "Hold on! we are looking for a partnet",
                        textAlign: TextAlign.start,
                        overflow: TextOverflow.clip,
                        style: TextStyle(
                          fontWeight: FontWeight.w700,
                          fontStyle: FontStyle.normal,
                          fontSize: 17,
                          color: Color(0xff000000),
                        ),
                      ),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      // _websocketChannel.sink.add("test data to send server");
                      //FindChatMate.route();
                    },
                    child: Text('send Now'),
                    style: ElevatedButton.styleFrom(foregroundColor: Colors.amber),
                  ),
                ],
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.3,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
          ],
        ),
      ),
    ),
    );
  }
}
