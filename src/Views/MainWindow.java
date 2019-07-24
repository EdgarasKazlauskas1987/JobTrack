package Views;

import Controller.Database;
import Controller.MainWindowController;
import Model.JobDay;
import Model.Workplace;
import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.ArrayList;


public class MainWindow {

    AddWorkplace newWorkplace;
    Database databaseConnection = Database.getDBInstance();
    MainWindowController mainWindowController;
    Utilities utilities = new Utilities();
    Login logInView;

    Workplace workplace;

    Label lblWorkplace = new Label("Workplace");
    Label lblCalendar = new Label("Choose date");
    Label lblHoursWorked = new Label("Hours Worked");
    Label lblPayHour = new Label("Pay/h");

    Label lblTotalHoursWorked = new Label("Total hourse worked");
    Label lblTotalMoneyEarned = new Label("Total money earned");

    Button btnSave = new Button("Save new Job day");
    Button btnAddNewWorkplace = new Button("Add new workplace");
    Button btnBack = new Button("Back");
    Button btnShow = new Button("Show");

    ComboBox workplaceCombobox;
    ComboBox<String> yearCombobox;
    ComboBox<String> monthCombobox;

    TextField txtHoursWorked = new TextField();
    TextField txtPayPerHour = new TextField();
    TextField txtTotalHoursWorked = new TextField();
    TextField txtTotalMoneyEarned = new TextField();

    DatePicker datePicker;
    TableView<JobDay> tableView;

    BorderPane borderPane;
    Scene scnMainWindow;
    Stage stgMainWindow;

    VBox vBoxLeft = new VBox();
    VBox vBoxRight = new VBox();

    VBox vBoxTop = new VBox();
    VBox vBoxFirstMiddle = new VBox();
    VBox vBoxSecondMiddle = new VBox();
    VBox vBoxBottom = new VBox();

    HBox hBoxBottom = new HBox();
    HBox hBoxForTop = new HBox();
    HBox leftHbox;

    Date date;

    ArrayList<Workplace> workplaces;

    ImageView selectedImage;
    Image imageLogo = new Image(getClass().getResourceAsStream("/logo.png"));

    public Stage getMainWindowStage()
    {
        logInView = new Login();

        ObservableList<Workplace> workplacesList = FXCollections.observableArrayList();
        workplacesList.addAll(databaseConnection.getWorkplaces2(logInView.whoIsLoggedIn().getUserId()));
        workplaceCombobox = new ComboBox(workplacesList);

        workplaceCombobox.setOnAction(event -> {
            Workplace workplace = (Workplace) workplaceCombobox.getSelectionModel().getSelectedItem();
            Double salaryPerHour = databaseConnection.getSalary(workplace.getName());
            String salary = String.valueOf(salaryPerHour);
            txtPayPerHour.setText(salary);
        });

        tableView = new TableView();

        TableColumn nameCol = new TableColumn("Workplace");
        nameCol.setCellValueFactory(new PropertyValueFactory<JobDay, String>("workplaceName"));

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<JobDay, Date>("date"));

        TableColumn hourseCol = new TableColumn("Hourse worked");
        hourseCol.setCellValueFactory(new PropertyValueFactory<JobDay, Integer>("hourseWorked"));

        TableColumn payCol = new TableColumn("dkk/h");
        payCol.setCellValueFactory(new PropertyValueFactory<JobDay, Double>("payPerHour"));

        tableView.getColumns().setAll(nameCol, dateCol, hourseCol, payCol);
        tableView.setMaxWidth(428);
        tableView.setPrefHeight(300);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        datePicker = new DatePicker();

        vBoxLeft.setStyle("-fx-border-color: black");

        borderPane = new BorderPane();
        borderPane.setLeft(vBoxLeft);

        mainWindowController = new MainWindowController();
        yearCombobox = new ComboBox<>(mainWindowController.populateYear());
        yearCombobox.setValue(utilities.getCurrentYear());

        ObservableList<String> monthList  = FXCollections.observableArrayList();
        monthList.addAll("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" );

        monthCombobox = new ComboBox<>(monthList);
        monthCombobox.setValue(utilities.getCurrentMonth());

        btnShow.setOnAction(event -> {
            logInView = new Login();
            ArrayList<JobDay> list = new ArrayList<JobDay>();

            String year = yearCombobox.getSelectionModel().getSelectedItem();
            String month = mainWindowController.getMonth(monthCombobox.getSelectionModel().getSelectedItem());

            list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),year,month );

            ObservableList<JobDay> data = FXCollections.observableList(list);
            tableView.setItems(data);

            Double totalHours;
            totalHours = databaseConnection.getTotalHours(logInView.whoIsLoggedIn().getUserId(),year,month);
            String formattedTotalHours = String.valueOf(totalHours);
            txtTotalHoursWorked.setText(formattedTotalHours);

