part of 'routing_bloc.dart';

sealed class RoutingEvent {
  const RoutingEvent();
}

final class _RoutingStatusChanged extends RoutingEvent {
  const _RoutingStatusChanged(this.status);

  final RoutingStatus status;
}

final class RoutingLogoutRequested extends RoutingEvent {}
