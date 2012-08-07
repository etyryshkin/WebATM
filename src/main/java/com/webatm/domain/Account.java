package com.webatm.domain;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/4/12
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    private int id;
    private User owner;
    private Currency currency;
    private double amount;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
