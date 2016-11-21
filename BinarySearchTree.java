import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.management.*;
import java.util.*;
/**
 * A Splay Tree.
 *
 * @author Reynaldo Pena
 * @version 0.2 2016-09-13 added inorder iterator
 * @version 0.1 2014-10-08
 */

public class BinarySearchTree implements IntSet301
{

    private Node root;
    private int size;
    
    public int size(){
      return size;
    }
    
    public BinarySearchTree(){
      root = null;
      size = 0;
    }
    
    //if the splayed node's parent is the root
    private void simpleRotation (Node node){
    
      Node parent = root;
      
      if (node.isLeftChild()){
         Node rightOfNode = node.right;
         node.right = parent;
         parent.parent = node;
         parent.left = rightOfNode;
         if (rightOfNode != null) rightOfNode.parent = parent;

      }
      
      else{
         Node leftOfNode = node.left;
         node.left = parent;
         parent.parent = node;
         parent.right = leftOfNode;
         if (leftOfNode != null) leftOfNode.parent = parent;
        
      }

      root = node;
      root.parent = null;   
        
      }
      
    //if node is left child of a left child  or right child of right child
    private void ZigZagZagZag (Node node){
    Node parent = node.parent;
    Node grandparent = parent.parent;
    Node greatgp = grandparent.parent;
    
      if (node.isLeftChild()){
       Node rightOfParent = parent.right;
       Node rightOfNode = node.right;
       node.right = parent;
       
       if (greatgp != null){
         if (grandparent.isLeftChild()) greatgp.left = node;
         else if (grandparent.isRightChild())greatgp.right = node;
       }
       parent.right = grandparent;
       grandparent.parent = parent;
       parent.parent = node;
       grandparent.left = rightOfParent;
       if (rightOfParent!=null) rightOfParent.parent = grandparent;
       parent.left = rightOfNode;
       if (rightOfNode != null) rightOfNode.parent = parent;
       node.parent = greatgp;
       
      }
      
      else{
       
       Node leftOfParent = parent.left;
       Node leftOfNode = node.left;
       node.left = parent;
       
       if (greatgp != null){
         if (grandparent.isLeftChild()) greatgp.left = node;
         else if (grandparent.isRightChild())greatgp.right = node;
       }
       parent.left = grandparent;
       grandparent.parent = parent;
       parent.parent = node;
       grandparent.right = leftOfParent;
       if (leftOfParent!=null) leftOfParent.parent = grandparent;
       parent.right = leftOfNode;
       if (leftOfNode != null) leftOfNode.parent = parent;
       node.parent = greatgp;
      }
      
      if(grandparent == root) root = node;
      
    }  
      
      //if splayed node is left child of a right child or vice versa
      private void ZigZag (Node node){
      Node parent = node.parent;
      Node grandparent = parent.parent;
      Node greatgp= grandparent.parent;
      Node leftOfNode = node.left;
      Node rightOfNode = node.right;
      
         if (node.isRightChild()){
            node.left = parent;
            node.right = grandparent;
            node.parent = greatgp;
          if (greatgp != null){
            if (grandparent.isLeftChild()) greatgp.left = node;
            else if (grandparent.isRightChild())greatgp.right = node;
            }
            parent.parent = node;
            grandparent.parent = node;
            parent.right = leftOfNode;
            if (leftOfNode != null)leftOfNode.parent = parent;
            grandparent.left = rightOfNode;
            if (rightOfNode != null)rightOfNode.parent = grandparent;
           
         }
         
         else{
            node.right = parent;
            node.left = grandparent;
            node.parent = greatgp;
          if (greatgp != null){
            if (grandparent.isLeftChild()) greatgp.left = node;
            else if (grandparent.isRightChild())greatgp.right = node;
            }
            parent.parent = node;
            grandparent.parent = node;
            parent.left = rightOfNode;
            if (rightOfNode != null) rightOfNode.parent = parent;
            grandparent.right = leftOfNode;
            if (leftOfNode != null) leftOfNode.parent = grandparent;

         }
         
         if (grandparent == root) root = node;
      
      }
      
