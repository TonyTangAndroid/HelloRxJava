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

  /**
   * <a href="https://nurkiewicz.com/2017/09/idiomatic-concurrency-flatmap-vs.html">blog post</a>
   *
   * <p>This highlight the section in blog post that
   *
   * <p>"Well, subscribeOn() on the outer stream level basically said that all events should be
   * processed sequentially, within this stream, on a different thread."
   */
  private static Flowable<Person> resolveInSequence() {
    return source().subscribeOn(Schedulers.io()).flatMapSingle(PersonRepo::asyncResolve);
  }

  /**
   * <a href="https://nurkiewicz.com/2017/09/idiomatic-concurrency-flatmap-vs.html">blog post</a>
   *
   * <p>This highlight the section in blog post that "Normally you would put subscribeOn() inside
   * asyncLoadBy() but for educational purposes I'll place it directly in the main pipeline".ø
   */
  private static Flowable<Person> resolveInParallel() {
    return source()
        .flatMapSingle(item -> PersonRepo.asyncResolve(item).subscribeOn(Schedulers.io()));
  }

  private static Flowable<String> source() {
    return Flowable.fromCallable(UUID::randomUUID).repeat().take(4).map(UUID::toString);
  }
}
