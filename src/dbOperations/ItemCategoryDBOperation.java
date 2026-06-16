/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbOperations;

import dataModels.ItemCategory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author malit
 */
public class ItemCategoryDBOperation {
    
    String url="jdbc:mysql://localhost:3306/pharmacy_management_system"; //change Port No & Database
    String username="root";
    String password="";
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;
    
    String driver = "com.mysql.jdbc.Driver";
    DecimalFormat df = new DecimalFormat("#0.00");
    /*
    month_1 - 
    month_2 -
    month_3 -last month  
    */
    /*cc
    void malith(){
       try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            String query1="SELECT item_code FROM stoke";
            pst=(PreparedStatement)con.prepareStatement(query1);          
            rs=pst.executeQuery();
            while (rs.next() != false) {
                addMovementItem(rs.getString(1),"2015-10-17");
            }
       }catch(Exception e){
           
       }
    }*/
    public boolean updateTableForNewMonth(String date){        
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            String query1="SELECT update_date FROM item_movement";
            pst=(PreparedStatement)con.prepareStatement(query1);          
            rs=pst.executeQuery();
            String tbleDate="";
            while (rs.next() != false) {
                tbleDate=rs.getString(1);
            }
            
            String thisMonth=date.substring(5, 7);//2015-10-17 -> 10
            String tbleMonth=tbleDate.substring(5, 7);
            if(thisMonth.equals(tbleMonth)){//before updated for this month
                
            }else{//data not has been updated for this month.now should be updated
                String query2="SELECT * FROM item_movement";
                pst=(PreparedStatement)con.prepareStatement(query2);          
                rs=pst.executeQuery();
                while (rs.next() != false) {
                    String itemCode=rs.getString(1);
                    int month1=rs.getInt(2);
                    int month2=rs.getInt(3);
                    int month3=rs.getInt(4);
                    int monthThis=rs.getInt(5);
                    String query3="UPDATE item_movement SET month_1='"+month2+"',month_2='"+month3+"',month_3='"+monthThis+"',this_month='0',update_date='"+date+"' WHERE item_code='"+itemCode+"' ";
                    pst=(PreparedStatement)con.prepareStatement(query3);          
                    pst.executeUpdate();
                }
            }
            return true;
        }catch(Exception e){
            return false;
            
            
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                
            }
        }     
    }
    public boolean addMovementItem(String itemCode,String date){
        
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO item_movement VALUES (?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);
            
            pst.setString(1,itemCode); //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setInt(2,0);
            pst.setInt(3,0);
            pst.setInt(4,0);
            pst.setInt(5,0);
            pst.setString(6,date);
            
            pst.executeUpdate();            
            return true;
            
        }catch(Exception e){
            return false; 
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                
            }
        }        
        
    }
    public boolean updateMovement(String itmCode,int qty){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            String query1="SELECT this_month FROM item_movement WHERE item_code='"+itmCode+"'";
            pst=(PreparedStatement)con.prepareStatement(query1);          
            rs=pst.executeQuery();
            int curQty=0;
            while (rs.next() != false) {
                curQty=curQty+rs.getInt(1);
            }
            qty=qty+curQty;
            String query2="UPDATE item_movement SET this_month='"+qty+"' WHERE item_code='"+itmCode+"'"; //add new movement data
            pst=(PreparedStatement)con.prepareStatement(query2); 
            pst.executeUpdate();
            
            return true;
        }catch(Exception e){
           
            return false;
            
            
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                
            }
        }    
    }
    public boolean deleteMovementItem(String itmCode){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="DELETE FROM item_movement WHERE item_code='"+itmCode+"'";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            pst.executeUpdate();
            
            return true;
        }catch(Exception e){
            return false;
            
            
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                
            }
        }    
    }
    
    public List<ItemCategory> getAllActiveItemCategoryList(){
        List<ItemCategory> list = new ArrayList<>();
        
        try{
            
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT `category_id`, `institute_id`, `cat_name`, `cat_name_sin`, `cat_name_tam`, `description`, `enable` FROM `category` WHERE `enable` = 1" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {
                
                ItemCategory category = new ItemCategory();
                category.setCategoryId(rs.getInt(1));
                category.setCategoryName(rs.getString(3));
                category.setCategoryNameSin(rs.getString(4));                
                category.setCategoryNameTam(rs.getString(5));
                list.add(category);
            }
            return list;     
            
        }catch(ClassNotFoundException | SQLException e){
            return null;
                    
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
    
}
