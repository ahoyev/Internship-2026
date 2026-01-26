import java.util.EmptyStackException;

public final class MyLinkedStack<T> implements StackInterface<T>
{
	private Node topNode; // References the first node in the chain
	
	public MyLinkedStack()
	{
		topNode = null;
	} // end default constructor
	
	public void push(T newEntry)
	{
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
		//topNode = new Node(newEntry, topNode); // Alternate code
	} // end push

	public T peek()
	{
		if (isEmpty())
			throw new EmptyStackException();
		else
         return topNode.getData();
	} // end peek

	public T pop()
	{
	   T top = peek();  // Might throw EmptyStackException
   // Assertion: topNode != null
      topNode = topNode.getNextNode();

	   return top;
	} // end pop

/****************************************************************************************
 * Task 1: Implement a countItems method that determines and returns the total number of 
 * elements in the stack without altering its contents. This method must preserve 
 * both the original order and state of the stack. It should achieve this by 
 * traversing the stack nodes rather than removing or rearranging any elements.
 */
	   public int countItems() {
	        int count = 0;
	        Node current = topNode;
	        while (current != null) {
	            count++;
	            current = current.getNextNode();
	        }
	        return count;
	    }
/****************************************************************************************
 * Task 2: Develop a peekTwo method that retrieves and returns the second element 
 * from the top of the stack without modifying the stack’s contents
 */
	   public T peekTwo() {
	        if (topNode == null || topNode.getNextNode() == null)
	            throw new EmptyStackException();

	        return topNode.getNextNode().getData();
	    }

	
/****************************************************************************************
 * Task 3: Develop a directPop method that retrieves and returns the top element 
 * of the stack directly, without utilizing the peek method.
*/
	   public T directPop() {
	        if (topNode == null)
	            throw new EmptyStackException();

	        T value = topNode.getData();
	        topNode = topNode.getNextNode();
	        return value;
	    }

//***************************************************************************************
	
	public boolean isEmpty()
	{
		return topNode == null;
	} // end isEmpty
	
	public void clear()
	{
		topNode = null;  // Causes deallocation of nodes in the chain
	} // end clear

	private class Node
	{
      private T    data; // Entry in stack
      private Node next; // Link to next node

      private Node(T dataPortion)
      {
         this(dataPortion, null);
      } // end constructor

      private Node(T dataPortion, Node linkPortion)
      {
         data = dataPortion;
         next = linkPortion;	
      } // end constructor

      private T getData()
      {
         return data;
      } // end getData

      private void setData(T newData)
      {
         data = newData;
      } // end setData

      private Node getNextNode()
      {
         return next;
      } // end getNextNode

      private void setNextNode(Node nextNode)
      {
         next = nextNode;
      } // end setNextNode
	} // end Node
} // end LinkedStack
