package kozlov.homework;

import kozlov.homework.entity.Order;
import kozlov.homework.entity.OrderStatus;
import kozlov.homework.repository.OrderArchive;
import kozlov.homework.repository.OrderCache;
import kozlov.homework.service.MemoryManager;
import kozlov.homework.service.OrderProcessor;
import kozlov.homework.service.SimpleOrderFactory;

import java.util.Comparator;

public class HomeWork {

    public static void main(String[] args) {

        { /*** API AND OPTIONAL ( WORKING WITH CACHE ) ***/

            System.out.println("\n*** API AND OPTIONAL ***");
            System.out.println("\n1. Working with cache.\n");

            final int NUMBER_OF_ORDERS_TO_BE_PROCESSED = 10;

            OrderArchive archive = new OrderArchive();
            OrderCache cache = new OrderCache();

            MemoryManager memoryManager = new MemoryManager(archive, cache);

            OrderProcessor processor = new OrderProcessor(memoryManager);

            memoryManager.createNumberOfNewOrdersInMemory(NUMBER_OF_ORDERS_TO_BE_PROCESSED);

            while (!processor.areAllOrdersCompleted()) {

                for (int orderID = 1; orderID <= archive.size(); orderID++) {

                    processor.processOrder(orderID);

                }

            }

            System.out.println("\nAll " + NUMBER_OF_ORDERS_TO_BE_PROCESSED + " orders are now COMPLETED:\n");

            archive.asList().forEach(order -> System.out.println("- " + order));

            System.out.println("\n\n2. Using Optional as incoming parameter.\n");

            System.out.println("Optional can be used as a parameter for some method. But in this case it will cause\n" +
                    "additional conditions inside the method body to check the contents of the optional object\n" +
                    "before trying to get it and use as a reference. The code in method would look a little bit bulky\n" +
                    "and excessive then.");

            System.out.println("\n*** END OF API AND OPTIONAL ***\n");

        } /*** END OF API AND OPTIONAL ***/


        { /*** METHOD REFERENCES AND STREAMS ***/

            System.out.println("\n*** METHOD REFERENCES AND STREAMS ***");
            System.out.println("\n1. Creating boolean method dependant from numeric field orderID.\n");

            OrderArchive archive = new OrderArchive();

            final int NUMBER_OF_ORDERS = 50;

            Order.resetCounter();

            for(int i = 0; i < NUMBER_OF_ORDERS; i++){

                archive.addObject(new Order());

            }

            archive.asList().forEach(order ->
                    System.out.println(order +
                            (order.isEachTenthOrder() ? " each tenth! Good reason to celebrate!!!" : "")));


            System.out.println("\n\n2. Filtering stream by boolean field and sorting in reversed order.\n");

            archive.asList().stream()
                    .filter(order-> order.isEachTenthOrder()) // Here the boolean method is used!
                    .sorted(Comparator.comparingInt(Order::getOrderID).reversed())
                    .forEach(order -> System.out.println(order));


            System.out.println("\n*** END OF METHOD REFERENCES AND STREAMS ***\n");

        } /*** END OF METHOD REFERENCES AND STREAMS ***/


        { /*** DEFAULT METHODS ***/

            System.out.println("\n*** DEFAULT METHODS ***");
            System.out.println("\n1. Implementing OrderFactory interface with default methods.\n");

            SimpleOrderFactory orderFactory = new SimpleOrderFactory();

            Order.resetCounter();

            System.out.println(orderFactory.getInstanceOfOrder());
            System.out.println(orderFactory.getInstanceOfOrder(OrderStatus.PROCESSING));
            System.out.println(orderFactory.getInstanceOfOrder(OrderStatus.COMPLETED));

            System.out.println("\nNon-overridden default methods from interface will be hidden in the body of the class\n" +
                    "that implements it. To prevent such a case of blank class with non-informative void body\n" +
                    "we have to include as mandatory additional dispatcher method that will choose proper default method\n" +
                    "according to incoming parameter provided by the caller.");


            System.out.println("\n*** END OF DEFAULT METHODS ***\n");

        } /*** END OF DEFAULT METHODS ***/

    }

}
