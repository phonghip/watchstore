/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author khiem
 */
public class Transaction {
    private int Tid;
    private int money;
    private int aid;
    private Date date;
    public Transaction(int Tid, int money, int aid) {
        this.Tid = Tid;
        this.money = money;
        this.aid = aid;
    }

    public Transaction(int Tid, int money, int aid, Date date) {
        this.Tid = Tid;
        this.money = money;
        this.aid = aid;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Transaction() {
    }

    public int getTid() {
        return Tid;
    }

    public void setTid(int Tid) {
        this.Tid = Tid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
    
    
}
