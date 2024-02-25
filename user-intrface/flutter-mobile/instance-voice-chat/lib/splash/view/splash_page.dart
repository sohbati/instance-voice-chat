import 'package:device_info_plus/device_info_plus.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:user_repository/user_repository.dart';
import 'package:local_storage/local_storage.dart';
import 'package:api_service/api_service.dart';

class SplashPage extends StatelessWidget {
   SplashPage({super.key
    });

  final LocalStorage _localStorage = GetIt.instance<LocalStorage>();
  final ApiService _apiService = GetIt.instance<ApiService>();

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) =>  SplashPage());
  }

  @override
  Widget build(BuildContext context) {
    checkIfDeviceUsedAppBefore();

    return const Scaffold(
      body: Center(child: CircularProgressIndicator()),
    );
  }

  void checkIfDeviceUsedAppBefore() {
    Future<void>.delayed(const Duration(seconds: 20));
    UserInfoLocalStoreModel? userInfo = _localStorage.getLocalStoredUserInfo();
    print('call backend');
    if (userInfo != null) {
      var info = _apiService.userInfo(userInfo.userId);
      print(info);
    }else {

    }
  }
}