    //the splay method that calls each of the possible rotations
    public void splay(Node node){
    
    if (node != null){
      while (node != root){
            if (node.parent == root){
               simpleRotation (node);
            }
            
            else if ((node.isLeftChild() && node.parent.isLeftChild()) || (node.isRightChild() && node.parent.isRightChild())){
               ZigZagZagZag(node);
            }
            
            else ZigZag(node);
          }
       }
     }

            
    //if int n is in the tree, it returns the node containing n and splays it
    //else, it returns null. This method is used in contains(int n) and remove(int n)
    private Node getNodeContaining(int n){
    
    if (root ==null) return null;
    
      // start at root
	Node curr = root;

	// keep track of parent of current and last direction travelled
	Node parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to node containing n or to where it should be
	while (curr != null && (dir = curr.compareTo(n))!=0)
	     {
	 	parCurr = curr;
      
	 	if (dir < 0)
	 	    {
	 		curr = curr.left;
	 	    }
	 	else
	 	    {
	 		curr = curr.right;
	 	    }
	 	depth++;      
	     }
     
      //if we get to the bottom of the tree without finding the node, we splay the last node we found and return it
         if (curr == null && parCurr!= null) {
            splay(parCurr);
            return parCurr;
          }
         
         //else, we found the node containing n and we splay it
         else splay(curr);
     
     
    return curr;
    }
    
    //DELETE!!!
    private Node getNode(int n){
    
    if (root ==null) return null;
    
      // start at root
	Node curr = root;

	// keep track of parent of current and last direction travelled
	Node parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to node containing n or to where it should be
	while (curr != null && (dir = curr.compareTo(n))!=0)
	     {
	 	parCurr = curr;
      
	 	if (dir < 0)
	 	    {
	 		curr = curr.left;
	 	    }
	 	else
	 	    {
	 		curr = curr.right;
	 	    }
	 	depth++;      
	     }
     
      //if we get to the bottom of the tree without finding the node, we splay the last node we found and return it
         if (curr == null && parCurr!= null) {
            
            return parCurr;
          }
         
         //else, we found the node containing n and we splay it
     
     
    return curr;
    }
    
    public boolean containsNew(int n){
       if (getNode(n).compareTo(n) != 0) return false;
       
       return true;
    }
    
    
    
          
    //uses the above method to determine wether n is in the tree
    public boolean contains(int n){
       if (getNodeContaining(n).compareTo(n) != 0) return false;
       
       return true;
    }
    

    

    
    
        /**
     * Adds the given value to this tree if it is not already present.
     *
     * @param r a value
     */

    public void add(int r){
    
    // start at root
    
    if (root == null){
      size++;
      root = new Node(r,r, null);
    }
      
    
	Node curr = root;

	// keep track of parent of current and last direction travelled
	Node parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or node containing r
	while (curr != null && (dir = curr.compareTo(r))!=0 && curr.start-1 != r && curr.end+1 !=r)
	     {
      parCurr = curr;
	 	if (dir < 0)
	 	    {
        
	 		curr = curr.left;
	 	    }
	 	else 
	 	    {
	 		curr = curr.right;
	 	    }
	 	depth++;
	     }
        

      //if we get to the bottom of the tree without finding the node where r should go, we change curr to its parent
        
     if (curr== null) {
        curr= parCurr;
        parCurr= curr.parent;
     }   
      
       //if we broke the loop and our curr node doesn't contain r, we add a number to the tree
     if (curr.compareTo(r) !=0) {
         size++;
       
       //if we add to the start point, then we check for merging with its predecessor
       if (curr.start-1==r){
            curr.start = r;
            Node predecessor = predecessor(curr);
            if (predecessor != null && predecessor.end+1 == curr.start){
               curr.start = predecessor.start;
               delete(predecessor);
            }
        }
       
       //if we add to the end of the interval, we check for merging with its successor
       else if (curr.end+1==r){
         curr.end = r;
         Node successor = successor(curr);
         if (successor.start-1 == curr.end){
            curr.end = successor.end;
            delete(successor);
         }
       }
       
       // didn't find node containing r; create new one
		 else 
	     {
        //store node we were workign with because we are changing it to the new one
	 	  Node newNode = new Node(r, r, curr);
        // // link from parent 
         if (curr.end < r) curr.right = newNode;
         else curr.left = newNode;
         //System.out.println(curr.right);
         //System.out.println(newNode.parent);
         curr = newNode;
         
         }
       }
       
       //we splay curr, which is the node that contains the int we added

       splay(curr);
	    }
       
