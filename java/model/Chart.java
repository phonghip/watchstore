/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author khiem
 */
public class Chart {
    private int x;
    private int y;
    String month;
    Category brand;
    Product p;

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }
    public Category getBrand() {
        return brand;
    }

    public void setBrand(Category brand) {
        this.brand = brand;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Chart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Chart() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
