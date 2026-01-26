[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-EdeOAmG)
# Assignment-02

**Task 1: Two additional methods — display() and equals(MyLinkedList<T> aList) — have been introduced in the MyLinkedList class. Please analyze and explain the purpose and functionality of each method in detail.**

``` java
	public void display() {
		int index = 1;
		Node currentNode = firstNode;

		while (currentNode != null) {
			System.out.println(index + ":" + currentNode.getData());

			currentNode = currentNode.getNextNode();
			index++;
		}

	} // end display

	public boolean equals(MyLinkedList<T> aList) {
		boolean isEqual = false; 

		Node currOne = firstNode;
		Node currTwo = aList.firstNode;
		int counter;

		if (numberOfEntries == aList.numberOfEntries) {

			while ((currOne != null) && (currOne.getData()).equals(currTwo.getData())) {
				currOne = currOne.getNextNode();
				currTwo = currTwo.getNextNode();
			} // end while

			isEqual = (currOne == null);
		}

		return isEqual;
	} // end equals

  ```

**Task 2: In the reverse method of MyLinkedList, implement the *Reverse Linked List* algorithm below.**

**Algorithm to Reverse Linked List**

The goal is to reverse the links between the nodes such that the last node becomes the head and the head becomes the last node.

***For example:***

Initial Linked List: [1] -> [2] -> [3] -> [4] -> [5] -> NULL

Reversed Linked List: NULL <- [1] <- [2] <- [3] <- [4] <- [5]

**Step1: Initialize Pointers**

> prev = NULL (to track the previous node).

> current = head (to track the current node).

> next = NULL (to temporarily store the next node).

**Step 2: Iterate through the list***

> Store the next node: next = current.getNextNode().

> Reverse the link: current.setNextNode(prev).

> Move prev to current.

> Move current to next.

**Step 3: Update head to the last non-null node (prev)**

***Checkpoint:*** *Compile and run LinkedListExtensionsTest. The checkReverse tests should pass. If not, debug and retest.*

**Task 3: In the cycle method of MyLinkedList, implement the *Linked Cycle* algorithm below.**

**Linked Cycle Algorithm**

The goal is to move the first node to the back of the list.

***For example:***

Initial Linked List: [1] -> [2] -> [3] -> [4] -> [5] -> NULL

cycled Linked List: [2] -> [3] -> [4] -> [5] -> [1] -> NULL

**Step 1: Initialization**

> Create a variable that refers to the second node in the list. 

> Find and then create a variable that refers to the last node in the list.

**Step 2: Update last node reference**

> Make the last node’s next reference be the first node.

**Step 3: Update firstNode content**

> Change the variable firstNode so that it references the second node.

**Step 4: Update former first node’s reference**

> Make the former first node’s next reference be null.

***checkpoint:*** *Compile and run LinkedListExtensionsTest. All tests should pass. If not, debug and retest.*
