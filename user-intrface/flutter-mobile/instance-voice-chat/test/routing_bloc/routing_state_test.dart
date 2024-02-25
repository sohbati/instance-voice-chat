// ignore_for_file: prefer_const_constructors
import 'package:flutter_test/flutter_test.dart';
import 'package:instance_voice_chat/routing_bloc/bloc/routing_bloc.dart';
import 'package:mocktail/mocktail.dart';
import 'package:user_repository/user_repository.dart';

class MockUser extends Mock implements User {}

void main() {
  group('RoutingState', () {
    group('RoutingState.unknown', () {
      test('supports value comparisons', () {
        expect(
          RoutingState.unknown(),
          RoutingState.unknown(),
        );
      });
    });

    group('RoutingState.authenticated', () {
      test('supports value comparisons', () {
        final user = MockUser();
        expect(
          RoutingState.authenticated(user),
          RoutingState.authenticated(user),
        );
      });
    });

    group('RoutingState.unauthenticated', () {
      test('supports value comparisons', () {
        expect(
          RoutingState.unauthenticated(),
          RoutingState.unauthenticated(),
        );
      });
    });
  });
}
