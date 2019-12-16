package Model.Structures;

import Model.Values.Value;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HeapTable<I extends Number, V> implements iHeap<Integer, Value> {
    private AtomicInteger nextFreeMemory;
    ConcurrentHashMap<Integer, Value> table;

    public HeapTable(){
        this.table = new ConcurrentHashMap<Integer, Value>();
        nextFreeMemory = new AtomicInteger(1);
    }

    @Override
    public boolean isDefined(Integer id) {
        return  table.containsKey(id);
    }

    @Override
    public void update(Integer id, Value value) {
        table.put(id, value);
    }

    @Override
    public void update(Value value) {
        table.put(nextFreeMemory.get(), value);
        nextFreeMemory.set(nextFreeMemory.get() + 1);
    }

    @Override
    public void remove(Value value) {
        table.remove(value);
    }

    @Override
    public Integer getCurrentFree() {
        return nextFreeMemory.get();
    }

    @Override
    public Set<Map.Entry<Integer, Value>> getEntrySet() {
        return this.table.entrySet();
    }

    @Override
    public Value getValue(Integer key) {
        return table.get(key);
    }

    @Override
    public void setContent(Map newContent) {
        this.table = (ConcurrentHashMap<Integer, Value>) newContent;
    }

    @Override
    public String toString(){
        return table.toString();
    }
}
