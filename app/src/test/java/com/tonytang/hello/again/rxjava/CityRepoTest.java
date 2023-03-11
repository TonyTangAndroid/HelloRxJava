package com.tonytang.hello.again.rxjava;

import static com.google.common.truth.Truth.assertThat;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class CityRepoTest {

  @Test
  public void listing() {

    // method 1
    Observable<List<CityEntity>> listing = CityRepo.listing();
    TestObserver<List<CityEntity>> testObserver = new TestObserver<>();
    listing.subscribe(testObserver);

    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertValueCount(1);
    List<List<CityEntity>> values = testObserver.values();
    List<CityEntity> cityEntities = values.get(0);
    assertThat(cityEntities)
        .isEqualTo(Arrays.asList(new CityEntity(1, "Seattle"), new CityEntity(2, "SFO")));
    //        assertThat(cityEntities).isEqualTo(Arrays.asList(new CityEntity(2, "SFO"),new
    // CityEntity(1,"Seattle")));
  }

  @Test
  public void method2() {

    // method 2
    Observable<List<CityEntity>> listing = CityRepo.listing();
    TestObserver<List<CityEntity>> testObserver = listing.test();

    // assert
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertValueCount(1);
    List<List<CityEntity>> values = testObserver.values();
    List<CityEntity> cityEntities = values.get(0);
    assertThat(cityEntities)
        .isEqualTo(Arrays.asList(new CityEntity(1, "Seattle"), new CityEntity(2, "SFO")));
    //        assertThat(cityEntities).isEqualTo(Arrays.asList(new CityEntity(2, "SFO"),new
    // CityEntity(1,"Seattle")));
  }

  @Test
  public void method2_minimum() {

    // method 3 test on website
    CityRepo.listing()
        .test()
        .assertValueAt(0, Arrays.asList(new CityEntity(1, "Seattle"), new CityEntity(2, "SFO")));
  }
}
