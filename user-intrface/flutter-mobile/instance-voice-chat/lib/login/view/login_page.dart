import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:routing_repository/routing_repository.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => const LoginPage());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(12),
        child: BlocProvider(
          create: (context) {
            return LoginBloc(
              routingRepository:
                  RepositoryProvider.of<RoutingRepository>(context),
            );
          },
          child: const LoginForm(),
        ),
      ),
    );
  }
}
