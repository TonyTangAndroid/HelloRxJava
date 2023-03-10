package learning.parallel;

public class ResolveAction {

  public final ResolveType type;
  public final long startTime;

  public ResolveAction(ResolveType type, long startTime) {
    this.type = type;
    this.startTime = startTime;
  }

  static ResolveAction create(ResolveType sequence) {
    return new ResolveAction(sequence, System.currentTimeMillis());
  }
}
