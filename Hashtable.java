
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public class Hashtable<K,V> {

    private static final int CAPACITY = 201;
   
    private static final double LOAD_THRESHOLD = 0.9;
  
    private int numberOfKeys;
        private int totalCollisions = 0;
    private int totalInsertionsLookups = 0;
    
    private int totalChainLengths = 0;
    
    private int maxChainLength = 0;
    
    private LinkedList<Entry<K,V>>[] mainTable;

    
    private static class Entry<K,V> implements Map.Entry<K,V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        
        @Override
        public K getKey() {
            return key;
        }

                @Override
        public V getValue() {
            return value;
        }

        
        
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

                
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

    }

    
    public Hashtable() {
        mainTable = new LinkedList[CAPACITY];
    }

        public double getLoadFactor(){
        return numberOfKeys / (double) mainTable.length;
    }

        public int getNoOfColl(){
        return totalCollisions;
    }

        public double getAverageChainLength(){
        if (totalInsertionsLookups < 1){
            return 0;
        }
        return (double) totalChainLengths / (double) totalInsertionsLookups;
    }

        public int getMaxChainLength(){
        return maxChainLength;
    }

    
    private void addChainLength(int len){
        maxChainLength = Math.max(maxChainLength, len);
        totalChainLengths += len;
        if (len > 0)
            totalInsertionsLookups++;
    }

        public V insert(K key, V value) {
        int index = key.hashCode() % mainTable.length;

        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            
            mainTable[index] = new LinkedList<Entry<K,V>>();
        } else {
            totalCollisions++;
        }

        int chainLength = 0;
        
        for (Entry<K,V> nextItem : mainTable[index]) {
            chainLength++;
           
            if (nextItem.key.equals(key)) {
                
                V oldVal = nextItem.value;
                nextItem.setValue(value);
                addChainLength(chainLength);
                return oldVal;
            }
        }

        addChainLength(chainLength);
        
        mainTable[index].addFirst(new Entry<K,V>(key, value));
        numberOfKeys++;
        if (numberOfKeys > (LOAD_THRESHOLD * mainTable.length)) {
            rehash();
        }
        return null;
    }

        public V get(Object key) {
        int index = key.hashCode() % mainTable.length;
        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            return null; 
        }

        int chainLength = 0;
       
        for (Entry<K,V> nextItem : mainTable[index]) {
            chainLength++;

            if (nextItem.key.equals(key)) {
                addChainLength(chainLength);
                return nextItem.value;
            }
        }

        addChainLength(chainLength);
        
        return null;
    }

        public V remove(Object key) {
        int index = key.hashCode() % mainTable.length;
        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            return null; 
        }
        Iterator<Entry<K,V>> iter = mainTable[index].iterator();
        while (iter.hasNext()) {
            Entry<K,V> nextItem = iter.next();
            
            if (nextItem.key.equals(key)) {
                V returnValue = nextItem.value;
                iter.remove();
                return returnValue;
            }
        }
        
        return null;
    }

    
    public int size() {
        return numberOfKeys;
    }

    
    public boolean isEmpty() {
        return numberOfKeys == 0;
    }

        public void rehash() {
        
        LinkedList<Entry<K,V>>[] origTable = mainTable;
        
        mainTable = new LinkedList[2 * origTable.length + 1];

        
        numberOfKeys = 0;
        for (int i = 0; i < origTable.length; i++) {
            if (origTable[i] != null) {
                for (Entry<K,V> nextEntry : origTable[i]) {
                    
                    insert(nextEntry.key, nextEntry.value);
                }
            }
        }
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("[");
        boolean first = true;
        for (LinkedList<Entry<K,V>> aTable : mainTable) {
            if (aTable != null) {
                for (Entry<K,V> e : aTable) {
                    stb.append(aTable);
                    if (first) {
                        first = false;
                    } else {
                        stb.append(", ");
                    }
                }
            }
        }
        stb.append("]");
        return stb.toString();
    }

}

