package Views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import Controller.Database;
import Views.Login;
import javafx.stage.Stage;

/**
 * Created by Edgaras on 4/8/2016.
 */
public class AddUser {

    Database databaseModel;
    Login logInView;

    Scene newAccountScene;
    Stage stage;

   // Label fullnameLabel;
    //Label usernameLabel;
    //Label passwordLabel;

    TextField fullnameTextfield;
    TextField usernameTextfield;
    TextField passwordTextfield;

    Button backButton;
    Button saveButton;

    VBox newAccountVbox;
    HBox newAccountHbox;

    public Stage getNewAccountStage()
    {
        fullnameTextfield = new TextField();
        fullnameTextfield.setStyle("-fx-font-size: 30px; -fx-border-color: black");
        fullnameTextfield.setPromptText("Your name");
        usernameTextfield = new TextField();
        usernameTextfield.setStyle("-fx-font-size: 30px; -fx-border-color: black");
        usernameTextfield.setPromptText("User name");
        passwordTextfield = new TextField();
        passwordTextfield.setStyle("-fx-font-size: 30px; -fx-border-color: black");
        passwordTextfield.setPromptText("Password");

        backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 37px; -fx-background-color: powderblue; -fx-border-color: black");
        saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 37px; -fx-background-color: powderblue; -fx-border-color: black");

        saveButton.setOnAction(event -> {
            databaseModel = new Database();

            databaseModel.addNewUser(fullnameTextfield.getText(), usernameTextfield.getText(),
                    passwordTextfield.getText());

           stage.close();
        });

        backButton.setOnAction(event -> {
           stage.close();
        });

        newAccountHbox = new HBox();
        newAccountVbox = new VBox();

        newAccountHbox.getChildren().addAll(backButton, saveButton);
        newAccountVbox.getChildren().addAll( fullnameTextfield,
                usernameTextfield, passwordTextfield, newAccountHbox);
        newAccountVbox.setSpacing(25);
        newAccountVbox.setStyle("-fx-background-color: ghostwhite");

        newAccountScene = new Scene(newAccountVbox, 256, 400);

        stage = new Stage();
        stage.setScene(newAccountScene);
        stage.show();

        return stage;
    }
}
