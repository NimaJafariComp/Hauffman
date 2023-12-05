public class Tree {
    Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Tree() {
        this.root = null;
    }

    // Todo: Implement Searh for a Node given the key
    public Node find(int key) {
        Node current = root;

        while (current != null) {
            if (key == current.id) {
                return current;
            } else if (key < current.id) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }

        return null;
    }

    public void insert(char id, int data) {
        Node newNode = new Node(id, data);
        insert(newNode);
    }

    // Todo: Implement the Node insertion
    public void insert(Node newNode) {
        if (root == null) {
            root = newNode;
            return;
        }

        Node current = root;
        while (current != null) {
            if (newNode.id == current.id) {
                // No duplicates allowed
                throw new ArrayIndexOutOfBoundsException();
            }

            if (newNode.id < current.id) {
                if (current.leftChild == null) {
                    current.leftChild = newNode;
                    break;
                } else {
                    current = current.leftChild;
                }
            } else {
                if (current.rightChild == null) {
                    current.rightChild = newNode;
                    break;
                } else {
                    current = current.rightChild;
                }
            }
        }
    }

    // Todo: Implement - delete a Node given a key
    public boolean delete(int key) {
        // Search for the node to be deleted
        Node current = root;
        Node parent = null;
        boolean isLeftChild = false;

        while (current != null) {
            if (key == current.id) {
                break;
            }

            parent = current;
            if (key < current.id) { // curret is the Left child
                isLeftChild = true;
                current = current.leftChild;
            } else { // Right child
                isLeftChild = false;
                current = current.rightChild;
            }
        }

        if (current == null) { // current will be null if tree is empty or key does not exist
            return false;
        }

        if (current.leftChild == null && current.rightChild == null) {
            // 1. Deleting a leaf node
            if (parent == null) { // current == root
                // 1a. Deleting the last node in tree
                root = null;
            } else {
                if (isLeftChild) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            }
        } else if (current.rightChild == null) {
            // 2a. Delete the current with a left child
            if (parent == null) { // current == root
                root = current.leftChild;
            } else {
                if (isLeftChild) {
                    parent.leftChild = current.leftChild;
                } else {
                    parent.rightChild = current.leftChild;
                }
            }
        } else if (current.leftChild == null) {
            // 2a. Delete the current with a right child
            if (parent == null) { // current == root
                root = current.rightChild;
            } else {
                if (isLeftChild) {
                    parent.leftChild = current.rightChild;
                } else {
                    parent.rightChild = current.rightChild;
                }
            }
        } else {
            Node successor = findSuccessor(current);

            successor.leftChild = current.leftChild;
            if (successor != current.rightChild) {
                successor.rightChild = current.rightChild;
            }

            if (parent == null) { // current == root
                root = successor;
            } else {
                if (isLeftChild) {
                    parent.leftChild = successor;
                } else {
                    parent.rightChild = successor;
                }
            }
        }

        return true;
    }

    private Node findSuccessor(Node delNode) {
        Node successor = delNode.rightChild;

        if (successor.leftChild == null) {
            return successor;
        }

        Node successorParent = delNode;
        while (successor.leftChild != null) {
            successorParent = successor;
            successor = successor.leftChild;
        }

        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
        }

        return successor;
    }

    private String traversePreOrder(Node root) {
        if (root == null)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(root.id);

        String pointerRight = "---";
        String pointerLeft = "|--";

        traverseNodes(sb, "", pointerLeft, root.leftChild, root.rightChild != null);
        traverseNodes(sb, "", pointerRight, root.rightChild, false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer,
            Node node, boolean hasRightSibling) {

        if (node == null)
            return;

        sb.append("\n");
        sb.append(padding);
        sb.append(pointer);
        sb.append(node.id);

        StringBuilder pb = new StringBuilder(padding);
        if (hasRightSibling)
            pb.append("|  ");
        else
            pb.append("   ");

        String p = pb.toString();
        String pntr = "|--";
        traverseNodes(sb, p, pntr, node.leftChild, node.rightChild != null);
        traverseNodes(sb, p, pntr, node.rightChild, false);
    }

    public Node getMinimum() {
        if (root == null)
            return null;

        Node current = root;
        while (current.leftChild != null)
            current = current.leftChild;

        return current;
    }

    public Node getMaximum() {
        if (root == null)
            return null;

        Node current = root;
        while (current.rightChild != null)
            current = current.rightChild;

        return current;
    }

    public void print() {
        String s = traversePreOrder(root);
        System.out.println(s);

    }
}
