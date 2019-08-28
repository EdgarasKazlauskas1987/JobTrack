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
    MainWindow mainWindow = new MainWindow();

    private Label lblWorkplaceName = new Label("Name of workplace");
    private Label lblSalary = new Label("dkk/hour");

    private TextField txtWorkplaceName = new TextField();
    private TextField txtSalary = new TextField();

    private Button btnAdd = new Button("Add");
    private Button btnBack = new Button("Back");
    
    private ImageView imgView;
    private Image imgLogo = new Image(getClass().getResourceAsStream("/logo.png"));
    
    private VBox vBoxLabelsTextfields;
    private HBox hBoxButtons;

    Stage stgAddWorkplace;
    Scene scnAddWorkplace;

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
        
        setButtonAddOnAction();
        setButtonBackOnAction();

        scnAddWorkplace = new Scene(vBoxLabelsTextfields, 300, 300);
        scnAddWorkplace.getStylesheets().add("Resources/Styles/AddWorkplaceStyles.css");

        stgAddWorkplace = new Stage();
        stgAddWorkplace.setScene(scnAddWorkplace);
        stgAddWorkplace.show();

        return stgAddWorkplace;
    }
    
    private void setButtonAddOnAction()
    {
        btnAdd.setOnAction(event -> {
            try 
            {
                logInView = new Login();
                int userId = logInView.whoIsLoggedIn().getUserId();
                double salary = Double.parseDouble(txtSalary.getText());

                databaseConnection.addWorkplace(userId, txtWorkplaceName.getText(), salary);
                stgAddWorkplace.close();  
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Please fill all the information!");
                alert.showAndWait();
            }
        });
    }
    
    private void setButtonBackOnAction()
    {
        btnBack.setOnAction(event -> {
        stgAddWorkplace.close();
        });
    }
}
