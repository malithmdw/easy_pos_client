 package tableModels;

import dataModels.SaleDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class AllSalesLoadTbl extends AbstractTableModel{
    DecimalFormat df=new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"  ","Invoice No","Sale Date","Sale Time","Gross Amount","Discount","Extra Discount","Net Total","Money Recieve","Cost of Bill","Sale by"};
    private static ArrayList<SaleDataModel> allSaleList;
    
    public AllSalesLoadTbl(ArrayList<SaleDataModel> allSaLst){
        allSaleList=allSaLst;
    }
    @Override
    public int getRowCount() {
        return allSaleList.size();
    }
    public String getColumnName(int columnIndex){
        return COLUMN_NAMES[columnIndex];
    }
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return allSaleList.get(rowIndex).getInvoiceNo();
            case 2:
                return dateFormatForUI(allSaleList.get(rowIndex).getSaleDate());
            case 3:
                return allSaleList.get(rowIndex).getSaleTime();
            case 4:
                return df.format(allSaleList.get(rowIndex).getGrossAmount());
            case 5:
                return df.format(allSaleList.get(rowIndex).getDiscount());
            case 6:
                return df.format(allSaleList.get(rowIndex).getExtraDiscount());
            case 7:
                return df.format(allSaleList.get(rowIndex).getNetTotal());
            case 8:
                return df.format(allSaleList.get(rowIndex).getMoneyRecieve());
            case 9:
                return df.format(allSaleList.get(rowIndex).getCost());
            case 10:
                return allSaleList.get(rowIndex).getSaleBy();
            default:
                return "Error";
            
                 
        }    
    }
    
    String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
}



    

    
    
    
   
    
