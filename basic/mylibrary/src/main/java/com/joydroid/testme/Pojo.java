package com.joydroid.testme;

import java.util.HashMap;

public class Pojo {
  private final HashMap<PojoAttribute, Object> attrMap = new HashMap<>();

  public Pojo(int id) {
    setId(id);
  }

  public void setName(String name) {
    attrMap.put(PojoAttribute.NAME, name);
  }

  public String getName() {
    return (String) attrMap.get(PojoAttribute.NAME);
  }

  private void setId(int id) {
    attrMap.put(PojoAttribute.ID, id);
  }

  public int getId() {
    return (Integer) attrMap.get(PojoAttribute.ID);
  }
}
