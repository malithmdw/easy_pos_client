
package dbOperations;

import dataModels.BillItemDataModel;
import dataModels.InventoryRecordDataModel;
import dataModels.SupplierDataModel;
import dataModels.SupplyVariables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SuppliesDBOperation {
    DecimalFormat df=new DecimalFormat("#0.00");
    
    String url="jdbc:mysql://localhost:3306/pharmacy_management_system"; //change Port No & Database
    String username="root";
    String password="";
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;   
    String driver = "com.mysql.jdbc.Driver";
    
    public boolean dispenseItems(List<BillItemDataModel> items){
        
        for (BillItemDataModel item : items) {
            double dispensableStock = item.getQty();
            // fetch old inventory record
            InventoryRecordDataModel irdm = getOldInventoryRecord(item.getItemId());
            
            while(irdm != null && dispensableStock != 0)
            {
                // check abale to reduce stock
                if (irdm.getRemainingStock() >= dispensableStock) {
                    double remainingStock = irdm.getRemainingStock() - dispensableStock;
                    dispenseFromInventoryRecord(irdm.getRecordId(), remainingStock);
                    break;
                }
                // else - reduce max from old record and reduce rest from next record
                else
                {
                    dispenseFromInventoryRecord(irdm.getRecordId(), 0);
                    
                    dispensableStock = dispensableStock - irdm.getRemainingStock();
                    irdm = getOldInventoryRecord(item.getItemId());                    
                }
            }
        }
        return false;
    }
    
    public InventoryRecordDataModel getOldInventoryRecord(int itemCode){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            String query="SELECT rec_id, insert_date_time, item_code, invoice_id, purchase_unit_price, "
                    + "selling_unit_price, qty, batch_num, exp_date, remaining_stock, "
                    + "added_by, added_date, updated_by, updated_date, MIN(insert_date_time) "
                    + " FROM inventory WHERE remaining_stock NOT 0 ";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
            rs=pst.executeQuery();
            while (rs.next() != false) {
                InventoryRecordDataModel irdm=new InventoryRecordDataModel();
                
                irdm.setRecordId(rs.getInt(1));
                irdm.setInsertDateTime(rs.getString(2));
                irdm.setItemcode(rs.getInt(3));
                irdm.setInvoiceId(rs.getInt(4));
                irdm.setPurchaseUnitPrice(Double.parseDouble(rs.getString(5)));
                irdm.setSellingUnitPrice(Double.parseDouble(rs.getString(6)));
                irdm.setQty(rs.getDouble(7));
                irdm.setBatchNum(rs.getString(8));
                irdm.setExpDate(rs.getString(9));
                irdm.setRemainingStock(rs.getDouble(10));
                irdm.setAddedBy(rs.getString(11));
                irdm.setAddedDate(rs.getString(12));
                irdm.setUpdatedBy(rs.getString(13));
                irdm.setUpdatedDate(rs.getString(14));
            }
            
            return null;
        }catch(ClassNotFoundException | SQLException e){
            return null;
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
    
    public boolean dispenseFromInventoryRecord(int recordId, double remainingStock){
        
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="UPDATE inventory SET remaining_stock='"+remainingStock+"' WHERE record_id='"+recordId+"'";
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public boolean editSupplier(BillItemDataModel item){
//        try{
//            Class.forName(DBCons.LOCAL_DB_DRIVER);
//            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
//            
//            String query="UPDATE suppliers SET supplier_name='"+sprv.getSupplierName()+"',"
//                    + "supplier_address='"+sprv.getSupplierAddress()+"',company='"+sprv.getCompany()+"',phone_no1='"+sprv.getPhoneNo1()+"',nic_no='"+sprv.getNIC()+"',"
//                    + "acc_no='"+sprv.getAccNo()+"',bank='"+sprv.getBank()+"',phone_no='"+sprv.getPhoneNo()+"' WHERE supplier_code='"+suppCode+"'"; //add data
//            pst=(PreparedStatement)con.prepareStatement(query);    
//           
//            
//            pst.executeUpdate();
//            
//            return true;
//        }catch(ClassNotFoundException | SQLException e){           
//            return false;
//        }finally{
//            try{
//                if(pst!=null){
//                    pst.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(SQLException e){
//                
//            }
//        }
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   //supplieRs
    public boolean checkSupplierExist(String suppCode){
        try{
            Class.forName(driver); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="SELECT supplier_code FROM suppliers";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
            rs=pst.executeQuery();
            while (rs.next() != false) {         
                if(suppCode.equals(rs.getString(1))){
                    return true;
                }
            
            }
            
            return false;
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
    
    public String getSupplierName(String suppCode){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            String query="SELECT supplier_name FROM suppliers WHERE supplier_code='"+suppCode+"'";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
            rs=pst.executeQuery();
            while (rs.next() != false) {         
                return (rs.getString(1));
            }
            
            return "";
        }catch(Exception e){
            return "";
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
    public boolean addSupplier(SupplierDataModel sprv){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO suppliers VALUES (?,?,?,?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);
           // java.sql.Date sqlDate = new java.sql.Date(sd.getSupp_dob());
           
            
            pst.setInt(1,sprv.getSupplierCode());        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,sprv.getSupplierName());
            pst.setString(3,sprv.getSupplierAddress());
            pst.setString(4,sprv.getCompany());
            pst.setString(5,sprv.getPhoneNo1());
            pst.setString(6,sprv.getNIC());
            pst.setString(7,sprv.getAccNo());
            pst.setString(8,sprv.getBank());
            pst.setString(9,sprv.getPhoneNo());
            
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
    public boolean editSupplier(SupplierDataModel sprv,String suppCode){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="UPDATE suppliers SET supplier_name='"+sprv.getSupplierName()+"',"
                    + "supplier_address='"+sprv.getSupplierAddress()+"',company='"+sprv.getCompany()+"',phone_no1='"+sprv.getPhoneNo1()+"',nic_no='"+sprv.getNIC()+"',"
                    + "acc_no='"+sprv.getAccNo()+"',bank='"+sprv.getBank()+"',phone_no='"+sprv.getPhoneNo()+"' WHERE supplier_code='"+suppCode+"'"; //add data
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
    public boolean deleteSupplier(String suppCode){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable            
            String query="DELETE FROM suppliers WHERE supplier_code='"+suppCode+"'"; //add data
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
    
    public ArrayList<SupplierDataModel> getSuppliers(int situation,String searchInput1,String searchInput2) {
        try{
            ArrayList<SupplierDataModel> supprList=new ArrayList<SupplierDataModel>();
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM suppliers";
            switch(situation){
                case 0:query="SELECT * FROM suppliers";break;
                case 1:query="SELECT * FROM suppliers WHERE supplier_code LIKE '"+searchInput1+"%'";break;
                case 2:query="SELECT * FROM suppliers WHERE supplier_name LIKE '"+searchInput1+"%'";break;
                case 3:query="SELECT * FROM suppliers WHERE nic_no LIKE '"+searchInput1+"%'";break;    
                case 4:query="SELECT * FROM suppliers WHERE company LIKE '"+searchInput1+"%'";break;
                default:query="SELECT * FROM suppliers";break;
            }
            
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                SupplierDataModel srv=new SupplierDataModel();
                
                srv.setSupplierCode(rs.getInt(1));
                srv.setSupplierName(rs.getString(2));
                srv.setSupplierAddress(rs.getString(3));
                srv.setCompany(rs.getString(4));
                srv.setPhone_no1(rs.getString(5));
                srv.setNIC(rs.getString(6));
                srv.setAccNo(rs.getString(7));
                srv.setBank(rs.getString(8));
                srv.setPhoneNo(rs.getString(9));
                
                supprList.add(srv);                                
            }
            return supprList;
            
            
        }catch(Exception e){
            return null;// other situation
                    
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
   
    
    
    
    
   //supply
    public boolean addSupply(SupplyVariables supplyVar){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO supplies VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);
           // java.sql.Date sqlDate = new java.sql.Date(sd.getSupp_dob());
           
            
            pst.setString(1,supplyVar.getDate());        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,supplyVar.getSupplier_code());
            pst.setString(3,supplyVar.getInvoice_no());
            pst.setString(4,df.format(supplyVar.getTotal()));
            pst.setString(5,df.format(supplyVar.getDiscount()));
            pst.setString(6,df.format(supplyVar.getNet_total()));
            pst.setInt(7,supplyVar.getSet_alert());
            pst.setString(8,df.format(supplyVar.getPaid()));
            pst.setString(9,df.format(supplyVar.getBalance()));
            pst.setString(10,supplyVar.getDue_to_pay());
            pst.setString(11,supplyVar.getAlert_date());
            pst.setString(12,supplyVar.getInputMethod());
            pst.setString(13,supplyVar.getEditBy());
            
            
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
    public boolean editSupply(SupplyVariables supplyVar){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="UPDATE supplies SET date='"+supplyVar.getDate()+"',"
                    + "supplier_code='"+supplyVar.getSupplier_code()+"',"
                    + "total='"+df.format(supplyVar.getTotal())+"',discount='"+df.format(supplyVar.getDiscount())+"',"
                    + "net_total='"+df.format(supplyVar.getNet_total())+"',set_alert='"+supplyVar.getSet_alert()+"',paid='"+df.format(supplyVar.getPaid())+"',"
                    + "balance='"+df.format(supplyVar.getBalance())+"',due_to_pay='"+supplyVar.getDue_to_pay()+"',alert_date='"+supplyVar.getAlert_date()+"',input_method='"+supplyVar.getInputMethod()+"',edit_by='"+supplyVar.getEditBy()+"' "
                    + "WHERE invoice_no='"+supplyVar.getInvoice_no()+"'"; //add data
            
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
    public boolean deleteSupply(String invoiceNo){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable            
            String query="DELETE FROM supplies WHERE invoice_no='"+invoiceNo+"'"; //add data
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
    public ArrayList<SupplyVariables> getSuppliesData(int situation,String searchInput1,String searchInput2,String searchInput3) {
        try{
            ArrayList<SupplyVariables> list=new ArrayList<SupplyVariables>();
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM supplies " ;
            switch(situation){
                case 0:query="SELECT * FROM supplies" ;break;
                case 1:query="SELECT * FROM supplies WHERE date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"'" ;break;
                case 2:query="SELECT * FROM supplies WHERE (date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"') AND supplier_code='"+searchInput3+"'" ;break;
                case 3:query="SELECT * FROM supplies WHERE supplier_code='"+searchInput3+"'" ;break;
                default:query="SELECT * FROM supplies" ;break;
            }
   
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                SupplyVariables suppV=new SupplyVariables();
                
                suppV.setDate(rs.getString(1));
                suppV.setSupplier_code(rs.getString(2));
                suppV.setInvoice_no(rs.getString(3));
                suppV.setTotal(Double.parseDouble(rs.getString(4)));
                suppV.setDiscount(Double.parseDouble(rs.getString(5)));
                suppV.setNet_total(Double.parseDouble(rs.getString(6)));
                suppV.setSet_alert(Integer.parseInt(rs.getString(7)));
                suppV.setPaid(Double.parseDouble(rs.getString(8)));
                suppV.setBalance(Double.parseDouble(rs.getString(9)));
                suppV.setDue_to_pay(rs.getString(10));
                suppV.setAlert_date(rs.getString(11));
                suppV.setInputMethod(rs.getString(12));
                suppV.setEditBy(rs.getString(13));
                   
                list.add(suppV); 
            }
            return list;           
            
        }catch(Exception e){
            System.out.println(e);
            return null;// other situation          
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
    
    public String alertForPay(String date){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="SELECT * FROM supplies WHERE alert_date='"+date+"'";
            pst=(PreparedStatement)con.prepareStatement(query);            
            
            rs=pst.executeQuery();
            String msg="";
            while (rs.next() != false) {         
                if(rs.getInt(7)==1){
                    msg=msg+"* Rs."+rs.getString(9)+"  Due to Pay on  "+rs.getString(10)+"   For Supplier "+rs.getString(2)+".   Invoice No: "+rs.getString(3)+".\n ";
                }            
            }
            
            return msg;
        }catch(Exception e){
            return "";   
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
    
   
    
    //temp invoice
    public boolean addNewTemp(String un,String in,String sc){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO temp_inv VALUES (?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);
           // java.sql.Date sqlDate = new java.sql.Date(sd.getSupp_dob());
           
            
            pst.setString(1,un);        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,in);
            pst.setString(3,sc);
            pst.setString(4,"0.00");
            pst.setString(5,"0.00");
            pst.setString(6,"0.00");
                 
            
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
    public boolean editTemp(double total,String dis,String netTot,String usrNme){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM temp_inv WHERE user_name='"+usrNme+"' " ;               
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            double tot=total;
            while (rs.next() != false) {
                tot=tot+Double.parseDouble(rs.getString(4)); 
            }
            String st1=df.format(tot);
            query="UPDATE temp_inv SET total='"+st1+"' WHERE user_name='"+usrNme+"'"; //add data
            
            pst=(PreparedStatement)con.prepareStatement(query); 
            pst.executeUpdate();
            
            return true;           
            
        }catch(Exception e){
            return false;// other situation          
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
    public SupplyVariables getTemp(String usrNme){
        try{
            SupplyVariables svForTmp=new SupplyVariables();
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM temp_inv WHERE user_name='"+usrNme+"' " ;
               
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                svForTmp.setInvoice_no(rs.getString(2));
                svForTmp.setSupplier_code(rs.getString(3));
                svForTmp.setTotal(Double.parseDouble(rs.getString(4)));
                svForTmp.setDiscount(Double.parseDouble(rs.getString(5)));
                svForTmp.setNet_total(Double.parseDouble(rs.getString(6))); 
                return svForTmp;
            }
            return null;           
            
        }catch(Exception e){
            return null;// other situation          
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
    public boolean deleteTemp(String invNo){
        try{
            Class.forName(driver);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable            
            String query="DELETE FROM temp_inv WHERE inv_no='"+invNo+"'"; //add data
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
    
    

}        

