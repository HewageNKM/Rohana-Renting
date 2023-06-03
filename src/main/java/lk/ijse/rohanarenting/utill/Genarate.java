/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 3/31/23, 11:01 AM
 *
 */

package lk.ijse.rohanarenting.utill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class Genarate {
   public static String generateEmployeeId() {
       String prefix = "E";
       Random random = new Random();
       int number = random.nextInt(9000) + 1000;
       return prefix + Integer.toString(number);
   }
    public static String generateToolId() {
        String prefix = "T";
        Random random = new Random();
        int number = random.nextInt(9000) + 1000;
        return prefix + Integer.toString(number);
    }
    public static String generateCustomerId() {
        String prefix = "C";
        Random random = new Random();
        int number = random.nextInt(9000) + 1000;
        return prefix + Integer.toString(number);
    }
    public static String genarateRentId(int i) {
        // Generate a UUID
        UUID uuid = UUID.randomUUID();
        // Get the current date-time
        LocalDateTime now = LocalDateTime.now();
        // Format the date-time to a string
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // Concatenate the UUID and formatted date-time
        String rentId = uuid.toString().replaceAll("-", "") + formattedDateTime;
        // Trim the order ID to 10 characters
        rentId = rentId.substring(0, 8);
        rentId = rentId.toUpperCase();
        return i>0 ? "RT"+rentId:"RV"+rentId;
    }

    public static String genarateReturnId() {
        // Generate a UUID
        UUID uuid = UUID.randomUUID();
        // Get the current date-time
        LocalDateTime now = LocalDateTime.now();
        // Format the date-time to a string
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // Concatenate the UUID and formatted date-time
        String rentId = uuid.toString().replaceAll("-", "") + formattedDateTime;
        // Trim the order ID to 10 characters
        rentId = rentId.substring(0, 9);
        return "R"+rentId;
    }

    public static String genarateRefundId() {
        // Generate a UUID
        UUID uuid = UUID.randomUUID();
        // Get the current date-time
        LocalDateTime now = LocalDateTime.now();
        // Format the date-time to a string
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // Concatenate the UUID and formatted date-time
        String rentId = uuid.toString().replaceAll("-", "") + formattedDateTime;
        // Trim the order ID to 10 characters
        rentId = rentId.substring(0, 8);
        return "RF"+rentId;
    }
}
