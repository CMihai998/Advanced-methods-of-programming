package Model.Structures;

public interface MyIStack<Type> {
    Type pop();
    void push(Type item);
    boolean isEmpty();
    int size();
    String toString();
    Type top();

}
