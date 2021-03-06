  **Dijkstra's Shortest Paths Algorithm Implementation**
  
  This project attempts to find the shortest path between two vertices in a two dimensional, weighted
  and undirected graph using Dijkstra's shortest paths algorithm. It does this in an efficient time 
  complexity of O(k^2 *logk + p), where k is the number of vertices, p is the total length in edges 
  of all of the shortest paths reported, and using an expected O(k) edges. 
  In order to achieve this time complexity, I used two types of graphs: a corner graph and a normal weighted graph.
  The corner graph is a grid (composed of squares), where each square is a normal weighted graph with vertices 
  and edges living inside it. The corner graph is a grid graph whose edges within each square of the grid represent the 
  shortest path (using the vertices that live inside the square) between two corner vertices. Thus,in order to find the 
  shortest path between any two vertices, the algorithm computes the shortest path beween the starting vertex and the 
  closest corner vertex. Then it computes the shortest path between the destination vertex and the closest corner vertex. 
  Then it computes the shortest path (using the corner graph) between the starting and the ending corner vertices. 
  Then it combines these paths to get the shortest path overall. 
  The algorithm prints the total weight of the shortest path and the vertices along it. 

 NOTE ON RUNNING THE PROGRAM!!!
 As stated by Professor Jim Glenn:
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
