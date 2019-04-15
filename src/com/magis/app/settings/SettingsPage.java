package com.magis.app.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.magis.app.Main;
import com.magis.app.UI.UIComponents;
import com.magis.app.icons.MaterialIcons;
import com.magis.app.models.StudentModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsPage {

    public static void Page() {
        StudentModel.Student student = Main.studentModel.getStudent(Main.username);

        /*
        Master
         */
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderpane-settings");

        /*
        Middle
         */
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(900);

        /*
        Profile
         */
        HBox profileSettings = new HBox();

        Label name = new Label(student.getFullName());
        Button editName = UIComponents.CreateSVGIconButton(MaterialIcons.edit, 20);
        JFXButton changePassword = new JFXButton("Change Password");

        profileSettings.getChildren().addAll(name, editName, changePassword);

        vBox.getChildren().addAll(profileSettings);

        /*
        Dark mode
         */
        HBox darkMode = new HBox();
        Label darkModeText = new Label("Enable dark mode");
        JFXToggleButton darkModeToggle = new JFXToggleButton();


        darkMode.getChildren().addAll(darkModeText, darkModeToggle);

        vBox.getChildren().add(darkMode);


        borderPane.setCenter(vBox);

        Main.setScene(borderPane, "Settings");
    }
}
