import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:formz/formz.dart';
import 'package:instance_voice_chat/login/login.dart';

class MainPageForm extends StatelessWidget {
  const MainPageForm({super.key});
  @override
  Widget build(BuildContext context) {
    final backgroundColor = Color(0x1f000000);
    return BlocListener<LoginBloc, LoginState>(
      listener: (context, state) {
        if (state.status.isFailure) {
          ScaffoldMessenger.of(context)
            ..hideCurrentSnackBar()
            ..showSnackBar(
              const SnackBar(content: Text('Authentication Failure')),
            );
        }
      },
      child:  Container(
        margin: EdgeInsets.all(0),
        padding: EdgeInsets.all(0),
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.zero,
          border: Border.all(color: Color(0x4d9e9e9e), width: 1),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.max,

          children: [
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width:  MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.15,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisSize: MainAxisSize.max,
                children: [
                  Image(
                    image: AssetImage('assets/boy-avatar.jpeg'),
                    height: 60,
                    width: 90,
                    fit: BoxFit.cover,
                  ),
                  Container(
                    margin: EdgeInsets.fromLTRB(0, 3, 0, 0),
                    padding: EdgeInsets.all(0),
                    width: 430,
                    height: 30,
                    decoration: BoxDecoration(
                      color: Color(0x1f000000),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.zero,
                    ),
                    child: Align(
                      alignment: Alignment(0.0, 0.1),
                      child: Text(
                        "Create Profile",
                        textAlign: TextAlign.start,
                        overflow: TextOverflow.clip,
                        style: TextStyle(
                          fontWeight: FontWeight.w700,
                          fontStyle: FontStyle.normal,
                          fontSize: 16,
                          color: Color(0xff000000),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisSize: MainAxisSize.max,
                children: [
                  Center(
                    // alignment: Alignment(-0.2, -0.1),
                    child: Container(
                      margin: EdgeInsets.all(0),
                      padding: EdgeInsets.all(0),
                      width: 230,
                      height: MediaQuery.of(context).size.height,
                      decoration: BoxDecoration(
                        color: Color(0x1f000000),
                        shape: BoxShape.rectangle,
                        borderRadius: BorderRadius.zero,
                        border: Border.all(color: Color(0x4d9e9e9e), width: 1),
                      ),
                      child: Align(
                          alignment: Alignment(0.7, 0.0),
                          child: Container(
                            margin: EdgeInsets.all(0),
                            padding: EdgeInsets.all(0),
                            width: 30,
                            height: 30,
                            decoration: BoxDecoration(
                              color: Color(0xaa5d3838),
                              shape: BoxShape.circle,
                              border: Border.all(
                                  color: Color(0x4d9e9e9e), width: 1),
                            ),
                          )),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.all(0),
                    padding: EdgeInsets.all(0),
                    width: 200,
                    height: 100,
                    decoration: BoxDecoration(
                      color: Color(0x1f000000),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.zero,
                      border: Border.all(color: Color(0x4d9e9e9e), width: 1),
                    ),
                    child: Align(
                      alignment: Alignment(-0.8, 0.0),
                      child: Text(
                        "Connect Now",
                        textAlign: TextAlign.start,
                        overflow: TextOverflow.clip,
                        style: TextStyle(
                          fontWeight: FontWeight.w700,
                          fontStyle: FontStyle.normal,
                          fontSize: 17,
                          color: Color(0xff000000),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.1,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
              child: Center(
                // alignment: Alignment(-0.1, -0.1),
                child: Container(
                  width:430,
                  height: 45,
                  padding: EdgeInsets.symmetric(vertical: 4, horizontal: 0),
                  decoration: BoxDecoration(
                    color: Color(0xffffffff),
                    borderRadius: BorderRadius.circular(0),
                  ),
                  child: DropdownButton(
                    value: "Connect Random",
                    items: ["Connect Random", "Male Only", "Female Only"]
                        .map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    style: TextStyle(
                      color: Color(0xff000000),
                      fontSize: 16,
                      fontWeight: FontWeight.w400,
                      fontStyle: FontStyle.normal,
                    ),
                    onChanged: (value) {},
                    elevation: 8,
                    isExpanded: true,
                  ),
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.all(0),
              padding: EdgeInsets.all(0),
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height * 0.3,
              decoration: BoxDecoration(
                color: backgroundColor,
                shape: BoxShape.rectangle,
                borderRadius: BorderRadius.zero,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
