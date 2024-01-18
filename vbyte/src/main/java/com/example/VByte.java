package com.example;


public class VByte {

  public static int min(int a, int b) {
    if (a < b) return a;

    return b;
  }

  public static int how_much_bytes(int el) {
    // calculates how much bytes is needed
    // to encode number

    int s = 1;
    int tmp = (1 << (7 * s)) - 1;
    while (tmp < el) {
      s += 1;
      tmp = (1 << min((7 * s), 31)) - 1;
    }

    return s;
  }

  public static void print_bin_int(int a) {
    // prints int in binary form

    int tmp = 0;
    for (int i = 31; i >= 0; i--) {
      if (((1 << i) & a) != 0) {
        tmp = 1;
      } else {
        tmp = 0;
      }
      System.out.print(tmp);
    }
    System.out.println("");
  }

  public static void print_bin_byte(byte a) {
    // prints byte in binary form

    byte tmp = 0;
    for (int i = 7; i >= 0; i--) {
      if (((1 << i) & a) != 0) {
        tmp = 1;
      } else {
        tmp = 0;
      }
      System.out.print(tmp);
    }
    System.out.println("");
  }

  public static byte[] encode_int(int a) {
    // encodes int into bytes

    int bytes_amount = how_much_bytes(a);
    byte[] res = new byte[bytes_amount];
    byte tmp = 1;

    for (int i = 0; i < bytes_amount; i++) {
      res[bytes_amount - 1 - i] = (byte) (a >> (7 * i));

      if (i != bytes_amount - 1) {
        res[bytes_amount - 1 - i] = (byte) ((~(tmp << 7)) & res[bytes_amount - 1 - i]);
      } else {
        res[bytes_amount - 1 - i] = (byte) (((tmp << 7)) | res[bytes_amount - 1 - i]);
      }
    }

    return res;
  }

  public static byte[] int_split_to_bytes(int a) {
    // splites ints into bytes

    byte[] res = new byte[4];
    byte tmp = 1;

    for (int i = 0; i < 4; i++) {
      res[i] = (byte) (a >> (i * 8));
    }

    return res;
  }

  public static byte[] concat_bytes_arrays(byte[] a1, byte[] a2) {
    // concatenates two bytes arrays

    byte[] res = new byte[a1.length + a2.length];

    if (a1.length > 0) {
      for (int i = 0; i < a1.length; i++) {
        res[i] = a1[i];
      }
    }

    if (a2.length > 0) {
      for (int i = 0; i < a2.length; i++) {
        res[i + a1.length] = a2[i];
      }
    }

    return res;
  }

  public static int[] concat_ints_arrays(int[] a1, int[] a2) {
    // concatenates two int arrays

    int[] res = new int[a1.length + a2.length];

    if (a1.length > 0) {
      for (int i = 0; i < a1.length; i++) {
        res[i] = a1[i];
      }
    }

    if (a2.length > 0) {
      for (int i = 0; i < a2.length; i++) {
        res[i + a1.length] = a2[i];
      }
    }

    return res;
  }

  public static int[] convert_bytes_to_ints(byte[] srcByte) {
    // transforms bytes array to int array

    int shiftBits;
    int byteNum = 0;
    int[] dstInt = new int[srcByte.length / 4];

    // Convert array of source bytes (srcByte) into array of integers (dstInt)
    for (int intNum = 0; intNum < srcByte.length / 4; ++intNum) { // for the four integers
      dstInt[intNum] = 0; // Start with the integer = 0

      for (shiftBits = 24; shiftBits >= 0; shiftBits -= 8) { // Add in each data byte, lowest first
        dstInt[intNum] |= (srcByte[byteNum] & 0xFF) << shiftBits;
        byteNum += 1;
      }
    }

    return dstInt;
  }

  public static int[] encode(int[] array) {
    byte[] res = new byte[0];
    byte[] additional = {(byte) (1 << 7)};

    for (int i = 0; i < array.length; i++) {
      res = concat_bytes_arrays(res, encode_int(array[i]));
    }

    // adding zeros in order to have size
    // divisible by 4
    if (res.length % 4 != 0) {
      while (res.length % 4 != 0) {
        res = concat_bytes_arrays(res, additional);
      }
    }

    return convert_bytes_to_ints(res);
  }

  public static int[] decode(int[] array) {
    byte[] tmp = new byte[0];
    int[] res = new int[0];

    for (int i = array.length - 1; i >= 0; i--) {
      tmp = concat_bytes_arrays(tmp, int_split_to_bytes(array[i]));
    }

    int tmp_var = 0;
    int cur_pos = 0;
    for (int i = 0; i < tmp.length; i++) {
      if (((1 << 7) & (tmp[i] & 0xFF)) != 0) {
        tmp_var = tmp_var | (((tmp[i] ^ 1 << 7) & 0xFF) << (cur_pos * 7));
        res = concat_ints_arrays(new int[] {tmp_var}, res);
        tmp_var = 0;
        cur_pos = 0;
      } else {
        tmp_var = tmp_var | ((tmp[i] & 0xFF) << (cur_pos * 7));
        cur_pos += 1;
      }
    }

    return res;
  }

  public static int binary_to_int(String a) {
    int res = 0;
    String[] a_spl = a.split("");

    for (int i = 31; i >= 0; i--) {
      if (a_spl[i].equals("1")) {
        res += (1 << (31 - i));
      }
    }

    return res;
  }
}
