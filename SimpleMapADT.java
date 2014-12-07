import java.util.List;

/**
 *
 * A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.
 *
 * The SimpleMapADT interface takes two generic parameters, K
 * and V, standing for the types of keys and values respectively.
 *
 */
public interface SimpleMapADT<K extends Comparable<K>, V> {

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null
     * if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null
     */
    public V get(K key);

    /**
     * Associates the specified value with the specified key in this map.
     * Neither the key nor the value can be null. If the map
     * previously contained a mapping for the key, the old value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or
     * null if there was no mapping for key.
     * @throws NullPointerException if the key or value is null
     */
    public V put(K key, V value);

    /**
     * Removes the mapping for the specified key from this map if present. This
     * method does nothing if the key is not in the map.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null
     * if there was no mapping for key.
     * @throws NullPointerException if key is null
     */
    public V remove(K key) ;

   /**
     * Returns the greatest key less than or equal to the given key, or null if there is no such key. 
	 * Throws NullPointerException if key is null. 
     * @param key key whose floor should be found
     * @return the largest key smaller than the one passed to it
     * @throws NullPointerException if key is null
     */
    public K floorKey(K key) ;
 
}
