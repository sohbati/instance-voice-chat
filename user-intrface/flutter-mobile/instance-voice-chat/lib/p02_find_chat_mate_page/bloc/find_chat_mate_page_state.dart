part of 'find_chat_mate_page_bloc.dart';

final class FindChatMateState extends Equatable {
  const FindChatMateState({
    this.status = FormzSubmissionStatus.initial,
  });

  final FormzSubmissionStatus status;

  @override
  List<Object> get props => [status];
}
