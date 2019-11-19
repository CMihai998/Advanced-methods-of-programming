package Controller;

import Model.Structures.iHeap;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyGarbageCollector {

    Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, iHeap heap){
        Set<Map.Entry<Integer, Value>> heapSet = heap.getEntrySet();

        return heapSet.stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey())) //don't take in consideration elements that are not reachable
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));//collect em all in a list
    }

    public List<Integer> getAddressFromSymbolTable(List<Value> content){
        //will generate a list of values and will filter the reference values and return their addresses
        return content
                .stream()
                .filter(element -> element instanceof RefValue) //check if is RefValue
                .map(element -> ((RefValue) element).getAddress()) //map refvalue to its address
                .collect(Collectors.toList()); //collect in list
    }

    public List<Integer> addIndirections(List<Integer> addressesFromSymbolTable, iHeap heapTable){
        boolean change = true;
        Set<Map.Entry<Integer, Value>> heapSet = heapTable.getEntrySet();//get entry set that needs modifications
        List<Integer> newAddressList = addressesFromSymbolTable.stream().collect(Collectors.toList()); //copy of list in order to add indirections

        //we go through heapSet again and again and each time we add to the address list new indirection level and new addresses which must NOT be deleted
        while (change){
            List<Integer> appendingList = null;
            change = false;
            appendingList = heapSet.stream()
                                   .filter(e -> e.getValue() instanceof RefValue)//check if val in heap is RefValue so it can have indirections todo SEMINAR
                                   .filter(e -> newAddressList.contains(e.getKey()))//check if address list contains ref to this
                                   .map(e -> (((RefValue) e.getValue()).getAddress()))//map the reference to its address so we can add it
                                   .filter(e -> !newAddressList.contains(e))//check if the address list already has that reference from prev elems
                                   .collect(Collectors.toList());//collect to list

            if(!appendingList.isEmpty()){
                //we still have indirect references so we have to keep checking
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }


}
