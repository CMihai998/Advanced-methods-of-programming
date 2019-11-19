package Model.Structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileTable<StringValue, BufferedReader> implements MyIDictionary<StringValue, BufferedReader>{
    Map<StringValue, BufferedReader> table;

    public  FileTable(){
        this.table = new HashMap<>();
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
    public BufferedReader get(StringValue key) {
        return (BufferedReader) table.get(key);
    }

    @Override
    public BufferedReader remove(StringValue key) {
        return (BufferedReader) table.remove(key);
    }

    @Override
    public BufferedReader update(StringValue key, BufferedReader value) {
        return (BufferedReader) table.put(key,value);
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
    public boolean isDefined(StringValue id) {
        return table.get(id) != null;
    }
}
