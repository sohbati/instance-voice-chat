import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:instance_voice_chat/p01_main_page/p01_main_page.dart';
import 'package:routing_repository/routing_repository.dart';

class MainPage extends StatelessWidget {
  const MainPage({super.key});

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => const MainPage());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: true,
      body: Padding(
        padding: const EdgeInsets.all(12),
        child: BlocProvider(
          create: (context) {
            return MainPageBloc(
              routingRepository:
                  RepositoryProvider.of<RoutingRepository>(context),
            );
          },
          child: const MainPageForm(),
        ),
      ),
    );
  }
}
