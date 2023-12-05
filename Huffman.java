import java.util.Scanner;

public class Huffman {

    private Node[] createNodes(String message) {

        final int ASCII_SIZE = 128;
        Node[] nodes = new Node[ASCII_SIZE];

        // Node objects
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (nodes[c] == null) {
                if (c == ' ') {
                    nodes[c] = new Node(' ', 1);
                } else {
                    nodes[c] = new Node(c, 1);
                }
            } else {
                nodes[c].data++; // increment frequency
            }
        }
        return nodes;
    }

    private Tree[] createTrees(Node[] nodes) {
        Tree[] trees = new Tree[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                trees[i] = new Tree();
                trees[i].root = nodes[i];
            }
        }

        return trees;
    }

    private PriorityQ CreatePriorityQ(Tree[] trees) {
        PriorityQ priorityQueue = new PriorityQ(trees.length);

        for (int i = 0; i < trees.length; i++) {
            if (trees[i] != null) {
                priorityQueue.insert(trees[i].root);
            }
        }
        return priorityQueue;
    }

    private Tree BuildHuffmanTree(String message) {
        Node[] nodes = createNodes(message);

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].display();
            }
        }

        Tree[] huffmanTrees = createTrees(nodes);
        for (int i = 0; i < huffmanTrees.length; i++) {
            if (huffmanTrees[i] != null) {
                System.out.println("Huffman Tree for Node " + (char) i + ":");
                huffmanTrees[i].print();
                System.out.println();
            }
        }

        PriorityQ priorityQueue = CreatePriorityQ(huffmanTrees);
        System.out.println("priorityQ is full = " + priorityQueue.isFull());
        System.out.println("priorityQ size = " + priorityQueue.getSize());
        System.out.println("priorityQ is empty = " + priorityQueue.isEmpty());

        /*
         * while (priorityQueue.getSize() > 1) {
         * Tree newLeftTree = new Tree();
         * newLeftTree.root = new Node('*', priorityQueue.remove());
         * 
         * Tree newRightTree = new Tree();
         * newRightTree.root = new Node('*', priorityQueue.remove());
         * 
         * Tree newTree = new Tree();
         * newTree.root = new Node('*', newLeftTree.root.data + newRightTree.root.data);
         * newTree.root.leftChild = newLeftTree.root;
         * newTree.root.rightChild = newRightTree.root;
         * 
         * priorityQueue.insert(newTree.root.data);
         * 
         * 
         * Tree OriginalHuffmanTree = new Tree();
         * OriginalHuffmanTree.root = new Node('*', priorityQueue.peekMin());
         * 
         * return OriginalHuffmanTree;
         */

        while (priorityQueue.getSize() > 1) {
            Tree newLeftTree = new Tree();
            newLeftTree.root = priorityQueue.remove(); // Remove the root of the left tree

            Tree newRightTree = new Tree();
            newRightTree.root = priorityQueue.remove(); // Remove the root of the right tree

            Tree newTree = new Tree();
            newTree.root = new Node('*', newLeftTree.root.data + newRightTree.root.data);
            newTree.root.leftChild = newLeftTree.root;
            newTree.root.rightChild = newRightTree.root;

            priorityQueue.insert(newTree.root);

        }

        Tree OriginalHuffmanTree = new Tree();
        OriginalHuffmanTree.root = priorityQueue.peekMin(); // Get the root of the final Huffman tree

        return OriginalHuffmanTree;

    }

    private void createCodeTable(Node node, String code, String[] codeTable) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            codeTable[(int) node.id] = code;
        }

        createCodeTable(node.leftChild, code + "0", codeTable);
        createCodeTable(node.rightChild, code + "1", codeTable);
    }

    private String encodeMessage(String message, String[] codeTable) {
        StringBuilder encodedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            encodedMessage.append(codeTable[(int) c]);
        }

        return encodedMessage.toString();
    }

    private String decodeMessage(String encodedMessage, String[] codeTable) {
        StringBuilder decodedMessage = new StringBuilder();
        StringBuilder tempCode = new StringBuilder();

        for (int i = 0; i < encodedMessage.length(); i++) {
            tempCode.append(encodedMessage.charAt(i));

            for (int j = 0; j < codeTable.length; j++) {
                if (codeTable[j] != null && codeTable[j].equals(tempCode.toString())) {
                    decodedMessage.append((char) j);
                    tempCode.setLength(0);
                    break;
                }
            }
        }

        return decodedMessage.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the message: ");
        String message = scanner.nextLine();
        scanner.close();

        Huffman huffman = new Huffman();

        Node[] nodes = huffman.createNodes(message);

        Tree realHuffmanTree = new Tree();
        realHuffmanTree = huffman.BuildHuffmanTree(message);
        System.out.println("Huffman Tree:");
        realHuffmanTree.print();

        String[] codeTable = new String[128];
        huffman.createCodeTable(realHuffmanTree.root, "", codeTable);
        System.out.println("Code Table:");
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                char c = nodes[i].id;
                if (c == ' ') {
                    System.out.println("sp" + " " + codeTable[i]);
                } else {
                    System.out.println(c + " " + codeTable[i]);
                }

            }
        }

        String encodedMessage = new String();
        encodedMessage = huffman.encodeMessage(message, codeTable);
        System.out.println("Encoded Message:");
        System.out.println(encodedMessage);

        String decodedMessage = new String();
        decodedMessage = huffman.decodeMessage(encodedMessage, codeTable);
        System.out.println("Decoded Message:");
        System.out.println(decodedMessage);

    }
}
