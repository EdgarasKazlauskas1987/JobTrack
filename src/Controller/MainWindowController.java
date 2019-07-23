/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Edgaras
 */
public class MainWindowController {
    
    private static Map<String, String> months = new HashMap<String, String>();
    
    public ObservableList<String> populateYear()
    {
        ObservableList<String> years  = FXCollections.observableArrayList();
        
        for(int year = 2015; year<= Calendar.getInstance().get(Calendar.YEAR); year++)
        {
            years.add(year+"");
        }
        return years;
    }
    
    public String getMonth(String month)
    {
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");
        
        return months.get(month);   
    }
}
