
package dbOperations;

import dataModels.ItemCategory;
import dataModels.LogEvent;
import dataModels.MeasureUnit;
import dataModels.StockItemDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DateTimeUtil;

public class EventLogDBOperation {
    
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;
    
    public void addSaleEventLog(int instituteId, String userName) {
        // Check record exist
        int eventLogRecordId = getEventLogID(instituteId, userName, util.DateTimeUtil.getTodayDateDBFormat(), LogEvent.DO_SALE);
        
        // Add or update
        if (eventLogRecordId > 0) {
            updateEventlogCount(eventLogRecordId);
        }
        else
        {
            addEventLog(instituteId, userName, util.DateTimeUtil.getTodayDateDBFormat(), LogEvent.DO_SALE);
        }        
    }
    
    public int getEventLogID(int instituteId, String userName, String date, LogEvent event){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
     
            String query="SELECT record_id FROM change_log WHERE institute_id = '"+instituteId+"' AND user_name = '"+userName+"' AND date = '"+date+"' AND event = '"+event.name()+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                               
                return rs.getInt(1);
            
            }
            return 0;
            
            
        }catch(ClassNotFoundException | SQLException e){
            
            return -1;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst!=null){
                    pst.close();
                }
                
            }catch(SQLException e){
                
            }
        }
    }
    
    
    public boolean updateEventlogCount(int recordId){
        
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="UPDATE change_log SET event_count=event_count+1 WHERE record_id='"+recordId+"'";
            pst=(PreparedStatement)con.prepareStatement(query2);
            pst.executeUpdate();           
            
            return true;
        }catch(ClassNotFoundException | SQLException e){
            return false;           
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(SQLException e){                
            }
        }         
    }
    
    public boolean addEventLog(int instituteId, String userName, String date, LogEvent event){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query="INSERT INTO change_log(institute_id, user_name, date, timestamp, event, event_count) "
                    + "VALUES (?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);
            
            pst.setInt(1,instituteId); //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,userName);
            pst.setString(3,date);
            pst.setString(4,DateTimeUtil.getTodayDateDBFormat() + " " + DateTimeUtil.getCurrentTimeHHmmss());
            pst.setString(5,event.name());
            pst.setInt(6,1);
            
            pst.executeUpdate();            
            return true;
            
        }catch(ClassNotFoundException | SQLException e){
            return false; 
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(SQLException e){
                
            }
        } 
    }
    
}
