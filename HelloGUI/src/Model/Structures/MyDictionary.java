package Model.Structures;

import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.util.*;

public class MyDictionary<String, Type> implements MyIDictionary<java.lang.String, Type> {
    HashMap<java.lang.String, Type> dictionary;

    public MyDictionary(){
        this.dictionary = new HashMap<java.lang.String, Type>();
    }

    public MyDictionary(HashMap<java.lang.String, Type> map){
        this.dictionary = map;
    }
    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public int size() {
        return dictionary.size();
    }


    @Override
    public Type get(java.lang.String key) {
        return dictionary.get(key);
    }

    @Override
    public Type remove(java.lang.String key) {
        return dictionary.remove(key);
    }


    @Override
    public Type update(java.lang.String key, Type value) {
        return dictionary.put(key,value);
    }

    @Override
    public List<Type> getContent() {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public java.lang.String toString() {
        return dictionary.toString();
    }

    @Override
    public boolean isDefined(java.lang.String id) {
        return dictionary.get(id) != null;
    }

    @Override
    public MyIDictionary<java.lang.String, Type> deepcopy() {
        HashMap<java.lang.String, Type> clone = new HashMap<java.lang.String, Type>();
        for (Map.Entry<java.lang.String, Type> element: dictionary.entrySet()){
            if(element.getValue() instanceof Value)
                clone.put(element.getKey(), (Type) ((Value)element.getValue()).deepcopy());
            else if(element.getValue() instanceof Model.Types.Type)
                    clone.put(element.getKey(), (Type) ((Model.Types.Type)element.getValue()).deepcopy());
            else
                clone.put(element.getKey(), element.getValue());
        }
        return new MyDictionary<java.lang.String, Type>(clone);
    }

    @Override
    public MyIDictionary<java.lang.String, Type> shallowcopy() {
        HashMap<java.lang.String, Type> clone = new HashMap<java.lang.String, Type>();
        for (Map.Entry<java.lang.String, Type> element: dictionary.entrySet()){
            clone.put(element.getKey(), element.getValue());
        }
        return new MyDictionary<java.lang.String, Type>(clone);
    }

    @Override
    public HashMap<java.lang.String, Type> getWrapped() {
        return dictionary;
    }

    public boolean isDefined(StringValue id) {
        return dictionary.get(id) != null;
    }
}
