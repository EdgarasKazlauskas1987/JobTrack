/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author Edgaras
 */
public class Queries {
    
    public static final String ADD_NEW_USER = "INSERT INTO useraccount VALUES(null, ?, ?, ? )";
    public static final String USER_AUTHORIZED = "SELECT * FROM useraccount WHERE userName = ? AND password= ?";
    public static final String ADD_WORKPLACE = "INSERT INTO workplace VALUES(null, ?, ?, ?)";
    public static final String GET_WORKPLACES = "SELECT * FROM workplace WHERE userId = ?";
    public static final String GET_SALARY = "SELECT salaryperhour FROM workplace WHERE workplacenames = ?";
    public static final String ADD_JOBDAY = "INSERT INTO jobday VALUES(null, ?, ?, ?, ? , ?, ?, ?, ?)";
    public static final String GET_JOBDAYS = "SELECT * FROM jobday WHERE userId = ? AND yearpart = ? AND monthpart = ? ";
    public static final String GET_MONEY_EARNED = "SELECT SUM(totalmoney) FROM jobday WHERE userId = ? AND yearpart = ? AND monthpart = ?";
    public static final String GET_TOTAL_HOURS = "SELECT SUM(hourseWorked) FROM jobday WHERE userId = ? AND yearpart = ? AND monthpart = ?";
}
