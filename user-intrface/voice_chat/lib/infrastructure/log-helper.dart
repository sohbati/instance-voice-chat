import 'package:intl/intl.dart';

class LogHelper {

  static void log(String s) {
    DateFormat dateFormat = DateFormat("yyyy-MM-dd HH:mm:ss");
    String datetime = dateFormat.format(DateTime.now());

    print('the_voice_chat: $datetime $s');
  }
}