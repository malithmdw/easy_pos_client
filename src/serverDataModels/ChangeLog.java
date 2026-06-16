
package serverDataModels;

/**
 *
 * @author MalithWanniarachchi
 */
public class ChangeLog {
    public int log_id; 
    public int institute_id; 
    public int terminal_id; 
    public String user; 
    public String timestamp; 
    public String action; 
    public String primary_db_table; 
    public String pk_column_name; 
    public String pk_value; 
    public String description; 
    public int is_synched;
}
