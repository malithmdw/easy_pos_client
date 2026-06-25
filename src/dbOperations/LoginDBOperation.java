 
package dbOperations;

import appDataModels.Permission;
import dataModels.UserDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class LoginDBOperation {

    
    Connection con=null;
    PreparedStatement pst1=null;
    PreparedStatement pst2=null;
    ResultSet rs1=null;
    ResultSet rs2=null;
    
    public UserDataModel checkUserLogin(String userName, String password){
        UserDataModel user = null;
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query1="SELECT user_name, institute_id, user_code, account_type, note FROM user_account WHERE user_name = '" + userName + "' AND password = '"+ password +"' AND status = 1";
                     
            pst2=(PreparedStatement)con.prepareStatement(query1);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {
                user = new UserDataModel();
                user.setUserName(rs2.getString(1));
                user.setInstituteId(rs2.getInt(2));     
                user.setUserCode(rs2.getInt(3));         
            }            
            return user;// the user_name not in DB
            
        }catch(ClassNotFoundException | SQLException e){
           
            return user;// other situation
                    
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
                                
            }catch(SQLException e){
                
            }
        }  
    }
    
    public ArrayList<Permission> getPermissionListOfUser(String userName){
        ArrayList<Permission> permissionList = new ArrayList<>();
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query1="SELECT record_id, user_name, permission FROM user_permission WHERE user_name = '" + userName + "'";
                     
            pst2=(PreparedStatement)con.prepareStatement(query1);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {
                permissionList.add(Permission.valueOf(rs2.getString(3)));
            }            
            return permissionList;// the user_name not in DB
            
        }catch(ClassNotFoundException | SQLException e){
           
            return permissionList;// other situation
                    
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
                                
            }catch(SQLException e){
                
            }
        }  
    }
    
    public Boolean isUserNameExist(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query1="SELECT user_name FROM user_account WHERE UPPER(user_name) = '" + userName.toUpperCase() +"'";
                     
            pst1=(PreparedStatement)con.prepareStatement(query1);
            rs1=pst1.executeQuery(); 
            
            
            while (rs1.next() != false) {
                return Boolean.TRUE;// the user_name exist in DB
            }
            
            return Boolean.FALSE;// the user_name not exist in DB
            
        }catch(ClassNotFoundException | SQLException e){
           
            return null;// other situation
                    
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
                                
            }catch(Exception e){
                
            }
        } 
    }
    
    public ArrayList<UserDataModel> getAllUsersOfInstitute(int instituteId){
        ArrayList<UserDataModel> userList = new ArrayList<>();
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query1="SELECT user_name, user_code, account_type, note, status FROM user_account WHERE institute_id = '" + instituteId + "'";
                     
            pst2=(PreparedStatement)con.prepareStatement(query1);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {
                UserDataModel user = new UserDataModel();
                user.setUserName(rs2.getString(1));
                user.setInstituteId(instituteId);     
                user.setUserCode(rs2.getInt(2)); 
                user.setAccountType(rs2.getInt(3)); 
                user.setNotes(rs2.getString(4)); 
                user.setStatusCode(rs2.getInt(5));         
            }            
            return userList;
            
        }catch(ClassNotFoundException | SQLException e){
           
            return null;// other situation
                    
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
                                
            }catch(SQLException e){
                
            }
        }  
    }
    
    public boolean updateAllPermissionsOfUser(String userName, ArrayList<Permission> permissionList)
    {
        ArrayList<Permission> permissionsToUpdate = new ArrayList<>();
        permissionsToUpdate.addAll(permissionList);
        ArrayList<Permission> currentPermissions = getPermissionListOfUser(userName);
        
        if (currentPermissions == null) {
            return false;
        }
        
        final Set<Permission> permissionBackup = new HashSet<>(permissionList);
        permissionsToUpdate.removeAll(currentPermissions);
        currentPermissions.removeAll(permissionBackup);

        // Add new permissions - permissionsToUpdate
        for (Permission permission : permissionsToUpdate) {
            addUserPermission(userName, permission);
        }
        
        // Remove permisssion no longer required - currentPermissions
        for (Permission permission : currentPermissions) {
            deleteUserPermission(userName, permission);
        }
        
        return true;
    }
    
    private boolean addUserPermission(String userName, Permission permissions)
    {
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query="INSERT INTO user_permission(user_name, permission) VALUES (?,?)"; //add data
            
            pst1=(PreparedStatement)con.prepareStatement(query);          
            
            pst1.setString(1,userName);        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst1.setString(2,permissions.name());
            
            pst1.executeUpdate();
            
            return true;
        }catch(ClassNotFoundException | SQLException e){
            return false;
            
            
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(SQLException e){
                
            }
        } 
    }
    
    private boolean deleteUserPermission(String userName, Permission permission)
    {
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="DELETE FROM user_permission WHERE user_name='"+userName+"' AND permission = '"+permission.name()+"'";
            pst2=(PreparedStatement)con.prepareStatement(query2);
            pst2.executeUpdate();
                      
            return true;// the suppCode not in DB
            
        }catch(ClassNotFoundException | SQLException e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(SQLException e){
                
            }
        }
    }
    
    
    
    
    
    
