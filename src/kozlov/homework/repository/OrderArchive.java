package kozlov.homework.repository;

import kozlov.homework.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderArchive implements Archive<Integer, Order> {

    private List<Order> listOfOrders = new ArrayList<>();

    @Override
    public void addObject(Order order) {
        listOfOrders.add(order);
    }

    @Override
    public Optional<Order> getObjectByTag(Integer orderID) {

        return Optional.ofNullable(listOfOrders.get(orderID - 1));

    }

    public List<Order> asList() {
        return listOfOrders;
    }

    public int size(){
        return listOfOrders.size();
    }

}
