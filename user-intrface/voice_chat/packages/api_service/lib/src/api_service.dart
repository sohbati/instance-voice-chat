import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  static const int HTTP_200_OK = 200;
   static const String _gatewayBaseUrl = 'http://localhost:8888';

  /**
   * GET User info
   */
  Future<Map<String, dynamic>?> userInfo(String userId) async {
     try{
       final response = await http.get(Uri.parse('$_gatewayBaseUrl/users/$userId/userinfo'));
       if (response.statusCode == HTTP_200_OK) {
         Map<String, dynamic> userInfo = json.decode(response.body);
         return userInfo;
       } else {
         return null;
       }
     }catch(e) {
       throw Exception('Failed to load data: $e');
     }
   }

  /**
   * Send User OfferSDP to Signling Server
   */
  Future<Map<dynamic, dynamic>?> sendUserOfferToSignalingServer(String userId, String) async {
    try{
      final response = await http.post(Uri.parse('$_gatewayBaseUrl/users/$userId/offer-sdp'));
      if (response.statusCode == HTTP_200_OK) {
        Map<dynamic, dynamic> userInfo = json.decode(response.body);
        return userInfo;
      } else {
        return null;
      }
    }catch(e) {
      throw Exception('Failed to load data: $e');
    }
  }
}