import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.*;

/**
 * @author Reynaldo Peña
 * For use with ShortestPathsMain
 * An undirected, weighted, simple graph.  The vertex set is static; edges
 * may be added but not removed. This graph has two implementations: a corner graph
 * where edges represent shortest paths, and a normal weighted graph. 
 *
 * @param K the type of the vertices
 * @version 0.1 2015-10-27
 */

public class WeightedGraph<K> implements Graph<K>
{
    /**
     * This graph's vertex set.
     */
    private Set<K> verts;

       /**
     * This graph's adjacency lists.
     */
    private Map<K, List<Neighbor<K>>> adjLists;

    /**
     * Creates a graph with the given vertex set and no edges.
     *
     * @param v a collection of vertices
     */
    public WeightedGraph(Collection<K> v)
	{
	    // make ourselves a private copy of the vertex set
	    verts = new HashSet<K>(v);
   	    // set up empty adkacency lists for each vertex
	    adjLists = new HashMap<K, List<Neighbor<K>>>();
	    for (K src : verts)
		{
		    adjLists.put(src, new ArrayList<Neighbor<K>>());
		}
	}
   
   public List<Neighbor<K>> getNeighbors(K v){
      return adjLists.get(v);
   }

   public void addVertex (K newVertex){
      if (!verts.contains(newVertex)){
         adjLists.put(newVertex, new ArrayList<Neighbor<K>>());
         verts.add(newVertex);
      }
         
   }
   
   //this adds a path as an edge to our corner graph
   //w is the actual weight of the path
   public void addPathEdge(K u, K v, int w, List<K> path)
   {
       
	if (u.equals(v))
	    {
		throw new IllegalArgumentException("adding self loop");
	    }

	// get u's adjacency list
	List<Neighbor<K>> adj = adjLists.get(u);

   Neighbor<K> UtoV = new Neighbor (u, v, w, path);
   //then we add the edge from VtoU by reversing the path from UtoV. 
   List<K> reversePath = new ArrayList<K>(path);
   Collections.reverse(reversePath);
   Neighbor<K> VtoU = new Neighbor (v, u, w, reversePath);
   
	// check for edge already being there
	if (adj.contains(UtoV)){
      Neighbor<K> get = adj.get(adj.indexOf(UtoV));
      if (get.weight > UtoV.weight){
         adj.set(adj.indexOf(UtoV), UtoV);
         adjLists.put(u, adj);
         List<Neighbor<K>> getV = adjLists.get(v);
         getV.set(getV.indexOf(VtoU), VtoU);
         adjLists.put(v, getV);
         
   }
   }
   
   //not there, so just add it
   else {
      adjLists.get(u).add(UtoV);
		adjLists.get(v).add(VtoU);
	    }
    }
   
    /**
     * Adds the given edge to this graph if it does not already exist.
     *
     * @param u a vertex in this graph
     * @param v a vertex in this graph
     */
    public void addEdge(K u, K v, int w)
    {
    
    if (w<0){
    throw new IllegalArgumentException("Negative weights not allowed");
	    }
       
	if (u.equals(v))
	    {
		throw new IllegalArgumentException("adding self loop");
	    }

	// get u's adjacency list
	List<Neighbor<K>> adj = adjLists.get(u);//either u is not in map, or value of u is null. 

   //create a new object that holds both weight and the from vertex
   //since it is an unweighted graph we need two objects each containing
   //the respective edge vertex
   Neighbor<K> UtoV = new Neighbor (u, v, w);
   Neighbor<K> VtoU = new Neighbor (v, u, w);
   
	// check for edge already being there
	if (adjLists.get(u)!= null && !adj.contains(UtoV))
	    {
		// edge is not already there -- add to both adjacency lists
		adj.add(UtoV);
		adjLists.get(v).add(VtoU);
	    }
    }
    
    

