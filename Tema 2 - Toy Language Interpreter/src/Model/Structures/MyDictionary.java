package Model.Structures;

import Model.Types.Type;
import Model.Values.StringValue;

import java.lang.reflect.MalformedParameterizedTypeException;

import java.util.*;

public class MyDictionary<Type1, Type2> implements MyIDictionary<Type1, Type2> {
    Map<Type1, Type2> dictionary;

    public MyDictionary(){
        this.dictionary = new HashMap<Type1, Type2>();
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
    public Type2 get(Type1 key) {
        return dictionary.get(key);
    }

    @Override
    public Type2 remove(Type1 key) {
        return dictionary.remove(key);
    }

    @Override
    public Type2 update(Type1 key, Type2 value) {
        return dictionary.put(key,value);
    }

    @Override
    public List<Type2> getContent() {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public boolean isDefined(Type1 id) {
        return dictionary.get(id) != null;
    }

    public boolean isDefined(StringValue id) {
        return dictionary.get(id) != null;
    }
}
