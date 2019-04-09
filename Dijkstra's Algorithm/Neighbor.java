import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;

/*Neighbors class is used in three parts of the WeightedGraph class (each constructor is for each of the different uses):
   - to store the predecessor and shortest distance, in Dijkstra's algorithm
   - to store the weight, predecessor, and current vertex name in the graph's adjacency list
   - to store the path edges between corners of a corner graph
*/

public class Neighbor<K>{
   public int d;
   public K pred;
   public K curr;
   public int weight;
   public List<K> path;
   
   public Neighbor (K pred, K c, int w, List<K> p){
      this.pred = pred;
      curr = c;
      path = p;
      weight = w;
   }
   
    public Neighbor (K pred, K curr, int w){
      this.pred = pred;
      this.curr = curr;
      weight = w;
   }
   
   public Neighbor (K p, int d){
      pred = p;
      this.d = d;
   }

//overwrites the equals method so that a vertex equals another one if both the predecessor and the current neighbor are equal
   public boolean equals(Object n1){
      if (n1 instanceof Neighbor) return (pred.equals(((Neighbor)n1).pred) && curr.equals(((Neighbor)n1).curr)); 

      return false;
}

   public String toString(){
      return "(" + pred + ", " + curr + ")";
   }
 }
