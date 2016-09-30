//this class is part of a three file project including classes Hex and World. 
//It simulates a tasmanian devil inhabiting a hexagonal grid


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

   

