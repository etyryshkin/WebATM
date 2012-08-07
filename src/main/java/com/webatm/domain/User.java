package com.webatm.domain;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/4/12
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private int id;
    private String name;

    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
