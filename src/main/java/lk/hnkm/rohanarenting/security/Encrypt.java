/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 5:16 PM
 *
 */

package lk.hnkm.rohanarenting.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static String encrypt(String password) throws NoSuchAlgorithmException {
         MessageDigest messageDigest = MessageDigest.getInstance("MD5");
         messageDigest.update(password.getBytes());
         byte[] bytes = messageDigest.digest();
         StringBuilder stringBuilder = new StringBuilder();
         for (byte aByte : bytes) {
             stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
         }
         return stringBuilder.toString();
    }
}
