import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:instance_voice_chat/p01_main_page/p01_main_page.dart';
import 'package:instance_voice_chat/routing_bloc/routing_bloc.dart';
import 'package:instance_voice_chat/home/home.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:instance_voice_chat/splash/splash.dart';
import 'package:routing_repository/routing_repository.dart';
import 'package:user_repository/user_repository.dart';

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  late final RoutingRepository _routingRepository;
  late final UserRepository _userRepository;

  @override
  void initState() {
    super.initState();
    _routingRepository = RoutingRepository();
    _userRepository = UserRepository();
  }

  @override
  void dispose() {
    _routingRepository.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return RepositoryProvider.value(
      value: _routingRepository,
      child: BlocProvider(
        create: (_) => RoutingBloc(
          routingRepository: _routingRepository,
          userRepository: _userRepository,
        ),
        child: const AppView(),
      ),
    );
  }
}

class AppView extends StatefulWidget {
  const AppView({super.key});

  @override
  State<AppView> createState() => _AppViewState();
}


class _AppViewState extends State<AppView> {
  final GlobalKey<NavigatorState> _navigatorKey = GlobalKey<NavigatorState>();

  NavigatorState get _navigator => _navigatorKey.currentState!;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: _navigatorKey,
      builder: (context, child) {
        return BlocListener<RoutingBloc, RoutingState>(
          listener: (context, state) {
            switch (state.status) {
              case RoutingStatus.authenticated: navigateToHomePage();break;
              case RoutingStatus.unauthenticated: navigateToUnAuthenticated();break;
              case RoutingStatus.MAIN_PAGE: navigateToMainPage();break;
              case RoutingStatus.unknown: break;
            }
          },
          child: child,
        );
      },
      onGenerateRoute: (_) {
        return SplashPage.route();
      },
    );
  }

  void navigateToMainPage() {
    _navigator.pushAndRemoveUntil<void>(
      MainPage.route(),
          (route) => false,
    );
  }

 @Deprecated('The home page of the project changed to main_page')
  void navigateToHomePage() {
    _navigator.pushAndRemoveUntil<void>(
      HomePage.route(),
          (route) => false,
    );
  }
  void navigateToUnAuthenticated(){
      _navigator.pushAndRemoveUntil<void>(
        LoginPage.route(),
            (route) => false,
      );
  }
}
