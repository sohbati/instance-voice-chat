import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:meta/meta.dart';
import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';

@immutable
@JsonSerializable()
class UserInfoLocalStoreModel extends Equatable {
  UserInfoLocalStoreModel({required this.userId}) {}
    final String userId;


  @override
  List<Object?> get props => [userId];

  factory UserInfoLocalStoreModel.fromJson(Map<String, dynamic> json) {
    return UserInfoLocalStoreModel(
      userId: json['userId']
    );
  }


  Map<String, dynamic> getMap() {
    return {
      'userId' : userId
    };
  }
}
