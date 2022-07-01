package com.tonytang.hello.again.rxjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class CityRepo {

  public  static   Observable<List<CityEntity>> listing() {
        return Observable.fromCallable(new Callable<List<CityEntity>>() {
            @Override
            public List<CityEntity> call() throws Exception {
                return Arrays.asList(new CityEntity(1, "Seattle"),new CityEntity(2, "SFO"));
            }
        });
    }
}
