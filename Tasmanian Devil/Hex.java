import java.awt.geom.*;

/**
 * Author @ Reynaldo Pena
 * This class is part of a project simulation containing classes World and TasmanianDevil. 
Within the project, the tasmanian devils inhabits a hexagonal grid. This class simulates a hex within the grid. 
*/

public class Hex{

   private int row;
   private int column;
   
   public Hex(int r, int c){
   row = r;
   column = c;
   }
   
   public int getRow(){
   return row;
   }
   
   public int getColumn() {
   return column;
   }
   
   //checks if two hexes are equal 
   public boolean equals (Hex other){
   if (other.getRow()==row && other.getColumn()==column)
      return true;
   else
      return false;
   }
   
   //returns the position of the hex in the grid
   public String toString(){
   String a = String.format ("[%d, %d]",row,column);
   return a;
   }
   
   //returns cartesian coordinate of hex
   public Point2D.Double getCartesian(){
   double x;
   double y;
   if (row%2==0) 
      x = column;
   else 
      x = column + 0.5;
   
   y = -(Math.sqrt(3)/2)*row;
   
   Point2D.Double coordinate = new Point2D.Double (x,y);
   
   return coordinate;
   }
   
   //returns distance from another hex
   public double distance (Hex other){
   Point2D.Double center1 = other.getCartesian();
   Point2D.Double center2 = getCartesian();
   double x1 = center1.getX();
   double x2= center2.getX();
   double y1= center1.getY();
   double y2= center2.getY();
   double dist = Math.sqrt(Math.pow((x2-x1),2) + Math.pow(y2-y1,2));
   return dist;
   }
   
   //This method allows the tasmanian devil to move one hex closer to its goal
   public Hex closer (Hex goal){
      int colGoal = goal.getColumn();
      int rowGoal = goal.getRow();
      
   // if the goal is equal to the current location, return goal 
   if (colGoal == column && rowGoal == row)
      return goal;
      
   // this condition applies only when the starting column and row 
   // both strictly less than or equal to the target col and row   
   else if (column <= colGoal && row <= rowGoal){
      //column and row cannot both be equal because of first if statement
      // so if one is equal, just move the other one until arriving at goal
      if (colGoal == column)
        return new Hex (row++, column);        
        
      else if (rowGoal == row)
        return new Hex (row, column++);
         
      // if both column and row are even
      else if (column % 2 == 0 && row % 2 == 0)
        return new Hex (row++, column);
         
      // if row is odd and column even or both are odd
      else if (row % 2 != 0 && column % 2 == 0 || row % 2 != 0 && column % 2 !=0)
         return new Hex (row++, column++); 
             
      
      // if row even and column odd
      else if (row % 2 == 0 && column % 2 != 0)
         return new Hex (row++, column);
         
   }
   
   // else if both starting column and row are greater than or equal 
   // to the goal column and row
   else if (column >= colGoal && row >= rowGoal){
      if (colGoal == column)
         return new Hex (row--, column);
      
      else if (rowGoal == row)
         return new Hex (row, column--);
         
      // if both column and row are even
      else if (column % 2 == 0 && row % 2 == 0)
         return new Hex (row--, column--);     
         
      // if row is odd and column even
      else if (row % 2 != 0 && column % 2 == 0)
         return new Hex (row, column--);
         
      //both are odd 
      else if (row % 2 != 0 && column % 2 !=0)
         return new Hex (row--, column);
         
      // if row even and column odd
      else if (row % 2 == 0 && column % 2 != 0)
         return new Hex (row--, column--);
      
   }     
    // the final condition applies if only one of the starting column or row is
    // less than the target column or row. 
   else if (row > rowGoal && column < colGoal){
   
      // if row is odd and col even
      if (row % 2 != 0 && column % 2 == 0)
         return new Hex (row--, column++);
        
      // if both row and column are even 
      else if (row % 2 == 0 && column % 2 == 0)
         return new Hex (row--, column);
         
      //if row is even and col odd 
      else if (row % 2 == 0 && column % 2 != 0)
         return new Hex (row--, column);
         
      // if both row and column are odd
      else if (row % 2 != 0 && column % 2 != 0)
         return new Hex (row--, column++);
   
   }      
         
   else if (row < rowGoal && column > colGoal){
      // if row is odd and col even
      if (row % 2 != 0 && column % 2 == 0)
         return new Hex (row++, column);
         
      // if both row and column are even  
      else if (row % 2 == 0 && column % 2 == 0)
         return new Hex (row++, column--);
      
      //if row is even and col odd 
      else if (row % 2 == 0 && column % 2 != 0)
         return new Hex (row++, column--);
        
      // if both row and column are odd
      else if (row % 2 != 0 && column % 2 != 0)
         return new Hex (row++, column);
      }
   
   return new Hex (row, column);
   }
   
   //this method checks whether two hexes are adjacent
   public boolean isAdjacent (Hex other){
      if (other.equals(this))
         return false;
         
      int r = other.getRow();
      int c = other.getColumn();
      
      if (r == row && c == column+1 || r == row && c == column -1)
         return true;
      
      else if (r == row + 1 && c == column || r == row -1 && c == column)
         return true;
      
      else if (row % 2 == 0){
         if (r == row -1 && c == column -1 || r == row +1 && c == column-1)
            return true;
      }
      
      else if (row %2 != 0){
         if (r == row + 1 && c == column + 1 || r == row -1 && c == column + 1)
            return true;
      }
      return false;
   }
  }
