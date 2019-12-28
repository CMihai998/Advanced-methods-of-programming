package Model.Structures;

import java.util.LinkedList;
import java.util.stream.Stream;

public class MyList<Type> implements MyIList<Type> {
    private LinkedList<Type> list;

    public MyList(){
        list = new LinkedList<Type>();
    }

    @Override
    public boolean add(Type item) {
        return list.add(item);
    }

    @Override
    public void add(int position, Type item) {
         list.add(position, item);
    }

    @Override
    public boolean remove(Type item) {
        return list.remove(item);
    }

    @Override
    public Type remove(int position) {
        return list.remove(position);
    }

    @Override
    public Type set(int index, Type item) {
        return list.set(index, item);
    }

    @Override
    public Type get(int index) {
        return list.get(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public Stream<Type> getStreamOfElements() {
        return list.stream();
    }
}
