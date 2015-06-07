package de.orangestar.engine.input;

/**
 * Represents a key on a keyboard and its status.
 * 
 * @author Basti
 */
public class Key {

    /**
     * The press-state of a key.
     * 
     * @author Basti
     */
    public static enum KeyState {
        PRESSED, HOLD_DOWN, RELEASED, UP;
        
        public static boolean isDown(KeyState state) {
            return state == HOLD_DOWN || state == PRESSED;
        }
        
        public static boolean isUp(KeyState state) {
            return state == UP || state == RELEASED;
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-constructor
     * @param glfwkey
     */
    public Key(int glfwkey) {
        this(null, glfwkey);
    }
    
    /**
     * Public-constructor
     * @param keyboard Adds the created key instance to a {@link Keyboard} instance
     * @param glfwkey The corresponding glfw key
     */
    public Key(Keyboard keyboard, int glfwkey) {
        _glfwKey = glfwkey;
        _state = KeyState.UP;
        
        if (keyboard != null) {
            keyboard.addKey(this);
        }
    }
    
    /**
     * Updates the key's status.
     * @param isDown If the current status is being pressed
     */
    public void setStatus(boolean isDown) {
        // DOWN -> RELEASED_UP
        if ((_state == KeyState.HOLD_DOWN || _state == KeyState.PRESSED) && !isDown) {
            _state = KeyState.RELEASED;
            return;
        }
        
        // UP -> PRESSED_DOWN
        if ((_state == KeyState.UP || _state == KeyState.RELEASED) && isDown) {
            _state = KeyState.PRESSED;
            return;
        }
        
        // PRESSED -> HOLD_DOWN
        if (_state == KeyState.PRESSED && isDown) {
            _state = KeyState.HOLD_DOWN;
            return;
        }
        
        // RELEASED -> HOLD_UP
        if (_state == KeyState.RELEASED && !isDown) {
            _state = KeyState.UP;
            return;
        }
    }
    
    /**
     * Returns the current key state.
     * @return A key state
     */
    public KeyState getState() {
        return _state;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    int _glfwKey;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private KeyState _state;

}