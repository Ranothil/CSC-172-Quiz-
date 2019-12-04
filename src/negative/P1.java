package negative;

import java.io.BufferedWriter;
import java.util.Scanner;
import java.lang.String;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;


/**
 * This class will detect the presence of a negative cycle within a weighted directed graph
 * provided within the input text file specified via command line arguments.
 * 
 * Workshop leader: Becky
 * 
 * @author Zachary Lee
 *
 */
public class P1
{    
	static String outputFile = "p1Output.txt";
   public static void main (String[] args) throws IOException 
   { 
   
      File file = new File(args[0]);
      Scanner scan = null;
      try {
          scan = new Scanner(file);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      
    // in this algorithm, we will need to examine the weight between each vertex with every other vertex.
  	// in the event that no path exists between two vertices, we will assign a large value to their weight 
     int maxWeight = 99999;      

      int n = scan.nextInt(); // read the first integer in the text file
      int m = scan.nextInt(); // read the second integer in the text file
      int graphArr[][] = new int[n][m];  // Initialize an array of the correct size to hold each edge
      for(int i = 0;i<n;i++) {
         for(int j = 0;j<m;j++) {
            if(i==j) graphArr[i][j] = 0;
            else graphArr[i][j] = maxWeight;
         }
      }
      for(int i = 0 ;i<n;i++) {
            int x = scan.nextInt()-1, y = scan.nextInt()-1; // text file is not 0 indexed like the array is, so subtract 1
            graphArr[x][y] = scan.nextInt();         
      }
            
      File output = new File(outputFile);
      BufferedWriter fileout = new BufferedWriter(new FileWriter(output));
      if (FloydWarshall(graphArr, n)) 
         fileout.write("YES");
      else
         fileout.write("NO"); 
      fileout.close();
   } 
   
   /**
    * Uses the Floyd Warshall algorithm to find the shortest paths between each pair of vertices
    * @param edgeArray this array is populated in main and it contains u, v, and weight information for edges
    * @param n represents the number of vertices in the graph
    * @return
    */
   static boolean FloydWarshall(int edgeArray[][], int n) 
   { 
      
      int vertexDistance[][] = new int[n][n];
   
      copyArray(vertexDistance, edgeArray,n);
   
      for (int i = 0; i < n; i++) 
      {        
         // Iterate through each vertex 
         for (int j = 0; j < n; j++) 
         {     
            // find the shortest distance to every other vertex from the vertex selected in the above loop
            for (int k = 0; k < n; k++) 
            {             
               // If vertex k is on the shortest path from 
               // i to j, then update the value of dist[i][j] 
               if (vertexDistance[j][i] + vertexDistance[i][k] < vertexDistance[j][k]) // if the distance is shorter, store it in the vertexDistance array
                     vertexDistance[j][k] = vertexDistance[j][i] + vertexDistance[i][k]; 
            } 
         } 
      } 
      
   return checkNegativeCycle(vertexDistance,n); 
   } 
   
   /**
    * Creates a copy of the edge array
    */
   static void copyArray(int[][] vertexDistance, int[][] edgeArray, int n)
   {
	   for (int i = 0; i < n; i++) 
	         for (int j = 0; j < n; j++) 
	            vertexDistance[i][j] = edgeArray[i][j]; 
   }
   
   /**
    * Checks if any node has a negative distance to itself, which the defintion of a negative cycle
    * due to the potential for infinite self visits to reduce weight.
    */
   static boolean checkNegativeCycle(int[][] vertexDistance, int n)
   {
	      for (int i = 0; i < n; i++) 
	         if (vertexDistance[i][i] < 0) 
	            return true;       
	     return false;
   }
}  