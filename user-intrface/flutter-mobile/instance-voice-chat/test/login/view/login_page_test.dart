// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mocktail/mocktail.dart';
import 'package:routing_repository/routing_repository.dart';

class MockRoutingRepository extends Mock
    implements RoutingRepository {}

void main() {
  group('LoginPage', () {
    late RoutingRepository routingRepository;

    setUp(() {
      routingRepository = MockRoutingRepository();
    });

    test('is routable', () {
      expect(LoginPage.route(), isA<MaterialPageRoute<void>>());
    });

    testWidgets('renders a LoginForm', (tester) async {
      await tester.pumpWidget(
        RepositoryProvider.value(
          value: routingRepository,
          child: MaterialApp(
            home: Scaffold(body: LoginPage()),
          ),
        ),
      );
      expect(find.byType(LoginForm), findsOneWidget);
    });
  });
}
