package kozlov.homework.service;

import kozlov.homework.entity.Order;
import kozlov.homework.repository.OrderArchive;
import kozlov.homework.repository.OrderCache;

import java.util.Optional;

public class MemoryManager {

    private OrderArchive archive;

    private OrderCache cache;

    public MemoryManager(OrderArchive archive, OrderCache cache) {

        this.archive = archive;

        this.cache = cache;

    }

    public Order getObjectFromMemoryByTag(int tag){

        // Trying get fast access to data
        Optional<Order> objectFromCache = cache.get(tag);

        // If hitting - get the data!
        if(objectFromCache.isPresent()){

            System.out.println("HITTING!!!");

            return objectFromCache.get();

        } else { // If missing - use slow long term memory

            System.out.println("MISSING((( --> Getting data from slow archive...");

            return archive.getObjectByTag(tag).get();

        }

    }

    public void saveObjectInMemoryWithTag(int tag, Order order){

        Optional<Order> orderFromArchive = archive.getObjectByTag(tag);

        if(orderFromArchive.isPresent()){

            orderFromArchive.get().status = order.status;

        } else {

            archive.addObject(order);

        }

        // Saving a copy in cache
        cache.put(tag, order);

    }

    public void createNumberOfNewOrdersInMemory(int numberOfNewOrdersToCreate){

        synchronized (cache) {

            System.out.println("Creating " + numberOfNewOrdersToCreate +
                    " new orders in memory to be processed (in long term archive and in fast access cache):\n");

            for (int i = 0; i < numberOfNewOrdersToCreate; i++) {

                Order newOrder = new Order();

                archive.addObject(newOrder);

                cache.put(newOrder.getOrderID(), newOrder);

            }

            System.out.println();

        }

    }


}
