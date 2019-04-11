# CS-Projects
This repository contains some of the computer programming projects I did in my programming courses in college:

1) Closest Pairs Algorithm in time complexity O(nlogn)
2) Tasmanian Devil Disease Simulation
3) Sorting Algorithms
4) Modified Splay Tree using intervals as nodes
5) Modified Dijkstra's Shortest Paths Algorithm

**Modified Dijkstra's Shortest Paths Algorithm Explained:**

Suppose we want to find the shortest path between vertex v0.0.0 and v2.2.0.
To achieve a more efficient time complexity using Dijkstra's Shortest Paths Algorithm, we first convert the weighted, undirected graph (left below) into a graph living inside a grid (right below):

<img src="https://github.com/reynaldop96/PICTURES/blob/master/pic6.png" width="1000">

Then, we employ Dijkstra's Shortest Paths Algorithm to find shortest path (using green triangles) between all (blue) vertices of the new grid, and we set the weight (in red below) to each edge between the (blue) vertices.  Blue vertices that don't have a direct path to each other using green triangles are set to infinity. 

Then, we employ Dijkstra's Shortest Paths Algorithm to find the shortest path between the origin vertex v0.0.0 and all blue vertices in the same cell (g0.0, g1.0, g0.1, and g1.1 below). We do the same for the final vertex v2.2.0 and all its adjacent blue vertices. Then we employ Dijkstra's Shortest Paths Algorithm between all blue vertices adjacent to v0.0.0 (g1.1 below) and all blue vertices adjacent to v2.2.0 (g3.2 and g2.3 below), using only blue vertices as a path.

(**Note: To find global shortest path between v0.0.0 and v2.2.0, we have to consider all paths between all 4 adjacent vertices to v0.0.0 and all 4 adjacent vertices to v2.2.0. This is not an issue because there are a maximum of 8 choose 2 = 28 paths to consider).**

<img src="https://github.com/reynaldop96/PICTURES/blob/master/pic7.png" width="1000">

Finally, we return the total path and its weight in time complexity of O(k^2 *logk + p), where k is the number of vertices, p is the total length in edges of all of the shortest paths reported, and using an expected O(k) edges. 

<img src="https://github.com/reynaldop96/PICTURES/blob/master/pic5.png" width="500">




