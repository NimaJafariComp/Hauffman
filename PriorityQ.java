public class PriorityQ {
    // Todo: Declare the array to store the queue data
    Node data[];

    // Todo: Declare the variables to maintain the queue
    int numValidValues;

    // Todo: Implement the constructor
    public PriorityQ(int maxSize) {
        data = new Node[maxSize];
        numValidValues = 0;
    }

    // Todo: insert the passed value in the queue
    public void insert(Node item) {
        if (isFull()) {
            throw new IndexOutOfBoundsException();
        }

        int i;
        for (i = numValidValues - 1; i >= 0; i--) {
            if (data[i].data < item.data) {
                data[i + 1] = data[i];
            } else {
                break;
            }
        }

        data[i + 1] = item;
        numValidValues++;
    }

    // Todo: Remove the minimum item
    public Node remove() {
        if (numValidValues == 0) {
            throw new IndexOutOfBoundsException();
        }

        return data[--numValidValues];
    }

    // Todo: return the minimum item without removing
    public Node peekMin() {
        if (numValidValues == 0) {
            throw new IndexOutOfBoundsException();
        }

        return data[numValidValues - 1];
    }

    // Todo: Implement
    public boolean isEmpty() {
        return numValidValues == 0;
    }

    // Todo: Implement
    public boolean isFull() {
        return numValidValues == data.length;
    }

    public int getSize() {
        return numValidValues;
    }
}
