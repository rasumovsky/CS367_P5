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
     * @return min - the minimum value in the array
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
     * @return max - the maximum value in the array
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
     * @return the mean of the values in the array
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
     * @return SD - the standard deviation of the values in the array
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
	System.out.printf("\n%s\n",type);
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
    	SimpleHashMap<Integer,String> hashMap;
    	SimpleMapADT<Integer,String> treeMap;
    	
	// Create a list to store all the key-value pairs from file:
	List<Entry<Integer,String>> mapList
	    = (ArrayList<Entry<Integer,String>>) (new ArrayList());
	
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
	// Obtain the number of items
	int lines = 0;
	Scanner fileScanner = new Scanner(inputFile);
	while(fileScanner.hasNext()){
		lines++;
		fileScanner.nextLine();
	}

	int keyItr = 0;
	int valueItr = 0;
	int[] keys = new int[lines];
	String[] values = new String[lines];

	//Store key and values into arrays
	fileScanner = new Scanner(inputFile);
	while(fileScanner.hasNext()){
		String[] line = fileScanner.nextLine().split(" ");
	    int key = Integer.parseInt(line[0]);
	    String value = line[1];

	    keys[keyItr] = key;
	    values[valueItr] = value;
	    keyItr++;
	    valueItr++;
	}

	// Iterate over the operations to get better measurements:
	for (int ndx = 0; ndx < numIter; ndx++) {
	    
	    // Create new maps each iteration:
	    hashMap = new SimpleHashMap<Integer, String>();
	    treeMap = new SimpleTreeMap<Integer, String>();
	    
	    // Loop over 8 of the computations that require similar code
	    // (get, floor, remove for each map). Clock each in the loop.
	    for (int valIdx = 0; valIdx < 8; valIdx++) {
				
		// Clock the hashMap get() method:
		long startTime = System.currentTimeMillis();

	    // Iterate over keys stored in the structures:
	    for(int i = 0; i < lines; i++){
	    // Perform specified operation on specified structure:
	    switch (valIdx) {
	    case 0: hashMap.put(keys[i],values[i]);
		break;
	    case 1: treeMap.put(keys[i],values[i]);
		break;
	    case 2: hashMap.get(keys[i]);
		break;
	    case 3: treeMap.get(keys[i]);
		break;
	    case 4: hashMap.floorKey(keys[i]);
		break;
	    case 5: treeMap.floorKey(keys[i]);
		break;
	    case 6: hashMap.remove(keys[i]);
		break;
	    case 7: treeMap.remove(keys[i]);
		break;
	    }

		}
		
		// Calculate and store elapsed time:
		long elapsed = System.currentTimeMillis() - startTime;
		switch (valIdx) {
		case 0: hashPop[ndx] = elapsed;
		    break;
		case 1: treePop[ndx] = elapsed;
		    break;
		case 2: hashGet[ndx] = elapsed;
		    break;
		case 3: treeGet[ndx] = elapsed;
		    break;
		case 4: hashFloor[ndx] = elapsed;
		    break;
		case 5: treeFloor[ndx] = elapsed;
		    break;
		case 6: hashRemove[ndx] = elapsed;
		    break;
		case 7: treeRemove[ndx] = elapsed;
		    break;
		}
	    }
	    
	    //Basic progress bar
System.out.print(String.format("%.2f",100* ndx/(float)numIter) + "% done \r"); 
	}
	fileScanner.close();
	// Obtain and print results for each structure and operation:
	System.out.println("Result:");
	printResult(hashGet, "HashMap: get");
	printResult(hashPop, "HashMap: put");
	printResult(hashFloor, "HashMap: floorKey");
	printResult(hashRemove, "HashMap: remove");
	printResult(treeGet, "TreeMap: get");
	printResult(treePop, "TreeMap: put");	
	printResult(treeFloor, "TreeMap: floorKey");	
	printResult(treeRemove, "TreeMap: remove");
    }
}
