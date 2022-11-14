package travelling_salesman;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class Main{
	
	public static final int CITIES = 8; 
	public static final String CITY_FILE = "test3atsp.txt";
	
	public static int[][] cityDistances = new int[CITIES][3];
	public static boolean[] citiesVisited = new boolean[cityDistances.length]; 
	//absolute distance b/w origin and the given city, done for ease of calculation
	public static double[][] absDistances = new double[CITIES][2]; 
	
	
	
	public static ArrayList<String> possiblePaths = new ArrayList<String>();
	public static ArrayList<Double> pathWeights = new ArrayList<Double>();
	public static ArrayList<String> NNpath = new ArrayList<String>();
	
	public static double shortestPathLength;
	public static char[] shortestPath; 
	
	public static void initState() {
		//initializing all arrays
		
		shortestPathLength = Double.MAX_VALUE;
		
		for (int row = 0; row < cityDistances.length; ++row) {
			for (int col = 0; col < cityDistances[row].length; ++col) {
				cityDistances[row][col] = 0;
			}
		}
		
		for (int city = 0; city < absDistances.length; ++city) {
			absDistances[city][0] = city + 1;
			absDistances[city][1] = 0;
		}
		
		for (int city = 0; city < citiesVisited.length; ++city) {
			citiesVisited[city] = false;
		}
	}
	
	
	public static void readFile() throws FileNotFoundException {

	        Scanner sc = new Scanner(new File("../travelling_salesman/testproblemstsp/"+CITY_FILE));
	        
	        while (sc.hasNextLine()) {
	            for (int row = 0; row < cityDistances.length; ++row) {
	            	if(sc.hasNextLine()) {
	                  //String[] line = sc.nextLine().trim().split(" ");
	            	  //remove all multiple spaces to replace them with a single space.
	                  String[] line = sc.nextLine().trim().replaceAll("\\s", " ").split(" "); 	
	                  
	                  for (int col = 0; col < line.length; ++col) {   
	                		cityDistances[row][col] = Integer.parseInt(line[col]);	
	                  }
	                  
	            	}
	            	else {
	            		break;
	            	}
	            }
	        }
	        
	}
	
	public static double pathWeight(String pathLine) {
		
		double total = 0;
		int fcityNum = 0; //first city number (index)
		int dcityNum = 0; //destination city number (index)
		
		String[] path = pathLine.split("\\s+");
		
		//calculating total path length of given path 
		for (int element = 0;  element < path.length - 1; ++element) {
			fcityNum = Math.abs(Integer.parseInt(path[element]) - 1);
			dcityNum = Math.abs(Integer.parseInt(path[element + 1]) - 1);
			total = total + Math.abs(absDistances[fcityNum][1] - absDistances[dcityNum][1]);
		}
		
		return total;
	}

	
	public static void nearestNeighbour() {
		
		//for each city, calculate distance to the nearest city
		for (int city = 0; city < cityDistances.length; ++city) {
			
			double shortestDist = Double.POSITIVE_INFINITY;
			
			for (int dist = 0; dist < cityDistances.length;++dist) {
				
				double distanceBW = Math.abs( Math.abs(absDistances[city][1]) - Math.abs(absDistances[dist][1]) );
				
				if(city != dist) {
					
					if(distanceBW < shortestDist) {
						shortestDist = distanceBW;
					}
					
					System.out.println("Distance b/w "+ absDistances[city][0] + " & "+ absDistances[dist][0] +" is: "+ distanceBW);
					
				} 
				
			}
			System.out.println("Shortest Distance is "+ shortestDist + "\n");
			
		}
		
		//move from each concurent city
		
		//if not all cities visited then visit first city and go to the shortest 
		//till all cities visited and add final returning length.      
		
	}
	
	
	//method to print permutations of specified array  
	public static void addPermuatations(int array[], int n)  {  
		
		ArrayList<String> pathLineArr = new ArrayList<String>();
		pathLineArr.add("1");
		for (int element = 0; element < n; element++) { 
			//System.out.print(array[i] + " ");
			pathLineArr.add(array[element] + "");
		}
		//throws the cursor to the next line
		pathLineArr.add("1");
		
		String pathLine = "";
		for (String string : pathLineArr) {
			pathLine += string + " ";
		}
		pathLine = pathLine.trim();
		//check if reverse present, only add if not
		StringBuilder sb = new StringBuilder(pathLine);
		
		//check if already present in reverse order, else store in arraylist
		if(!(possiblePaths.contains(sb.reverse().toString().trim()))) {
				
			pathWeights.add(pathWeight(pathLine));
			possiblePaths.add(pathLine);
			
			//calculate path weight
			if(pathWeight(pathLine) < shortestPathLength) {
				shortestPathLength = pathWeight(pathLine);
				shortestPath = possiblePaths.get(pathWeights.indexOf(shortestPathLength)).toCharArray();
			}
			
			//System.out.println(pathLine + "-> " + pathWeight(pathLine) + "\n");
		}
		
		 
	}  
	
	
	//finds permutation using Heap Algorithm  
	public static void findPermutation(int array[], int size, int n)  {  
	// if size becomes 1, it prints the obtained permutation  
		if (size == 1)  
			addPermuatations(array, n);  
		for (int city = 0; city < size; ++city)   {  
			findPermutation(array, size - 1, n);  
			//if the length of the array is odd, it swaps the 0th element with the last element   
		if (size % 2 == 1)   {  
			//performing swapping     
			int temp = array[0];  
			array[0] = array[size - 1];  
			array[size - 1] = temp;  
		}  
		//if the size of the array is even, it swaps the ith element with the last element  
		else{  
			//taking a temp variable for swapping     
			int temp;  
			//performing swapping   
			temp = array[city];  
			array[city] = array[size - 1];  
			array[size - 1] = temp;  
		}  
	}  
	}  
	
	
	public static void DFS() {
		
		int[] cities = new int[absDistances.length - 1];
		
		for (int count = 0; count < cities.length; ++count) {
			cities[count] = (int) absDistances[count][0] +  1;
		}
		 
		
		findPermutation(cities, cities.length, cities.length);
		
	}
	
	
	public static void main(String[] args) throws Exception {	 
		long startTime = System.nanoTime();
		 initState();
		 readFile();
		 	
		 for (int col = 0; col < cityDistances.length; ++col) {		
				absDistances[col][1] = Math.sqrt(Math.pow(cityDistances[col][1],2) + Math.pow(cityDistances[col][2],2));		
		 }
		 
		 
//		 for (int j = 0; j < absDistances.length; ++j) {
//			 	System.out.println( "Origin -> "+ absDistances[j][0] + " = "+ absDistances[j][1]);	 
//		 }
		 
		
		//nearestNeighbour();	
		DFS();	 
		long endTime = System.nanoTime();
		
		System.out.println("Total Cities = " + absDistances.length);
		
		
		
		System.out.println("\nPath: ");
		for (char element : shortestPath) {
			System.out.print(element + "");
		}
		
		System.out.println("");
		long duration = (endTime - startTime);
		System.out.println("\nLENGTH -> " + shortestPathLength + " (" + Math.round(shortestPathLength) +") units");
		System.out.println("\nTime: " + duration/1000000000 +" sec" );
	}
}
