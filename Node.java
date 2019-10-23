public class Node<T> {

    private T element;
    private Node next;

    public Node() {
        this.next = null;
    }

    public Node(T element) {
        this.element = element;
        this.next = null;
    }

    public T getElement() {
        return this.element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
