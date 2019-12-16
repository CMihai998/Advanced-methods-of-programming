package Model.Structures;

import java.util.Stack;

public class MyStack<Type> implements MyIStack<Type> {

    private Stack<Type> stack;

    public MyStack(){
        stack = new Stack<Type>();
    }

    @Override
    public Type pop() {
        return stack.pop();
    }

    @Override
    public void push(Type item) {
        stack.push(item);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        String result = "";
        StringBuilder builder = new StringBuilder(result);
        for(Type element: stack){
            builder.append(element.toString());
            builder.append(" --- ");
        }
        return builder.toString();
    }

    @Override
    public Type top() {
        if(stack.isEmpty())
            return null;
        return stack.peek();
    }
}
