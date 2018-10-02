import java.util.*;
import java.io.*;

public class ShortestPathsMain

/* @author Reynaldo Peña
  This class uses the following classes: WeightedGraph, Graph, Neighbor, PriorityQueue.
  This project attempts to find the shortest path between two vertices in a two dimensional, weighted
  and undirected graph using Dijkstra's shortest paths algorithm. It does this in an efficient time 
  complexity of O(k^2 *logk + p), where k is the number of vertices, p is the total length in edges 
  of all of the shortest paths reported, and uising an expected O(k) edges. 
  In order to achieve this time complexity, I used two types of graphs: a corner graph and a normal weighted graph.
  The corner graph is a grid (composed of squares), where each square is a normal weighted graph with vertices 
  and edges living inside it. The corner graph is a grid graph whose edges within each square of the grid represent the 
  shortest path (using the vertices that live inside the square) between two corner vertices. Thus,in order to find the 
  shortest path between any two vertices, the algorithm computes the shortest path beween the starting vertex and the 
  closest corner vertex. Then it computes the shortest path between the destination vertex and the closest corner vertex. 
  Then it computes the shortest path (using the corner graph) between the starting and the ending corner vertices. 
  Then it combines these paths to get the shortest path overall. 
  The algorithm prints the total weight of the shortest path and the vertices along it. 
 */
 
 /*NOTE ON RUNNING THE PROGRAM!!!
 As state by Professor Jim Glenn:
 - the input must be read from standard input where:
   a) the first line of input is three integers giving the width and height of the grid,
   b) the rest of the input is divided into two sections separated by the word "queries" on a separate line, where
   c) the first part contains information about the edges, one edge per line in the format "endpoint endpoint weight"; and
   the second part contains the endpoints of shortest paths we wish to compute, one query per line in the format "source destination"
 
 You may assume that

 - the weights are non-negative integers,
 - the names of the vertices are in the form "gi.j" for the corner vertex in the top left of the grid cell in row i, column j (0≤i≤h and 0≤j≤w) 
   and "v.i.j.k" for the kth vertex in cell (i, j), where 0≤k<n
 - each corner vertex has an edge to some non-corner vertex in each cell it is adjacent to,
 - edges from corners vertices to interior vertices are listed with the corner vertex first,
 - there are no edges directly between corner vertices,
 - no edge is listed in the input twice,
 - each cell is connected,
 - the graph is connected, and 
 - the source and destination vertices for shortest path queries exist and are non-corner vertices in different cells.
 */
{
    public static void main(String[] args) throws Exception
    {
	Scanner in = new Scanner(new File(args[0]));

	// read the first line with the dimensions of the grid
   
	int width = in.nextInt();
	int height = in.nextInt();
	int n = in.nextInt();

	// THIS WILL MAKE A ARRAY (okay, a list of lists since Java won't allow
	// arrays of generics) OF GRAPHS FOR THE INDIVIDUAL CELLS --
	// g.get(r).get(c) IS THE GRAPH FOR THE CELL IN ROW r COLUMN c

	// make an empty graph for each cell
	List<List<WeightedGraph<String>>> g = new ArrayList<List<WeightedGraph<String>>>();
	for (int r = 0; r < height; r++)
	    {
		List<WeightedGraph<String>> row = new ArrayList<WeightedGraph<String>>();
		for (int c = 0; c < width; c++)
		    {
			// make the list of vertices in this cell starting
			// with the corners...
			List<String> verts = new ArrayList<String>();
			verts.add("g" + r + "." + c); // upper left
			verts.add("g" + (r + 1) + "." + c); // lower left
			verts.add("g" + r + "." + (c + 1)); // upper right
			verts.add("g" + (r + 1) + "." + (c + 1)); // lower right

			//...then the interior vertices
			for (int k = 0; k < n; k++)
			    {
				verts.add("v" + r + "." + c + "." + k);
			    }

			// add that graph!
			row.add(new WeightedGraph<String>(verts));
		    }
		g.add(row);
	    }

	// loop over edges to add
	String from;
	while (!(from = in.next()).equals("queries"))
	    {
		String to = in.next();
		int w = in.nextInt();
		
		// the to vertex is always in the interior of the cell
		assert to.charAt(0) == 'v';

		// figure out from the to vertex which cell we're in
		StringTokenizer tok = new StringTokenizer(to.substring(1), ".");	
		int r = Integer.parseInt(tok.nextToken());
		int c = Integer.parseInt(tok.nextToken());
		
		// add the edge to the correct cell
		g.get(r).get(c).addEdge(from, to, w);
	    }
       
   //create a corner graph where edges store the shortest paths between corners
   List<String> initialize = Arrays.asList("g.0.0", "g.0.1", "g.1.0", "g.1.1");
   //initialize a cornerGraph with
   WeightedGraph<String> cornerGraph = new WeightedGraph<String>(initialize);
	for (int r = 0; r < height; r++)
	    {
			for (int c = 0; c < width; c++)
		    {
          cornerGraph.addVertex("g" + r + "." + c);
          cornerGraph.addVertex("g" + (r + 1) + "." + c);
          cornerGraph.addVertex("g" + r + "." + (c + 1));
          cornerGraph.addVertex("g" + (r + 1) + "." + (c + 1));
          
          WeightedGraph<String> graph = g.get(r).get(c);
          //we now run Dijkstra on each corner vertex to find the shortest distances starting at each corner vertex
          
          Map<String, Neighbor<String>> source1 = graph.dijkstra("g" + r + "." + c); // upper left
			 Map<String, Neighbor<String>> source2 = graph.dijkstra("g" + (r + 1) + "." + c); // lower left
			 Map<String, Neighbor<String>> source3 = graph.dijkstra("g" + r + "." + (c + 1)); // upper right
          
          
          //we get the weights between each of the new edges of the corner graph
          int w1 = source1.get("g" + (r + 1) + "." + c).d;
          int w2 = source1.get("g" + r + "." + (c + 1)).d;
          int w3 = source1.get("g" + (r + 1) + "." + (c + 1)).d;
          int w4 = source2.get("g" + r + "." + (c + 1)).d;
          int w5 = source2.get("g" + (r+1) + "." + (c + 1)).d;
          int w6 = source3.get("g" + (r + 1) + "." + (c + 1)).d;
          
          //now we get and store the path between each corner vertex
          List<String> path1 = graph.getShortestPath("g" + r + "." + c, "g" + (r + 1) + "." + c, source1); //upper left to lower left
          List<String> path2 = graph.getShortestPath("g" + r + "." + c, "g" + r + "." + (c + 1), source1); //upper left to upper right
          List<String> path3 = graph.getShortestPath("g" + r + "." + c, "g" + (r + 1) + "." + (c + 1), source1); //upper left to lower right
          List<String> path4 = graph.getShortestPath("g" + (r + 1) + "." + c, "g" + r + "." + (c + 1), source2);//lower left to upper right
          List<String> path5 = graph.getShortestPath("g" + (r + 1) + "." + c, "g" + (r+1) + "." + (c + 1), source2);//lower left to lower right
          List<String> path6 = graph.getShortestPath("g" + r + "." + (c + 1), "g" + (r + 1) + "." + (c + 1), source3);//upper right to lower right
          
          
          //add each of the paths found as an edge between corners with its corresponding weight
          cornerGraph.addPathEdge("g" + r + "." + c, "g" + (r + 1) + "." + c, w1, path1); //upper left to lower left
          cornerGraph.addPathEdge("g" + r + "." + c, "g" + r + "." + (c + 1), w2, path2); //upper left to upper right
          cornerGraph.addPathEdge("g" + r + "." + c, "g" + (r + 1) + "." + (c + 1), w3,  path3); //upper left to lower right
          cornerGraph.addPathEdge("g" + (r + 1) + "." + c, "g" + r + "." + (c + 1), w4, path4); //lower left to upper right
          cornerGraph.addPathEdge("g" + (r + 1) + "." + c, "g" + (r+1) + "." + (c + 1), w5, path5); //lower left to lower right
          cornerGraph.addPathEdge("g" + r + "." + (c + 1), "g" + (r + 1) + "." + (c + 1), w6, path6); //upper right to lower right
          

      }
   }

	// process the queries
	while (in.hasNext())
	    {
		from = in.next();
		String to = in.next();

		// determine what cells we're in
		StringTokenizer tok = new StringTokenizer(from.substring(1), ".");
		int fromR = Integer.parseInt(tok.nextToken());
		int fromC = Integer.parseInt(tok.nextToken());

		tok = new StringTokenizer(to.substring(1), ".");
		int toR = Integer.parseInt(tok.nextToken());
		int toC = Integer.parseInt(tok.nextToken());
		
		String[] fromCorners = {"g" + fromR + "." + fromC,
					"g" + (fromR + 1) + "." + fromC,
					"g" + fromR + "." + (fromC + 1),
					"g" + (fromR + 1) + "." + (fromC + 1)};
		String[] toCorners = {"g" + toR + "." + toC,
				      "g" + (toR + 1) + "." + toC,
				      "g" + toR + "." + (toC + 1),
				      "g" + (toR + 1) + "." + (toC + 1)};
      
       //Get the graphs where both the from and the to vertices live
       WeightedGraph<String> fromGraph = g.get(fromR).get(fromC);
       WeightedGraph<String> toGraph = g.get(toR).get(toC);
       
       //run Dijkstra on both, taking the from and the to vertices as a source, respectively
       Map<String, Neighbor<String>> fromDij = fromGraph.dijkstra(from);
       Map<String, Neighbor<String>> endDij = toGraph.dijkstra(to);
      
       //check distance between source and all four corners
       //store only the shortest one and the closest corner
       int shortestDist = Integer.MAX_VALUE;
       String startCorner = fromCorners[0];
       String endCorner = toCorners[0];
       for (int i = 0; i<4; i++){
         for (int j = 0; j<4; j++){
            int dist1 = fromDij.get(fromCorners[i]).d;
            int dist3 = endDij.get(toCorners[j]).d;
            Map<String, Neighbor<String>> cornerDij= cornerGraph.dijkstra(fromCorners[i]);
            int dist2 = cornerDij.get(toCorners[j]).d;
            if (dist1 + dist2 + dist3 < shortestDist){
               shortestDist = dist1 + dist2 + dist3;
               startCorner = fromCorners[i];
               endCorner = toCorners[j];
            }
         }
       }
            
            Map<String, Neighbor<String>> cornerDij= cornerGraph.dijkstra(startCorner);

            List<String> path1 = fromGraph.getShortestPath(from, startCorner, fromDij);
            List<String> path3 = toGraph.getShortestPath(to, endCorner, endDij);
            List<String> path2 = cornerGraph.getShortestPath(startCorner, endCorner, cornerDij);
       //completePath will store the entire path from source to destination
       List<String> completePath = new ArrayList<String>();
       
       //add the path from source vertex to starting corner
       completePath.addAll(path1);
       path2.remove(0);
       completePath.addAll(path2);
      //reverse the end path so that it goes from the corner to the destination vertex
       Collections.reverse(path3);
       path3.remove(0);
       completePath.addAll(path3);
       
     System.out.println(shortestDist + " " + completePath);

      

	    }
       
       
    }
 }
      
