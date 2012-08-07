package com.webatm.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: etyryshkin
 * Date: 7/4/12
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {

    private int id;
    private Date date;
    private Account account;
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
