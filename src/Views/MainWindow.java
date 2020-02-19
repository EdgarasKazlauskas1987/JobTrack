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
import java.util.stream.Collectors;


public class MainWindow {

    AddWorkplace newWorkplace;
    Database databaseConnection = Database.getDBInstance();
    MainWindowController mainWindowController;
    Login logInView;

    Workplace workplace;

    private Label lblWorkplace = new Label("Workplace");
    private Label lblCalendar = new Label("Choose date");
    private Label lblHoursWorked = new Label("Hours Worked");
    private Label lblPayHour = new Label("Pay/h");

    private Label lblTotalHoursWorked = new Label("Total hourse worked");
    private Label lblTotalMoneyEarned = new Label("Total money earned");

    private Button btnSave = new Button("Save new Job day");
    private Button btnAddNewWorkplace = new Button("Add new workplace");
    private Button btnBack = new Button("Back");
    private Button btnShow = new Button("Show");

    private ComboBox workplaceCombobox;
    private ComboBox<String> yearCombobox;
    private ComboBox<String> monthCombobox;

    private TextField txtHoursWorked = new TextField();
    private TextField txtPayPerHour = new TextField();
    private TextField txtTotalHoursWorked = new TextField();
    private TextField txtTotalMoneyEarned = new TextField();

    private DatePicker datePicker;
    private TableView<JobDay> tableView;

    BorderPane borderPane;
    Scene scnMainWindow;
    Stage stgMainWindow;

    private VBox vBoxLeft = new VBox();
    private VBox vBoxRight = new VBox();

    private VBox vBoxTop = new VBox();
    private VBox vBoxFirstMiddle = new VBox();
    private VBox vBoxSecondMiddle = new VBox();
    private VBox vBoxBottom = new VBox();

    private HBox hBoxBottom = new HBox();
    private HBox hBoxForTop = new HBox();
    private HBox leftHbox;

    Date date;

    private ArrayList<Workplace> workplaces;

    private ImageView selectedImage;
    private Image imageLogo = new Image(getClass().getResourceAsStream("/logo.png"));

    public Stage getMainWindowStage()
    {
        logInView = new Login();

        ObservableList<Workplace> workplacesList = FXCollections.observableArrayList();
        workplacesList.addAll(databaseConnection.getWorkplaces(logInView.whoIsLoggedIn().getUserId()));
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
        yearCombobox.setValue(Utilities.getCurrentYear());

        ObservableList<String> monthList  = FXCollections.observableArrayList();
        monthList.addAll("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" );

        monthCombobox = new ComboBox<>(monthList);
        monthCombobox.setValue(Utilities.getCurrentMonth());

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
        ArrayList<Workplace> list = databaseConnection.getWorkplaces(logInView.whoIsLoggedIn().getUserId());
        System.out.println(list.size());
       // workplaceCombobox = new ComboBox(workplacesList);

        //list.stream().forEach(w -> workplaces.add(w));
        
        for (int i = 0; i < list.size(); i++)
        {
            workplaces.add(list.get(i));
        }
        workplaceCombobox.setItems(workplaces);
    }
}
