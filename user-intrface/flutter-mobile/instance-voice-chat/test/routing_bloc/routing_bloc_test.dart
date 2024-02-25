import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:instance_voice_chat/routing_bloc/bloc/routing_bloc.dart';
import 'package:mocktail/mocktail.dart';
import 'package:routing_repository/routing_repository.dart';
import 'package:user_repository/user_repository.dart';

class _MockRoutingRepository extends Mock
    implements RoutingRepository {}

class _MockUserRepository extends Mock implements UserRepository {}

void main() {
  const user = User('id');
  late RoutingRepository routingRepository;
  late UserRepository userRepository;

  setUp(() {
    routingRepository = _MockRoutingRepository();
    when(
      () => routingRepository.status,
    ).thenAnswer((_) => const Stream.empty());
    userRepository = _MockUserRepository();
  });

  group('RoutingBloc', () {
    test('initial state is RoutingState.unknown', () {
      final routingBloc = RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      );
      expect(routingBloc.state, const RoutingState.unknown());
      routingBloc.close();
    });

    blocTest<RoutingBloc, RoutingState>(
      'emits [unauthenticated] when status is unauthenticated',
      setUp: () {
        when(() => routingRepository.status).thenAnswer(
          (_) => Stream.value(RoutingStatus.unauthenticated),
        );
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.unauthenticated(),
      ],
    );

    blocTest<RoutingBloc, RoutingState>(
      'emits [authenticated] when status is authenticated',
      setUp: () {
        when(() => routingRepository.status).thenAnswer(
          (_) => Stream.value(RoutingStatus.authenticated),
        );
        when(() => userRepository.getUser()).thenAnswer((_) async => user);
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.authenticated(user),
      ],
    );
  });

  group('RoutingStatusChanged', () {
    blocTest<RoutingBloc, RoutingState>(
      'emits [authenticated] when status is authenticated',
      setUp: () {
        when(
          () => routingRepository.status,
        ).thenAnswer((_) => Stream.value(RoutingStatus.authenticated));
        when(() => userRepository.getUser()).thenAnswer((_) async => user);
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.authenticated(user),
      ],
    );

    blocTest<RoutingBloc, RoutingState>(
      'emits [unauthenticated] when status is unauthenticated',
      setUp: () {
        when(
          () => routingRepository.status,
        ).thenAnswer((_) => Stream.value(RoutingStatus.unauthenticated));
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.unauthenticated(),
      ],
    );

    blocTest<RoutingBloc, RoutingState>(
      'emits [unauthenticated] when status is authenticated but getUser fails',
      setUp: () {
        when(
          () => routingRepository.status,
        ).thenAnswer((_) => Stream.value(RoutingStatus.authenticated));
        when(() => userRepository.getUser()).thenThrow(Exception('oops'));
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.unauthenticated(),
      ],
    );

    blocTest<RoutingBloc, RoutingState>(
      'emits [unauthenticated] when status is authenticated '
      'but getUser returns null',
      setUp: () {
        when(
          () => routingRepository.status,
        ).thenAnswer((_) => Stream.value(RoutingStatus.authenticated));
        when(() => userRepository.getUser()).thenAnswer((_) async => null);
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.unauthenticated(),
      ],
    );

    blocTest<RoutingBloc, RoutingState>(
      'emits [unknown] when status is unknown',
      setUp: () {
        when(
          () => routingRepository.status,
        ).thenAnswer((_) => Stream.value(RoutingStatus.unknown));
      },
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      expect: () => const <RoutingState>[
        RoutingState.unknown(),
      ],
    );
  });

  group('RoutingLogoutRequested', () {
    blocTest<RoutingBloc, RoutingState>(
      'calls logOut on routingRepository '
      'when RoutingLogoutRequested is added',
      build: () => RoutingBloc(
        routingRepository: routingRepository,
        userRepository: userRepository,
      ),
      act: (bloc) => bloc.add(RoutingLogoutRequested()),
      verify: (_) {
        verify(() => routingRepository.logOut()).called(1);
      },
    );
  });
}
