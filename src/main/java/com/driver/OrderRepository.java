package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private final HashMap<String,Order> orderDB=new HashMap<>();
    private final HashMap<String,DeliveryPartner> partnerDB=new HashMap<>();

    private final HashMap<String,List<String>> partnerToOrderDb=new HashMap<>();

    private final HashSet<String> unassignedOrderDb=new HashSet<>();
    public String addOrderToDb(String id,Order order){
        orderDB.put(id,order);
        unassignedOrderDb.add(id);
        return "New order added successfully";
    }
    public void setUnassignedOrderDb(String orderId){
        unassignedOrderDb.add(orderId);
    }
    public String addPartnerToDb(String partnerId,DeliveryPartner deliveryPartner){
        partnerDB.put(partnerId,deliveryPartner);
        return "New delivery partner added successfully";
    }
    public Order getOrderByIdFromDb(String orderId){
        return orderDB.getOrDefault(orderId,null);
    }
    public DeliveryPartner getPartnerByIdFromDb(String partnerId){
        return partnerDB.getOrDefault(partnerId,null);
    }
    public String addOrderPartnerPairToDb(String orderId,String partnerId){
        if(!partnerToOrderDb.containsKey(partnerId)){
            partnerToOrderDb.put(partnerId,new ArrayList<>());
        }
        partnerToOrderDb.get(partnerId).add(orderId);
        unassignedOrderDb.remove(orderId);
        return "New order-partner pair added successfully";
    }
    public List<String> getOrdersByPartnerIdFromDb(String partnerId){
        return partnerToOrderDb.getOrDefault(partnerId,new ArrayList<>());
    }
    public List<String> getAllOrdersFromDb(){
        return new ArrayList<>(orderDB.keySet());
    }
    public Integer getCountOfUnassignedOrdersFromDb(){
        return unassignedOrderDb.size();
    }
    public String deletePartnerByIdFromDb(String partnerId){
        partnerDB.remove(partnerId);
        return partnerId + " removed successfully";
    }
    public String deleteOrderByIdFromDb(String orderId){
        if(unassignedOrderDb.contains(orderId)){
            unassignedOrderDb.remove(orderId);
        }else{
            for(List<String> orders:partnerToOrderDb.values()){
                orders.remove(orderId);
            }
        }
        orderDB.remove(orderId);
        return orderId + " removed successfully";
    }
}
