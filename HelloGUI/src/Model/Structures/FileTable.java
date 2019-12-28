package Model.Structures;

import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileTable implements MyIDictionary<Model.Values.StringValue, BufferedReader>{
    ConcurrentHashMap<StringValue, BufferedReader> table;

    public  FileTable(){
        this.table = new ConcurrentHashMap<Model.Values.StringValue, BufferedReader>();
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public int size() {
        return table.size();
    }


    @Override
    public BufferedReader get(Model.Values.StringValue key) {
        return table.get(key);
    }

    @Override
    public BufferedReader remove(Model.Values.StringValue key) {
        return table.remove(key);
    }

    @Override
    public BufferedReader update(Model.Values.StringValue key, BufferedReader value) {
        return table.put(key,value);
    }

    @Override
    public List<BufferedReader> getContent() {
        return new LinkedList<BufferedReader>(table.values());
    }

    @Override
    public String toString() {
        return table.toString();
    }


    @Override
    public boolean isDefined(Model.Values.StringValue id) {
        return table.get(id) != null;
    }

    @Override
    public MyIDictionary<Model.Values.StringValue, BufferedReader> deepcopy() {
        Map<Model.Values.StringValue, BufferedReader> clone = new HashMap<Model.Values.StringValue, BufferedReader>();
        for (Map.Entry<Model.Values.StringValue, BufferedReader> element: table.entrySet()){
            clone.put((Model.Values.StringValue) element.getKey().deepcopy(), element.getValue());
        }
        return (MyIDictionary<Model.Values.StringValue, BufferedReader>) clone;
    }

    @Override
    public MyIDictionary<Model.Values.StringValue, BufferedReader> shallowcopy() {
        Map<Model.Values.StringValue, BufferedReader> clone = new HashMap<Model.Values.StringValue, BufferedReader>();
        for (Map.Entry<Model.Values.StringValue, BufferedReader> element: table.entrySet()){
            clone.put(element.getKey(), element.getValue());
        }
        return (MyIDictionary<Model.Values.StringValue, BufferedReader>) clone;
    }

    @Override
    public HashMap<StringValue, BufferedReader> getWrapped() {
        return null;
    }

    public List<String> getStringValues(){
       return (table.keySet().stream().map(e -> e.getValue()).collect(Collectors.toList()));
    }
}
