/**
 * This class is part of a project simulation containing classes Hex and World. 
 * The directions of the project were as follows: 
 	Write classes to complete a simulation of devils moving around a hex-grid world infecting 
	each other with devil facial tumor disease (DFTD). A world will be specified by the width of the 
	grid (the height should be chosen so that the world is roughly square in terms of Cartesian coordinates),
	the total number of devils in it, and the number of devils initially infected with DFTD. Each devil moves 
	around the world by randomly chosing a location on the grid and moving one hex closer to its target during 
	each step of the simulation. When a devil reaches its goal, it randomly chooses another goal. When two 
	devils are at the same location and one of them is infected with DFTD and the other is not, there is an 
	80% chance that the other devil becomes infected (if more than two devils are at the same location, this 
	interaction should be repeated for each pair of devils).
 
This class simulates an individual tasmanian devil inhabiting a hex-grid world. 
*/

public class TasmanianDevil{

   private Hex location;
   private Hex goal;
   private World home;
   private boolean infected;
   
   //creates a tasmanian devil in a heagonal world and sets whether it is sick or not
   public TasmanianDevil(World w, boolean sick){
      home = w;
      location = w.randomLocation();
      goal = w.randomLocation();
      infected = sick;
   }
   
   //returns whether the devil is infected
   public boolean isInfected (){
      return infected;
       }
      
   //returns location of the devil on the hexagonal grid
   public Hex getLocation(){
   return location;
   }
   
   //returns the hex that the devil is moving towards
   public Hex getGoal(){
   return goal;
   }
   
   //infects the devil
   public void infect(){
      infected = true;
   }
   
   //returns the current location of the devil and the goal it is moving towards
   public String toString(){
   String a = location + "->" + goal;
   return a;
   }
   
   //it moves the devil one step closer. This uses a method within Hex.java 
   public void move (){
   if (location.equals(goal))
      goal = home.randomLocation();   
   location.closer(goal);
      
   }
   
   //this method simulates the encounter between two devils, assuming one is infected
   
   public void encounter(TasmanianDevil devil){  
   int randomNum = (int) (Math.random()*10 + 1);
   
      if (infected == true && randomNum<=8)
          devil.infect(); 
            
      else if (devil.isInfected() == true && randomNum<=8)
          infected = true; 
         
      }   
      }

   

