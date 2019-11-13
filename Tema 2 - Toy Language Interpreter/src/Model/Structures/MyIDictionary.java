package Model.Structures;

import java.util.Enumeration;

public interface MyIDictionary<Type1, Type2> {
    boolean isEmpty();
    int size();
    Type2 get(Type1 key);
    Type2 remove(Type1 key);
    Type2 update(Type1 key, Type2 value);
    String toString();
    boolean isDefined(Type1 id);
}