    //this method finds predecessor of a node
    //if there is no predecessor (if the node contains the smallest integer in the set)
    //then it simply returns the node
    //If the tree contains only one node (the root), it returns it. 
    public Node predecessor(Node node){
      if (node.left == null && node == root) return root;
      else if (node.left==null && node.isRightChild()) {
         splay(node.parent);
         return node.parent;
       }
      else if(node.left==null) {
         splay(node);
         return node;
       }
      Node predecessor = node.left;
      while(predecessor.right!=null) predecessor = predecessor.right;
      
      splay(predecessor);
      return predecessor;
      }
      
    //this method finds the successor of a node
    //if the tree contains only a single node, it returns it
    //if the node contains the largest number, it simply returns the node
    
    public Node successor(Node node){  
      if (node.right == null && node == root) return root;
      else if (node.right ==null && node.isLeftChild()) {
         splay(node.parent);
         return node.parent;
      }
      else if (node.right==null) {
         splay(node);
         return node;
       }
       
      Node successor = node.right;
      
      while (successor.left != null)
		      {
			  successor = successor.left;
		      }
            
       splay(successor);
      return successor;
    }

    /**
     * Removes r from this tree.  There is no effect if r is not in this tree.
     *
     * @param r the value to remove
     */
    public void remove(int r)
    {
    //we get the node containing r
    //getNodeContaining(r) splays and returns the node that contains r or the parent of where it should be if it doesn't
    //getNodeContaining(r) is in charge of splaying it 
    Node curr = getNodeContaining(r);

   //if our curr node contains r, we subtract one from size
   if (curr.compareTo(r) ==0) {size--;
   
   //if the node contains only int r, we delete it, find the predecessor and splay it (see delete)
   if (curr.start == r && curr.end == r) {
      delete(curr);
   }
      
   
   //if r is either the start or end of the node, we shrink the interval
   else if (curr.start==r) curr.start++;
   else if (curr.end==r) curr.end--;
       
   //else node is in the middle of the interval then we shrink the original one
   //we add a new node and since it gets splayed, it becomes the root and we can modify its endpoint easily
   else{
         int end = curr.end;
         curr.end = r-1;
         add(r+1);
         //we decrease size to counteract the fact that we increased in add();
         size--;
         root.end = end;
     }
     }
    }

