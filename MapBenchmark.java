// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class MapBenchmark{

    public static void main(String[] args) {
        int numIter = 0; //number of iterations to run

       for(int ndx = 0;ndx < numIter;ndx++){
			//Basic progress bar
            System.out.print(String.format("%.2f",100* ndx/(float)numIter) +
							"% done \r"); 
		}


    }
}
