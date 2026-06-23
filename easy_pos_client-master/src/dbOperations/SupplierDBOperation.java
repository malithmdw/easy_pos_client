
package dbOperations;

import dataModels.SupplierDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDBOperation {
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;
    
    private ArrayList<SupplierDataModel> getSuppliers(String whereClause) {
        try{
            ArrayList<SupplierDataModel> supprList=new ArrayList<>();
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="SELECT supplier_code, institute_id, supplier_name, supplier_address, company, "
                    + "phone_no1, nic_no, acc_no, bank, phone_no FROM suppliers WHERE " + whereClause;
            
            
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                SupplierDataModel srv=new SupplierDataModel();
                
                srv.setSupplierCode(rs.getInt(1));
                srv.setSupplierName(rs.getString(3));
                srv.setSupplierAddress(rs.getString(4));
                srv.setCompany(rs.getString(5));
                srv.setPhone_no1(rs.getString(6));
                srv.setNIC(rs.getString(7));
                srv.setAccNo(rs.getString(8));
                srv.setBank(rs.getString(9));
                srv.setPhoneNo(rs.getString(10));
                
                supprList.add(srv);                                
            }
            return supprList;
            
            
        }catch(ClassNotFoundException | SQLException e){
            return null;// other situation
                    
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
    
    public ArrayList<SupplierDataModel> getAllSuppliers(int instituteId) {
        String whereClause = " status = 1 AND institute_id = " + instituteId;
        return getSuppliers(whereClause);
    }
    
    
    
    
    
    
    public boolean editSupplier(SupplierDataModel sprv){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="UPDATE suppliers SET supplier_name='"+sprv.getSupplierName()+"',"
                    + "supplier_address='"+sprv.getSupplierAddress()+"',company='"+sprv.getCompany()+"',phone_no1='"+sprv.getPhoneNo1()+"',nic_no='"+sprv.getNIC()+"',"
                    + "acc_no='"+sprv.getAccNo()+"',bank='"+sprv.getBank()+"',phone_no='"+sprv.getPhoneNo()+"' WHERE supplier_code='"+sprv.getSupplierCode()+"'"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
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
    
   //supplieRs
    public boolean checkSupplierExist(String suppCode){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="SELECT supplier_code FROM suppliers";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
            rs=pst.executeQuery();
            while (rs.next() != false) {         
                if(suppCode.equals(rs.getString(1))){
                    return true;
                }
            
            }
            
            return false;
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
    
    public String getSupplierName(String suppCode){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            String query="SELECT supplier_name FROM suppliers WHERE supplier_code='"+suppCode+"'";
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
            rs=pst.executeQuery();
            while (rs.next() != false) {         
                return (rs.getString(1));
            }
            
            return "";
        }catch(ClassNotFoundException | SQLException e){
            return "";
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
    
    public boolean addSupplier(SupplierDataModel sprv){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
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
    
    public boolean editSupplier(SupplierDataModel sprv,String suppCode){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="UPDATE suppliers SET supplier_name='"+sprv.getSupplierName()+"',"
                    + "supplier_address='"+sprv.getSupplierAddress()+"',company='"+sprv.getCompany()+"',phone_no1='"+sprv.getPhoneNo1()+"',nic_no='"+sprv.getNIC()+"',"
                    + "acc_no='"+sprv.getAccNo()+"',bank='"+sprv.getBank()+"',phone_no='"+sprv.getPhoneNo()+"' WHERE supplier_code='"+suppCode+"'"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);    
           
            
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
    
    public boolean deleteSupplier(String suppCode){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="DELETE FROM suppliers WHERE supplier_code='"+suppCode+"'"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);  
           
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
    
    public ArrayList<SupplierDataModel> getSuppliers(int situation,String searchInput1,String searchInput2) {
        try{
            ArrayList<SupplierDataModel> supprList=new ArrayList<>();
            Class.forName(DBCons.LOCAL_DB_DRIVER); //load and register the driver
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
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
            
            
        }catch(ClassNotFoundException | SQLException e){
            return null;// other situation
                    
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

