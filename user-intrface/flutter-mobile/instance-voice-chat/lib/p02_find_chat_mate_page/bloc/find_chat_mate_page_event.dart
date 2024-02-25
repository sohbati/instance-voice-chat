part of 'find_chat_mate_page_bloc.dart';

sealed class FindChatMateEvent extends Equatable {
  const FindChatMateEvent();

  @override
  List<Object> get props => [];
}

final class LoginUsernameChanged extends FindChatMateEvent {
  const LoginUsernameChanged(this.username);

  final String username;

  @override
  List<Object> get props => [username];
}

final class LoginPasswordChanged extends FindChatMateEvent {
  const LoginPasswordChanged(this.password);

  final String password;

  @override
  List<Object> get props => [password];
}

final class LoginSubmitted extends FindChatMateEvent {
  const LoginSubmitted();
}
