package Model.Structures;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public interface MyIDictionary<Type1, Type2> {
    boolean isEmpty();
    int size();
    Type2 get(Type1 key);
    Type2 remove(Type1 key);
    Type2 update(Type1 key, Type2 value);
    List<Type2> getContent();
    String toString();
    boolean isDefined(Type1 id);
   MyIDictionary<Type1, Type2> deepcopy();
    MyIDictionary<Type1, Type2> shallowcopy();
    HashMap<Type1, Type2> getWrapped();
}
