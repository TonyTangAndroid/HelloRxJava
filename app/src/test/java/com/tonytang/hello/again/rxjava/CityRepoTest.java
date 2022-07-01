package com.tonytang.hello.again.rxjava;

import static com.google.common.truth.Truth.*;
import static org.junit.Assert.*;

import com.google.common.truth.Truth;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

public class CityRepoTest {

    @Test
    public void listing() {

        Observable<List<CityEntity>> listing = CityRepo.listing();
        TestObserver<List<CityEntity>> testObserver = new TestObserver<>();
        listing.subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<List<CityEntity>> values = testObserver.values();
        List<CityEntity> cityEntities = values.get(0);
        assertThat(cityEntities).isEqualTo(Arrays.asList(new CityEntity(1,"Seattle"),new CityEntity(2, "SFO")));
//        assertThat(cityEntities).isEqualTo(Arrays.asList(new CityEntity(2, "SFO"),new CityEntity(1,"Seattle")));
    }
}