Closest Pair Algorithm in time O(nlogn)

This class is part of a project to find the pair of 2D points that are 
the closest apart, given an input of 2D points. Instead of 
a brute force solution, which entails checking every pair of points in 
the input (time O(n^2)), this algorithm implements divide and conquer in 
time O(nlogn). The overall idea of the algorithm is as follows: divide the 
space of points in two sides containg an equal number of points. Find the 
closest pair of points on each side. Of these two pairs (one from each side),
find the one that is closest. Then check along the dividng line whether there 
is a closest pair than the one already found.
