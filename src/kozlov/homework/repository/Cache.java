package kozlov.homework.repository;

import java.util.Optional;

/**
 * Short life time but fast access memory
 *
 */
public interface Cache<K, T> {

    void put(K tag, T object);

    Optional<T> get(K tag);

    int size();

}
