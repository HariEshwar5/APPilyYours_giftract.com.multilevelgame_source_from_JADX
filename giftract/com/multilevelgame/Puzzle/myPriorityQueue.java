package giftract.com.multilevelgame.Puzzle;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class myPriorityQueue extends AbstractCollection {
    private Comparator myComp;
    private ArrayList myList;
    private int mySize;

    private static class DefaultComparator implements Comparator {
        private DefaultComparator() {
        }

        public int compare(Object o1, Object o2) {
            return ((Comparable) o1).compareTo(o2);
        }
    }

    private class PQItr implements Iterator {
        private int myCursor;

        private PQItr() {
            this.myCursor = 1;
        }

        public Object next() {
            return myPriorityQueue.this.myList.get(this.myCursor);
        }

        public boolean hasNext() {
            return this.myCursor <= myPriorityQueue.this.mySize;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not implemented");
        }
    }

    public void clear() {
        if (!isEmpty()) {
            while (size() > 0) {
                remove();
            }
        }
    }

    public myPriorityQueue() {
        this.myComp = new DefaultComparator();
        this.myList = new ArrayList(32);
        this.myList.add(null);
        this.mySize = 0;
    }

    public myPriorityQueue(Comparator comp) {
        this();
        this.myComp = comp;
    }

    public myPriorityQueue(Collection coll, Comparator comp) {
        this();
        this.myComp = comp;
        this.myList.addAll(coll);
        this.mySize = coll.size();
        for (int k = coll.size() / 2; k >= 1; k--) {
            heapify(k);
        }
    }

    public myPriorityQueue(Collection coll) {
        this();
        this.myList.addAll(coll);
        this.mySize = coll.size();
        for (int k = coll.size() / 2; k >= 1; k--) {
            heapify(k);
        }
    }

    public boolean add(Object o) {
        this.myList.add(o);
        this.mySize++;
        int k = this.mySize;
        while (k > 1 && this.myComp.compare(this.myList.get(k / 2), o) > 0) {
            this.myList.set(k, this.myList.get(k / 2));
            k /= 2;
        }
        this.myList.set(k, o);
        return true;
    }

    public int size() {
        return this.mySize;
    }

    public boolean isEmpty() {
        return this.mySize == 0;
    }

    public Object remove() {
        if (isEmpty()) {
            return null;
        }
        Object hold = this.myList.get(1);
        this.myList.set(1, this.myList.get(this.mySize));
        this.myList.remove(this.mySize);
        this.mySize--;
        if (this.mySize <= 1) {
            return hold;
        }
        heapify(1);
        return hold;
    }

    public Object peek() {
        return this.myList.get(1);
    }

    public Iterator iterator() {
        return new PQItr();
    }

    private void heapify(int vroot) {
        Object last = this.myList.get(vroot);
        int k = vroot;
        while (k * 2 <= this.mySize) {
            int child = k * 2;
            if (child < this.mySize && this.myComp.compare(this.myList.get(child), this.myList.get(child + 1)) > 0) {
                child++;
            }
            if (this.myComp.compare(last, this.myList.get(child)) <= 0) {
                break;
            }
            this.myList.set(k, this.myList.get(child));
            k = child;
        }
        this.myList.set(k, last);
    }

    public static void main(String[] args) {
    }
}
