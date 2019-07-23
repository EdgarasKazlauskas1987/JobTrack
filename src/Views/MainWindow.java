package Views;

import Controller.Database;
import Model.JobDay;
import Model.Workplace;
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
    ComboBox<Integer> yearCombobox;
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

    VBox vBox1;
    VBox vBox2;
    VBox vBox3;
    VBox vBox4;

    HBox bottomHbox;
    HBox leftHbox;

    HBox hBox1;
    HBox hBox2;

    Date date;

    ArrayList<Workplace> workplaces;

    ImageView selectedImage3;
    Image image3;

    Label workplacenameLabel;
    Label salaryLabel;

    TextField nameTextField;
    TextField salaryTextField;

    Button addButton;
    Button backButton2;

    Stage stage;

    Scene scene;

    VBox vBox;
    HBox hBox;
    HBox hBoxForTop;

    ImageView selectedImage2;
    Image image2;

    public Stage getMainWindowStage()
    {
        logInView = new Login();

        ObservableList<Workplace> workplacesList = FXCollections.observableArrayList();
        workplacesList.addAll(databaseConnection.getWorkplaces2(logInView.whoIsLoggedIn().getUserId()));
        workplaceCombobox = new ComboBox(workplacesList);

       // editComboBox();

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

        ObservableList<Integer> yearList  = FXCollections.observableArrayList();

        yearList.addAll(2016, 2017, 2018);
        yearCombobox = new ComboBox<>(yearList);
        yearCombobox.setValue(2016);

        ObservableList<String> monthList  = FXCollections.observableArrayList();
        monthList.addAll("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" );

        monthCombobox = new ComboBox<>(monthList);
        monthCombobox.setValue("April");


        btnShow.setOnAction(event -> {
            logInView = new Login();
            ArrayList<JobDay> list = new ArrayList<JobDay>();

            String year = myOwnYear();
            String month = myOwnMonth();

            list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),year,month );
            System.out.println(list.size());

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
        hBoxForTop = new HBox();
        hBoxForTop.getChildren().addAll(yearCombobox,monthCombobox,btnShow);

        hBoxForTop.setSpacing(78);
        vBoxRight.getChildren().addAll(hBoxForTop, tableView, leftHbox);
        borderPane.setRight(vBoxRight);

        btnSave.setOnAction(event -> {
            try {
                    logInView = new Login();
                    date = Date.valueOf(datePicker.getValue());

                    Workplace car = (Workplace) workplaceCombobox.getSelectionModel().getSelectedItem();
                    int howManyHourseWorked = Integer.parseInt(txtHoursWorked.getText());
                    double howMuchSalary = Double.parseDouble(txtPayPerHour.getText());

                    String data = String.valueOf(datePicker.getValue());
                    String partyear = data.substring(0, 4);
                    System.out.println(partyear);

                    String data2 = String.valueOf(datePicker.getValue());
                    String monthpart = data2.substring(5, 7);
                    System.out.println(monthpart);

                    System.out.println(data);

                    databaseConnection.addJobDay(logInView.whoIsLoggedIn().getUserId(), car.getName(), date,
                        howManyHourseWorked, howMuchSalary, partyear, monthpart);

                    String a = myOwnYear();
                    String b = myOwnMonth();

                    if (!a.equals(null) && !b.equals(null))
                    {
                        ArrayList<JobDay> list = new ArrayList<JobDay>();

                        String year = myOwnYear();
                        String month=myOwnMonth();

                        list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),year,month );
                        System.out.println(list.size());

                        ObservableList<JobDay> dataSS = FXCollections.observableList(list);
                        tableView.setItems(dataSS);

                        Double bybis;
                        bybis = databaseConnection.getTotalHours(logInView.whoIsLoggedIn().getUserId(),year,month);
                            String bybis2 = String.valueOf(bybis);
                            txtTotalHoursWorked.setText(bybis2);

                        Double pyzda;
                        pyzda = databaseConnection.getMoneyEarned(logInView.whoIsLoggedIn().getUserId(), year, month);
                        String pyzda2 = String.valueOf(pyzda);
                        txtTotalMoneyEarned.setText(pyzda2);
                    }

            else {
                        
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
            //getNewWorkplaceStage();
            newWorkplace = new AddWorkplace();
            newWorkplace.getNewWorkplaceStage();
            
        });

        bottomHbox = new HBox();
        bottomHbox.getChildren().addAll(btnSave, btnAddNewWorkplace, btnBack);
        borderPane.setBottom(bottomHbox);

        vBox1 = new VBox();
        vBox1.getChildren().addAll(lblWorkplace, workplaceCombobox);
        vBox2 = new VBox();
        vBox2.getChildren().addAll(lblCalendar, datePicker);
        vBox3 = new VBox();
        vBox3.getChildren().addAll(lblHoursWorked, txtHoursWorked);
        vBox4 = new VBox();
        vBox4.getChildren().addAll(lblPayHour, txtPayPerHour);

        selectedImage3 = new ImageView();
        //image3 = new Image(LogInView.class.getResourceAsStream("pic2.jpg"));
        //selectedImage3.setImage(image3);

        vBoxLeft.getChildren().addAll(selectedImage3, vBox1, vBox2, vBox3, vBox4);
        vBoxLeft.setSpacing(15);
        vBoxLeft.setAlignment(Pos.BOTTOM_CENTER);

        btnSave.setStyle("-fx-font-size: 25px; -fx-background-color: powderblue; -fx-border-color: black");
        btnAddNewWorkplace.setStyle("-fx-font-size: 25px; -fx-background-color: powderblue; -fx-border-color: black");
        btnBack.setStyle("-fx-font-size: 25px; -fx-background-color: powderblue; -fx-border-color: black");

        borderPane.setStyle("-fx-background-color: ghostwhite");

        scnMainWindow = new Scene(borderPane, 605, 400);
        stgMainWindow = new Stage();

        stgMainWindow.setScene(scnMainWindow);
        stgMainWindow.show();
        return stgMainWindow;
    }

    public String myOwnYear()
    {
        String year = "";


        // String wholeDate = year+"-"+month+"-"+"10";

        int yearFromBox =  yearCombobox.getSelectionModel().getSelectedItem();
       // String monthFromBox = monthCombobox.getSelectionModel().getSelectedItem();

        if (yearFromBox == 0)
        {
            year = "2016";
        }
        else if (yearFromBox == 2017){
            year = "2017";
        }
        else if (yearFromBox == 2018)
        {
            year = "2018";
        }
        else {
            year = "2016";
        }


        String wholeDate = year;
        return wholeDate;

    }

    public String myOwnMonth()
    {
        String month  = "";

        String monthFromBox =  monthCombobox.getSelectionModel().getSelectedItem();

        if (monthFromBox.equals("January"))
        {
            month = "01";
        }
        else if (monthFromBox.equals("February")){
            month = "02";
        }
        else if (monthFromBox.equals("March"))
        {
            month = "03";
        }

        else if (monthFromBox.equals("April"))
        {
            month = "04";
        }

        else if (monthFromBox.equals("May"))
        {
            month = "05";
        }

        else if (monthFromBox.equals("June"))
        {
            month = "06";
        }

        else if (monthFromBox.equals("July"))
        {
            month = "07";
        }

        else if (monthFromBox.equals("August"))
        {
            month = "08";
        }

        else if (monthFromBox.equals("September"))
        {
            month = "09";
        }

        else if (monthFromBox.equals("October"))
        {
            month = "10";
        }

        else if (monthFromBox.equals("November"))
        {
            month = "11";
        }

        else if (monthFromBox.equals("December"))
        {
            month = "12";
        }

        String wholeDate = month;
        return wholeDate;
    }

    public void updateTable()
    {
        ArrayList<JobDay> list = new ArrayList<JobDay>();

        list = databaseConnection.getJobdays(logInView.whoIsLoggedIn().getUserId(),"2016","04" );
        System.out.println(list.size());

        ObservableList<JobDay> dataSS = FXCollections.observableList(list);
        tableView.setItems(dataSS);

        Double bybis;
        bybis = databaseConnection.getTotalHours(logInView.whoIsLoggedIn().getUserId(),"2016","04");
        String bybis2 = String.valueOf(bybis);
        txtTotalHoursWorked.setText(bybis2);

        Double pyzda;
        pyzda = databaseConnection.getMoneyEarned(logInView.whoIsLoggedIn().getUserId(), "2016", "04");
        String pyzda2 = String.valueOf(pyzda);
        txtTotalMoneyEarned.setText(pyzda2);

    }

    public void editComboBox()
    {
        ObservableList<Workplace> workplacesList = FXCollections.observableArrayList();
        ArrayList<Workplace> lis = databaseConnection.getWorkplaces2(logInView.whoIsLoggedIn().getUserId());
        System.out.println(lis.size());
       // workplaceCombobox = new ComboBox(workplacesList);

        for (int i = 0; i < lis.size(); i++)
        {
            workplacesList.add(lis.get(i));
        }
        workplaceCombobox.setItems(workplacesList);
    }

    public Stage getNewWorkplaceStage()
    {
        nameTextField = new TextField();
        nameTextField.setPromptText("Workplace name");
        nameTextField.setStyle("-fx-font-size: 30px; -fx-border-color: black");
        salaryTextField = new TextField();
        salaryTextField.setPromptText("dkk/hour");
        salaryTextField.setStyle("-fx-font-size: 30px; -fx-border-color: black");

        addButton = new Button("Add");
        addButton.setStyle("-fx-font-size: 37px; -fx-background-color: powderblue; -fx-border-color: black");
        btnBack = new Button("Back");
        btnBack.setStyle("-fx-font-size: 37px; -fx-background-color: powderblue; -fx-border-color: black");

        btnBack.setOnAction(event1 -> {
            stage.close();
        });

        addButton.setOnAction(event -> {
            try {
            logInView = new Login();
            int userId = logInView.whoIsLoggedIn().getUserId();
            double salary = Double.parseDouble(salaryTextField.getText());

            databaseConnection.addWorkplace(userId, nameTextField.getText(), salary);
            editComboBox();
            stage.close(); 
            }

            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Please fill all the information!");
                alert.showAndWait();
            }
        });

        hBox = new HBox();
        hBox.getChildren().addAll(addButton, btnBack);

        selectedImage2 = new ImageView();
        image2 = new Image(Login.class.getResourceAsStream("pic2.jpg"));
        selectedImage2.setImage(image2);

        vBox = new VBox();
        vBox.getChildren().addAll( selectedImage2, nameTextField, salaryTextField,
                hBox);

        vBox.setStyle("-fx-background-color: ghostwhite");

        scene = new Scene(vBox, 248, 316);

        stage = new Stage();
        stage.setScene(scene);
        stage.show();

        return stage;
    }
}
