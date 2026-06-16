package dbOperations;

import dataModels.StockItemDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ItemMovement {
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
    
    String getItemMovementData(String itmcode){
        int arr[]=new int[4];
        try{
            
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM item_movement WHERE item_code='"+itmcode+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            String s="";
            while (rs.next() != false) {
                
                s=(rs.getInt(2))+"/"+(rs.getInt(3))+"/"+(rs.getInt(4))+"/"+(rs.getInt(5))+"/"+(new DecimalFormat("#0.0").format((rs.getInt(2)+rs.getInt(3)+rs.getInt(4))/3));        
                return s;
            }
            return "e/e/e/e/e";     
            
        }catch(Exception e){
            return "e/e/e/e/e";// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst!=null){
                    pst.close();
                }
                
            }catch(Exception e){
                
            }
        }      
    }
    
    
//    
//    public ArrayList<StockItemDataModel> getStockAndItemMovement(int situation,String searchInput1,String searchInput2){
//        try{
//            ArrayList<StockItemDataModel> list=new ArrayList<StockItemDataModel>();
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query="SELECT * FROM stoke" ;            
//                switch(situation){                
//                case 11:
//                    query="SELECT s.item_code, s.item_name, s.sub_name, s.category, s.qty,purchase_price, "
//                            + "s.selling_price, s.discount, s.supp_code1, s.supp_code2, s.coment, "
//                            + "s.exp_date,i.this_month,i.month_1,i.month_2,i.month_3\n" 
//                            +"FROM item_movement i,stoke s WHERE i.item_code=s.item_code AND s.qty < s.minimum_stoke" ;break;
//                case 12:
//                    query="SELECT s.item_code, s.item_name, s.sub_name, s.category, s.qty,purchase_price,"
//                            + " s.selling_price, s.discount, s.supp_code1, s.supp_code2, s.coment,"
//                            + " s.exp_date,i.this_month,i.month_1,i.month_2,i.month_3\n" 
//                            +"FROM item_movement i,stoke s WHERE i.item_code=s.item_code AND s.qty < s.minimum_stoke "
//                            + "AND (s.supp_code1='"+searchInput1+"' OR s.supp_code2='"+searchInput1+"')" ;break;
//               
//                default:
//                    query="SELECT * FROM stoke" ;break;
//            }
//            
//            
//             
//            
//            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
//                StockItemDataModel s=new StockItemDataModel();
//                
//                s.setItem_code(rs.getString(1));
//                s.setItem_name(rs.getString(2));
//                s.setSub_name(rs.getString(3));
//                s.setCategory(rs.getString(4));
//                s.setQty(Integer.parseInt(rs.getString(5)));
//                s.setPurchase_price(Double.parseDouble(rs.getString(6)));
//                s.setSelling_price(Double.parseDouble(rs.getString(7)));
//                s.setDiscount(Double.parseDouble(rs.getString(8)));
//                s.setSupp_code1(rs.getString(9));
//                s.setSupp_code2(rs.getString(10));
//                s.setComment(rs.getString(11));
//                s.setExp_date(rs.getString(12));
//                //tempery assignments
//                s.setLast_edit_date(new Integer(rs.getInt(16)).toString());//last month selling rate
//                s.setLast_editor(df.format((rs.getInt(14)+rs.getInt(14)+rs.getInt(14))/3));//avg selling rate of last 3 month
//                s.setMinimumQtyForAlert(rs.getInt(13));//this month selling rate
//                
//                list.add(s);
//                
//                
//            }
//            return list;
//            
//            
//        }catch(Exception e){
//            return null;// other situation
//                    
//        }finally{
//            try{
//                
//                if(con!=null){
//                    con.close();
//                }
//                if(pst!=null){
//                    pst.close();
//                }
//                
//            }catch(Exception e){
//                
//            }
//        }      
//    }
    
}
