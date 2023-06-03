/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 11:31 AM
 *
 */

package lk.ijse.rohanarenting.utill.notification;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class TopUpNotifications {

    public static void copied(String text){
        Notifications notifications = Notifications.create();
        Image image = new Image("/img/copy.png");
        notifications.title("Copied");
        notifications.text("Copied "+text+" to clipboard");
        notifications.graphic(new ImageView(image));
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
        Image image = new Image("/img/log.png");
        notifications.title("Logged Out");
        notifications.text(userName+" Successfully logged out");
        notifications.graphic(new ImageView(image));
        notifications.hideAfter(javafx.util.Duration.seconds(4));
        notifications.position(Pos.BOTTOM_RIGHT);
        notifications.showConfirm();
    }
    public static void success(String text){
        Notifications notifications = Notifications.create();
        notifications.title("Successfully");
        Image image = new Image("/img/success.png");
        notifications.text(text);
        notifications.graphic(new ImageView(image));
        notifications.hideAfter(javafx.util.Duration.seconds(2));
        notifications.position(Pos.TOP_RIGHT);
        notifications.showConfirm();
    }
}
