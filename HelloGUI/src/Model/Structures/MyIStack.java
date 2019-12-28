package Model.Structures;

import java.util.Stack;

public interface MyIStack<Type> {
    Type pop();
    void push(Type item);
    boolean isEmpty();
    int size();
    String toString();
    Type top();
    Stack<Type> getWrapped();
}
