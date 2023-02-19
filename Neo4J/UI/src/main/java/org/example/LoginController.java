package org.example;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements GUIController{
    public TextField user,password;
    public Button begin;
    GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void begin(ActionEvent actionEvent) {
        Global.dbUser = user.getText(); Global.dbPassword = password.getText();
        gui.changeStage("starting.fxml");
    }
}
