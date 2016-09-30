public class TasmanianDevil{

   private Hex location;
   private Hex goal;
   private World home;
   private boolean infected;
   
   public TasmanianDevil(World world){
      home = world;
      location = world.randomLocation();
      goal = world.randomLocation();
   }
   
   public TasmanianDevil(World w, boolean sick){
      home = w;
      location = w.randomLocation();
      goal = w.randomLocation();
      infected = sick;
   }
   
   public boolean isInfected (){
      return infected;
       }
      
   public Hex getLocation(){
   return location;
   }
   
   public Hex getGoal(){
   return goal;
   }
   
   public void infect(){
      infected = true;
   }
   
   public String toString(){
   String a = location + "->" + goal;
   return a;
   }
   
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

   

