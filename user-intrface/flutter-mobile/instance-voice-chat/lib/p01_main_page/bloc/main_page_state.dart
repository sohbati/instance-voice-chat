part of 'main_page_bloc.dart';

final class MainPageState extends Equatable {
  const MainPageState({
    this.status = FormzSubmissionStatus.initial,
  });

  final FormzSubmissionStatus status;


  MainPageState copyWith({
    FormzSubmissionStatus? status,
  }) {
    return MainPageState(
      status: status ?? this.status,
    );
  }

  @override
  List<Object> get props => [status];
}
