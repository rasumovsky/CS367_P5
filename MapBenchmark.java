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
    
    public static void main(String[] args) {
        int numIter; //number of iterations to run
	
	for(int ndx = 0;ndx < numIter;ndx++){
	    //Basic progress bar
            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
			     "% done \r"); 
	}
	
	
    }
}
