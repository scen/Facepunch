package com.stanleycen.facepunch.event;

/**
 * Created by scen on 2/11/14.
 */
public class LoginResponseEvent {
    public boolean success = false;

    public LoginResponseEvent() {
    }

    public LoginResponseEvent(boolean b) {
        success = b;
    }
}
