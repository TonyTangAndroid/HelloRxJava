package learning.parallel;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

public class PersonStreaming {

  private PersonStreaming() {}

  public static Single<ResolveResult> resolve(ResolveAction action) {
    return flowable(action).toList().map(list -> resolveResult(action, list));
  }

  private static ResolveResult resolveResult(ResolveAction action, List<Person> list) {
    return new ResolveResult(span(action), action.type, list);
  }

  private static Span span(ResolveAction action) {
    return new Span(action.startTime, System.currentTimeMillis());
  }

  private static Flowable<Person> flowable(ResolveAction action) {
    return action.type.equals(ResolveType.SEQUENCE) ? resolveInSequence() : resolveInParallel();
  }

  private static Flowable<Person> resolveInSequence() {
    return source().subscribeOn(Schedulers.io()).flatMapSingle(PersonRepo::asyncResolve);
  }

  private static Flowable<Person> resolveInParallel() {
    return source()
        .flatMapSingle(item -> PersonRepo.asyncResolve(item).subscribeOn(Schedulers.io()));
  }

  private static Flowable<String> source() {
    return Flowable.fromCallable(UUID::randomUUID).repeat().take(4).map(UUID::toString);
  }
}
