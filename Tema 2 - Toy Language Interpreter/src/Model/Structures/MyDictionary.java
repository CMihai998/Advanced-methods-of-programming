package Model.Structures;

import Model.Values.StringValue;
import Model.Values.Value;

import java.util.*;

public class MyDictionary<String, Value> implements MyIDictionary<java.lang.String, Model.Values.Value> {
    HashMap<java.lang.String, Model.Values.Value> dictionary;

    public MyDictionary(){
        this.dictionary = new HashMap<java.lang.String, Model.Values.Value>();
    }

    public MyDictionary(HashMap<java.lang.String, Model.Values.Value> map){
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
    public Model.Values.Value get(java.lang.String key) {
        return dictionary.get(key);
    }

    @Override
    public Model.Values.Value remove(java.lang.String key) {
        return dictionary.remove(key);
    }


    @Override
    public Model.Values.Value update(java.lang.String key, Model.Values.Value value) {
        return dictionary.put(key,value);
    }

    @Override
    public List<Model.Values.Value> getContent() {
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
    public MyIDictionary<java.lang.String, Model.Values.Value> deepcopy() {
        HashMap<java.lang.String, Model.Values.Value> clone = new HashMap<java.lang.String, Model.Values.Value>();
        for (Map.Entry<java.lang.String, Model.Values.Value> element: dictionary.entrySet()){
            clone.put(new java.lang.String(element.getKey()), element.getValue().deepcopy());
        }
        return new MyDictionary<java.lang.String, Model.Values.Value>(clone);
    }

    public boolean isDefined(StringValue id) {
        return dictionary.get(id) != null;
    }
}
