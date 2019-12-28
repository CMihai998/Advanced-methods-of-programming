package Model.Structures;

import java.util.stream.Stream;

public interface MyIList<Type> {
    boolean add(Type item);
    void add(int position, Type item);
    boolean remove(Type item);
    Type remove(int position);
    Type set(int index, Type item);
    Type get(int index);
    boolean isEmpty();
    int size();
    String toString();
    Stream<Type> getStreamOfElements();
}
