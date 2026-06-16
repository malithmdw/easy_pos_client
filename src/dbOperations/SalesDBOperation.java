package dbOperations;

import com.itextpdf.text.Document;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
//import javax.swing.text.Document;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfPTable;
import dataModels.BillDataModel;
import dataModels.BillItemDataModel;
import dataModels.SaleDataModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SalesDBOperation {
    DecimalFormat df = new DecimalFormat("#0.00");
    
    
    
    String url="jdbc:mysql://localhost:3306/pharmacy_management_system"; //change Port No & Database
    String username="root";
    String password="";
    Connection con=null;
    PreparedStatement pst=null;    
    ResultSet rs=null;
    
    String driver = "com.mysql.jdbc.Driver";
    
    public String getTodayMyLastInvNo(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
     
            String query="SELECT invoice_no, MAX(billing_time) FROM sales WHERE sale_date='" +util.DateTimeUtil.getTodayDateDBFormat()+ "' AND sale_by = '" + userName + "'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            String invNo = null;
            while (rs.next() != false) {                               
                invNo=rs.getString(1);               
            }
            return invNo;            
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
    
    public boolean addSale(int instituteId, String userName, BillDataModel billDataModel){
        addSaleInvoice(instituteId, userName, billDataModel);
        
        for (BillItemDataModel billItem : billDataModel.getBillItems()) {
            addSaleItem(instituteId, billDataModel.getInvoiceNumber(),  billItem);    
        }
        
        return true;
            
    } 
    
    private boolean addSaleInvoice(int instituteId, String userName, BillDataModel billDataModel)
    {
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="INSERT INTO sales (institute_id, sale_date, billing_time, invoice_no, sale_time, "
                    + "sale_by, gross_amount, discount, extra_discount, net_total, "
                    + "money_recieve, cost) "
                    + "VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            
            pst.setInt(1,instituteId);
            pst.setString(2, billDataModel.getDate());
            pst.setString(3, billDataModel.getDate() + " " + billDataModel.getTime());
            pst.setString(4, billDataModel.getInvoiceNumber());           
            pst.setString(5, billDataModel.getDate() + " " + billDataModel.getTime());
            pst.setString(6, userName);
            pst.setDouble(7,billDataModel.getTotalGrossAmount());
            pst.setDouble(8,billDataModel.getTotalDiscount());
            pst.setDouble(9,billDataModel.getTotalDiscount());
            pst.setDouble(10,billDataModel.getNetTotal());
            pst.setDouble(11,billDataModel.getMoneyReceive());
            pst.setDouble(12,0);
            
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
    
    private boolean addSaleItem(int instituteId, String invoiceNo, BillItemDataModel billItemDataModel)
    {
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);  //exception throwable
            
            String query="INSERT INTO sale_items(institute_id, invoice_no, item_code, qty, status) VALUES (?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            
            pst.setInt(1,instituteId);        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,invoiceNo);
            pst.setInt(3,billItemDataModel.getItemId());           
            pst.setDouble(4,billItemDataModel.getQty());
            pst.setString(5,"SOLD");
            
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public int getLastInvNo(String userName){
        try{
            Class.forName(DBCons.LOCAL_DB_DRIVER);
            con=(Connection)DriverManager.getConnection(DBCons.LOCAL_DB_URL, DBCons.LOCAL_DB_USER, DBCons.LOCAL_DB_PASSWORD);//make connection
     
            String query="SELECT invoice_no FROM sales WHERE sale_date=(SELECT MAX(sale_date)FROM sales) AND sale_by = '" + userName + "'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            int invNo=-1;
            while (rs.next() != false) {                               
                invNo=rs.getInt(1);               
            }
            return invNo;            
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
    public String getLastDate(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT MAX(sale_date)FROM sales" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            String dte="";
            while (rs.next() != false) {                               
                dte=rs.getString(1);               
            }
            return dte;            
        }catch(Exception e){
            System.out.println("geTdate");
            return "";// other situation
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
    //add data to sales table    
    public boolean addNewSale(SaleDataModel saleV){
        
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO sales VALUES (?,?,?,?,?,?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            
            pst.setInt(1,saleV.getInvoiceNo());        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,saleV.getSaleDate());
            pst.setString(3,saleV.getSaleTime());
            pst.setString(4,saleV.getSaleBy());           
            pst.setString(5,df.format(saleV.getGrossAmount()));
            pst.setString(6,df.format(saleV.getDiscount()));
            pst.setString(7,df.format(saleV.getExtraDiscount()));
            pst.setString(8,df.format(saleV.getNetTotal()));
            pst.setString(9,df.format(saleV.getMoneyRecieve()));
            pst.setString(10,df.format(saleV.getCostOfBill()));
            
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
    
    //add data to saleitems tables
    public boolean addSaleItems(int invNo,String date,String itmNo,int qty){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO sale_items VALUES (?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            
            pst.setInt(1,invNo);        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,date);
            pst.setString(3,itmNo);
            pst.setInt(4,qty);           
            
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
    
    public boolean deleteSaleItems(int invNo,int situation){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="DELETE FROM sale_items WHERE invoice_no BETWEEN '' AND "+invNo+"'";
            if(situation==1){
                query="DELETE FROM sale_items ";
            }
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
    public boolean deleteSaleInvoice(String a){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            Document document=new Document();
            try {
                PdfAWriter.getInstance(document, new FileOutputStream("c:/PMS/SaleInvoice"+a+".pdf"));
                document.open();
                document.add(new Paragraph("Data of Sale Invoices"));
                PdfPTable pt=new PdfPTable(10);
                pt.addCell("Inv No");
                pt.addCell("Sale Date");
                pt.addCell("Sale Time");
                pt.addCell("Sale By");
                pt.addCell("Gross Amount");
                pt.addCell("Discount");
                pt.addCell("Extra Discount");
                pt.addCell("Net Total");
                pt.addCell("Money Recieve");
                pt.addCell("Cost");
                
                String query="SELECT * FROM sales ";
                    
                pst=(PreparedStatement)con.prepareStatement(query);    
                rs=pst.executeQuery();
                while (rs.next() != false) {                               
                    pt.addCell(rs.getString(1));
                    pt.addCell(rs.getString(2));
                    pt.addCell(rs.getString(3));
                    pt.addCell(rs.getString(4));
                    pt.addCell(rs.getString(5));
                    pt.addCell(rs.getString(6));
                    pt.addCell(rs.getString(7));
                    pt.addCell(rs.getString(8));
                    pt.addCell(rs.getString(9));
                    pt.addCell(rs.getString(10));
                }
                document.add(pt);
                document.close(); 

            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Can not create Text backup file !");
            }
            String query="DELETE FROM sales ";
            
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
    public boolean deleteSaleDaily(String a){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
                        
            Document document=new Document();
            try {
                PdfAWriter.getInstance(document, new FileOutputStream("c:/PMS/DailySales"+a+".pdf"));
                document.open();
                document.add(new Paragraph("Daily Sales"));
                PdfPTable pt=new PdfPTable(4);
                pt.addCell("Date");
                pt.addCell("Income");
                pt.addCell("Cost");
                pt.addCell("Profit");
                                
                String query="SELECT * FROM daily_sales ";
                    
                pst=(PreparedStatement)con.prepareStatement(query);    
                rs=pst.executeQuery();
                while (rs.next() != false) {                               
                    pt.addCell(rs.getString(1));
                    pt.addCell(rs.getString(2));
                    pt.addCell(rs.getString(3));
                    pt.addCell(rs.getString(4));
                    
                }
                document.add(pt);
                document.close(); 

            } catch(Exception e){
                
            }
            String query="DELETE FROM daily_sales ";
            
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
    public boolean deleteSalesMonthly(String a){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            Document document=new Document();
            try {
                PdfAWriter.getInstance(document, new FileOutputStream("c:/PMS/MonthlySales"+a+".pdf"));
                document.open();
                document.add(new Paragraph("Monthly Sales"));
                PdfPTable pt=new PdfPTable(5);
                pt.addCell("Month");
                pt.addCell("Date");
                pt.addCell("Income");
                pt.addCell("Cost");
                pt.addCell("Profit");
                                
                String query="SELECT * FROM monthly_sales ";
                    
                pst=(PreparedStatement)con.prepareStatement(query);    
                rs=pst.executeQuery();
                while (rs.next() != false) {                               
                    pt.addCell(rs.getString(1));
                    pt.addCell(rs.getString(2));
                    pt.addCell(rs.getString(3));
                    pt.addCell(rs.getString(4));
                    pt.addCell(rs.getString(5));
                    
                }
                document.add(pt);
                document.close(); 

            } catch(Exception e){
                
            }
            String query="DELETE FROM monthly_sales ";
            
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
    
    
//    public String getSalesItems(int invNo,String date){
//        try{
//            Class.forName(driver);
//            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
//            String query="SELECT * FROM sale_items WHERE invoice_no='"+invNo+"' AND date='"+date+"'" ;
//            
//            pst=(PreparedStatement)con.prepareStatement(query);
//            rs=pst.executeQuery();
//            Stocks s=new Stocks();
//            String outCome="";
//            while (rs.next() != false) { 
//                outCome=outCome+""+rs.getString(3)+"    "+s.getItemName(rs.getString(3))+"    "+rs.getString(4)+"\n";
//            }            
//            return outCome;            
//        }catch(Exception e){
//            return "ERROR";// other situation
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
    
    public int checkInvoiceCount(String todayDate){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            
            String query1="SELECT MIN(sale_date) FROM sales" ;
            pst=(PreparedStatement)con.prepareStatement(query1);
            rs=pst.executeQuery(); 
            String oldDate="";
            while (rs.next() != false) {
                oldDate=rs.getString(1);
            }
                    
            String query="SELECT sale_date FROM sales WHERE sale_date BETWEEN '"+oldDate+"' AND '"+todayDate+"'";
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            int noOfDays=0;
            String date="";
            while (rs.next() != false) { 
                if(date.equals(rs.getString(1))){
                }else{
                    noOfDays=noOfDays+1;
                    date=rs.getString(1);
                }               
            }
            return noOfDays;            
        }catch(Exception e){
            //System.out.println(e);
            return -1;// other situation
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
    public int checkInvoiceItemCount(String todayDate){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            
            String query1="SELECT MIN(date) FROM sale_items" ;
            pst=(PreparedStatement)con.prepareStatement(query1);
            rs=pst.executeQuery(); 
            String oldDate="";
            while (rs.next() != false) {
                oldDate=rs.getString(1);
            }  
            
            String query="SELECT date FROM sale_items WHERE date BETWEEN '"+oldDate+"' AND '"+todayDate+"'";
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            int noOfDays=0;
            String date="";
            while (rs.next() != false) { 
                if(date.equals(rs.getString(1))){
                }else{
                    noOfDays=noOfDays+1;
                    date=rs.getString(1);
                }               
            }
            return noOfDays;            
        }catch(Exception e){
            return -1;// other situation
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
    public boolean deleteInvoice(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable 
            
            String query1="SELECT MIN(sale_date) FROM sales" ;
            pst=(PreparedStatement)con.prepareStatement(query1);
            rs=pst.executeQuery(); 
            String oldDate="";
            while (rs.next() != false) {
                oldDate=rs.getString(1);
            }
            
            String query="DELETE FROM sales WHERE sale_date='"+oldDate+"'";  
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
    public boolean deleteInvoiceData(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query1="SELECT MIN(date) FROM sale_items" ;
            pst=(PreparedStatement)con.prepareStatement(query1);
            rs=pst.executeQuery(); 
            String oldDate="";
            while (rs.next() != false) {
                oldDate=rs.getString(1);
            }
            
            String query="DELETE FROM sale_items WHERE date='"+oldDate+"'";
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
    
    
    public int noOfFieldsOfSalesItemsDBTable(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT * FROM sale_items" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            int noOfFields=0;
            while (rs.next() != false) {                               
                noOfFields=noOfFields+1;               
            }
            return noOfFields;            
        }catch(Exception e){
            
            return -1;// other situation
                    
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
    public int noOfFieldsOfSalesDBTable(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT * FROM sales" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            int noOfFields=0;
            while (rs.next() != false) {                               
                noOfFields=noOfFields+1;               
            }
            return noOfFields;            
        }catch(Exception e){
            
            return -1;// other situation
                    
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
    public int noOfFieldsOfSalesDailyDBTable(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT * FROM daily_sales" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            int noOfFields=0;
            while (rs.next() != false) {                               
                noOfFields=noOfFields+1;               
            }
            return noOfFields;            
        }catch(Exception e){
            
            return -1;// other situation
                    
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
    public int noOfFieldsOfSalesMonthlyDBTable(){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT * FROM monthly_sales" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            int noOfFields=0;
            while (rs.next() != false) {                               
                noOfFields=noOfFields+1;               
            }
            return noOfFields;            
        }catch(Exception e){
            
            return -1;// other situation
                    
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

    
    public String todayNowSales(String date){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT * FROM sales WHERE sale_date='"+date+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            double income=0;
            double cost=0;
            double prof=0;
            while (rs.next() != false) {
                income=income+Double.parseDouble(rs.getString(8));
                cost=cost+Double.parseDouble(rs.getString(10));
                prof=prof+(Double.parseDouble(rs.getString(8))-Double.parseDouble(rs.getString(10)));
            }
            String res="  Rs. "+df.format(income)+"\nRs. "+df.format(cost)+"\nRs. "+df.format(prof)+"";
            return res;            
        }catch(Exception e){
            
            return "Error";// other situation
                    
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
    public SaleDataModel todayTotalSales(String date){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            SaleDataModel sv=new SaleDataModel();
            String query="SELECT * FROM sales WHERE sale_date='"+date+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            double income=0;
            double cost=0;
            double prof=0;
            while (rs.next() != false) {
                income=income+Double.parseDouble(rs.getString(8));
                cost=cost+Double.parseDouble(rs.getString(10));
                prof=prof+(Double.parseDouble(rs.getString(8))-Double.parseDouble(rs.getString(10)));
            }
            sv.setIncome(income);
            sv.setCost(cost);
            sv.setProfit(prof);
            return sv;            
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
    public SaleDataModel thisMonthTotalSales(String year,String month){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            SaleDataModel sv=new SaleDataModel();
            String query="SELECT * FROM daily_sales WHERE date BETWEEN '"+year+"-"+month+"-01' AND '"+year+"-"+month+"-31'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            double income=0;
            double cost=0;
            double prof=0;
            while (rs.next() != false) {
                income=income+Double.parseDouble(rs.getString(2));
                cost=cost+Double.parseDouble(rs.getString(3));
                prof=prof+(Double.parseDouble(rs.getString(4)));
            }
            sv.setIncome(income);
            sv.setCost(cost);
            sv.setProfit(prof);
            return sv;            
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
    public SaleDataModel thisYearTotalSales(String year){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            SaleDataModel sv=new SaleDataModel();
            String query="SELECT * FROM monthly_sales WHERE date BETWEEN '"+year+"-01-01' AND '"+year+"-12-31'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            double income=0;
            double cost=0;
            double prof=0;
            while (rs.next() != false) {
                income=income+Double.parseDouble(rs.getString(3));
                cost=cost+Double.parseDouble(rs.getString(4));
                prof=prof+(Double.parseDouble(rs.getString(5)));
            }
            sv.setIncome(income);
            sv.setCost(cost);
            sv.setProfit(prof);
            return sv;            
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
    
    
    public ArrayList<SaleDataModel> getAllSales(String searchInput1,String searchInput2) {
        try{
            ArrayList<SaleDataModel> list=new ArrayList<SaleDataModel>();
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM sales " ;
            if(searchInput1.equals("") && searchInput2.equals("")){
                query="SELECT * FROM sales";
            }else{
                query="SELECT * FROM sales WHERE sale_date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"'" ;
            }
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {
                SaleDataModel sa=new SaleDataModel();
                
                sa.setInvoiceNo(Integer.parseInt(rs.getString(1)));
                sa.setSaleDate(rs.getString(2));
                sa.setSaleTime(rs.getString(3));
                sa.setSaleBy(rs.getString(4));
                sa.setGrossAmount(Double.parseDouble(rs.getString(5)));
                sa.setDiscount(Double.parseDouble(rs.getString(6)));
                sa.setExtraDiscount(Double.parseDouble(rs.getString(7)));
                sa.setNetTotal(Double.parseDouble(rs.getString(8)));
                sa.setMoneyRecieve(Double.parseDouble(rs.getString(9)));
                sa.setCost(Double.parseDouble(rs.getString(10)));
                list.add(sa);
            }
            return list;
            
            
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
    public ArrayList<SaleDataModel> getTotalSales(int situation,String searchInput1,String searchInput2) {
        try{
            ArrayList<SaleDataModel> list=new ArrayList<SaleDataModel>();
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
            String query="SELECT * FROM daily_sales " ;
            switch(situation){
                case 0:query="SELECT * FROM daily_sales " ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setDate(rs.getString(1));
                            sa.setIncome(Double.parseDouble(rs.getString(2)));
                            sa.setCost(Double.parseDouble(rs.getString(3)));
                            sa.setProfit(Double.parseDouble(rs.getString(4)));

                            list.add(sa); 
                        }
                        return list;
                case 1:query="SELECT * FROM monthly_sales " ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setMonthName(rs.getString(1));
                            sa.setDate(rs.getString(2));
                            sa.setIncome(Double.parseDouble(rs.getString(3)));
                            sa.setCost(Double.parseDouble(rs.getString(4)));
                            sa.setProfit(Double.parseDouble(rs.getString(5)));

                            list.add(sa); 
                        }
                        return list;
                case 2:query="SELECT * FROM annual_sales " ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setYearName(rs.getString(1));
                            sa.setDate(rs.getString(2));
                            sa.setIncome(Double.parseDouble(rs.getString(3)));
                            sa.setCost(Double.parseDouble(rs.getString(4)));
                            sa.setProfit(Double.parseDouble(rs.getString(5)));

                            list.add(sa); 
                        }
                        return list;
                case 3:query="SELECT * FROM daily_sales WHERE date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"'" ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setDate(rs.getString(1));
                            sa.setIncome(Double.parseDouble(rs.getString(2)));
                            sa.setCost(Double.parseDouble(rs.getString(3)));
                            sa.setProfit(Double.parseDouble(rs.getString(4)));

                            list.add(sa); 
                        }
                        return list;
                case 4:query="SELECT * FROM monthly_sales WHERE date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"'" ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setMonthName(rs.getString(1));
                            sa.setDate(rs.getString(2));
                            sa.setIncome(Double.parseDouble(rs.getString(3)));
                            sa.setCost(Double.parseDouble(rs.getString(4)));
                            sa.setProfit(Double.parseDouble(rs.getString(5)));

                            list.add(sa); 
                        }
                        return list;
                case 5:query="SELECT * FROM annual_sales WHERE date BETWEEN '"+searchInput1+"' AND '"+searchInput2+"'" ;
                        pst=(PreparedStatement)con.prepareStatement(query);
                        rs=pst.executeQuery();

                        while (rs.next() != false) {
                            SaleDataModel sa=new SaleDataModel();

                            sa.setYearName(rs.getString(1));
                            sa.setDate(rs.getString(2));
                            sa.setIncome(Double.parseDouble(rs.getString(3)));
                            sa.setCost(Double.parseDouble(rs.getString(4)));
                            sa.setProfit(Double.parseDouble(rs.getString(5)));

                            list.add(sa); 
                        }
                        return list;
                default:
                    query="SELECT * FROM daily_sales ";break ;
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
    
    
    public boolean checkExistInSales(String date){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT sale_date FROM sales WHERE sale_date='"+date+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {                               
                return true;
            }
            return false;            
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
    public boolean checkExistInDailySales(String date,String date2){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT date FROM daily_sales WHERE date BETWEEN '"+date+"' AND '"+date2+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {
                return true;
            }
            return false;            
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
    public boolean checkExistInMonthlySales(String date,String date2){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT date FROM monthly_sales WHERE date BETWEEN '"+date+"' AND '"+date2+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {                               
                return true;
            }
            return false;            
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
    public boolean checkExistInAnnualySales(String date,String date2){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);//make connection
     
            String query="SELECT date FROM daily_sales WHERE date BETWEEN '"+date+"' AND '"+date2+"'" ;
            
            pst=(PreparedStatement)con.prepareStatement(query);
            rs=pst.executeQuery();
            
            while (rs.next() != false) {                               
                return true;
            }
            return false;            
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
    
    
    public boolean addToDailySales(String date,double income,double cost,double profit){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO daily_sales VALUES (?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
           //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(1,date);          
            pst.setString(2,df.format(income));
            pst.setString(3,df.format(cost));
            pst.setString(4,df.format(profit));
                      
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
    public boolean addToMonthlySales(String month,String date,double income,double cost,double profit){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO monthly_sales VALUES (?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(1,month);
            pst.setString(2,date);          
            pst.setString(3,df.format(income));
            pst.setString(4,df.format(cost));
            pst.setString(5,df.format(profit));
            
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
    public boolean addToAnnualySales(int year,String date,double income,double cost,double profit){
        try{
            Class.forName(driver);
            con=(Connection)DriverManager.getConnection(url, username, password);  //exception throwable
            
            String query="INSERT INTO annual_sales VALUES (?,?,?,?,?)"; //add data
            pst=(PreparedStatement)con.prepareStatement(query);           
            
            pst.setInt(1,year);        //insert value for DB columns   pst.setString(columnNoofDBtable,insertData);
            pst.setString(2,date);            
            pst.setString(3,df.format(income));
            pst.setString(4,df.format(cost));
            pst.setString(5,df.format(profit));
            
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
