package com.joydroid.testme;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author epeng on 5/2/16
 */
public class PojoTest {
  @Test public void testConstructor() {
    Pojo p = new Pojo(1);
    assertSame(p.getId(), 1);
    assertNull(p.getName());

    String name = "kate";
    p.setName(name);
    assertEquals(p.getName(), name);
  }

  @Test public void testName() {
    Pojo p = new Pojo(1);
    assertSame(p.getId(), 1);
    assertNull(p.getName());

    String name = "kate";
    p.setName(name);
    assertEquals(p.getName(), name);
  }
}
