/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import dataModels.MenuItemType;

/**
 *
 * @author malit
 */
public interface MenuItemChangeEvent {
    public abstract void onMenuItemSelected(MenuItemType menuItemType);
}
