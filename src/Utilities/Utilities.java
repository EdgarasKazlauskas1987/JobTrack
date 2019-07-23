/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Edgaras
 */
public class Utilities {
    
    public String getCurrentYear()
    {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        
        return String.valueOf(year);
    }
    
    public String getCurrentMonth()
    {
        Calendar currentDate = Calendar.getInstance();
        String month = currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        
        return month;
    }
}
