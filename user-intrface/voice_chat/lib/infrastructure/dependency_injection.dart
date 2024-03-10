import 'package:get_it/get_it.dart';
import 'package:local_storage/local_storage.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:api_service/api_service.dart';


final GetIt getIt = GetIt.instance;

Future<void> setupDependencies() async {
  //SharedPreferences
  final sharedPreferences = await SharedPreferences.getInstance();
  getIt.registerSingleton<SharedPreferences>(sharedPreferences);

  //Local Storage
  final localStorage = await LocalStorage();
  getIt.registerSingleton<LocalStorage>(localStorage);

  //api service
  final apiService = await ApiService();
  getIt.registerSingleton<ApiService>(apiService);

}