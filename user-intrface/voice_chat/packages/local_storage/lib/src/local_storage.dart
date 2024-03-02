import 'dart:async';
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:user_repository/src/models/user_info_local_store_model.dart';
import 'package:get_it/get_it.dart';
class LocalStorage {
  /// {@macro local_storage}
  LocalStorage();

  static const String USER_INFO_LOCAL_STORE = 'USER_INFO';

  final SharedPreferences _plugin= GetIt.instance<SharedPreferences>();

  UserInfoLocalStoreModel? getLocalStoredUserInfo() {
    String? userInfoStr = _plugin.getString(USER_INFO_LOCAL_STORE);
    if (userInfoStr == null || userInfoStr.isEmpty) {
      return null;
    }
    Map<String, dynamic> jsonMap = json.decode(userInfoStr!);
    UserInfoLocalStoreModel userInfo = UserInfoLocalStoreModel.fromJson(jsonMap);
    return userInfo;
  }

  Future<void> _setLocalStoredUserInfo(UserInfoLocalStoreModel userInfo) async {
    Map<String, dynamic> userInfoMap = userInfo.getMap();
    String usreInfoStr = jsonEncode(userInfoMap);

    _plugin.setString(USER_INFO_LOCAL_STORE, usreInfoStr);
  }
}
