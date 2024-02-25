import 'dart:async';

enum RoutingStatus { unknown, authenticated, unauthenticated, MAIN_PAGE  }

class RoutingRepository {
  final _controller = StreamController<RoutingStatus>();

  Stream<RoutingStatus> get status async* {
    await Future<void>.delayed(const Duration(seconds: 1));
    yield RoutingStatus.MAIN_PAGE;
    yield* _controller.stream;
  }

  Future<void> logIn({required String username, required String password,}) async {
    await Future.delayed(
      const Duration(milliseconds: 300),
      () => _controller.add(RoutingStatus.authenticated),
    );
  }

  void logOut() {
    _controller.add(RoutingStatus.unauthenticated);
  }

  void dispose() => _controller.close();
}
