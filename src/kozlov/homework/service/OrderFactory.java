package kozlov.homework.service;

import kozlov.homework.entity.Order;
import kozlov.homework.entity.OrderStatus;

public interface OrderFactory {

    Order getInstanceOfOrder(OrderStatus status);

    Order getInstanceOfOrder();


    default Order getInstanceOfNotStartedOrder(){

        Order order = new Order();

        order.status = OrderStatus.NOT_STARTED;

        return order;

    }

    default Order getInstanceOfProcessingOrder(){

        Order order = new Order();

        order.status = OrderStatus.PROCESSING;

        return order;

    }

    default Order getInstanceOfCompletedOrder(){

        Order order = new Order();

        order.status = OrderStatus.COMPLETED;

        return order;

    }

}
