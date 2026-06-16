
package appDataModels;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import serverDataModels.SaleReturn;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnModel {
    private long recId;
    private String requestNo;
    private long saleInvoiceNo;
    private long customerId;
    private double totalAmount;
    private String requestDateTime;
    private String requestBy;
    private List<SaleReturnItemModel> saleReturnItemModels;

    // ✅ DTO → Model
    public SaleReturnModel(SaleReturn dto) {
        this.recId = dto.rec_id;
        this.requestNo = dto.request_no;
        this.saleInvoiceNo = dto.sale_invoice_no;
        this.customerId = dto.customer_id;
        this.totalAmount = dto.total_amount;
        this.requestDateTime = dto.request_date_time;
        this.requestBy = dto.request_by;

        if (dto.sale_return_items != null) {
            this.saleReturnItemModels = dto.sale_return_items.stream()
                    .map(SaleReturnItemModel::new)  // DTO → Model
                    .collect(Collectors.toList());
        } else {
            this.saleReturnItemModels = new ArrayList<>();
        }
    }

    // ✅ Model → DTO
    public SaleReturn newSaleReturnDTO() {
        SaleReturn dto = new SaleReturn();
        dto.rec_id = this.getRecId();
        dto.request_no = this.getRequestNo();
        dto.sale_invoice_no = this.getSaleInvoiceNo();
        dto.customer_id = this.getCustomerId();
        dto.total_amount = this.getTotalAmount();
        dto.request_date_time = this.getRequestDateTime();
        dto.request_by = this.getRequestBy();

        if (this.saleReturnItemModels != null) {
            dto.sale_return_items = this.saleReturnItemModels.stream()
                    .map(SaleReturnItemModel::newSaleReturnItemDTO)  // Model → DTO
                    .collect(Collectors.toList());
        } else {
            dto.sale_return_items = new ArrayList<>();
        }

        return dto;
    }

    // ✅ Getters & Setters
    public long getRecId() { return recId; }
    public void setRecId(long recId) { this.recId = recId; }

    public String getRequestNo() { return requestNo; }
    public void setRequestNo(String requestNo) { this.requestNo = requestNo; }

    public long getSaleInvoiceNo() { return saleInvoiceNo; }
    public void setSaleInvoiceNo(long saleInvoiceNo) { this.saleInvoiceNo = saleInvoiceNo; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getRequestDateTime() { return requestDateTime; }
    public void setRequestDateTime(String requestDateTime) { this.requestDateTime = requestDateTime; }

    public String getRequestBy() { return requestBy; }
    public void setRequestBy(String requestBy) { this.requestBy = requestBy; }

    public List<SaleReturnItemModel> getSaleReturnItemModels() { return saleReturnItemModels; }
    public void setSaleReturnItemModels(List<SaleReturnItemModel> saleReturnItemModels) { this.saleReturnItemModels = saleReturnItemModels; }
}
