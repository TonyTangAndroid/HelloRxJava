package learning.parallel;

import io.reactivex.Single;

public class PersonRepo {

  private PersonRepo() {
  }

  public static Single<Person> asyncResolve(String uuid){
   return Single.fromCallable(() -> asyncResolveInternal(uuid));
  }

  private static Person asyncResolveInternal(String uuid) throws InterruptedException {
    Thread.sleep(1000);
    return new Person(uuid);
  }
}
