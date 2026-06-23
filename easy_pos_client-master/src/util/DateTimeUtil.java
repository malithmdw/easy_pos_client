
package util;

import control.EasyPosLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author malit
 */
public class DateTimeUtil {
    
    public static String getTodayDateDBFormat() 
    {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
        return ft.format(new Date());
    }
    
    public static String getMinusDaysFromTodayDBFormat(int days) {
        return LocalDate.now().minusDays(days).toString();
    }
    
    public static String getCurrentTimeHHmmss() 
    {
        LocalTime currentTime = LocalTime.now(); 
        return currentTime.toString().substring(0,8); 
    } 
    
    public static String getCurrentDateTimeDBFormat() 
    {
        LocalTime currentTime = LocalTime.now(); 
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        
        return ft.format(new Date()) + " " + currentTime.toString().substring(0,8); 
    } 
    
    public static String dateFormatForDB(String date){
        String dt=""+date.substring(6,10)+"-"+date.substring(3,5)+"-"+date.substring(0,2)+"";       
        return dt;
    }
    public static String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
    
    public static Date todayDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
    
    public static Date getDateFromDBFormatString(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            try {
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
                return ft.parse(dateString);
            } catch (ParseException e) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            }
        }
        
        return null;
    }
    
    public static String getDBFormatStringFromDate(Date date) {
        if (date != null) {
            try {
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                return ft.format(date);
            } catch (Exception e) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            }
        }
        return null;
    }
    
}
