package control;

import appDataModels.APIHeaderData;
import appDataModels.CategoryModel;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import localDatabase.DatabaseManager;
import serverDataModels.ChangeLog;
import serverDataModels.Item;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverResponseDataModel.CommonResponse;
import webService.ServerAPIConnection;
import webService.WebService;

/**
 *
 * @author MalithWanniarachchi
 */
public class ServerDataSubmissionQueue {
    
    private static ServerDataSubmissionQueue INSTANCE;

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private ServerDataSubmissionQueue(){
        startWorker();
    }

    public static synchronized ServerDataSubmissionQueue getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ServerDataSubmissionQueue();
        }
        return INSTANCE;
    }
    
    private void startWorker(){
        Thread worker = new Thread(() -> {

            while (true) {
                try {

                    Runnable task = queue.take(); // waits if empty
                    task.run();

                } catch (InterruptedException e) {
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
                }

            }

        });

        worker.setDaemon(true);
        worker.start();
    }
    
    /**
     * ServerDataSubmissionQueue.getInstance()
        .notifyAction(() -> ServerDataSubmissionQueue.getInstance().doImageSync());

        ServerDataSubmissionQueue.getInstance()
                .notifyAction(() -> ServerDataSubmissionQueue.getInstance().doChangeLogDataSync());
     * @param action 
     */
    public void notifyAction(Runnable action){
        queue.offer(action);
    }
        
    public void doImageSync(){

        String categoryFolder = ApplicationDataManager.CATEGORY_LOCAL_FOLDER_PATH;
        String itemFolder = ApplicationDataManager.ITEM_LOCAL_FOLDER_PATH;
        String receiptLogoFolder = ApplicationDataManager.RECEIPT_LOGO_LOCAL_FOLDER_PATH;

        new File(categoryFolder).mkdirs();
        new File(itemFolder).mkdirs();
        new File(receiptLogoFolder).mkdirs();

        // Category Images
        for (CategoryModel category : DatabaseManager.getInstance().getCategories()) {

            String fileName = category.getImageName();
            if (fileName == null || fileName.trim().isEmpty()) continue;

            File localFile = new File(categoryFolder + fileName);

            if (!localFile.exists()) {

                String serverFileLocation = WebService.IMAGE_FOLDER_PATH + "/category/" + fileName;

                downloadFile(serverFileLocation, localFile.getAbsolutePath());
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "File download - " +fileName);
            }
        }

        // Item Images
        for (Item item : DatabaseManager.getInstance().getItems()) {

            String fileName = item.image_name;
            if (fileName == null || fileName.trim().isEmpty()) continue;

            File localFile = new File(itemFolder + fileName);

            if (!localFile.exists()) {

                String serverFileLocation = WebService.IMAGE_FOLDER_PATH + "/item/" + fileName;

                downloadFile(serverFileLocation, localFile.getAbsolutePath());
                
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "File download - " +fileName);
            }
        }
        
        // Item Images
        String fileName = ApplicationDataManager.getInstance().getReceiptLogoName(Integer.parseInt(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId()));

        File localFile = new File(receiptLogoFolder + fileName);

        if (!localFile.exists()) {

            String serverFileLocation = WebService.IMAGE_FOLDER_PATH + "/other/" + fileName;

            downloadFile(serverFileLocation, localFile.getAbsolutePath());
            
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "File download - " +fileName);
        }
        ApplicationDataManager.getInstance().setReceiptLogo(localFile);
    }
    
    private void downloadFile(String fileURL, String savePath) {

        try (InputStream in = new URL(fileURL).openStream()) {

            Files.copy(in, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }

    }
    
    public void doChangeLogSaleDataSync(){
        // Check the available unposted data in the local database
        List<ChangeLog> changeLogs = DatabaseManager.getInstance().getUnpostedChangeLogData();
        
        for (ChangeLog changeLog : changeLogs) {
            
            if ("SALE".equals(changeLog.action)) {
                SaleInvoice saleInvoice = DatabaseManager.getInstance().getSaleInvoice(changeLog.pk_value);
                if (saleInvoice != null) {
                    List<SaleItem> saleItems = DatabaseManager.getInstance().getSaleItems((int) saleInvoice.rec_id);
                    saleInvoice.sale_items = saleItems;
                    
                    if (saleItems.isEmpty()) {                        
                        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, "Incomplete sale is detected in the local database");
                        continue;
                    }
                    
                    // Submit to server
                    APIHeaderData aPIHeaderData = new APIHeaderData();
                    aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                    aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());
                    
                    CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).sale(saleInvoice);
                    
                    // update the status
                    if (commonResponse.getAPIResponse().isSuccess() || 
                            ("01").equals(commonResponse.getAPIResponse().getResponseCode())) {
                        DatabaseManager.getInstance().updateChangeLogSynced(Integer.toString(changeLog.log_id));
                        
                        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, 
                                "Sale submitted to server - TRXN:" +changeLog.pk_value + 
                                        " STATUS:" + commonResponse.getAPIResponse().getResponseCode() + "-" + commonResponse.getAPIResponse().getMessage());
                    }
                    else{
                        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, 
                                "FAILED - Sale submit to server action failed - TRXN:" +changeLog.pk_value + 
                                        " STATUS:" + commonResponse.getAPIResponse().getResponseCode() + "-" + commonResponse.getAPIResponse().getMessage());
                    }
                }
            }
        }
        
        
    }
    
}
