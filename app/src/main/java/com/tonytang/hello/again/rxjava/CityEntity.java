package com.tonytang.hello.again.rxjava;

import java.util.Objects;

public class CityEntity {

  public final int id;
  public final String name;

  public CityEntity(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CityEntity that = (CityEntity) o;
    return id == that.id && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
