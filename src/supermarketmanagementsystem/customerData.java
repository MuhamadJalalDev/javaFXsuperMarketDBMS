/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagementsystem;

import java.util.Date;


public class customerData {
    private Integer customer_id;
    private String brand;
    private String productName;
    private Integer quantity;
    private Double price;
    private Date date;

    public customerData(Integer customer_id, String brand, String productName, Integer quantity, Double price, Date date) {
        this.customer_id = customer_id;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
