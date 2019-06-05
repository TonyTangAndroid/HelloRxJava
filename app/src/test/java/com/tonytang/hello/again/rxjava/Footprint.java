package com.tonytang.hello.again.rxjava;

public interface Footprint {

  void success(String value);

  void error(Throwable throwable);
}