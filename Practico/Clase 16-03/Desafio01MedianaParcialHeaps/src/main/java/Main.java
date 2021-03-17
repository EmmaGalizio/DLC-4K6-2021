import utils.HeapLoader;


public class Main {

    public static void main(String[] args) {

        HeapLoader heapLoader = new HeapLoader();
        float [] results = heapLoader.getMedianasParciales();
        System.out.println("Medias Parciales a 25000 elementos: " + results[0]);
        System.out.println("Medias Parciales a 50000 elementos: " + results[1]);
        System.out.println("Medias Parciales a 75000 elementos: " + results[2]);
        System.out.println("Medias Parciales a 100000 elementos: " + results[3]);

    }

}
