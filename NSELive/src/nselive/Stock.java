/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nselive;

/**
 *
 * @author kmacharia
 */
public class Stock {
    private int id;
    private String name;
    private double priceYesterday;
    private double currentPrice;    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the priceYesterday
     */
    public double getPriceYesterday() {
        return priceYesterday;
    }

    /**
     * @param priceYesterday the priceYesterday to set
     */
    public void setPriceYesterday(double priceYesterday) {
        this.priceYesterday = priceYesterday;
    }

    /**
     * @return the currentPrice
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice the currentPrice to set
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
    
}
