public class Node {
    char id;
    long data; // frequency
    Node leftChild;
    Node rightChild;

    public Node(char character, long frequency) {
        this.id = character;
        this.data = frequency;
        this.leftChild = null;
        this.rightChild = null;
    }

    public int getKey() {
        return this.id;
    }

    public void display() {
        System.out.println("[" + id + ", " + data + "]");
    }

    public boolean isLeaf() {
        return (leftChild == null) && (rightChild == null);
    }
}
