package com.wemade.coupon.utils;

import java.security.SecureRandom;

public class RandomUtil {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int LENGTH = 16;
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();
  public static String generateRandomString() {
    StringBuilder sb = new StringBuilder(LENGTH);

    for (int i = 0; i < LENGTH; i++) {
      int index = SECURE_RANDOM.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }

    return sb.toString();
  }
}
