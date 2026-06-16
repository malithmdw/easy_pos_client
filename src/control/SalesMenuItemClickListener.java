package control;

/**
 *
 * @author malit
 */
public interface SalesMenuItemClickListener {
    
    public enum SalesMenuItem{
        SEARCH_STOCK,
        WHOLE_SALE_BILL,
        RE_PRINT,
        CALCULATOR,
        NOTES,
        NUMBER_PAD;
    };
    
    public void onMenuItemClicked(SalesMenuItem menuItem);
}
