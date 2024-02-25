import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:routing_repository/routing_repository.dart';
import 'package:user_repository/user_repository.dart';

part 'routing_event.dart';
part 'routing_state.dart';

class RoutingBloc extends Bloc<RoutingEvent, RoutingState> {
  RoutingBloc({
    required RoutingRepository routingRepository,
    required UserRepository userRepository,
  })  : _routingRepository = routingRepository,
        _userRepository = userRepository,
        super(const RoutingState.unknown()) {
    //event
    on<_RoutingStatusChanged>(_onRoutingStatusChanged);
    on<RoutingLogoutRequested>(_onRoutingLogoutRequested);
    //stream
    _routingStatusSubscription = _routingRepository.status.listen(
      (status) => add(_RoutingStatusChanged(status)),
    );
  }

  final RoutingRepository _routingRepository;
  final UserRepository _userRepository;
  late StreamSubscription<RoutingStatus> _routingStatusSubscription;

  @override
  Future<void> close() {
    _routingStatusSubscription.cancel();
    return super.close();
  }

  Future<void> _onRoutingStatusChanged(
    _RoutingStatusChanged event,
    Emitter<RoutingState> emit,
  ) async {
    switch (event.status) {
      case RoutingStatus.unauthenticated: return emit(const RoutingState.unauthenticated());
      case RoutingStatus.authenticated:
        final user = await _tryGetUser();
        return emit(
          user != null
              ? RoutingState.authenticated(user)
              : const RoutingState.unauthenticated(),
        );
      case RoutingStatus.unknown: return emit(const RoutingState.unknown());

      case RoutingStatus.MAIN_PAGE: return emit(const RoutingState.mainPage());
    }
  }
  void _onRoutingLogoutRequested(
    RoutingLogoutRequested event,
    Emitter<RoutingState> emit,
  ) {
    _routingRepository.logOut();
  }

  Future<User?> _tryGetUser() async {
    try {
      final user = await _userRepository.getUser();
      return user;
    } catch (_) {
      return null;
    }
  }
}
