import 'package:flutter/material.dart';
import 'package:voice_chat/main_page_form.dart';
import 'package:voice_chat/infrastructure/dependency-injection/dependency_injection.dart';


 void main() async {
   WidgetsFlutterBinding.ensureInitialized();
   await setupDependencies();
   runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Instance Voice Chat',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MainPageForm(title: 'Instance Voice Chat'),
    );
  }
}