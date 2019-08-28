package Views;

import Model.User;
import javafx.application.Application;
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

    private Button btnLogin = new Button("Login");
    private Button btnNewUser = new Button("New User");

    private TextField txtUsername = new TextField();
    private PasswordField ptxtPassword = new PasswordField();

    private Label lblUsername = new Label("Username");
    private Label lblPassword = new Label("Password");

    private ImageView selectedImage;
    private Image image = new Image(getClass().getResourceAsStream("/logo.png"));
    
    private HBox hBoxLogin = new HBox();
    private VBox vBoxLogin = new VBox();

    private VBox vBoxUsername = new VBox();
    private VBox vBoxPassword = new VBox();
    
    Stage mainStage = new Stage();
    Scene loginScene;
    
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
        
        setButtonNewUserOnAction();
        setButtonLoginOnAction();
        
        return loginScene;
    }
    
    private void setButtonNewUserOnAction()
    {
        btnNewUser.setOnAction(event -> {
        newAccountView = new AddUser();
        newAccountView.getNewAccountStage();
        });
    }
    
    private void setButtonLoginOnAction()
    {
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

