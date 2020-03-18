package kozlov.homework.service;

import kozlov.homework.entity.Order;
import kozlov.homework.entity.OrderStatus;

public class OrderProcessor {

    MemoryManager memoryManager;

    public OrderProcessor(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    public void processOrder(int orderID){

        System.out.print("[Order Processor] tries to get Order" + orderID +
                " from cache to process it...");

        Order order = memoryManager.getObjectFromMemoryByTag(orderID);

        System.out.print("[Order Processor] processes an order: Order{ID=" + order.getOrderID() +
                ", status=" + order.status);

        if(order.status == OrderStatus.NOT_STARTED){

            order.status = OrderStatus.PROCESSING;

        } else if(order.status == OrderStatus.PROCESSING){

            order.status = OrderStatus.COMPLETED;
        }

        System.out.print(" -> " + order.status + "}\n");

        memoryManager.saveObjectInMemoryWithTag(orderID, order);

        // Just pausing a little bit - to be able to watch the process
        pauseInSeconds(1);

    }

    public boolean areAllOrdersCompleted(){

        for(int i = 0; i < Order.getCounter(); i++){

            System.out.print("[Order Processor] tries to get Order" + (i + 1) +
                    " from cache to check its status...");

            if(memoryManager.getObjectFromMemoryByTag(i + 1).status != OrderStatus.COMPLETED) {

                return false;

            }

        }

        return true;
    }

    private void pauseInSeconds(int timeToPauseInSeconds){

        try {

            Thread.sleep(timeToPauseInSeconds * 1000);

        } catch (InterruptedException e) {

            e.printStackTrace();
            
        }

    }


}
