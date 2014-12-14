///////////////////////////////////////////////////////////////////////////////
// 
// Main Class File:  MapBenchmark.java 
// File:             Entry.java
// Semester:         CS367 Fall 2014
//
// Author:           Andrew Hard 
// Email:            hard@wisc.edu
// CS Login:         hard
// Lecturer's Name:  Jim Skrentny 
// Lab Section:      LEC-002 (77632)
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Wayne Chew
// Email:            mchew2@wisc.edu
// CS Login:         mchew
// Lecturer's Name:  Jim Skrentny
// Lab Section:      LEC-001 (77631)
//
///////////////////////////////////////////////////////////////////////////////

/**
 * A map entry (key-value pair).
 */
public class Entry<K, V> {
    
    private K key;
    private V value;
    
    
    /**
     * Constructs the map entry with the specified key and value.
     * @param k - map key
     * @param v - map value
     * @throws NullPointerException if the key or value are null.
     */
    public Entry(K k, V v) {
	
	// Check that proper arguments are provided:
	if (k == null || v == null) {
	    throw new NullPointerException();
	}
	
        key = k;
	value = v;
    }
    
    
    /**
     * Returns the key corresponding to this entry.
     * @return the key corresponding to this entry
     */
    public K getKey() {
        return key;
    }
    
    
    /**
     * Returns the value corresponding to this entry.
     * @return the value corresponding to this entry
     */
    public V getValue() {
        return value;
    }
    
    
    /**
     * Replaces the value corresponding to this entry with the specified
     * value.
     * @param value new value to be stored in this entry
     * @return old value corresponding to the entry
     * @throws NullPointerException if new value is null
     */
    public V setValue(V value) {
	
	// Check that argument has a value:
        if (value == null) {
	    throw new NullPointerException();
	}
	
	// store current value (to return) before assigning new value:
	V oldValue = this.value;
	this.value = value;
	return oldValue;
    }
    
}
