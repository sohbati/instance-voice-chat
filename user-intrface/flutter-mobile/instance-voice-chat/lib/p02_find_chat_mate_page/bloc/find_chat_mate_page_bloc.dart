import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:formz/formz.dart';
import 'package:instance_voice_chat/login/login.dart';
import 'package:routing_repository/routing_repository.dart';

part 'find_chat_mate_page_event.dart';
part 'find_chat_mate_page_state.dart';

class FindChatMateBloc extends Bloc<FindChatMateEvent, FindChatMateState> {
  FindChatMateBloc({
    required RoutingRepository routingRepository,
  })  : _routingRepository = routingRepository,
        super(const FindChatMateState()) {
    on<LoginUsernameChanged>(_onUsernameChanged);
    on<LoginPasswordChanged>(_onPasswordChanged);
    on<LoginSubmitted>(_onSubmitted);
  }

  final RoutingRepository _routingRepository;

  void _onUsernameChanged(LoginUsernameChanged event, Emitter<FindChatMateState> emit,) {
    final username = Username.dirty(event.username);
    emit(
      state.copyWith(
        username: username,
        isValid: Formz.validate([state.password, username]),
      ),
    );
  }

  void _onPasswordChanged(
    LoginPasswordChanged event,
    Emitter<FindChatMateState> emit,
  ) {
    final password = Password.dirty(event.password);
    emit(
      state.copyWith(
        password: password,
        isValid: Formz.validate([password, state.username]),
      ),
    );
  }

  Future<void> _onSubmitted(
    LoginSubmitted event,
    Emitter<FindChatMateState> emit,
  ) async {
    if (state.isValid) {
      emit(state.copyWith(status: FormzSubmissionStatus.inProgress));
      try {
        await _routingRepository.logIn(
          username: state.username.value,
          password: state.password.value,
        );
        emit(state.copyWith(status: FormzSubmissionStatus.success));
      } catch (_) {
        emit(state.copyWith(status: FormzSubmissionStatus.failure));
      }
    }
  }
}
