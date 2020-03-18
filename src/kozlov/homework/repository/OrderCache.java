package kozlov.homework.repository;

import kozlov.homework.entity.Order;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Cache memory implementation for Order instances
 * stored during timeout period in a HashMap collection.
 * Cleaning expired data is implemented in parallel thread started by cache constructor.
 */
public class OrderCache implements Cache<Integer, Order> {

    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 5;
    private static final long CLEAN_UP_PERIOD_IN_SECONDS = 3;

    private long timeout;

    private HashMap<Integer, CacheDataElement> dataMap;

    public OrderCache() {
        this(DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public OrderCache(long timeoutInSeconds) {

        System.out.println("[Cache Constructor] starts building a cache instance...");

        this.timeout = timeoutInSeconds;

        dataMap = new HashMap<>();

        Thread cleaningThread = new Thread(new CacheCleaner());

        cleaningThread.setDaemon(true);

        System.out.println("[Cache Constructor] switches concurrent cleaning thread on - in parallel to main program thread...");

        cleaningThread.start();

        System.out.println("[Cache Constructor] has made the cache instance (to be used as a fast access storage area).\n");

    }

    @Override
    public void put(Integer tag, Order order) {

        dataMap.put(tag, new CacheDataElement(order));

        System.out.println("[Cache] accepts new element: " + order);

    }

    @Override
    public Optional<Order> get(Integer tag) {

        Optional<CacheDataElement> cacheDataElement = Optional.ofNullable(dataMap.get(tag));

        if(cacheDataElement.isPresent()){

            return Optional.of(cacheDataElement.get().data);

        } else {

            return Optional.empty();

        }

    }

    @Override
    public int size() {

        return dataMap.size();

    }


    // Data wrapper with time stamp to be stored in cache
    private final class CacheDataElement {

        public Order data;

        public long insertTime;

        public CacheDataElement(Order data) {

            this.data = data;

            insertTime = System.currentTimeMillis();

        }

        @Override
        public String toString() {

            return "CacheDataElement{" +
                    "data=" + data +
                    ", insertTime=" + insertTime +
                    " msec}";

        }
    }

    /**
     * Object CacheCleaner is used to create and start special concurrent thread
     * with cleaning method run() to remove regularly expired data from cache.
     */
    private class CacheCleaner implements Runnable{

        @Override
        public void run() {

            while(!Thread.currentThread().isInterrupted()){

                try {

                    System.out.println("\t||[Cache Cleaner] goes to sleep...hr-rrr\n");

                    Thread.sleep(CLEAN_UP_PERIOD_IN_SECONDS * 1000);

                    System.out.println("\n\t||[Cache Cleaner] awakens and looks for something ripened to eat " +
                            "(i.e. something expired to clean up)...");

                    // The root cleaning method itself
                    cleanIfExpired();

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                }
            }

        }

    }

    // The root cleaning method
    private void cleanIfExpired(){

        synchronized (dataMap) {

            for (int i = 0; i < Order.getCounter(); i++) {

                Optional<CacheDataElement> cacheDataElement = Optional.ofNullable(dataMap.get(i + 1));

                if (!cacheDataElement.isPresent()) {

                    continue;

                }

                long lifeTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()
                        - cacheDataElement.get().insertTime);

                if (lifeTime > timeout) {

                    System.out.println("\t||[Cache Cleaner] removes expired element (lifeTime = " + lifeTime + " sec > "
                            + timeout + " sec = timeout): " + dataMap.get(i + 1).data);

                    dataMap.remove(i + 1);

                }

            }

        }

    }

    public HashMap<Integer, CacheDataElement> asMap(){

        return dataMap;

    }

    public boolean isEmpty(){

        return size() == 0;

    }

}
