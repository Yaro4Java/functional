package kozlov.homework.repository;

import java.util.Optional;

/**
 * Long time storage container
 *
 */
public interface Archive<K, T> {

    void addObject(T object);

    Optional<T> getObjectByTag(K tag);

}