//    public int getUser(String userName){
//        try{
//            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query1="SELECT user_name, account_type, password_hint, hint_type, stocks, editing_stocks, `deleting_stocks`, `adding_stocks`, `supplies`, `adding_suppliers`, `editing_suppliers`, `deleting_suppliers`, `reports`, `note` FROM user_accounts";
//                     
//            pst1=(PreparedStatement)con.prepareStatement(query1);
//            rs1=pst1.executeQuery(); 
//            
//            
//            while (rs1.next() != false) {
//                               
//                if(userName.equals(rs1.getString(1))){
//                  return 0;                        
//                }
//            }
//            
//            return 1;// the user_name not in DB
//            
//        }catch(Exception e){
//           
//            return 2;// other situation
//                    
//        }finally{
//            try{
//                if(pst1!=null){
//                    pst1.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//                                
//            }catch(Exception e){
//                
//            }
//        }  
//    
//    }

    public int checkUserName(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);  //load and register the driver
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query1="SELECT user_name FROM user_accounts";
                     
            pst1=(PreparedStatement)con.prepareStatement(query1);
            rs1=pst1.executeQuery(); 
            
            
            while (rs1.next() != false) {
                               
                if(userName.equals(rs1.getString(1))){
                  return 0;                        
                }
            }
            
            return 1;// the user_name not in DB
            
        }catch(Exception e){
           
            return 2;// other situation
                    
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
                                
            }catch(Exception e){
                
            }
        }  
    
}
    public int checkPassWord(String userName,String passWord){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT password FROM user_accounts WHERE user_name='"+userName+"'";
            
            pst2=(PreparedStatement)con.prepareStatement(query2);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {
                               
                if(passWord.equals(rs2.getString(1))){
                    return 0;
                }
            
            }
            
            return 1;// the suppCode not in DB
            
        }catch(Exception e){
            
            return 2;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }  
    
}
    
    public boolean userType(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT account_type FROM user_accounts WHERE user_name='"+userName+"'";
            int userType=1;
            pst2=(PreparedStatement)con.prepareStatement(query2);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {
                               
                if(userType==rs2.getInt(1)){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }         
    }
    public int checkPassWordHint(String userName,String passWordHint,String hintType){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query3="SELECT password_hint,hint_type FROM user_accounts WHERE user_name='"+userName+"'";
            
            pst1=(PreparedStatement)con.prepareStatement(query3);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                if(passWordHint.equals(rs1.getString(1)) && hintType.equals(rs1.getString(2))){
                    return 0;
                }                
            }
            
            return 1;// the matching is not correct
            
        }catch(Exception e){
            
            return 2;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
    }
    public String toGetPwd(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query4="SELECT password FROM user_accounts WHERE user_name='"+userName+"'";
            
            pst2=(PreparedStatement)con.prepareStatement(query4);
            rs2=pst2.executeQuery();
           
            while (rs2.next() != false) {                               
                return rs2.getString(1);  /////////////////////////////////////////////////////////////////////////////////////////          
            }
            
            return "mali";// the suitable pwd not in DB
            
        }catch(Exception e){
            
            return "mali";// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
        
    }
    public String usersList(){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query4="SELECT user_name FROM user_accounts";
            
            pst2=(PreparedStatement)con.prepareStatement(query4);
            rs2=pst2.executeQuery();
            String returnResult="";
            while (rs2.next() != false) {
                if(rs2.getString(1).equals("mali")){                    
                }else{
                    returnResult=returnResult+"*  "+rs2.getString(1)+"\n"; 
                }
                 
            }
            
            return returnResult;// the suitable pwd not in DB
            
        }catch(Exception e){
            
            return "";// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }       
    }
    
    //add new user
    public boolean addNewUser(UserDataModel uv) {
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            // 1.make connection
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query="INSERT INTO user_accounts VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //add data
            pst1=(PreparedStatement)con.prepareStatement(query);          
            
            pst1.setString(1,uv.getUserName());        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst1.setString(2,uv.getPassword());
            pst1.setInt(3,uv.getAccountType());
            pst1.setString(4,uv.getPwdHint());
            pst1.setInt(5,uv.getHintType());
            pst1.setInt(6,uv.getStocks());            
            pst1.setInt(7,uv.getEditStocks());
            pst1.setInt(8,uv.getDelStocks());
            pst1.setInt(9,uv.getAddStocks());
            pst1.setInt(10,uv.getSupplies());
            pst1.setInt(11,uv.getAddSupplies());
            pst1.setInt(12,uv.getEditSupplies());
            pst1.setInt(13,uv.getDelSupplies());
            pst1.setInt(14,uv.getReports());
            pst1.setString(15,"");
            
            pst1.executeUpdate();
            
            return true;
        }catch(Exception e){
            return false;
            
            
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                
            }
        } 
    }
    public boolean deleteUserAccount(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="DELETE FROM `user_accounts` WHERE user_name='"+userName+"'";
            pst2=(PreparedStatement)con.prepareStatement(query2);
            pst2.executeUpdate();
                      
            return true;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    } 
    public boolean changeUserPwd(String userName,String pwd){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="UPDATE user_accounts SET password='"+pwd+"' WHERE user_name='"+userName+"'";
            pst2=(PreparedStatement)con.prepareStatement(query2);
            pst2.executeUpdate();
                       
            return true;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }     
    
    
    //access levels
    public boolean stocks(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query="SELECT stocks FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean editingStocks(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query="SELECT editing_stocks FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean deletingStocks(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query="SELECT deleting_stocks FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean addingStocks(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT adding_stocks FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean supplies(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT supplies FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean addingSuppliers(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT adding_suppliers FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean editingSuppliers(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT editing_suppliers FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean deletingSuppliers(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT deleting_suppliers FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    public boolean reports(String userName){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT reports FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                               
                if(rs1.getInt(1)==1){
                    return true;
                }
            
            }
            
            return false;// the suppCode not in DB
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    }
    
    public String getNote(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="SELECT note FROM user_accounts WHERE user_name='"+userName+"'";
            pst1=(PreparedStatement)con.prepareStatement(query2);
            rs1=pst1.executeQuery();
           
            while (rs1.next() != false) {
                
                    return rs1.getString(1);
                
            
            }
            
            return "";
            
        }catch(Exception e){
            
            return "ERROR !";// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst1!=null){
                    pst1.close();
                }
                
            }catch(Exception e){
                
            }
        }        
    }
    
    public boolean changeNote(String userName,String noteText){ 
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query="UPDATE user_accounts SET note='"+noteText+"' WHERE user_name='"+userName+"'";
            pst2=(PreparedStatement)con.prepareStatement(query);
            pst2.executeUpdate();
                       
            return true;//
            
        }catch(Exception e){
            
            return false;// other situation
                    
        }finally{
            try{
                
                if(con!=null){
                    con.close();
                }
                if(pst2!=null){
                    pst2.close();
                }
                
            }catch(Exception e){
                
            }
        }
         
    } 
    
    
}
    
    