    /**
     * Deletes the given node from this tree.
     *
     * @param curr a node in this tree
     */
     private void delete(Node curr)
    { 
    
   if (curr== root && size == 0) root = null;
   
   else{
   if (curr == root){
      Node predecessor = predecessor (curr);
      }

	if (curr.left == null && curr.right == null)
	    {
		Node parent = curr.parent;
		if (curr.isLeftChild())
		    {
			parent.left = null;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = null;
		    }
		else
		    {
			// deleting the root
			root = null;
		    }
	    }
	else if (curr.left == null)
	    {
		// node to delete only has right child
		Node parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.right;
			curr.right.parent = parent;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.right;
			curr.right.parent = parent;
		    }
		else
		    {
			root = curr.right;
			root.parent = null;
		    }
	    }
	else if (curr.right == null)
	    {
		// node to delete only has left child
		Node parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.left;
			curr.left.parent = parent;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.left;
			curr.left.parent = parent;
		    }
		else
		    {
			root = curr.left;
			root.parent = null;
		    }
	    }
	else
	      {
		  // node to delete has two children

		  // find inorder successor 
		  Node replacement = successor(curr);
		  Node replacementChild = replacement.right;
        Node replacementParent = replacement.parent;
        

		  // stitch up
		  if (curr.isLeftChild())
		      {
			  curr.parent.left = replacement;
		      }
		  else if (curr.isRightChild())
		      {
			  curr.parent.right = replacement;
		      }
		  else
		      {
			  root = replacement;
           root.parent = null;

		      }
		  
        if (curr == replacementParent){
         replacement.left = curr.left;
         curr.left.parent = replacement;
        }
        
        else{ 
		  replacement.parent.left = replacementChild;
		  if (replacementChild != null)
		      {
			  replacementChild.parent = replacement.parent;
		      }
		  
		  replacement.right = curr.right;
		  curr.right.parent = replacement;

		  replacement.left = curr.left;
		  curr.left.parent = replacement;

		  replacement.parent = curr.parent;
        }
	      }
         }
    }
    
    
    public int nextExcluded(int n){
    Node getNode = getNodeContaining(n);
    //if the node return does not contain n, then n is not in the tree and we return n
      if (getNode.compareTo(n) != 0) return n;
      
      //else, the node is in the tree and we return the next excluded element. 
      return getNode.end+1;
    }
    
        /**
     * Returns a printable representation of this tree.
     *
     * @return a printable representation of this tree
     */
    public String toString()
    {
	StringBuilder s = new StringBuilder();
	buildOutput(root, s, 0);
	return s.toString();
    }
    
        /**
     * Appends a printable representation of the subtree rooted at the
     * given node to the given string builder.
     *
     * @param curr a node in this tree
     * @param s a string builder
     * @param depth the depth of curr
     */
    private void buildOutput(Node curr, StringBuilder s, int depth)
    {
	if (curr != null)
	    {
		buildOutput(curr.left, s, depth + 1);
		s.append(depth + " " + curr + "\n");
		buildOutput(curr.right, s, depth + 1);
	    }
    }

 private static class Node
    {
	private int start, end;
   private Node parent;
	private Node left;
	private Node right;
		
     public Node(int s, int e, Node p)
	    {
		start = s;
      end = e;
      parent = p;
     }
     
      public int compareTo (int n){
      if (n<start) return -1;
      
      else if (n>end) return 1;
      
      return 0;
      }
      
      private boolean isLeftChild()
	   {
	    return (parent != null && parent.left == this);
	      }

	   private boolean isRightChild()
	   {
	    return (parent != null && parent.right == this);
	   }

      
      public String toString()
	   {
	    return "<" + start + "," + end + ">";
	    }
    }
   
   
    
   
    public static void main(String[] args)
    {
    
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    
	BinarySearchTree s = new BinarySearchTree();
//    int[] store = null;
// 	int[] testValues = new int[500000];
//       for (int f = 0; f<500000; f++){ 
//       testValues[f] = (int)(Math.random()*10000000);
//    }
//    
//    
//    //remove is removing more than it should
//    
//    store = testValues;
//    
//    //System.out.println(store.indexOf( 9156679));
// //    //(int)(Math.random()*10000000);
// // 
// //    int[] test = new int[50];
// //    int j = 0;
// //    for (int f = 0; f<50; f=f++){ 
// //       test[f] = j+2;
// //    }
// // // 
// //     
// // 
//    	for (int i : testValues)
// 	    {
// 		//System.out.printf("=== ADDING %d ===\n", i);
// 		s.add(i);	
// 	}
//    
//    for (int j=4999990; j<testValues.length; j++){
//       System.out.println(s.containsNew(testValues[j]));
//       }
// 
//    
//  long t = bean.getCurrentThreadUserTime();
//    int fail = 0;
//    Node hi= s.root;
//    int index =0;
//    try{
//    for (int i=0; i<testValues.length; i++){
//    if (s.size() <=3){
//       System.out.println(s);
//        System.out.println(testValues[i]);
//        System.out.println("array index: " + i);
//       }
//   fail = i;
//   hi = s.root;
//       s.remove(testValues[i]);
//       }
//     }
//     catch(NullPointerException E) {
//     System.out.println("fail");
//     System.out.println(fail);
//     System.out.println(s.size);
//     System.out.println();
//     System.out.println(s);}
// 
//       
//       System.out.printf ("add took %f seconds.\n",
//  			       (bean.getCurrentThreadUserTime()-t) / 1e9);

   int[] test = new int[100];
   int j = 0;
   for (int f = 0; f<100; f++){ 
      test[f] = j;
      j= j+2;
      }

   	for (int i : test)
	    {
		//System.out.printf("=== ADDING %d ===\n", i);
		s.add(i);	
	}
   
  
long t = bean.getCurrentThreadUserTime();

		for (int i = 0; i < 100; i++)
		    {
			int a = s.nextExcluded(test[i]);
         System.out.println(a);
 		    }
          
          
          System.out.printf ("add took %f seconds.\n",
 			       (bean.getCurrentThreadUserTime()-t) / 1e9);

// 	    
// // 
// 
// // 	for (int i = 0; i <= 11; i++)
// // 	    {
// // 		System.out.println(i + ": " + s.contains(i));
// // 	    }
// // 	System.out.println(s);
// // 


}

    }
