package Model.Structures;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class MyStack<iStatement> implements MyIStack<iStatement> {

    private Stack<iStatement> stack;

    public MyStack(){
        stack = new Stack<iStatement>();
    }

    @Override
    public iStatement pop() {
        return stack.pop();
    }

    @Override
    public void push(iStatement item) {
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
        for(iStatement element: stack){
            builder.append(element.toString());
            builder.append(" --- ");
        }
        return builder.toString();
    }

    @Override
    public iStatement top() {
        if(stack.isEmpty())
            return null;
        return stack.peek();
    }

    @Override
    public Stack<iStatement> getWrapped() {
        return (Stack<iStatement>) stack.clone();
    }

    }

