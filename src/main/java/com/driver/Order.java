package com.driver;

import java.util.Arrays;
import java.util.List;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.deliveryTime=delivery(deliveryTime);
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    private  int delivery(String deliveryTime){
        List<String>list = Arrays.asList(deliveryTime.split(":"));
        int HH = Integer.parseInt(list.get(0));
        int MM = Integer.parseInt(list.get(1));
        return HH * 60 + MM;
    }


    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}