package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public String addOrderService(Order order){
        String id=order.getId();
        return orderRepository.addOrderToDb(id,order);
    }
    public String addPartnerService(String partnerId){
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        return orderRepository.addPartnerToDb(partnerId,deliveryPartner);
    }
    public Order getOrderByIdService(String orderId){
        return orderRepository.getOrderByIdFromDb(orderId);
    }
    public DeliveryPartner getPartnerByIdService(String partnerId){
        return orderRepository.getPartnerByIdFromDb(partnerId);
    }
    public Integer getOrderCountByPartnerIdService(String partnerId){
        DeliveryPartner deliveryPartner=orderRepository.getPartnerByIdFromDb(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }
    public String addOrderPartnerPairService(String orderId,String partnerId){
        String result=orderRepository.addOrderPartnerPairToDb(orderId,partnerId);
        DeliveryPartner deliveryPartner=orderRepository.getPartnerByIdFromDb(partnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        return result;
    }
    public List<String> getOrdersByPartnerIdService(String partnerId){
        return orderRepository.getOrdersByPartnerIdFromDb(partnerId);
    }
    public List<String> getAllOrdersService(){
        return orderRepository.getAllOrdersFromDb();
    }
    public Integer getCountOfUnassignedOrdersService(){
        return orderRepository.getCountOfUnassignedOrdersFromDb();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerIdService(String time,String partnerId){
        List<String> orders=orderRepository.getOrdersByPartnerIdFromDb(partnerId);
        String[] timeArr=time.split(":");
        int hour=Integer.parseInt(timeArr[0]);
        int min=Integer.parseInt(timeArr[1]);
        int givenTime=(hour*60)+min;
        int count=0;
        for(String orderId:orders){
            Order order=orderRepository.getOrderByIdFromDb(orderId);
            if(order.getDeliveryTime()>givenTime){
                count++;
            }
        }
        return count;
    }
    public String getLastDeliveryTimeByPartnerIdService(String partnerId){
        List<String> orders=orderRepository.getOrdersByPartnerIdFromDb(partnerId);
        int lastOrderDeliveryTime=0;
        for(String orderId:orders){
            Order order=orderRepository.getOrderByIdFromDb(orderId);
            lastOrderDeliveryTime=Math.max(lastOrderDeliveryTime,order.getDeliveryTime());
        }
        String hour= String.valueOf(lastOrderDeliveryTime/60);
        String min= String.valueOf(lastOrderDeliveryTime%60);
        return (hour.length()==1?("0"+hour):hour)+":"+(min.length()==1?("0"+min):min);
    }
    public String deletePartnerByIdService(String partnerId){
        List<String> orders=orderRepository.getOrdersByPartnerIdFromDb(partnerId);

        for(String orderId:orders){
            orderRepository.setUnassignedOrderDb(orderId);
        }
        return orderRepository.deletePartnerByIdFromDb(partnerId);
    }
    public String deleteOrderByIdService(String orderId){
        return orderRepository.deleteOrderByIdFromDb(orderId);
    }
}
