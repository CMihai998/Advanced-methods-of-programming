package Model.Structures;

import Model.Values.Value;

import java.util.Map;
import java.util.Set;

public interface iHeap<T, V> {
    boolean isDefined(T id);
    void update(Integer id, Value value);
    void update(V value);
    void remove(Value value);
    T getCurrentFree();
    Set<Map.Entry<T,V>> getEntrySet();
    V getValue(T key);
    void setContent(Map<Integer, Value> newContent);
}