            Double moneyEarned;
            moneyEarned = databaseConnection.getMoneyEarned(logInView.whoIsLoggedIn().getUserId(), year, month);
            String formattedMoneyEarned = String.valueOf(moneyEarned);
            txtTotalMoneyEarned.setText(formattedMoneyEarned);
        });

        leftHbox = new HBox();
        leftHbox.getChildren().addAll(lblTotalHoursWorked, txtTotalHoursWorked,
            lblTotalMoneyEarned, txtTotalMoneyEarned);

        yearCombobox.setStyle("-fx-font-size: 15px; -fx-background-color: powderblue; -fx-border-color: black");
        monthCombobox.setStyle("-fx-font-size: 15px; -fx-background-color: powderblue; -fx-border-color: black");
        btnShow.setStyle("-fx-font-size: 15px; -fx-background-color: powderblue; -fx-border-color: black");

        hBoxForTop.getChildren().addAll(yearCombobox,monthCombobox,btnShow);

        hBoxForTop.setSpacing(78);
        vBoxRight.getChildren().addAll(hBoxForTop, tableView, leftHbox);
        borderPane.setRight(vBoxRight);

        btnSave.setOnAction(event -> {
            try {
                    logInView = new Login();
                    date = Date.valueOf(datePicker.getValue());

                    Workplace workplace = (Workplace) workplaceCombobox.getSelectionModel().getSelectedItem();
                    int hoursWorked = Integer.parseInt(txtHoursWorked.getText());
                    double payPerHour = Double.parseDouble(txtPayPerHour.getText());

                    String data = String.valueOf(datePicker.getValue());
                    String yearPart = data.substring(0, 4);
                    String monthPart = data.substring(5, 7);

                    databaseConnection.addJobDay(logInView.whoIsLoggedIn().getUserId(), workplace.getName(), date,
                        hoursWorked, payPerHour, yearPart, monthPart);

                    String a = yearCombobox.getSelectionModel().getSelectedItem();
                    String b = mainWindowController.getMonth(monthCombobox.getSelectionModel().getSelectedItem());

                    if (!a.equals(null) && !b.equals(null))
                    {
                        ArrayList<JobDay> list = new ArrayList<JobDay>();

                        String year = yearCombobox.getSelectionModel().getSelectedItem();
                        String month = mainWindowController.getMonth(monthCombobox.getSelectionModel().getSelectedItem());
                        //String month = myOwnMonth();

                        list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),year,month );

                        ObservableList<JobDay> dataSS = FXCollections.observableList(list);
                        tableView.setItems(dataSS);

                        Double totalHoursWorked;
                        totalHoursWorked = databaseConnection.getTotalHours(logInView.whoIsLoggedIn().getUserId(),year,month);
                        txtTotalHoursWorked.setText(String.valueOf(totalHoursWorked));

                        Double moneyEarned;
                        moneyEarned = databaseConnection.getMoneyEarned(logInView.whoIsLoggedIn().getUserId(), year, month);
                        txtTotalMoneyEarned.setText(String.valueOf(moneyEarned));
                    }

                    else
                    {       
                        updateTable(); 
                    } 
            }

            catch (NullPointerException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Please fill all the information!");
                alert.showAndWait();
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Please fill all the information!");
                alert.showAndWait();
            }
        });

        btnBack.setOnAction(event -> {
          stgMainWindow.close();
        });

        btnAddNewWorkplace.setOnAction(event -> {
            newWorkplace = new AddWorkplace();
            newWorkplace.getNewWorkplaceStage();
            
        });

        hBoxBottom.getChildren().addAll(btnSave, btnAddNewWorkplace, btnBack);
        borderPane.setBottom(hBoxBottom);

        vBoxTop.getChildren().addAll(lblWorkplace, workplaceCombobox);
        vBoxFirstMiddle.getChildren().addAll(lblCalendar, datePicker);
        vBoxSecondMiddle.getChildren().addAll(lblHoursWorked, txtHoursWorked);
        vBoxBottom.getChildren().addAll(lblPayHour, txtPayPerHour);

        selectedImage = new ImageView();
        selectedImage.setImage(imageLogo);

        vBoxLeft.getChildren().addAll(selectedImage, vBoxTop, vBoxFirstMiddle, vBoxSecondMiddle, vBoxBottom);
        vBoxLeft.setSpacing(15);
        vBoxLeft.setAlignment(Pos.BOTTOM_CENTER);

        btnSave.getStyleClass().add("btn-save");
        btnAddNewWorkplace.getStyleClass().add("btn-addNewWorkplace");
        btnBack.getStyleClass().add("btn-back");

        borderPane.setStyle("-fx-background-color: ghostwhite");

        scnMainWindow = new Scene(borderPane, 605, 400);
        scnMainWindow.getStylesheets().add("Resources/Styles/MainWindowStyles.css");
        stgMainWindow = new Stage();

        stgMainWindow.setScene(scnMainWindow);
        stgMainWindow.show();
        return stgMainWindow;
    }

    public void updateTable()
    {
        ArrayList<JobDay> list = new ArrayList<JobDay>();

        list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),"2016","04" );

        ObservableList<JobDay> dataSS = FXCollections.observableList(list);
        tableView.setItems(dataSS);

        Double totalHours;
        totalHours = databaseConnection.getTotalHours(logInView.whoIsLoggedIn().getUserId(),"2016","04");
        txtTotalHoursWorked.setText(String.valueOf(totalHours));

        Double moneyEarned;
        moneyEarned = databaseConnection.getMoneyEarned(logInView.whoIsLoggedIn().getUserId(), "2016", "04");
        txtTotalMoneyEarned.setText(String.valueOf(moneyEarned));
    }

    public void updateComboBox()
    {
        ObservableList<Workplace> workplaces = FXCollections.observableArrayList();
        ArrayList<Workplace> lis = databaseConnection.getWorkplaces2(logInView.whoIsLoggedIn().getUserId());
        System.out.println(lis.size());
       // workplaceCombobox = new ComboBox(workplacesList);

        for (int i = 0; i < lis.size(); i++)
        {
            workplaces.add(lis.get(i));
        }
        workplaceCombobox.setItems(workplaces);
    }


}
