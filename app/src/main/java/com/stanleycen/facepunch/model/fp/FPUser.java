package com.stanleycen.facepunch.model.fp;

import java.io.Serializable;

/**
 * Created by scen on 2/18/14.
 */
public class FPUser implements Serializable {
    public String name;
    public int id;

    public FPUser() {}
    public FPUser(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
