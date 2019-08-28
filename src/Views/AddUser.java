package Views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Controller.Database;
import javafx.stage.Stage;

public class AddUser {
    
    Database databaseConnection = Database.getDBInstance();
    Login logInView;
    
    private TextField txtFullname = new TextField();
    private TextField txtUsername = new TextField();
    private TextField txtPassword = new TextField();

    private Button btnBack = new Button("Back");
    private Button btnSave = new Button("Save");

    private VBox vBoxNewUser = new VBox();
    private HBox hBoxNewUser = new HBox();

    private Scene scnNewUser;
    private Stage stage;

    public Stage getNewAccountStage()
    {
        scnNewUser = new Scene(vBoxNewUser, 256, 400);
        scnNewUser.getStylesheets().add("Resources/Styles/AddUserStyles.css");
        
        txtFullname.getStyleClass().add("txt-addUser");
        txtFullname.setPromptText("Your name");
        
        txtUsername.getStyleClass().add("txt-addUser");
        txtUsername.setPromptText("User name");
        
        txtPassword.getStyleClass().add("txt-addUser");
        txtPassword.setPromptText("Password");

        btnBack.getStyleClass().add("btn-addUser");
        btnSave.getStyleClass().add("btn-addUser");
        
        setButtonSaveOnAction();
        setButtonBackOnAction();

        hBoxNewUser.getChildren().addAll(btnBack, btnSave);
        vBoxNewUser.getChildren().addAll(txtFullname,
                txtUsername, txtPassword, hBoxNewUser);
        vBoxNewUser.getStyleClass().add("vBox-addUser");

        stage = new Stage();
        stage.setScene(scnNewUser);
        stage.show();

        return stage;
    }
    
    private void setButtonSaveOnAction()
    {
        btnSave.setOnAction(event -> {
        databaseConnection.addNewUser(txtFullname.getText(), txtUsername.getText(),
            txtPassword.getText());

        stage.close();
        });
    }
    
    private void setButtonBackOnAction()
    {
        btnBack.setOnAction(event -> {
        stage.close();
        });
    }
}
