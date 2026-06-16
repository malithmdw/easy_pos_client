
package dbOperations;

import dataModels.ItemCategory;
import dataModels.MeasureUnit;
import dataModels.StockItemDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDBOperation {
    
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;
    
        
    public ArrayList<StockItemDataModel> getItemByCodeOrBarcode(String searchInput) {
        String whereclause = "s.barcode='"+searchInput+"' OR s.item_code='"+searchInput+"'";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getItemListBySupplierCode(int supplierCode) {
        String whereclause = "s.supp_code1='"+supplierCode+"' OR s.supp_code2='"+supplierCode+"'";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getAllItems() {
        String whereclause = "1";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getAllExpiredItems() {
        String todayDate = util.DateTimeUtil.getTodayDateDBFormat();
        String whereclause = "exp_date<'"+todayDate+"'";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getAllOutOfStockItems() {
        String whereclause = "qty < minimum_stoke";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getExpiredItemsBySupplierCode(String supplierCode) {
        String todayDate = util.DateTimeUtil.getTodayDateDBFormat();
        String whereclause = "exp_date<'"+todayDate+"' AND (supp_code1='"+supplierCode+"' OR supp_code2='"+supplierCode+"')";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getOutOfStockItemsBySupplierCode(String supplierCode) {
        String whereclause = "qty < minimum_stoke AND (supp_code1='"+supplierCode+"' OR supp_code2='"+supplierCode+"'";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getItemsByInput(String searchInput) {
        String whereclause = "(item_code LIKE '"+searchInput+"%' OR barcode LIKE '"+searchInput+"%' OR item_name LIKE '"+searchInput+"%' "
                + "OR sub_name LIKE '"+searchInput+"%' OR item_name_sinhala LIKE '"+searchInput+"%' OR item_name_tamil LIKE '"+searchInput+"%')";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getItemsByInputAndCategoryCode(String searchInput, String categoryId) {
        String whereclause = "(item_code LIKE '"+searchInput+"%' OR barcode LIKE '"+searchInput+"%' OR item_name LIKE '"+searchInput+"%' "
                + "OR sub_name LIKE '"+searchInput+"%' OR item_name_sinhala LIKE '"+searchInput+"%' OR item_name_tamil LIKE '"+searchInput+"%') AND category_id='"+categoryId+"'";
        return getStockDatalist(whereclause);
    }
    
    public ArrayList<StockItemDataModel> getItemsByInputAndCategoryCode(int categoryId) {
        String whereclause = " s.category_id='"+categoryId+"'";
        return getStockDatalist(whereclause);
    }
    
    private ArrayList<StockItemDataModel> getStockDatalist(String whereclause)
    {
        ArrayList<StockItemDataModel> res = new ArrayList<>();
        
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            String query="SELECT s.item_code, s.barcode, s.item_name, s.sub_name, s.item_name_sinhala, "
                    + "s.item_name_tamil, s.category_id, s.measure_unit_id, s.qty, s.selling_price, "
                    + "s.whole_sale_price, s.whole_sale_min_qty, s.discount, s.supp_code1, s.supp_code2, "
                    + "s.comment, s.last_edit_date, s.editor, s.minimum_stock, s.exp_date, "
                    + "c.cat_name, c.cat_name_sin, c.cat_name_tam, c.description, "
                    + "m.unit_name_eng, m.unit_name_sin, m.unit_name_tam, m.description "
                    + "FROM stock s "
                    + "INNER JOIN category c ON s.category_id = c.category_id " 
                    + "INNER JOIN measure_unit m ON s.measure_unit_id = m.measure_unit_id " 
                    + "WHERE " + whereclause ;
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
           
            while (rs.next() != false) {
                
                StockItemDataModel s=new StockItemDataModel();  
                ItemCategory category = new ItemCategory();
                MeasureUnit measureUnit = new MeasureUnit();
                
                s.setItem_code(rs.getInt(1));
                s.setBarcode(rs.getString(2));
                s.setItem_name(rs.getString(3));
                s.setSub_name(rs.getString(4));
                s.setItem_name_sinhala(rs.getString(5));
                
                s.setItem_name_tamil(rs.getString(6));
                category.setCategoryId(rs.getInt(7));                
                measureUnit.setMeasureUnitId(rs.getInt(8));
                s.setQty(Double.parseDouble(rs.getString(9)));
                s.setSelling_price(Double.parseDouble(rs.getString(10)));
                
                s.setWholeSalePrice(Double.parseDouble(rs.getString(11)));                
                s.setWholeSaleMinQty(Integer.parseInt(rs.getString(12)));
                s.setDiscount(Double.parseDouble(rs.getString(13)));
                s.setSupp_code1(rs.getString(14));
                s.setSupp_code2(rs.getString(15));
                
                s.setComment(rs.getString(16));                
                s.setLast_edit_date(rs.getString(17));
                s.setLast_editor(rs.getString(18));
                s.setMinimumQtyForAlert(Integer.parseInt(rs.getString(19)));
                s.setExp_date(rs.getString(20));
                
                category.setCategoryName(rs.getString(21));                
                category.setCategoryNameSin(rs.getString(22));
                category.setCategoryNameTam(rs.getString(23));
                category.setDescription(rs.getString(24));
                
                measureUnit.setUnitNameEng(rs.getString(25));                
                measureUnit.setUnitNameSin(rs.getString(26));
                measureUnit.setUnitNameTam(rs.getString(27));
                measureUnit.setDescription(rs.getString(28));
                
                s.setCategory(category);
                s.setMeasureUnit(measureUnit);
                res.add(s);
            }
            return res;
            
            
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
    
    
    public boolean dispenseStock(int itmCode, double dispenseQty){
        
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
            
            String query2="UPDATE stock SET qty=qty-"+dispenseQty+" WHERE item_code='"+itmCode+"'";
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
    
    
    
    
    
    
    
    
//    
//    
//    public StockItemDataModel getItem(String searchInput) {
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query="SELECT * FROM stoke WHERE item_code='"+searchInput+"'" ;
//            StockItemDataModel s=new StockItemDataModel();  
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
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
//                s.setLast_edit_date(rs.getString(12));
//                s.setLast_editor(rs.getString(13));
//                s.setMinimumQtyForAlert(rs.getInt(14));
//                s.setExp_date(rs.getString(15));
//            }
//            return s;
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
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    public boolean checkItemCodeExist(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//     
//            String query="SELECT item_code FROM stoke " ;
//            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
//                               
//                if(itmCode.equals(rs.getString(1))){
//                    return true;
//                }
//            
//            }
//            return false;
//            
//            
//        }catch(Exception e){
//            
//            return false;// other situation
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
//        
//        
//    }
//    public String getItemName(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection     
//            String query="SELECT item_name FROM stoke WHERE item_code='"+itmCode+"'" ;            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
//                return rs.getString(1);
//            }
//            return "Error"; 
//        }catch(Exception e){
//            return "Error";// other situation                    
//        }finally{
//            try{                
//                if(con!=null){
//                    con.close();
//                }
//                if(pst!=null){
//                    pst.close();
//                }                
//            }catch(Exception e){                
//            }
//        }        
//    }
//    public String getExpDate(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection     
//            String query="SELECT exp_date FROM stoke WHERE item_code='"+itmCode+"'" ;            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
//                return rs.getString(1);
//            }
//            return "Error"; 
//        }catch(Exception e){
//            return "Error";// other situation                    
//        }finally{
//            try{                
//                if(con!=null){
//                    con.close();
//                }
//                if(pst!=null){
//                    pst.close();
//                }                
//            }catch(Exception e){                
//            }
//        }       
//    }
//    public String unitSellingPriceAndDis(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection     
//            String query="SELECT selling_price,discount FROM stoke WHERE item_code='"+itmCode+"'" ;            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//            
//            while (rs.next() != false) {
//                return (rs.getString(1)+"&"+rs.getString(2));
//            }
//            return "Error"; 
//        }catch(Exception e){
//            return "Error";// other situation                    
//        }finally{
//            try{                
//                if(con!=null){
//                    con.close();
//                }
//                if(pst!=null){
//                    pst.close();
//                }                
//            }catch(Exception e){                
//            }
//        }       
//    }
//    public int stockValue(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection     
//            String query="SELECT qty FROM stoke WHERE item_code='"+itmCode+"'" ;            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           
//            while (rs.next() != false) {
//                return rs.getInt(1);
//            }
//            return -1; 
//        }catch(Exception e){
//            return -1;// other situation                    
//        }finally{
//            try{                
//                if(con!=null){
//                    con.close();
//                }
//                if(pst!=null){
//                    pst.close();
//                }                
//            }catch(Exception e){                
//            }
//        }       
//    }
//    
//    
//    public boolean addNewItem(StockItemDataModel sv){
//        
//        try{
//            Class.forName(driver);
//            // 1.make connection
//            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
//            
//            String query="INSERT INTO stoke VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //add data
//            pst=(PreparedStatement)con.prepareStatement(query);
//            
//            pst.setString(1,sv.getItem_code()); //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
//            pst.setString(2,sv.getItem_name());
//            pst.setString(3,sv.getSub_name());
//            pst.setString(4,sv.getCategory());
//            pst.setInt(5,sv.getQty());
//            pst.setString(6,df.format(sv.getPurchase_price()));            
//            pst.setString(7,df.format(sv.getSelling_price()));
//            pst.setString(8,df.format(sv.getDiscount()));
//            pst.setString(9,sv.getSupp_code1());
//            pst.setString(10,sv.getSupp_code2());
//            pst.setString(11,sv.getComment());
//            pst.setString(12,sv.getLast_edit_date());
//            pst.setString(13,sv.getLast_editor());
//            pst.setInt(14,sv.getMinimumQtyForAlert());
//            pst.setString(15, sv.getExp_date());
//            
//            pst.executeUpdate();            
//            return true;
//            
//        }catch(Exception e){
//            return false; 
//        }finally{
//            try{
//                if(pst!=null){
//                    pst.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(Exception e){
//                
//            }
//        }        
//        
//    }
//    public boolean outingStock(String itmCode,int qty){
//        
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
//            
//            String query="SELECT qty FROM stoke WHERE item_code='"+itmCode+"'" ;             
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//            int curQty=0;
//            while (rs.next() != false) {                               
//                curQty=rs.getInt(1);
//            }            
//            curQty=curQty-qty;
//            if(curQty<0){
//                JOptionPane.showMessageDialog(null, "Can not complete Transaction !\n"+itmCode+"item stock is 0 ");
//                return false;
//            }
//            String query2="UPDATE stoke SET qty='"+curQty+"' WHERE item_code='"+itmCode+"'";
//            pst2=(PreparedStatement)con.prepareStatement(query2);
//            pst2.executeUpdate();           
//            
//            return true;
//        }catch(Exception e){
//            return false;           
//        }finally{
//            try{
//                if(pst!=null && pst2!=null ){
//                    pst.close();
//                    pst2.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(Exception e){                
//            }
//        }         
//    }
//    public boolean addStoke(StockItemDataModel sv,String itmCode){
//        
//        try{
//            Class.forName(driver);
//            // 1.make connection
//            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
//            
//            String query="SELECT qty,coment FROM stoke WHERE item_code='"+itmCode+"'" ; 
//            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//            int qty=0;
//            String comen="";
//            while (rs.next() != false) {
//                               
//                qty=rs.getInt(1);  
//                comen=rs.getString(2);
//            }
//            
//            
//            qty=qty+sv.getQty();           
//            comen=comen+sv.getComment();
//            
//            String query2="UPDATE stoke SET qty='"+qty+"',purchase_price='"+df.format(sv.getPurchase_price())+"',selling_price='"+df.format(sv.getSelling_price())+"',discount='"+df.format(sv.getDiscount())+"',coment='"+comen+"',last_edit_date='"+sv.getLast_edit_date()+"',editor='"+sv.getLast_editor()+"',exp_date='"+sv.getExp_date()+"'  WHERE item_code='"+itmCode+"'";
//                   
//                   
//                        
//            
//            pst2=(PreparedStatement)con.prepareStatement(query2);
//            pst2.executeUpdate();
//            
//            return true;
//        }catch(Exception e){
//            //System.out.println(e);
//            return false;
//            
//            
//        }finally{
//            try{
//                if(pst!=null){
//                    pst.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(Exception e){
//                
//            }
//        }         
//    }
//    public boolean changeStoke(StockItemDataModel sv,String itmCode){
//        try{
//            Class.forName(driver);
//            // 1.make connection
//            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
//            
//            String query="UPDATE stoke SET item_name='"+sv.getItem_name()+"',"
//                    + "sub_name='"+sv.getSub_name()+"',category='"+sv.getCategory()+"',qty='"+sv.getQty()+"',"
//                    + "purchase_price='"+df.format(sv.getPurchase_price())+"',selling_price='"+df.format(sv.getSelling_price())+"',discount='"+df.format(sv.getDiscount())+"',"
//                    + "supp_code1='"+sv.getSupp_code1()+"',supp_code2='"+sv.getSupp_code2()+"',coment='"+sv.getComment()+"',"
//                    + "last_edit_date='"+sv.getLast_edit_date()+"',editor='"+sv.getLast_editor()+"',minimum_stoke='"+sv.getMinimumQtyForAlert()+"',exp_date='"+sv.getExp_date()+"' WHERE item_code='"+itmCode+"'"; //add data
//            pst=(PreparedStatement)con.prepareStatement(query);    
//           
//            
//            pst.executeUpdate();
//            
//            return true;
//        }catch(Exception e){
//           
//            return false;
//            
//            
//        }finally{
//            try{
//                if(pst!=null){
//                    pst.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(Exception e){
//                
//            }
//        }    
//    }
//    public boolean deleteStock(String itmCode){
//        try{
//            Class.forName(driver);
//            // 1.make connection
//            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
//            
//            String query="DELETE FROM stoke WHERE item_code='"+itmCode+"'";
//            pst=(PreparedStatement)con.prepareStatement(query);    
//           
//            pst.executeUpdate();
//            
//            return true;
//        }catch(Exception e){
//            return false;
//            
//            
//        }finally{
//            try{
//                if(pst!=null){
//                    pst.close();
//                }
//                if(con!=null){
//                    con.close();
//                }
//            }catch(Exception e){
//                
//            }
//        }    
//    }
//    
//    
//    public double purchasePrice(String itmCode){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//     
//            String query="SELECT * FROM stoke WHERE item_code='"+itmCode+"'" ;
//            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//            
//            while (rs.next() != false) {                               
//                 return Double.parseDouble(rs.getString(6));
//            }
//            return -1;            
//        }catch(Exception e){
//            
//            return -1;// other situation
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
//    public ArrayList<StockItemDataModel> getStocks(int situation,String searchInput1,String searchInput2){
//        try{
//            ArrayList<StockItemDataModel> list=new ArrayList<StockItemDataModel>();
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query="SELECT * FROM stoke" ;
//            
//                switch(situation){
//                case 0:
//                    query="SELECT * FROM stoke" ;break;
//                case 1:
//                    query="SELECT * FROM stoke WHERE item_code='"+searchInput1+"'" ;break;
//                case 2:
//                    query="SELECT * FROM stoke WHERE item_name='"+searchInput1+"'" ;break;
//                case 3:
//                    query="SELECT * FROM stoke WHERE sub_name='"+searchInput1+"'" ;break;
//                case 4:
//                    query="SELECT * FROM stoke WHERE supp_code1='"+searchInput1+"' OR supp_code2='"+searchInput1+"'" ;break;
//                
//                case 5:
//                    query="SELECT * FROM stoke WHERE item_code LIKE '"+searchInput1+"%'" ;break;
//                case 6:
//                    query="SELECT * FROM stoke WHERE item_name LIKE '"+searchInput1+"%'" ;break;
//                case 7:
//                    query="SELECT * FROM stoke WHERE sub_name LIKE '"+searchInput1+"%'" ;break;
//                case 8:
//                    query="SELECT * FROM stoke WHERE item_code LIKE '"+searchInput1+"%' AND category='"+searchInput2+"'" ;break;
//                case 9:
//                    query="SELECT * FROM stoke WHERE item_name LIKE '"+searchInput1+"%' AND category='"+searchInput2+"'" ;break;
//                case 10:
//                    query="SELECT * FROM stoke WHERE sub_name LIKE '"+searchInput1+"%' AND category='"+searchInput2+"'" ;break;
//                
//                case 11:
//                    query="SELECT * FROM stoke WHERE qty<minimum_stoke" ;break;
//                case 12:
//                    query="SELECT * FROM stoke WHERE qty<minimum_stoke AND (supp_code1='"+searchInput1+"' OR supp_code2='"+searchInput1+"')" ;break;
//                    
//                case 13:
//                    query="SELECT * FROM stoke WHERE exp_date<'"+searchInput1+"'" ;break;
//                case 14:
//                    query="SELECT * FROM stoke WHERE exp_date<'"+searchInput1+"' AND (supp_code1='"+searchInput2+"' OR supp_code2='"+searchInput2+"')" ;break;
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
//                s.setLast_edit_date(rs.getString(12));
//                s.setLast_editor(rs.getString(13));
//                s.setMinimumQtyForAlert(Integer.parseInt(rs.getString(17)));
//                s.setExp_date(rs.getString(18));
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
//     
//      
//    public String getStockForPrint() {
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query="SELECT * FROM stoke" ;
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//           String s="";
//           double totalValueOfStock=0;
//           double totalPaidForStock=0;
//           
//            while (rs.next() != false) {
//                                
//                s=s+rs.getString(1)+"  "+rs.getString(2)+"\n       "+rs.getString(5)+"\n";
//                double totalValueOfItem=Integer.parseInt(rs.getString(5)) * (Double.parseDouble(rs.getString(7))-Double.parseDouble(rs.getString(8)));
//                double totalPaidForItem=Integer.parseInt(rs.getString(5))*Double.parseDouble(rs.getString(6)); 
//                totalValueOfStock=totalValueOfStock+totalValueOfItem; 
//                totalPaidForStock=totalPaidForStock+totalPaidForItem;
//            }
//                s=""+s+"\n"
//                    + "-------------------------------------------------------\n"
//                    + "Total Value of Stock : "
//                    + "     Rs."+df.format(totalValueOfStock)+"\n"
//                    + "Paid for Stock : "
//                    + "     Rs."+df.format(totalPaidForStock)+"\n"
//                    + "Total Profit of Stock :"
//                    + "     Rs."+df.format(totalValueOfStock-totalPaidForStock)+"\n";
//            return s;
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
