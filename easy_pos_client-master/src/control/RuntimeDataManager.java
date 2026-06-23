
package control;

import appDataModels.RuntimeData;

/**
 *
 * @author MalithWanniarachchi
 */
public class RuntimeDataManager {

    private static RuntimeDataManager INSTANCE;
    private RuntimeDataManager(){}
    
    private static RuntimeData runtimeData;
    
    public static RuntimeDataManager getInstance() {
        if (INSTANCE == null) {
            RuntimeDataManager.INSTANCE = new RuntimeDataManager();
            
            RuntimeDataManager.runtimeData = new RuntimeData();
        }
        return INSTANCE;
    }
    
    /**
     * @return the runtimeData
     */
    public RuntimeData getRuntimeData() {
        return runtimeData;
    }

    /**
     * @param runtimeData the runtimeData to set
     */
    public void setRuntimeData(RuntimeData runtimeData) {
        this.runtimeData = runtimeData;
    }
    
}
