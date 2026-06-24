
package control;

import appDataModels.UserAccountModel;
import dataModels.MenuItemType;
import java.util.ArrayList;

/**
 *
 * @author malit
 */
public class EventManager {
    
    private static EventManager INSTANCE;
    
    public static EventManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventManager();
        }
        return INSTANCE;
    }
    
    private final ArrayList<LoginEvent> loginEvents = new ArrayList<>();
    private final ArrayList<MenuItemChangeEvent> menuItemChangeEvents = new ArrayList<>();
    private final ArrayList<SalesMenuItemClickListener> salesMenuItemChangeEvents = new ArrayList<>();
    private final ArrayList<NumberPadKeyPressListener> numberPadKeyPressEvents = new ArrayList<>();
    private final ArrayList<LanguageChangeListener> languageChangeListeners = new ArrayList<>();
    
    public void addLoginEvent(LoginEvent loginEvent){
        loginEvents.add(loginEvent);
    }
    
    public void addMenuItemChangeEvent(MenuItemChangeEvent event){
        menuItemChangeEvents.add(event);
    }
    
    public void addSalesMenuItemClickEvent(SalesMenuItemClickListener event){
        salesMenuItemChangeEvents.add(event);
    }
    
    public void notifyLoginSuccess(UserAccountModel user) {
        for (LoginEvent loginEvent : loginEvents) {
            loginEvent.onLoginSuccess(user);
        }
    }
    
    public void notifyForgotPassword(String userName) {
        for (LoginEvent loginEvent : loginEvents) {
            loginEvent.onForgotPassword(userName);
        }
    }
    
    public void notifyMenuItemChanged(MenuItemType menuItemType) {
        for (MenuItemChangeEvent menuItemChangeEvent : menuItemChangeEvents) {
            menuItemChangeEvent.onMenuItemSelected(menuItemType);
        }
    }    
    
    public void notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem menuItemType) {
        for (SalesMenuItemClickListener event : salesMenuItemChangeEvents) {
            event.onMenuItemClicked(menuItemType);
        }
    } 

    public void addSalesPanelCustomNumberPadKeyEventListener(NumberPadKeyPressListener numberPadKeyPressListener) {
        numberPadKeyPressEvents.add(numberPadKeyPressListener);
    }
        
    public void notifySalesPanelCustomNumberPadKeyPressed(NumberPadKeyPressListener.NumberPadButton pressed) {
        for (NumberPadKeyPressListener event : numberPadKeyPressEvents) {
            event.onKeyPressed(pressed);
        }
    }

    public void addLanguageChangeListener(LanguageChangeListener listener) {
        languageChangeListeners.add(listener);
    }

    public void notifyLanguageChanged() {
        for (LanguageChangeListener listener : languageChangeListeners) {
            listener.onLanguageChanged();
        }
    }
}
