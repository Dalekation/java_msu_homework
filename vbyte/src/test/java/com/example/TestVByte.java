package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestVByte {
  @Test
  @DisplayName("Test for elemet < 2^7")
  public void testType1() {
    int[] a = {11, 11, 11, 11};
    int[] a_encoded = {};

    int[] res = VByte.encode(a);
    int[] res2 = VByte.decode(res);

    assertEquals(VByte.binary_to_int("10001011100010111000101110001011"), res[0], "Wrong encoding");

    for (int i = 0; i < a.length; i++) {
      assertEquals(a[i], res2[i], "Wrong decoding");
    }
  }

  @Test
  @DisplayName("Test for elements < 2^14 and >= 2^7")
  public void testType2() {
    int[] a = {(1 << 9) - 1, (1 << 9) - 1, (1 << 9) - 1, (1 << 9) - 1};
    int[] a_encoded = {};

    int[] res = VByte.encode(a);
    int[] res2 = VByte.decode(res);

    assertEquals(VByte.binary_to_int("10000011011111111000001101111111"), res[0], "Wrong encoding");

    for (int i = 0; i < a.length; i++) {
      assertEquals(a[i], res2[i], "Wrong decoding");
    }
  }

  @Test
  @DisplayName("Test for elements < 2^21 and >= 2^14")
  public void testType3() {
    int[] a = {(1 << 16) - 1, (1 << 16) - 1, (1 << 16) - 1, (1 << 16) - 1};
    int[] a_encoded = {};

    int[] res = VByte.encode(a);
    int[] res2 = VByte.decode(res);

    assertEquals(VByte.binary_to_int("10000011011111110111111110000011"), res[0], "Wrong encoding");

    for (int i = 0; i < a.length; i++) {
      assertEquals(a[i], res2[i], "Wrong decoding");
    }
  }

  @Test
  @DisplayName("Test for elements < 2^28 and >= 2^21")
  public void testType4() {
    int[] a = {(1 << 23) - 1, (1 << 23) - 1, (1 << 23) - 1, (1 << 23) - 1};
    int[] a_encoded = {};

    int[] res = VByte.encode(a);
    int[] res2 = VByte.decode(res);

    assertEquals(VByte.binary_to_int("10000011011111110111111101111111"), res[0], "Wrong encoding");

    for (int i = 0; i < a.length; i++) {
      assertEquals(a[i], res2[i], "Wrong decoding");
    }
  }

  @Test
  @DisplayName("Test for different elements")
  public void testType5() {
    int[] a = {2135, 436367, 23, 8934235, 9, 236284589};
    int[] a_encoded = {};

    int[] res = VByte.encode(a);
    int[] res2 = VByte.decode(res);

    for (int i = 0; i < a.length; i++) {
      assertEquals(a[i], res2[i], "Wrong decoding");
    }
  }
}
