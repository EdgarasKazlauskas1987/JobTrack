package Views;

import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import Controller.DatabaseModel;

import javax.swing.*;

/**
 * Created by Edgaras on 4/8/2016.
 */
public class Login extends Application
{
    AddUser newAccountView;
    DatabaseModel databaseModel;

    public static User user;

    Stage mainStage = new Stage();
    Scene loginScene;

    HBox loginHbox;
    VBox loginVbox;

    VBox vBox1;
    VBox vBox2;

    Button loginButton;
    Button newAccountButton;

    TextField usernameTextField;
   // TextField passwordTextField;
    PasswordField passwordTextField;

    Label usernameLabel;
    Label passwordLabel;

    MainWindow mainWindowView;

    ImageView selectedImage;
    Image image = new Image(getClass().getResourceAsStream("/logo.png"));

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        mainStage.setScene(getLoginScene());
        mainStage.show();
    }

    public Scene getLoginScene()
    {
        usernameLabel = new Label("User Name");
        passwordLabel = new Label("Password");

        usernameTextField = new TextField();
        passwordTextField = new PasswordField();

        loginButton = new Button("Log in");
        loginButton.setStyle("-fx-font-size: 20px; -fx-background-color: powderblue; -fx-border-color: black");

        newAccountButton = new Button("New account");
        newAccountButton.setStyle("-fx-font-size: 20px; -fx-background-color: powderblue; -fx-border-color: black");

        loginHbox = new HBox();
        loginVbox = new VBox();

        vBox1 = new VBox();
        vBox2 = new VBox();
        vBox1.getChildren().addAll(usernameLabel, usernameTextField);
        vBox1.setStyle("-fx-font-size: 20px");
        vBox2.getChildren().addAll(passwordLabel, passwordTextField);
        vBox2.setStyle("-fx-font-size: 20px");

        selectedImage = new ImageView();
        selectedImage.setImage(image);

        loginHbox.getChildren().addAll(loginButton, newAccountButton);
        loginVbox.getChildren().addAll(selectedImage,vBox1, vBox2, loginHbox);
        loginVbox.setSpacing(20);
        loginVbox.setStyle("-fx-background-color: ghostwhite");

        loginScene = new Scene(loginVbox, 232, 350);
        loginScene.getStylesheets().add("Resources/Styles/LoginStyles.css");
        loginVbox.setAlignment(Pos.BOTTOM_CENTER);

        newAccountButton.setOnAction(event -> {
            newAccountView = new AddUser();
            newAccountView.getNewAccountStage();
        });

        loginButton.setOnAction(event -> {
            databaseModel = new DatabaseModel();
            String userName = usernameTextField.getText();
            String password = passwordTextField.getText();

            user = databaseModel.retrieveUserIfAllowedToLogIn(userName, password);

            if (user == null)
            {
                //System.out.println("sorry");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Username or password is wrong!");
                //alert.setContentText("Careful with the next step!");
                alert.showAndWait();
            }

            else 
            {
                mainWindowView = new MainWindow();
               // mainStage.setScene(mainWindowView.getMainWindowScene());
                mainWindowView.getMainWindowStage();
                mainWindowView.updateTable();
            }
        });
        
        return loginScene;
    }

    public User whoIsLoggedIn()
    {
        return user;
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}

