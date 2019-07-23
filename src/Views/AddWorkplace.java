package Views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Controller.Database;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AddWorkplace {

    Database databaseConnection = Database.getDBInstance();
    Login logInView;
    MainWindow mainWindowView;

    Label lblWorkplaceName = new Label("Name of workplace");
    Label lblSalary = new Label("dkk/hour");

    TextField txtWorkplaceName = new TextField();
    TextField txtSalary = new TextField();

    Button btnAdd = new Button("Add");
    Button btnBack = new Button("Back");
    
    ImageView imgView;
    Image imgLogo = new Image(getClass().getResourceAsStream("/logo.png"));

    Stage stgAddWorkplace;

    Scene scnAddWorkplace;

    VBox vBoxLabelsTextfields;
    HBox hBoxButtons;

    public Stage getNewWorkplaceStage()
    {
        txtWorkplaceName.setPromptText("Workplace name");
        txtWorkplaceName.getStyleClass().add("txt-addWorkplace");
        
        txtSalary.setPromptText("dkk/hour");
        txtSalary.getStyleClass().add("txt-addWorkplace");
        
        btnAdd.getStyleClass().add("btn-addWorkplace");
        btnBack.getStyleClass().add("btn-addWorkplace");
        
        imgView = new ImageView();
        imgView.setImage(imgLogo);
        
        hBoxButtons = new HBox();
        hBoxButtons.getChildren().addAll(btnAdd, btnBack);

        vBoxLabelsTextfields = new VBox();
        vBoxLabelsTextfields.getChildren().addAll(lblWorkplaceName, txtWorkplaceName, lblSalary, txtSalary, hBoxButtons);
        
        btnBack.setOnAction(event -> {
            //mainWindowView = new MainWindow();
            //mainWindowView.editComboBox();
            stgAddWorkplace.close();
        });

        btnAdd.setOnAction(event -> {
            try 
            {
                logInView = new Login();
                int userId = logInView.whoIsLoggedIn().getUserId();
                double salary = Double.parseDouble(txtSalary.getText());

                databaseConnection.addWorkplace(userId, txtWorkplaceName.getText(), salary);
                //editComboBox();
                stgAddWorkplace.close();  
                //mainWindowView = new MainWindow();
                //mainWindowView.editComboBox();
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Please fill all the information!");
                alert.showAndWait();
            }
        });
        

        scnAddWorkplace = new Scene(vBoxLabelsTextfields, 300, 300);
        scnAddWorkplace.getStylesheets().add("Resources/Styles/AddWorkplaceStyles.css");

        stgAddWorkplace = new Stage();
        stgAddWorkplace.setScene(scnAddWorkplace);
        stgAddWorkplace.show();

        return stgAddWorkplace;
    }
}
