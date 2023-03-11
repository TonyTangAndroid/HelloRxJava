package com.tonytang.hello.again.rxjava;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;

public class CityRepo {

  public static Observable<List<CityEntity>> listing() {
    return Observable.fromCallable(() -> list());
  }

  private static List<CityEntity> list() {
    return Arrays.asList(new CityEntity(1, "Seattle"), new CityEntity(2, "SFO"));
  }
}
