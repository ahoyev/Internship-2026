public class LinkedQueue<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    public LinkedQueue() {
        front = null;
        rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void enqueue(T item) {
        Node newNode = new Node(item);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public T dequeue() {
        if (isEmpty()) return null;

        T item = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return item;
    }

    public T peek() {
        if (isEmpty()) return null;
        return front.data;
    }

    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        Node current = front;
        System.out.println("Current Ride Queue:");
        while (current != null) {
            System.out.println(current.data.toString());
            System.out.println("----------------------");
            current = current.next;
        }
    }
}
