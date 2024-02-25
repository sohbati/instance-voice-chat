import 'package:flutter/widgets.dart';
import 'package:instance_voice_chat/app.dart';
import 'package:instance_voice_chat/infrastructure/dependency_injection.dart';
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await setupDependencies();

  runApp(const App());
}
