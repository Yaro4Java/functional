package kozlov.homework.service;

import kozlov.homework.entity.Order;
import kozlov.homework.entity.OrderStatus;

public class SimpleOrderFactory implements OrderFactory {

    public Order getInstanceOfOrder(OrderStatus status) {

            switch (status) {

            case PROCESSING:
                return getInstanceOfProcessingOrder();

            case COMPLETED:
                return getInstanceOfCompletedOrder();

            case NOT_STARTED:
            default:
                return getInstanceOfNotStartedOrder();

            }
    }

    public Order getInstanceOfOrder() {

        return getInstanceOfOrder(OrderStatus.NOT_STARTED);

    }

}
