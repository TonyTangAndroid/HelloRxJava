package com.tonytang.hello.again.rxjava;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class CityRepo {

  public  static   Observable<List<CityEntity>> listing() {
        return Observable.fromCallable(() -> list());
    }

    private static List<CityEntity> list() {
        return Arrays.asList(new CityEntity(1, "Seattle"),new CityEntity(2, "SFO"));
    }
}
