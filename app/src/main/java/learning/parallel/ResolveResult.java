package learning.parallel;

import java.util.List;

public class ResolveResult {

  public final List<Person> list;
  public final ResolveType resolveType;
  public final Span span;

  public ResolveResult(Span span, ResolveType resolveType, List<Person> list) {
    this.span = span;
    this.resolveType = resolveType;
    this.list = list;
  }
}
