import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:instance_voice_chat/p02_find_chat_mate_page/p02_find_chat_mate_page.dart';
import 'package:routing_repository/routing_repository.dart';

class FindChatMate extends StatelessWidget {
  const FindChatMate({super.key});

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => const FindChatMate());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: true,
      body: Padding(
        padding: const EdgeInsets.all(12),
        child: BlocProvider(
          create: (context) {
            return FindChatMateBloc(
              routingRepository:
                  RepositoryProvider.of<RoutingRepository>(context),
            );
          },
          child: const FindChatMateForm(),
        ),
      ),
    );
  }
}
