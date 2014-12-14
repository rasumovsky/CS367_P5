////////////////////////////////////////////////////////////////////////////////
// 
// Title:            MapBenchmark
// Files:            SimpleHashMap.java, SimpleTreeMap.java, Entry.java
// Semester:         CS302 Fall 2014
//
// Author:           Andrew Hard
// Email: x           hard@wisc.edu
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

// TODO *** add comments as specified in the commenting guide ***

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
 */
public class MapBenchmark {

	//calculate min
	public static long calMin(long[] array){
		long min = array[0];
		
		for(int i = 0; i < array.length; i++){
			if(min > array[i]){
				min = array[i];
			}
		}

		return min;
	}

	//calculate max
	public static long calMax(long[] array){
		long max = array[0];
		
		for(int i = 0; i < array.length; i++){
			if(max < array[i]){
				max = array[i];
			}
		}
		
		return max;
	}

	//calculate mean
	public static double calMean(long[] array){
		long total = 0;

		for(int i = 0; i < array.length; i++){
			total = total + array[i];
		}

		return (double)total / (double)array.length;
	}

	//calculate standard deviation
	public static double calSD(long[] array){

		double mean = calMean(array);

		//square the number subtract mean and add up
		double total = 0;
		double add;

		for(int i = 0; i < array.length; i++){
			add = (double)array[i] - mean;

			total = total + Math.pow(add, 2);
		}
		//return the square root of total divided by number of items
		return Math.sqrt(total/(double)array.length);
	}

	//print result
	public static void printResult(long[] array, String type){
		long min;
		long max;
		double mean;
		double sd;

		min = calMin(array);
		max = calMax(array);
		mean = calMean(array);
		sd = calSD(array);
		System.out.printf("\n%s:\n",type);
		System.out.printf("Min: %d\nMax: %d\nMean: %f\nStandard Deviation: %f\n", min, max, mean, sd);
	}
    
    public static void main(String[] args) throws FileNotFoundException{

    	int numIter = 0; //number of iterations to run
    	SimpleHashMap<Integer,String> hashMap = new SimpleHashMap<Integer, String>();
    	SimpleMapADT<Integer,String> treeMap = new SimpleTreeMap<Integer, String>();
    	List<Integer> keyList = new ArrayList<Integer>();

    	//create the hashMap and treeMap
    	if(args.length != 2){
    		System.out.println("Usage: java MapBenchmark input.txt NumIter");
    		System.exit(0);
    	}

    	
		//set number of iterations
		numIter = Integer.parseInt(args[1]);

		//set storage for results
		long[] hashPop = new long[numIter];
		long[] treePop = new long[numIter];		
		long[] hashGet = new long[numIter];
		long[] treeGet = new long[numIter];
		long[] hashFloor = new long[numIter];
		long[] treeFloor = new long[numIter];
		long[] hashRemove = new long[numIter];
		long[] treeRemove = new long[numIter];


		//check for inputFile
		File inputFile = new File(args[0]);

		if(!inputFile.exists()){
			System.out.println("Error: Cannot access inputFile");
			System.exit(0);
		}

		//store list of keys
		Scanner fileScanner = new Scanner(inputFile);

			while(fileScanner.hasNext()){
				String[] line = fileScanner.nextLine().split(" ");
				int key = Integer.parseInt(line[0]);
				String value = line[1];

				//store list of keys
				keyList.add(key);
			}

		//hashMap population iteration		
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			fileScanner = new Scanner(inputFile);

			while(fileScanner.hasNext()){
				//get key and value
				String[] line = fileScanner.nextLine().split(" ");
				int key = Integer.parseInt(line[0]);
				String value = line[1];

				//insert key and value into hashMap
				hashMap.put(key, value);

			}

			long elapsed = System.currentTimeMillis() - startTime;

			hashPop[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//Tree population iteration		
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			fileScanner = new Scanner(inputFile);

			while(fileScanner.hasNext()){
				//get key and value
				String[] line = fileScanner.nextLine().split(" ");
				int key = Integer.parseInt(line[0]);
				String value = line[1];

				//insert key and value into hashMap
				treeMap.put(key, value);

			}

			long elapsed = System.currentTimeMillis() - startTime;

			treePop[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//hashMap get iteration		
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				hashMap.get(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;

			hashGet[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//treeMap get iteration		
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				treeMap.get(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;

			treeGet[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//hashMap floorKey	
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				hashMap.floorKey(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;

			hashFloor[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//treeMap floorKey	
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				treeMap.floorKey(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;

			treeFloor[ndx] = elapsed;

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//hashMap remove
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				hashMap.remove(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;
			hashRemove[ndx] = elapsed;

			fileScanner = new Scanner(inputFile);

			while(fileScanner.hasNext()){
				//get key and value
				String[] line = fileScanner.nextLine().split(" ");
				int key = Integer.parseInt(line[0]);
				String value = line[1];

				//insert key and value into hashMap
				hashMap.put(key, value);

			}

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}

		//treeMap remove
		for(int ndx = 0;ndx < numIter; ndx++){
			long startTime = System.currentTimeMillis();
			
			Iterator<Integer> itr = keyList.iterator();
			while(itr.hasNext()){
				treeMap.remove(itr.next());
			}

			long elapsed = System.currentTimeMillis() - startTime;
			treeRemove[ndx] = elapsed;

			fileScanner = new Scanner(inputFile);

			while(fileScanner.hasNext()){
				//get key and value
				String[] line = fileScanner.nextLine().split(" ");
				int key = Integer.parseInt(line[0]);
				String value = line[1];

				//insert key and value into hashMap
				treeMap.put(key, value);

			}

		    //Basic progress bar
	            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
				     "% done \r"); 
		}
		
		//Result
		System.out.println("Result:");

		//result for populating hashMap
		printResult(hashPop,"Populating HashMap");

		//result for populating treeMap
		printResult(treePop, "Populating TreeMap");

		//result hashMap getValues
		printResult(hashGet, "HashMap get values");

		//result treeMap getValues
		printResult(treeGet, "TreeMap get values");

		//result hashMap floorKey
		printResult(hashFloor, "HashMap floorKey");

		//result treeMap floorKey
		printResult(treeFloor, "TreeMap floorKey");

		//result hashMap remove
		printResult(hashRemove, "HashMap remove");

		//result treeMap remove
		printResult(treeRemove, "TreeMap remove");
	}
}
