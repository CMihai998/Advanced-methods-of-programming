package Model.Structures;

import java.util.concurrent.ConcurrentLinkedDeque;

public class MyList<Type> implements MyIList<Type> {
    private ConcurrentLinkedDeque<Type> list;

    public MyList(){
        list = new ConcurrentLinkedDeque<Type>();
    }

    @Override
    public boolean add(Type item) {
        return list.add(item);
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
}
