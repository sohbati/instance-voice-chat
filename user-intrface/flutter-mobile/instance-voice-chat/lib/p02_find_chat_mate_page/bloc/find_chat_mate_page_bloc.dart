import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:formz/formz.dart';
import 'package:routing_repository/routing_repository.dart';

part 'find_chat_mate_page_event.dart';
part 'find_chat_mate_page_state.dart';

class FindChatMateBloc extends Bloc<FindChatMateEvent, FindChatMateState> {
  FindChatMateBloc({
    required RoutingRepository routingRepository,
  })  : _routingRepository = routingRepository,
        super(const FindChatMateState()) {
  }

  final RoutingRepository _routingRepository;


}
