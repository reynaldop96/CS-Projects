import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;

/**
 * A hex-grid world of Tasmanian Devils.
 *
 * @author Jim Glenn
 * @version 0.1 2015-09-28
 */

public class World
{
    /**
     * The width of this world.
     */
    private int width;

    /**
     * The height of this world, in rows.
     */
    private int height;
    
    private int totalDevils;
    private int numInfected;
    
    private ArrayList<TasmanianDevil> array = new ArrayList();

    /**
     * Creates an approximately square world of the given width.
     * 
     * @param w a positive integer
     */
    public World(int w)
    {
	width = w;
	height = (int)(w * 2 / Math.sqrt(3));
    }
    
    //create a world with a given number of healthy and sick devils
    // this method calls createDevils method
    public World(int w, int totalPop, int sick)
    {
	width = w;
	height = (int)(w * 2 / Math.sqrt(3));
   createDevils(totalPop, sick);
   }
     
     //returns height of world
    public int getHeight(){
       return height;
    }
    
    //returns width of world
    public int getWidth(){
      return width;
    }
    
    // returns numbers of devils currently infected
    public int countInfected(){
      return numInfected;
    }
  
    /**
     * Returns a random location in this world.
     *
     * @return a random location
     */
     
    public Hex randomLocation()
    {
	return new Hex((int)(Math.random() * height),
		       (int)(Math.random() * width));
    }
   
   //returns a list of devils in the world
   public List<TasmanianDevil> getDevils(){
      return array;
   }
   
   //update() moves all devils in this world one step closer toward goal
   public void update(){
      for (int i = 0; i< array.size(); i++){
         TasmanianDevil a = array.get(i);
         a.move();
        }
    }
    
   //simulate() simulates actions of devils until they are all infected
   //used a while loop to keep simulation going until all devils are infected
   //returns how many times simulation ran 
   public int simulate(){
   int counter=0;
      while (!allInfected()){
         infectSimulation();
         this.update();
         counter++;
      }
   return counter;
   }
   
   //allInfected() checks whether all devils are infected
   //returns true if they are all infected, false otherwise
   private boolean allInfected(){
      for (int i = 0; i<array.size(); i++){
         TasmanianDevil a = array.get(i);
         if (a.isInfected() == false)
            return false;
      }  
      return true;
  }
     
   //infectSimulation() goes through each devil in the array (i.e. in the world)  
   //and determines whether it is at the same position as another devil
   //if they are at the same position and if one of them is infected while the other is not
   //then it calls the encounter(a,b) method to determine whether the healthy devil
   //becomes infected. It then updates the new infected devil (or healthy, depending on outcome)
   //into the array
   
   private void infectSimulation(){
        for (int i = 0; i<array.size(); i++){
         TasmanianDevil a = array.get(i);
         //we make j = i to avoid having have more than one encounter on any given pair
           for (int j = i; j<array.size(); j++){
              TasmanianDevil b = array.get(j); 
              if (a.getLocation().equals(b.getLocation()) && a.isInfected() != b.isInfected()){
                  a.encounter(b);
                                      
     }}            
      }}
   
   /*the createDevils method creates a given number of devils in this world
   with a specific number of infected devils. It returns a List
   containing all the devils created.*/
   private List<TasmanianDevil> createDevils (int totalDevils, int infected){
      for (int i = 0; i<infected; i++){
      TasmanianDevil a = new TasmanianDevil(this, true);
         array.add(a);
         numInfected++;
         
      }
      
      for (int j = 0; j <(totalDevils-infected); j++){
         TasmanianDevil b = new TasmanianDevil(this, false);
         array.add(b);
      }
      
      return array;
      
   }
   
}  
