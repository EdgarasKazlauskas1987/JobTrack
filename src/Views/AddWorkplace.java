package Views;

import Controller.DatabaseModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Controller.DatabaseModel;

/**
 * Created by Edgaras on 4/9/2016.
 */
public class AddWorkplace {

    DatabaseModel databaseModel;
    Login logInView;
    MainWindow mainWindowView;

    Label workplacenameLabel;
    Label salaryLabel;

    TextField nameTextField;
    TextField salaryTextField;

    Button addButton;
    Button backButton;

    Stage stage;

    Scene scene;

    VBox vBox;
    HBox hBox;

    public Stage getNewWorkplaceStage()
    {
        workplacenameLabel = new Label("Name of workplace");
        salaryLabel = new Label("dkk/hour");

        nameTextField = new TextField();
        salaryTextField = new TextField();

        addButton = new Button("Add");
        backButton = new Button("Back");

        backButton.setOnAction(event1 -> {
            mainWindowView = new MainWindow();
            mainWindowView.editComboBox();
        });

        addButton.setOnAction(event -> {
            databaseModel = new DatabaseModel();
            logInView = new Login();
            int userId = logInView.whoIsLoggedIn().getUserId();
            double salary = Double.parseDouble(salaryTextField.getText());

            databaseModel.addWorkplace(userId, nameTextField.getText(), salary);
            mainWindowView = new MainWindow();
           // mainWindowView.editComboBox();
        });

        hBox = new HBox();
        hBox.getChildren().addAll(addButton, backButton);

        vBox = new VBox();
        vBox.getChildren().addAll(workplacenameLabel, nameTextField, salaryLabel, salaryTextField,
                hBox);

        scene = new Scene(vBox, 300, 300);

        stage = new Stage();
        stage.setScene(scene);
        stage.show();

        return stage;
    }
}
