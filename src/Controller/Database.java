package Controller;

import Model.JobDay;
import Model.User;
import Model.Workplace;
import Utilities.Queries;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Database {

    private static Database databaseInstance = null;
    private java.sql.Connection conn = null;

    private Database() 
    {
        String DB_URL = "jdbc:mysql://localhost/jobtrack";
        String USER = "root";
        String PASS = "root";
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Database getDBInstance()
    {
        if(databaseInstance == null)
            databaseInstance = new Database();
        
        return databaseInstance;
    }

    public void addNewUser(String fullName, String userName, String password) 
    {
        String sql = Queries.ADD_NEW_USER;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);

            int numberOfRows = preparedStatement.executeUpdate();
            } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public User retrieveUserIfAllowedToLogIn(String userName, String password) 
    {
        String sql = Queries.USER_AUTHORIZED;
        User user = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                int userId = resultSet.getInt(1);
                String fullName = resultSet.getString(2);
                user = new User(userId, fullName, userName, password);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return user;
    }

    public void addWorkplace(int userId, String name, double salary) 
    {
        String sql = Queries.ADD_WORKPLACE;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, salary);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Workplace> getWorkplaces(int userId) 
    {
        ArrayList<Workplace> workplaceList = new ArrayList<>();
        try 
        {
            String sql = Queries.GET_WORKPLACES;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Workplace workplace = new Workplace(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                        resultSet.getInt(4));
                workplaceList.add(workplace);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return workplaceList;
    }

    public Double getSalary(String workplacenames) 
    {
        double out = 0;
        try 
        {
            String sql = Queries.GET_SALARY;
            //Statement statement=conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, workplacenames);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                out = resultSet.getDouble(1);
            } 
            else 
            {
                out = 0;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return out;
    }

    public void addJobDay(int userId, String workplacename, java.sql.Date dates, int hourseWorked, double payperhour,
    String yearpart, String monthpart)
    {
        //double sum = hourseWorked * payperhour;
        String sql = Queries.ADD_JOBDAY;
        try 
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, workplacename);
            preparedStatement.setDate(3, dates);
            preparedStatement.setInt(4, hourseWorked);
            preparedStatement.setDouble(5, payperhour);
            preparedStatement.setString(6, yearpart);
            preparedStatement.setString(7, monthpart);
            preparedStatement.setDouble(8, payperhour * hourseWorked);

            int numberOfRows = preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<JobDay> getJobdays(int userId, String year, String month ) 
    {
        ArrayList<JobDay> jobDaysList = new ArrayList<>();
        try 
        {
            String sql = Queries.GET_JOBDAYS;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, year);
            preparedStatement.setString(3, month);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {
                JobDay jobDay = new JobDay(resultSet.getString(3),
                        resultSet.getDate(4), resultSet.getInt(5), resultSet.getDouble(6)
                );
                jobDaysList.add(jobDay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobDaysList;
    }

    public Double getMoneyEarned(int userId, String year, String month)
    {
        double out = 0;
        try 
        {
            String sql = Queries.GET_MONEY_EARNED;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, year);
            preparedStatement.setString(3, month);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                out = resultSet.getDouble(1);
            } 
            else 
            {
                out = 0;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return out;
    }

    public Double getTotalHours(int userId, String year, String month)
    {
        double out = 0;
        try {
            String sql = Queries.GET_TOTAL_HOURS;

            //Statement statement=conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, year);
            preparedStatement.setString(3, month);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) 
            {
                out = resultSet.getDouble(1);
            } 
            else 
            {
                out = 0;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return out;
    }
}










