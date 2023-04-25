/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 11:31 AM
 *
 */

package lk.hnkm.rohanarenting.notification;

import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class TopUpNotifications {
    public static void copied(String text){
        Notifications notifications = Notifications.create();
        notifications.title("Copied");
        notifications.text("Copied "+text+" to clipboard");
        notifications.hideAfter(javafx.util.Duration.seconds(2));
        notifications.position(Pos.TOP_RIGHT);
        notifications.showConfirm();
    }
    public static void logIn(String userName){
        Notifications notifications = Notifications.create();
        notifications.title("Logged In");
        notifications.text(userName+" Successfully logged in");
        notifications.hideAfter(javafx.util.Duration.seconds(4));
        notifications.position(Pos.BOTTOM_RIGHT);
        notifications.showConfirm();
    }
    public static void logOut(String userName){
        Notifications notifications = Notifications.create();
        notifications.title("Logged Out");
        notifications.text(userName+" Successfully logged out");
        notifications.hideAfter(javafx.util.Duration.seconds(4));
        notifications.position(Pos.BOTTOM_RIGHT);
        notifications.showConfirm();
    }
    public static void success(String text){
        Notifications notifications = Notifications.create();
        notifications.title("Successfully");
        notifications.text(text);
        notifications.hideAfter(javafx.util.Duration.seconds(2));
        notifications.position(Pos.TOP_RIGHT);
        notifications.showConfirm();
    }
}
