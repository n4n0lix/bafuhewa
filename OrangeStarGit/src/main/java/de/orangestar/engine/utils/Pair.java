package de.orangestar.engine.utils;

public class Pair<K, V> {

    public static <K, V> Pair<K,V> New(K k, V v) {
        return new Pair<K,V>(k, v);
    }
    
    public Pair(K k, V v) {
        x = k;
        y = v;
    }
    
    public K x;
    public V y;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        return true;
    }
    

}
