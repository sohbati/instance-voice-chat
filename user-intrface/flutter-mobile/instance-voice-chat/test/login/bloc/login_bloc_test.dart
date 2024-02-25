import 'package:bloc_test/bloc_test.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:formz/formz.dart';
import 'package:mocktail/mocktail.dart';
import 'package:routing_repository/routing_repository.dart';

class MockRoutingRepository extends Mock
    implements RoutingRepository {}

void main() {
  late RoutingRepository routingRepository;

  setUp(() {
    routingRepository = MockRoutingRepository();
  });

  group('LoginBloc', () {
    test('initial state is LoginState', () {
      final loginBloc = LoginBloc(
        routingRepository: routingRepository,
      );
      expect(loginBloc.state, const LoginState());
    });

    group('LoginSubmitted', () {
      blocTest<LoginBloc, LoginState>(
        'emits [submissionInProgress, submissionSuccess] '
        'when login succeeds',
        setUp: () {
          when(
            () => routingRepository.logIn(
              username: 'username',
              password: 'password',
            ),
          ).thenAnswer((_) => Future<String>.value('user'));
        },
        build: () => LoginBloc(
          routingRepository: routingRepository,
        ),
        act: (bloc) {
          bloc
            ..add(const LoginUsernameChanged('username'))
            ..add(const LoginPasswordChanged('password'))
            ..add(const LoginSubmitted());
        },
        expect: () => const <LoginState>[
          LoginState(username: Username.dirty('username')),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
          ),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
            status: FormzSubmissionStatus.inProgress,
          ),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
            status: FormzSubmissionStatus.success,
          ),
        ],
      );

      blocTest<LoginBloc, LoginState>(
        'emits [LoginInProgress, LoginFailure] when logIn fails',
        setUp: () {
          when(
            () => routingRepository.logIn(
              username: 'username',
              password: 'password',
            ),
          ).thenThrow(Exception('oops'));
        },
        build: () => LoginBloc(
          routingRepository: routingRepository,
        ),
        act: (bloc) {
          bloc
            ..add(const LoginUsernameChanged('username'))
            ..add(const LoginPasswordChanged('password'))
            ..add(const LoginSubmitted());
        },
        expect: () => const <LoginState>[
          LoginState(
            username: Username.dirty('username'),
          ),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
          ),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
            status: FormzSubmissionStatus.inProgress,
          ),
          LoginState(
            username: Username.dirty('username'),
            password: Password.dirty('password'),
            isValid: true,
            status: FormzSubmissionStatus.failure,
          ),
        ],
      );
    });
  });
}
