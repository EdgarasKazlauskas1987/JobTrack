/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Edgaras
 */
public class MainWindowController {
    
    public ObservableList<String> populateYear()
    {
        ObservableList<String> years  = FXCollections.observableArrayList();
        
        for(int year = 2015; year<=Calendar.getInstance().get(Calendar.YEAR); year++)
        {
            years.add(year+"");
        }
        return years;
    }
}
