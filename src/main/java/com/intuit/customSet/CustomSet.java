package com.intuit.customSet;

import java.util.BitSet;
import java.util.Objects;

public class CustomSet {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node[] table;
    private int size;
    private int threshold;

    public CustomSet() {
        table = new Node[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    private static class Node {
        final Integer key;
        final int hash;
        Node next;

        public Node(int key, int hash, Node next) {
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
    }

    private int hash(Integer key) {
        if (key == null) return 0;

        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    public boolean add(Integer key) {
        int hash = hash(key);
        int index = hash & (table.length - 1);

        for (Node e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (Objects.equals(key, e.key))) {
                return false; // Already exists
            }
        }

        Node newElem =  new Node(key, hash, table[index]);
        table[index] = newElem;
        size++;

        if (size > threshold) {
            resize();
        }

        return true;
    }

    private void resize() {
        Node[] oldTable = table;
        int newCapacity = oldTable.length * 2;
        Node[] newTable = new Node[newCapacity];
        threshold = (int)(newCapacity * LOAD_FACTOR);

        for(Node node : oldTable) {
            while (node != null) {
                Node next = node.next;
                int index = node.hash & (newCapacity-1); // Recompute the index for the new table
                node.next = newTable[index]; // Insert the node at the start of the linked list
                newTable[index] = node; // Update the new table's index to point to this node
                node = next; // Move to the next node in the old table
            }
        }

        table = newTable;
    }

    public boolean contains(Integer key) {
        int hash = hash(key);
        int index = hash & (table.length - 1);

        for (Node e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (Objects.equals(key, e.key))) {
                return true;
            }
        }

        return false;
    }

    public boolean remove(Integer key) {
        int hash = hash(key);
        int index = hash & (table.length - 1);

        Node prev = null;
        Node node = table[index];


        while(node != null) {
            Node next = node.next;
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                if (prev == null) {
                    table[index] = next;
                } else {
                    prev.next = next;
                }
                size--;
                return true;
            }
            prev = node;
            node = next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

}