    /**
     * Determines if the given edge is present in this graph.
     *
     * @param u a vertex in this graph
     * @param v a vertex in this graph
     * @return true if and only if the edge (u, v) is in this graph
     */
    public boolean hasEdge(K u, K v)
	{
   //iterates over all neighbor objects to check whether one contains v
   List<Neighbor<K>> neighbors = adjLists.get(u);
   for (Neighbor<K> a: neighbors){
      if (a.equals(v)) return true;
   }
   
	    return false;
	}

    /**
     * Returns an iterator over the vertices in this graph.
     *
     * @return an iterator over the vertices in this graph
     */
    public Iterator<K> iterator()
    {
	return (new HashSet<K>(verts)).iterator();
    }

    /**
     * Returns an iterator over the neighbors of the vertices in this graph.
     *
     * @param v a vertex in this graph
     * @return an iterator over the vertices in this graph
     */
    public Iterable<Neighbor<K>> neighbors(K v)
    {
	return new ArrayList<Neighbor<K>>(adjLists.get(v));
    }

    /**
     * Returns a printable represenation of this graph.
     *
     * @return a printable representation of this graph
     */
    public String toString()
    {
	return adjLists.toString();
    }
    
 //returns a map that maps a vertex to an object containing both its predecessor and the
 //shortest distance from the given source
 public Map<K, Neighbor<K>> dijkstra (K source){
      //we set that holds the vertices currently in the priority queue. 
      Set<K> verticesInQueue = new HashSet<K>(verts);
      PriorityQueue<K, Integer> pqueue = new PriorityQueue<K, Integer>();
      Map<K, Neighbor<K>> shortestDist = new HashMap<K, Neighbor<K>>();
      for (K v: verts){
         //this object will store the shortest distance, the predecessor, and its vertex name
         //initialize setting predecessor = null and d = infinity
         
         
         Neighbor<K> info = new Neighbor(null, Integer.MAX_VALUE);
         info.curr = v;
         if (v.equals(source)){
            info.d = 0;
            
            shortestDist.put(v, info);
            pqueue.addItem(v, 0);
          }
          
          else{
            shortestDist.put(v, info);
            pqueue.addItem(v, Integer.MAX_VALUE);
          }
       }
       
      while (pqueue.getSize() != 0){
         K u = pqueue.removeItem();
         verticesInQueue.remove(u);
         List<Neighbor<K>> neighbors = getNeighbors(u);
         for (Neighbor<K> v: neighbors){
            int u_d = shortestDist.get(u).d;
            Neighbor<K> v_temp =  shortestDist.get(v.curr);
            int v_d = v_temp.d;
            
            if (verticesInQueue.contains(v.curr) && (u_d + v.weight) < v_d){
               v_temp.d = (u_d + v.weight);
               v_d = v_temp.d;
               v_temp.pred = u;
               shortestDist.put(v.curr, v_temp);
               pqueue.decreasePriority(v.curr, v_d);
             }
          }
       }
          return shortestDist;
       }
       
    //returns an edge in our corner graph, i.e. a list of vertices representing the shortest path between two corners
    public List<K> getPathEdge (K from, K to){
         List<Neighbor<K>> neighbors = adjLists.get(from);
         Neighbor<K> temp = new Neighbor(from, to, 0);
         List<K> edge = new ArrayList<K>();
         if (neighbors!= null){
         for (Neighbor<K> a : neighbors) System.out.println(a);
         }
         
         return edge;
   }       
   
   //returns the weight between two vertices
   public int getWeight(K f, K t){
      List<Neighbor<K>> v = adjLists.get(f);
      int ind = v.indexOf(t);
      Neighbor<K> nei = v.get(ind);
      return nei.weight;
   }
       
    //this method returns a list of ordered vertices in the shortest path between from and to
    //This assumes we have already run Dijkstra from vertex source
    public List<K> getShortestPath(K source, K to, Map<K, Neighbor<K>> vertices){
    List<K> path = new ArrayList<K>();
    path.add(to);
    Neighbor<K> endV = vertices.get(to);
    
    //if we have a graph where edges represent only weights

      K pred = endV.pred;
         while(pred!= null && pred!= source){
            path.add(0, pred);
            pred = vertices.get(pred).pred;
         }
    
    return path;
    }
                 
}
