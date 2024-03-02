import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:formz/formz.dart';
import 'package:routing_repository/routing_repository.dart';

part 'main_page_event.dart';
part 'main_page_state.dart';

class MainPageBloc extends Bloc<MainPageEvent, MainPageState> {
  MainPageBloc({
    required RoutingRepository routingRepository,
  })  : _routingRepository = routingRepository,
        super(const MainPageState()) {
  }

  final RoutingRepository _routingRepository;


}
