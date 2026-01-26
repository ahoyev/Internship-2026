
public class MyLinkedList<T> implements ListInterface<T> {

	private Node firstNode;
	private int numberOfEntries;

	public void add(T newEntryData) {
		Node newEntry = new Node(newEntryData);

		if (isEmpty()) {
			firstNode = newEntry;
		} else {
			newEntry.setNextNode(firstNode);
			firstNode = newEntry;
		}
		numberOfEntries++;

	}

	private Node getNodeAt(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			Node nodeAtGivenPosition = firstNode;
			for (int position = 1; position < givenPosition; position++) {
				nodeAtGivenPosition = nodeAtGivenPosition.getNextNode();
			}
			return nodeAtGivenPosition;
		} else {
			throw new IndexOutOfBoundsException("Given position is out of bounds.");
		}

	}

	public void addTwo(int newPosition, T newEntryValue) {
		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
			Node newEntry = new Node(newEntryValue);
			if (newPosition == 1) {
				newEntry.setNextNode(firstNode);
				firstNode = newEntry;
				numberOfEntries++;
			} else {
				Node nodeBefore = getNodeAt(newPosition - 1);
				Node nodeAfter = getNodeAt(newPosition);
				nodeBefore.setNextNode(newEntry);
				newEntry.setNextNode(nodeAfter);
				numberOfEntries++;
			}
		} else {
			throw new IndexOutOfBoundsException("Given position is out of bounds.");
		}
	}

	public void add(int newPosition, T newEntryValue) {
		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
			Node newEntry = new Node(newEntryValue);
			if (newPosition == 1) {
				newEntry.setNextNode(firstNode);
				firstNode = newEntry;
				numberOfEntries++;
				// add(newEntryValue);????
			} else {
				Node nodeBefore = null, nodeCurrent = null, nodeAfter = null;
				nodeCurrent = firstNode;
				for (int i = 1; i < newPosition; i++) {
					nodeBefore = nodeCurrent;
					nodeAfter = nodeCurrent.getNextNode();
					nodeCurrent = nodeCurrent.getNextNode();
				}
				nodeBefore.setNextNode(newEntry);
				newEntry.setNextNode(nodeAfter);
				numberOfEntries++;
			}
		} else {
			throw new IndexOutOfBoundsException("Given position is out of bounds.");
		}

	}
	
	public void addLast(T newEntry) {
		Node newNode = new Node(newEntry);
		if (isEmpty())
			firstNode = newNode;
		else {
			Node lastNode = getNodeAt(numberOfEntries);
			lastNode.setNextNode(newNode);
		}
		numberOfEntries++;
	}
	
	public boolean contains(T anEntry) {
		boolean found = false;

		Node currentNode = firstNode;

		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while

		return found;
	}

	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		T[] result = (T[]) new Object[numberOfEntries];

		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null)) {
			result[index] = currentNode.getData();
			currentNode = currentNode.getNextNode();
			index++;
		} // end while

		return result;
	} // end toArray

	public int removeItem(T itemToRemove) {
		int count = 0;
		Node prev = null;
		Node curr = firstNode;

		while (curr != null) {
			if (curr.getData().equals(itemToRemove)) {
				if (prev == null) {
					firstNode = curr.getNextNode();
					curr = firstNode;
				} else {
					prev.setNextNode(curr.getNextNode());
					curr = curr.getNextNode();
				}
				count++;
				numberOfEntries--;
			} else {
				prev = curr;
				curr = curr.getNextNode();
			}
		}
		return count;
	}

	public T remove(int givenPosition) {
		T nodeToRemove = null;
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			if (givenPosition == 1) {
				nodeToRemove = firstNode.getData();
				firstNode = firstNode.getNextNode();
			} else {
				Node before = getNodeAt(givenPosition - 1);
				Node after = getNodeAt(givenPosition + 1);
				nodeToRemove = getNodeAt(givenPosition).getData();
				// Node toNeedToRemove = getNodeAt(givenPosition);
				// nodeToRemove = toNeedToRemove.getData();
				before.setNextNode(after);
			}
			numberOfEntries--;
			return nodeToRemove;
		} else {
			throw new IndexOutOfBoundsException("Given position of getEntry's is out of bounds.");
		}
	}

	public void clear() {
		firstNode = null;
		numberOfEntries = 0;

	}

	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			Node nodeToReplace = getNodeAt(givenPosition);
			T nodeToReplaceValue = nodeToReplace.getData();
			nodeToReplace.setData(newEntry);
			return nodeToReplaceValue;
		} else {
			throw new IndexOutOfBoundsException("Given position of getEntry's is out of bounds.");
		}
	}

	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			Node itemToReturn = firstNode;
			for (int i = 1; i < givenPosition; i++) {
				itemToReturn = itemToReturn.getNextNode();
			}
			return itemToReturn.getData();
		} else {
			throw new IndexOutOfBoundsException("Given position of getEntry's is out of bounds.");
		}
	}

	public T getEntryTwo(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			Node currentNode = getNodeAt(givenPosition);
			return currentNode.getData();
		} else {
			throw new IndexOutOfBoundsException("Given position of getEntry's is out of bounds.");
		}
	}

	public int getLength() {
		return numberOfEntries;
	}

	public boolean isEmpty() {
		return (numberOfEntries == 0);
	}

	/**
	 * Display the list with indices one to a line This will correctly display an
	 * infinite list, whereas the toString() method will never return.
	 * 
	 */
	public void display() {
		int index = 1;// So we know what the node position 
		Node currentNode = firstNode;// Head of the list

		while (currentNode != null) { // while loop
			System.out.println(index + ":" + currentNode.getData());

			currentNode = currentNode.getNextNode();// next node
			index++;
		}

	} // end display

	public boolean equals(MyLinkedList<T> aList) {
		boolean isEqual = false; 

		Node currOne = firstNode; // This is the head of the list
		Node currTwo = aList.firstNode; // This would be the head of another list
		int counter;

		if (numberOfEntries == aList.numberOfEntries) {

			while ((currOne != null) && (currOne.getData()).equals(currTwo.getData())) {
				currOne = currOne.getNextNode(); // Move next node in this list
				currTwo = currTwo.getNextNode(); // Move next node in this list
			} // end while

			isEqual = (currOne == null);
		}

		return isEqual;
	} // end equals

	/*********************************************************************
	 * 
	 * METHODS TO BE COMPLETED
	 * 
	 ***********************************************************************/

	/**
	 * Reverse the order of items in a list.
	 */
	public void reverse() {
		  Node prev = null;                
		    Node current = firstNode;        
		    Node next = null;               

		    while (current != null) {
		        next = current.getNextNode();   
		        current.setNextNode(prev);      
		        prev = current;                
		        current = next;                 
		    }

		    firstNode = prev;  
		}

		// CODE TO BE COMPLETED
	

	/**
	 * Cycle the first item to the end of the list.
	 */
	public void cycle() {
		 if (firstNode == null || firstNode.getNextNode() == null) {
		        return;
		    }

		    Node secondNode = firstNode.getNextNode(); 
		    Node lastNode = firstNode;

		    
		    while (lastNode.getNextNode() != null) {
		        lastNode = lastNode.getNextNode();
		    }

		    
		    lastNode.setNextNode(firstNode);

		    
		    firstNode = secondNode;

		    
		    lastNode.getNextNode().setNextNode(null);

		// CODE TO BE COMPLETED
	}

	private class Node {
		private T data; 
		private Node next; 

		private Node(T dataPortion) {
			data = dataPortion;
			next = null;
		} 

		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		} 

		private T getData() {
			return data;
		} 

		private void setData(T newData) {
			data = newData;
		} 

		private Node getNextNode() {
			return next;
		} 

		private void setNextNode(Node nextNode) {
			next = nextNode;
		} 
	} 

} // End of MyLinkedList class
