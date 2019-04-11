# CS-Projects
This repository contains some of the computer programming projects I did in my programming courses in college:

1) Closest Pairs Algorithm in time complexity O(nlogn)
2) Tasmanian Devil Disease Simulation
3) Sorting Algorithms
4) Modified Splay Tree using intervals as nodes
5) Modified Dijkstra's Shortest Paths Algorithm

**Modified Dijkstra's Shortest Paths Algorithm Explained:**

Suppose we want to find the shortest path between vertex v0.0.0 and v2.2.0
To achieve a more efficient time complexity using Dijkstra's Shortest Paths Algorithm, we first convert the weighted, undirected graph (left below) into a graph living inside a grid (right below):

<img src="https://github.com/reynaldop96/PICTURES/blob/master/pic6.png" width="1000">

Then, we employ Dijkstra's Shortest Paths Algorithm between all (blue) vertices of the new grid, and we set the weight (in red below) to each edge between the (blue) grid vertices.  Blue vertices that don't have a direct path to each other using green triangles are set to infinity. 

Then, we employ Dijkstra's Shortest Paths Algorithm between the origin vertex v0.0.0 and all blue vertices in the same cell. We do the same for the final vertex v2.2.0 and all adjacent blue vertices. Then we employ Dijkstra's Shortest Paths Algorithm again between the blue vertex closest to v0.0.0 and the blue vertex closest to v2.2.0
Finally, we return the total path and its weight. 

<img src="https://github.com/reynaldop96/PICTURES/blob/master/pic7.png" width="1000">
