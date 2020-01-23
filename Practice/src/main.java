import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

interface litera{}
class A implements litera{}   class B extends A implements litera {}   class C extends A implements litera {}



class Amain{



    litera  method1(ArrayList<litera> list) {  if (list.isEmpty()) return null;

                                   else return list.get(0);}



    void method2(ArrayList<litera>  list) {  list.add(null);}



    void method3(){

        ArrayList<litera> listA=new ArrayList<litera>(); listA.add(new A());

        ArrayList<litera> listB = new ArrayList<litera>(); listB.add(new B());

        ArrayList<litera> listC = new ArrayList<litera>(); listC.add(new C());

        this.method1(listA); this.method1(listB); this.method1(listC);

        this.method2(listA); this.method2(listB); this.method2(listC);

    }


}

public class main{
    public static void main(String[] args) {
        Amain a = new Amain();
        a.method3();

        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2); //thread-safe without synchronization
                    executor.submit(task);
                });
        executor.shutdownNow();
        System.out.println(atomicInt.get());
    }
}