[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/aNzzL_xh)
# Assignment-04: Rideshare Dispatch System

In this assignment, you're asked to build a simple rideshare dispatch system. It works like a mini Uber dispatcher: passengers request rides, the system queues the requests, and drivers are assigned to the next waiting passenger. You’ll create three key Java classes to handle this:

RideRequest – Represents an individual ride request.

LinkedQueue<T> – A generic queue structure to manage ride requests using a linked list.

RideDispatcher – A menu-driven console app that simulates the dispatch workflow.

# 1. RideRequest Class

This class models the data and behavior for a single ride request.

*Key Attributes:*

- passengerName: Name of the rider.

- pickupLocation: Where the ride starts.

- destination: Where the ride ends.

- requestTime: Time the request is made (set automatically).

- assignedTime: Time when a driver is assigned (initially null).

*Required Methods:*

- Constructor: Accepts name, pickup, and destination. Automatically sets the requestTime using LocalDateTime.now().

- assignNow(): Sets the assignedTime to the current time.

- getWaitTimeSeconds(): Calculates and returns the time (in seconds) between request and assignment.

- toString(): Nicely formats and prints all the request details.


# 2. LinkedQueue<T> Class

This is a generic queue implemented with a linked list. You’ll use it to store ride requests in the order they are received (FIFO: First-In, First-Out).

*Two pointers:*

- front: Points to the front node of the queue.

- rear: Points to the end node of the queue.

*Required Methods:*

- enqueue(T item): Adds a new item to the rear of the queue.

- dequeue(): Removes and returns the item at the front.

- peek(): Returns the front item without removing it.

- isEmpty(): Checks if the queue is empty.

- printQueue(): Prints all elements from front to rear.

# RideDispatcher Class

This is your main driver class – it runs a console-based menu where users can:

- Add a new ride request

- Assign a driver to the next passenger

- View the ride queue

- Show dispatch statistics

- Exit the app

*Core Features:*

- Stores all pending ride requests using a linked queue.

- Ensures passengers are served in the order they requested a ride.

- Tracks:
  
  - totalRides

  - totalWaitSeconds (for averaging)
  
  - longestWaitSeconds

*Menu Logic:*

- When a user adds a request, you prompt for their name, pickup, and destination.

- When a driver is assigned:

  - You dequeue() the next request.
    
  - Call assignNow() and print the request details.
    
  - Update wait time stats.
    
  - Display statistics: average and longest wait times.



