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
import javafx.stage.Stage;
import Controller.Database;


public class Login extends Application
{
    AddUser newAccountView;
    Database databaseConnetion = Database.getDBInstance();

    Stage mainStage = new Stage();
    Scene loginScene;

    HBox hBoxLogin = new HBox();
    VBox vBoxLogin = new VBox();

    VBox vBoxUsername = new VBox();
    VBox vBoxPassword = new VBox();

    Button btnLogin = new Button("Login");
    Button btnNewUser = new Button("New User");

    TextField txtUsername = new TextField();
    PasswordField ptxtPassword = new PasswordField();

    Label lblUsername = new Label("Username");
    Label lblPassword = new Label("Password");

    ImageView selectedImage;
    Image image = new Image(getClass().getResourceAsStream("/logo.png"));
    
    MainWindow mainWindowView;
    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        mainStage.setScene(getLoginScene());
        mainStage.show();
    }

    public Scene getLoginScene()
    {        
        btnLogin.getStyleClass().add("login-button");
        btnNewUser.getStyleClass().add("login-button");

        vBoxUsername.getChildren().addAll(lblUsername, txtUsername);
        vBoxUsername.getStyleClass().add("vBox-credentials");
        vBoxPassword.getChildren().addAll(lblPassword, ptxtPassword);
        vBoxPassword.getStyleClass().add("vBox-credentials");

        selectedImage = new ImageView();
        selectedImage.setImage(image);

        hBoxLogin.getChildren().addAll(btnLogin, btnNewUser);
        vBoxLogin.getChildren().addAll(selectedImage,vBoxUsername, vBoxPassword, hBoxLogin);
        vBoxLogin.getStyleClass().add("vBox-login");

        loginScene = new Scene(vBoxLogin, 232, 350);
        loginScene.getStylesheets().add("Resources/Styles/LoginStyles.css");

        btnNewUser.setOnAction(event -> {
            newAccountView = new AddUser();
            newAccountView.getNewAccountStage();
        });

        btnLogin.setOnAction(event -> {
            String userName = txtUsername.getText();
            String password = ptxtPassword.getText();

            user = databaseConnetion.retrieveUserIfAllowedToLogIn(userName, password);

            if (user == null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Username or password is wrong!");
                alert.showAndWait();
            }
            else 
            {
                mainWindowView = new MainWindow();
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

