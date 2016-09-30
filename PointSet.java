import java.awt.geom.Point2D;
import java.util.*;
import java.util.Collections;

/*Reynaldo Pena 
This class is part of a project to find the pair of 2D points that are 
the closest apart, given an input of 2D points. Instead of 
a brute force solution, which entails checking every pair of points in 
the input (time O(n^2)), this algorithm implements divide and conquer in 
time O(nlogn). The overall idea of the algorithm is as follows: divide the 
space of points in two sides containg an equal number of points. Find the 
closest pair of points on each side. Of these two pairs (one from each side),
find the one that is closest. Then check along the dividng line whether there 
is a closest pair than the one already found.
*/


public class PointSet{

   //closestPair takes in the array of points, sorts them by both X and Y values
   //and then calls a recursive cp function
   public static PointPair closestPair(Point2D.Double[] pts){
   Point2D.Double[] copy = Arrays.copyOf(pts, pts.length);
   Point2D.Double[] sortX= sortX(copy);
   Point2D.Double[] sortY = sortY(pts);

   return cp(sortX, sortY);
   }
   
   private static Point2D.Double[] sortX (Point2D.Double[] pts){
    Arrays.sort(pts, new CompareX());
    return pts;
   }
   
   private static Point2D.Double[] sortY (Point2D.Double[] pts){
    Arrays.sort(pts, new CompareY());
    return pts;
   }
   
   //compare X sorts points by X coordinate. If X coordinates are equal, 
   //it sorts points by Y coordinate. It does this in order to take care of 
   //the case when there are multiple points along the partition line
   private static class CompareX implements Comparator<Point2D.Double>{
      public int compare (Point2D.Double point1, Point2D.Double point2){
      int a;
      if (point1.getX() < point2.getX()) a = -1;
         
      else if (point1.getX() > point2.getX()) a = 1;
         
      else{
         if (point1.getY() < point2.getY()) a = -1;
            
         else if (point1.getY() > point2.getY()) a = 1;

         else a = 0;
         }
         
         return a; 
      }
   }
   
   //compareY sorts points by Y coodinate
   private static class CompareY implements Comparator<Point2D.Double>{
      public int compare (Point2D.Double point1, Point2D.Double point2){
         return point1.getY() < point2.getY() ? -1: point1.getY() == point2.getY() ? 0:1; 
      }
   }


   // cp is a recursive function that takes the list of points
   // sorted by both their x and y coordiantes
   public static PointPair cp (Point2D.Double[] x, Point2D.Double[] y){
      // if list is less than or equal to three points implement brute force algorithm
      if (x.length <= 3) return bruteForce(x);
      
      PointPair closestPair;
      
      //xL = first half of x array
      Point2D.Double[]  xL = Arrays.copyOfRange(x, 0, x.length/2);
           
      //xR = right half of x array
      Point2D.Double[]  xR = Arrays.copyOfRange(x, x.length/2, x.length);
            
      //we make two arrays, one  containing the first half sorted by y (yL)
      //the other array containing the right half sorted by y (yR)
      
      //get the x-coordinate of the point that we have as partition
      //(this is the last element if xL)
      Point2D.Double point = xL[xL.length-1];
      double partitionX= point.getX();
      
      //Then we sort both halves by Y, taking advantage of the fact that we already have 
      //the list of ALL points sorted by Y
      Point2D.Double[] yL = new Point2D.Double[xL.length];
      Point2D.Double[] yR = new Point2D.Double[xR.length];
      
      int q = 0;
      int w = 0;
      for (Point2D.Double a: y){
         if (a.getX()< partitionX)
            yL[q++] = a; 
            
         else if (a.getX() > partitionX)
            yR[w++]=a;
           
         //if the x coordinates are equal, then we add the point to yL only if
         // yL is not yet full. If it is full, then we add the point to yR. 
         //Note that since the points with equal x coordinate were already 
         //sorted by Y, then xL and yL will have the same elements (as well as xR and yR).  
         else{
            if (yL.length != q)
               yL[q++] = a;
               
            else yR[w++] = a;
         }
     }

      // recurse on left half of x array
      PointPair closestLeft = cp(xL, yL);
      // recurse on right half of x array
      PointPair closestRight = cp(xR, yR);
      
      //find closest pair in both left and right of array
      if (closestLeft.getDistance()<closestRight.getDistance())
         closestPair = closestLeft;
         
      else
         closestPair = closestRight;
            
      double distance = closestPair.getDistance();
      // mY= points sorted by Y within closestPair.getDistance() of the xL/xR division
      ArrayList<Point2D.Double> mY = inDistance(y, xL[xL.length-1], distance); 
         
      //for each point in mY and subsequent points within distance vertically of it,
      //update closestPair if distance is less
      for (int i = 0; i<mY.size(); i++){
      //there are a max of 7 points to check
         for (int j = i+1; j <mY.size() && j<=i+7; j++){
            PointPair pair = new PointPair(mY.get(i), mY.get(j));
            if (pair.getDistance()< distance){
               distance = pair.getDistance();
               closestPair = pair;
            }
         }
      }
       
       return closestPair;     
     }
     
    // bruteForce solution
    public static PointPair bruteForce (Point2D.Double[] points){
      double min = Double.MAX_VALUE;
      PointPair minPair = new PointPair (points[0],points[1]);
      
      for (int i = 0; i< points.length-1; i++){
         for (int j = i+1; j< points.length; j++){
            PointPair pair = new PointPair(points[i], points[j]);
            if (pair.getDistance() < min){
               min = pair.getDistance();
               minPair = pair;               
             }
          }
       }
       return minPair;
    }
        
    // this method returns the points within distance d of the dividing line,
    // where d is the shortest distance between two points found so far
    // It returns the points sorted by Y
    public static ArrayList<Point2D.Double> inDistance(Point2D.Double[] y, Point2D.Double division, double distance){
         double dividingLine = division.getX();
         ArrayList points = new ArrayList<Point2D.Double>();
         for (Point2D.Double a : y){
            if (Math.abs(a.x - dividingLine) < distance)
               points.add(a);
          }
      return points;
         }
  }
      
