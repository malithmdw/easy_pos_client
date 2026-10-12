
package control;

/**
 *
 * @author malit
 */
public interface NumberPadKeyPressListener {
    
    public enum NumberPadButton
    {
        BUTTON_1,
        BUTTON_2,
        BUTTON_3,
        BUTTON_4,
        BUTTON_5,
        BUTTON_6,
        BUTTON_7,
        BUTTON_8,
        BUTTON_9,
        BUTTON_0,
        BUTTON_DOT,
        BUTTON_BACK,
        BUTTON_OK;
    }  
    
    public void onKeyPressed(NumberPadButton pressed);
}
