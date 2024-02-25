part of 'routing_bloc.dart';

class RoutingState extends Equatable {
  const RoutingState._({
    this.status = RoutingStatus.unknown,
    this.user = User.empty,
  });

  const RoutingState.unknown() : this._();

  const RoutingState.authenticated(User user) : this._(status: RoutingStatus.authenticated, user: user);

  const RoutingState.unauthenticated() : this._(status: RoutingStatus.unauthenticated);

  const RoutingState.mainPage() : this._(status: RoutingStatus.MAIN_PAGE);

  final RoutingStatus status;
  final User user;

  @override
  List<Object> get props => [status, user];
}
