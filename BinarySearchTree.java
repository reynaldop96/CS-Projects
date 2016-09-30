import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A binary search tree.
 * @version 0.2 2016-09-13 added inorder iterator
 * @version 0.1 2014-10-08
 */

public class BinarySearchTree<E extends Comparable<? super E>>
{
    private Node<E> root;
    private int size;

    /**
     * Counts the number of tree in this tree.
     *
     * @return the number of items in this tree
     */
    public int count()
    {
	return countRecursion(root); 
    }
    
    private int countRecursion (Node<E> curr){
       if (curr == null)
         return 0;
        
       Node<E> left = curr.left;
       Node<E> right = curr.right;
       int count =1;
         
       if (left != null)
          count += countRecursion (left);
       
       if (right != null)
          count += countRecursion(right);
          
     return count;
     }
    
    

    /**
     * Adds the given value to this tree if it is not already present.
     *
     * @param r a value
     */
    public boolean add(E r)
    {
	return add(r, true);
    }

    public boolean add(E r, boolean verbose)
    {
	// start at root
	Node<E> curr = root;

	// keep track of parent of current and last direction travelled
	Node<E> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or node containing r
	while (curr != null && (dir = r.compareTo(curr.data)) != 0)
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

	 if (curr == null)
	     {
	 	// didn't find containing node; create new one
	 	Node<E> newNode = new Node<E>(r, parCurr);
	 	size++;

	 	// link from parent according to last direction
	 	if (parCurr == null)
	 	    {
	 		root = newNode;
	 	    }
	 	else
	 	    {
			if (dir < 0)
			    {
				parCurr.left = newNode;
			    }
			else
			    {
				parCurr.right = newNode;
			    }
		    }
		
		// yes, we added
		return true;
	    }
	else
	    {
		// no, we didn't add
		return false;
	    }
    }

    /**
     * Removes r from this tree.  There is no effect if r is not in this tree.
     *
     * @param r the value to remove
     */
    public void remove(E r)
    {
	// start at root
	Node<E> curr = root;

	// keep track of parent of current and last direction travelled
	Node<E> parCurr = null;
	int dir = 0;
	int depth = 0;

	// traverse tree to insertion location or node containing r
	while (curr != null && (dir = r.compareTo(curr.data)) != 0)
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
	
	if (curr == null)
	    {
	    }
	else
	    {
		delete(curr);
	    }
    }

    /**
     * Deletes the given node from this tree.
     *
     * @param curr a node in this tree
     */
     private void delete(Node<E> curr)
    {
	size--; 

	if (curr.left == null && curr.right == null)
	    {
		Node<E> parent = curr.parent;
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
		Node<E> parent = curr.parent;

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
		Node<E> parent = curr.parent;

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

		  // find inorder successor (leftmopst in right subtree)
		  Node<E> replacement = curr.right;
		  while (replacement.left != null)
		      {
			  replacement = replacement.left;
		      }

		  Node<E> replacementChild = replacement.right;
        Node<E> replacementParent = replacement.parent;
        

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
    

    /**
     * Returns a new inorder iterator positioned at the first value
     * in this tree.
     *
     * @return an inorder iterator or this tree
     */
    public InorderIterator inorderIterator()
    {
	return new InorderIterator();
    }

    /**
     * Returns the inorder successor of the given node.
     *
     * @param curr a node in this tree that is not the rightmost
     * @return the inorder successor of that node
     */
    public Node<E> successor(Node<E> curr)
    {
    
    if (curr.right == null && curr.isLeftChild())
      return curr.parent;
      
    if (curr.right == null && curr.isRightChild())
      return curr.parent.parent;
      
    Node<E> leftmostRight = curr.right;
    while (leftmostRight.left != null)
         leftmostRight = leftmostRight.left;
      
	return leftmostRight; 
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
    private void buildOutput(Node<E> curr, StringBuilder s, int depth)
    {
	if (curr != null)
	    {
		buildOutput(curr.left, s, depth + 1);
		s.append(depth + " " + curr.data + "\n");
		buildOutput(curr.right, s, depth + 1);
	    }
    }

    /**
     * Checks the class invariants for this tree.  An assertion will
     * fail if the tree violates the order property or if size is not
     * maintained correctly.  (Run with the -ea option to enable
     * assertions.)
     */
    public void validate()
    {
	validate(root, null, null, 0);
	assert (count() == size) : "Number of nodes is " + count() + "; size=" + size;
    }

    private void validate(Node<E> curr, E lowerBound, E upperBound, int dir)
    {
	if (curr != null)
	    {
		assert (dir == 0 && curr.parent == null) || (dir == -1 && curr.parent.left == curr) || (dir == 1 && curr.parent.right == curr) : "Parent is incorrect at " + curr.data;

		assert (lowerBound == null || lowerBound.compareTo(curr.data) < 0) : "value violates lower bound: " + curr.data + "<=" + lowerBound;

		assert (upperBound == null || upperBound.compareTo(curr.data) > 0) : "value violates upper bound: " + curr.data + ">=" + upperBound;

		validate(curr.left, lowerBound, curr.data, -1);
		validate(curr.right, curr.data, upperBound, 1);
	    }
    }

    public static class Node<E>
    {
	private Node<E> parent;
	private Node<E> left;
	private Node<E> right;

	private E data;

	private Node(E d, Node<E> p)
	    {
		data = d;
		parent = p;
	    }

	private boolean isLeftChild()
	{
	    return (parent != null && parent.left == this);
	}

	private boolean isRightChild()
	{
	    return (parent != null && parent.right == this);
	}
    }

    /**
     * An inorder iterator through this BST.
     */
    public class InorderIterator implements Iterator<E>
    {
	/**
	 * The node this iterator is currently at (the next one to
	 * be returned by next.)
	 */
	private Node<E> curr;
	
	/**
	 * The number of times next has been invoked on this iterator.
	 */
	private int i;

	// Note that this implementation does not check that the
	// tree is not changed structurally while an iterator is active.
	// Think about what that is a problem and how we might detect
	// modifications so that the iterator can throw an exception if
	// the tree it is iterating through is changed while it is active.

	/**
	 * Creates an iterator thirough this tree.
	 */
	public InorderIterator()
	    {
		if (size > 0)
		    {
			curr = root;
			while (curr.left != null)
			    {
				curr = curr.left;
			    }
		    }
	    }

	/**
	 * Determines if this iterator has a next value.
	 */
	public boolean hasNext()
	{
	    return (i < size);
	}

	/**
	 * Returns the next value from this iterator.
	 */
	public E next()
	{
	    if (!hasNext())
		{
		    throw new NoSuchElementException();
		}
	    
	    E value = curr.data;
	    
	    if (i < size - 1)
		{
		    curr = successor(curr);
		}
	    i++;

	    return value;
	}

	public void remove()
	{
	    throw new UnsupportedOperationException();
	}
    }
}

    
