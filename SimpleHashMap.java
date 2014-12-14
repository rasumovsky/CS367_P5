///////////////////////////////////////////////////////////////////////////////
// 
// Main Class File:  MapBenchmark.java 
// File:             SimpleHashMap.java
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

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.
 *
 * The SimpleHashMap takes two generic parameters, K
 * and V, standing for the types of keys and values respectively.
 *
 */
public class SimpleHashMap<K extends Comparable<K>,V>
    implements SimpleMapADT<K, V> {
    
    private int[] tableSizes = { 11, 23, 47, 97, 197, 397, 797, 1597, 3203,
				 6421, 12853, 25717, 51437, 102877, 205759,
				 411527, 823117, 1646237, 3292489, 6584983,
				 13169977, 26339969, 52679969, 105359939,
				 210719881, 421439783, 842879579, 1685759167};
    private double lf = 0.75;
    
    // Store the number of elements contained:
    private int numItems;
    // Store the index for the table size prime number:
    private int sizeIndex;
    // Array to store the hash table with LinkedList buckets:
    private LinkedList<Entry<K, V>>[] hashTable;
    
    
    /**
     * Constructor for the SimpleHashMap class. Initializes the table size index
     * and create an empty hash table. 
     */
    public SimpleHashMap() {
	numItems = 0;
	sizeIndex = 0;
	hashTable = (LinkedList<Entry<K, V>>[])
	    (new LinkedList[tableSizes[sizeIndex]]);
    }
    
    
    /**
     * A private method for obtaining the hash value for key K. Return the java-
     * provided hashCode() value modulo the hash table size. Also make sure that
     * the hash value is positive.
     * @param k - the key value to convert to a hash value.
     * @return the new hash value.
     * @throws NullPointerException if key k is null. 
     */
    private int hash(K k) {
	
	// Check that arguments are given correctly:
	if (k == null) {
	    throw new NullPointerException();
	}
	
	// modulo the Java has value by the table size:
	int newHashValue = k.hashCode() % tableSizes[sizeIndex];
	
	// if the has value is negative, add table size:
	if (newHashValue < 0) { 
	    return newHashValue + tableSizes[sizeIndex];
	}
	else {
	    return newHashValue;
	} 
    }
    
    
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * @param key - the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null
     * if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null
     */
    public V get(K key) {
	
	// Check that specified key is contained.
	if (key == null) {
	    throw new NullPointerException();
	}
	
	// Check that the key corresponds to a hash table entry:
	if (hashTable[this.hash(key)] != null) {
	    
	    // Iterate over the bucket (LinkedList):
	    Iterator<Entry<K,V>> bucketIter
		= hashTable[this.hash(key)].iterator();
	    while (bucketIter.hasNext()) {
		
		Entry<K, V> currItem = bucketIter.next();
		
		if (key.equals(currItem.getKey())) {
		    return currItem.getValue();
		}
	    }
	}
	
	// Returns null by default (if no mapping contains the key)
	return null;
    }   
    

    /**
     * Associates the specified value with the specified key in this map.
     * Neither the key nor the value can be null. If the map
     * previously contained a mapping for the key, the old value is replaced.
     * @param key - key with which the specified value is to be associated
     * @param value - value to be associated with the specified key
     * @return the previous value associated with key, or
     * null if there was no mapping for key.
     * @throws NullPointerException if the key or value is null
     */
    public V put(K key, V value) {
	
	// Check that non-null arguments are provided:
	if (key == null || value == null) {
	    throw new NullPointerException();
	}
	
	// Old value corresponding to key to be returned:
	V result = null;
	
	Entry<K,V> currEntry = new Entry<K,V>(key, value);
	
	// If no hash table collision, create LinkedList to hold the item:
	if (hashTable[this.hash(currEntry.getKey())] == null) {
	    
	    // Create a new LinkedList bucket for the hash table with new entry:
	    LinkedList<Entry<K,V>> currBucket
		= (LinkedList<Entry<K,V>>) (new LinkedList());
	    currBucket.add(currEntry);
	    
	    // Add the new LinkedList bucket to the hash table:
	    hashTable[this.hash(currEntry.getKey())] = currBucket;
	}
	
	// If there is a collision, add to the LinkedList bucket:
	else {
	    
	    // Iterate to check for other elements with same key:
	    LinkedList<Entry<K,V>> currBucket
		= hashTable[this.hash(currEntry.getKey())];
	    
	    Iterator<Entry<K,V>> bucketIter = currBucket.iterator();
	    while (bucketIter.hasNext()) {
		
		Entry<K,V> currItem = bucketIter.next();
		
		// Update Entry value if a matching key found in the bucket:
		if (currItem.getKey().equals(currEntry.getKey())) {
		    result = currItem.setValue(currEntry.getValue());
		}
	    }
	    
	    // Otherwise, add new Entry to end of the LinkedList
	    if (result == null) {
		currBucket.add(currEntry);
	    }
	}
	
	// Check load factor, resize table if necessary, then return result:
	double currLoadFactor = (double)numItems / 
	    ((double)tableSizes[sizeIndex]);
	
	// Resize the hash table if load factor exceeded:
	if (currLoadFactor > lf) {
	    
	    // Duplicate the old hash table:
	    LinkedList<Entry<K,V>>[] oldHashTable = hashTable;
	    
	    // increment the hash index:
	    sizeIndex++;
	    
	    // Create a new hashTable to copy over old entries:
	    LinkedList<Entry<K,V>>[] hashTable = (LinkedList<Entry<K,V>>[])
		(new LinkedList[tableSizes[sizeIndex]]);;
	    
	    // Loop over old array.
	    for (int i = 0; i < oldHashTable.length-1; i++) {
		
		if (oldHashTable[i] != null) {
		    
		    // Iterate over the old bucket:
		    Iterator<Entry<K,V>> oldBucketIter
			= oldHashTable[i].iterator();
		    while (oldBucketIter.hasNext()) {
			
			Entry<K,V> oldEntry = oldBucketIter.next();
			
			// Recursive call to put() method:
			this.put(oldEntry.getKey(), oldEntry.getValue());
		    }
		}
	    }
	}
	
	// Return previous value to which key was mapped:
	return result;
    }   
    

    /**
     * Removes the mapping for the specified key from this map if present. This
     * method does nothing if the key is not in the map.
     * @param key - key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     * mapping for key.
     * @throws NullPointerException if key is null
     */
    public V remove(K key) {
	
	// Check that non-null key is provided:
	if (key == null) {
	    throw new NullPointerException();
	}
	
	// Return null if hash table doesn't have Entry corresponding to key
	if (hashTable[this.hash(key)] == null) {
	    return null;
	}
	
	// Otherwise, iterate over the corresponding bucket in the hash table:
	else {
	    LinkedList<Entry<K,V>> currBucket = hashTable[this.hash(key)];
	    Iterator<Entry<K,V>> bucketIter = currBucket.iterator();
	    while (bucketIter.hasNext()) {
		
		Entry<K,V> currItem = bucketIter.next();
		
		// If key of current Entry matches, remove and return the Entry:
		if (currItem.getKey().equals(key)) {
		    
		    V result = currItem.getValue();
		    currBucket.remove(currItem);
		    return result;
		}
	    }
	}
	
	return null;
    }
    
    
   /**
     * Returns the greatest key less than or equal to the given key, or null if
     * there is no such key. 
     * @param key key whose floor should be found
     * @return the largest key smaller than the one passed to it
     * @throws NullPointerException if key is null
     */
    public K floorKey(K key) {

	// Check that non-null key is provided:
	if (key == null) {
	    throw new NullPointerException();
	}
	
	K floor = null;
	
	// Loop over hash table elements:
	for (int i = 0; i < hashTable.length-1; i++) {
	    
	    // Iterate over buckets:
	    if (hashTable[i] != null) {
		
		Iterator<Entry<K,V>> bucketIter = hashTable[i].iterator();
		while (bucketIter.hasNext()) {
		    
		    Entry<K,V> currItem = bucketIter.next();
		    
		    // If equal, don't have to continue loop.
		    if (currItem.getKey().equals(key)) {
			return currItem.getKey();
		    }
		    
		    // Check if the Entry's key is less than the provided key:
		    else if (currItem.getKey().compareTo(key) == -1) {
			
			// Automatically assign floor if floor is null:
			if (floor == null) {
			    floor = currItem.getKey();
			}
			
			// Otherwise, update floor if new Entry has higher key:
			else if (currItem.getKey().compareTo(floor) == 1) {
			    floor = currItem.getKey();
			}
		    }
		}
	    }
	}
	
	return floor;
    }
}
