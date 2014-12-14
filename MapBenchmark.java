////////////////////////////////////////////////////////////////////////////////
// 
// Title:            MapBenchmark
// Files:            SimpleHashMap.java, SimpleTreeMap.java, Entry.java
// Semester:         CS302 Fall 2014
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

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The MapBenchmark class provides a comparison of the SimpleTreeMap and 
 * SimpleHashMap performances for various tasks. The program reads input files
 * containing key-value pairs and a non-zero integer NumIter from the command
 * line, populates the two maps, gets every key-value pair from the maps, then
 * removes every key-value pair from the maps. The times required to perform
 * each task are measured and reported. 
 *
 * @author Ming Chew
 * @author Andrew Hard
 */
public class MapBenchmark {
    
    
    /**
     * A private method for calculating the minimum value in an array.
     * @param array - the array of numbers in which the minimum will be found.
     */
    private static long calMin(long[] array) {
	long min = array[0];
	
	for (int i = 0; i < array.length; i++) {
	    if (min > array[i]) {
		min = array[i];
	    }
	}
	return min;
    }
    
    
    /**
     * A private method for calculating the maximum value in an array.
     * @param array - the array of numbers in which the maximum will be found.
     */
    private static long calMax(long[] array) {
	long max = array[0];
	
	for (int i = 0; i < array.length; i++) {
	    if (max < array[i]) {
		max = array[i];
	    }
	}
	return max;
    }
    
    
    /**
     * A private method for calculating the mean value of an array.
     * @param array - the array of numbers for which the mean is found.
     */
    private static double calMean(long[] array) {
	long total = 0;
	
	for (int i = 0; i < array.length; i++) {
	    total = total + array[i];
	}
	return (double)total / (double)array.length;
    }
    
    
    /**
     * A private method for calculating the standard deviation of an array.
     * @param array - the array of numbers for which the std deviation is found.
     */
    private static double calSD(long[] array) {
	
	double mean = calMean(array);
	
	//square the number subtract mean and add up
	double total = 0;
	double add;
	
	for (int i = 0; i < array.length; i++) {
	    add = (double)array[i] - mean;
	    
	    total = total + Math.pow(add, 2);
	}
	//return the square root of total divided by number of items
	return Math.sqrt(total/(double)array.length);
    }
    
    
    /**
     * A method for printing results.
     * @param array - the array to use for calculations of min, max, mean, etc.
     * @param type - the data structure and operation names making the numbers. 
     */
    private static void printResult(long[] array, String type) {
	
	long min;
	long max;
	double mean;
	double sd;
	
	min = calMin(array);
	max = calMax(array);
	mean = calMean(array);
	sd = calSD(array);
	System.out.printf("\n%s:\n",type);
	System.out
	    .printf("Min: %d\nMax: %d\nMean: %f\nStandard Deviation: %f\n",
		    min,
		    max,
		    mean,
		    sd);
    }
    
    
    /**
     * The main method of the analysis.
     * @param args - The input file and number of iterations for the program. 
     * @throws FileNotFoundException if the provided input file is invalid.
     */
    public static void main(String[] args) throws FileNotFoundException {
	
    	int numIter = 0; // Number of iterations to run
    	SimpleHashMap<Integer,String> hashMap
	    = new SimpleHashMap<Integer, String>();
    	SimpleMapADT<Integer,String> treeMap
	    = new SimpleTreeMap<Integer, String>();
    	List<Integer> keyList = new ArrayList<Integer>();
	
    	// Create the hashMap and treeMap
    	if (args.length != 2) {
	    System.out.println("Usage: java MapBenchmark input.txt NumIter");
	    System.exit(0);
    	}
	
	// Set number of iterations
	numIter = Integer.parseInt(args[1]);
	
	// Set storage for 4 results:
	long[] hashPop = new long[numIter];
	long[] treePop = new long[numIter];		
	long[] hashGet = new long[numIter];
	long[] treeGet = new long[numIter];
	long[] hashFloor = new long[numIter];
	long[] treeFloor = new long[numIter];
	long[] hashRemove = new long[numIter];
	long[] treeRemove = new long[numIter];
	
	// Check for inputFile
	File inputFile = new File(args[0]);
	
	if (!inputFile.exists()) {
	    System.out.println("Error: Cannot access inputFile");
	    System.exit(0);
	}
	
	// Scan input file to store list of keys:
	Scanner fileScanner = new Scanner(inputFile);
	while (fileScanner.hasNext()) {
	    String[] line = fileScanner.nextLine().split(" ");
	    int key = Integer.parseInt(line[0]);
	    String value = line[1];
	    
	    // Store list of keys
	    keyList.add(key);
	}
	
	// Clock the hashMap put() method for population:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    fileScanner = new Scanner(inputFile);
	    
	    while (fileScanner.hasNext()) {
		// Get key and value
		String[] line = fileScanner.nextLine().split(" ");
		int key = Integer.parseInt(line[0]);
		String value = line[1];
		
		// Insert key and value into hashMap
		hashMap.put(key, value);
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    hashPop[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the treeMap put() method for population:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    fileScanner = new Scanner(inputFile);
	    
	    while (fileScanner.hasNext()) {
		// Get key and value
		String[] line = fileScanner.nextLine().split(" ");
		int key = Integer.parseInt(line[0]);
		String value = line[1];
		
		// Insert key and value into hashMap
		treeMap.put(key, value);
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    treePop[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the hashMap get() method:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		hashMap.get(itr.next());
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    hashGet[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the treeMap get() method:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		treeMap.get(itr.next());
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    treeGet[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the hashMap floorKey() method:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		hashMap.floorKey(itr.next());
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    hashFloor[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the treeMap floorKey() method:	
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		treeMap.floorKey(itr.next());
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    treeFloor[ndx] = elapsed;
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the hashMap remove() method:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		hashMap.remove(itr.next());
	    }
	    
	    // Calculate and store elapsed time:
	    long elapsed = System.currentTimeMillis() - startTime;
	    hashRemove[ndx] = elapsed;
	    
	    // ************
	    // I think this shows why we only should have one iteration loop.
	    fileScanner = new Scanner(inputFile);
	    while (fileScanner.hasNext()) {
		//get key and value
		String[] line = fileScanner.nextLine().split(" ");
		int key = Integer.parseInt(line[0]);
		String value = line[1];
		
		//insert key and value into hashMap
		hashMap.put(key, value);
	    }
	    // ************
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	
	// Clock the treeMap remove() method:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    long startTime = System.currentTimeMillis();
	    
	    Iterator<Integer> itr = keyList.iterator();
	    while (itr.hasNext()) {
		treeMap.remove(itr.next());
	    }
	    
	    long elapsed = System.currentTimeMillis() - startTime;
	    treeRemove[ndx] = elapsed;
	    
	    
	    // ************
	    // I think this shows why we only should have one iteration loop.
	    fileScanner = new Scanner(inputFile);
	    while (fileScanner.hasNext()) {
		//get key and value
		String[] line = fileScanner.nextLine().split(" ");
		int key = Integer.parseInt(line[0]);
		String value = line[1];
		
		//insert key and value into hashMap
		treeMap.put(key, value);
	    }
	    // ************
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
		
	// Obtain and print results for each structure and operation:
	System.out.println("Result:");
	printResult(hashPop,"Populating HashMap");
	printResult(treePop, "Populating TreeMap");
	printResult(hashGet, "HashMap get values");
	printResult(treeGet, "TreeMap get values");
	printResult(hashFloor, "HashMap floorKey");
	printResult(treeFloor, "TreeMap floorKey");
	printResult(hashRemove, "HashMap remove");
	printResult(treeRemove, "TreeMap remove");
    }
}
