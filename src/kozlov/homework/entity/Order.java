package kozlov.homework.entity;

public class Order {

    private static int counter;

    private final int orderID;

    public OrderStatus status;

    public Order() {

        orderID = ++counter;
        status = OrderStatus.NOT_STARTED;

    }

    public int getOrderID() {
        return orderID;
    }

    public static int getCounter() {
        return counter;
    }

    public static void resetCounter(){
        counter = 0;
    }

    @Override
    public String toString() {

        return "Order{" +
                "ID=" + orderID +
                ", status=" + status +
                "}";

    }

    public boolean isEachTenthOrder(){
        return orderID % 10 == 0;
    }
}

