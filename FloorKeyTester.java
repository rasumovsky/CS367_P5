import java.util.*;
public class FloorKeyTester{

    public static void main(String[] args) {
        SimpleHashMap<Integer,String> hash = new SimpleHashMap<Integer,String>();

        hash.put(1,"one");System.out.println("Adding 1 to map");
        hash.put(5,"five");System.out.println("Adding 5 to map");
        hash.put(10,"ten");System.out.println("Adding 10 to map");


        System.out.println("floorKey(0) should return null since no key is less than or equal to 0");
        System.out.println("Your code returned "+hash.floorKey(0));

        System.out.println("floorKey(1) should return 1 since key 1 is less than or equal to 1");
        System.out.println("Your code returned "+hash.floorKey(1));       

        for(int ndx = 2;ndx < 5;ndx++){
            System.out.println("floorKey("+ndx+") should return 1 since key 1 is less than or equal to "+ndx);
            System.out.println("Your code returned "+hash.floorKey(ndx));

        }

        System.out.println("floorKey(5) should return 5 since key 5 is less than or equal to 5");
        System.out.println("Your code returned "+hash.floorKey(5));

        for(int ndx = 6;ndx < 9;ndx++){
            System.out.println("floorKey("+ndx+") should return 5 since key 5 is less than or equal to "+ndx);
            System.out.println("Your code returned "+hash.floorKey(ndx));

        }

        System.out.println("floorKey(10) should return 10 since key 10 is less than or equal to 10");
        System.out.println("Your code returned "+hash.floorKey(10));

        for(int ndx = 11;ndx < 16;ndx++){
            System.out.println("floorKey("+ndx+") should return 10 since key 10 is less than or equal to "+ndx);
            System.out.println("Your code returned "+hash.floorKey(ndx));

        }

        System.out.println(hash.get(10));

    }

}