package emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure;

import java.util.Comparator;

public class ModifiedToken implements Comparable<ModifiedToken> {

    private String token;
    private Long startOfListIndex;
    private int originalListSize;

    /*Puedo mantener la lísta ordenada por startOfListIndex para poder recorrerla
    * solo en los intervalos en los que haya alguna lista sin modificar
    * Hago un ciclo recorriendo todos los elementos de la lista de los tokens modificados
    * empiezo recorriendo el archivo desde el principio hasta que el índice sea igual al startOfListIndex
    * ahí corta la lectura del archivo e incrementa el FOR externo para empezar con otro token,
    * se posiciona el puntero del archivo en startOfListIndex + originalListSize + 1 y se
    * empieza otra vez a recorrer siempre y cuando startOfListIndex sea menor al puntero actual del archivo*/

    public ModifiedToken() {
        startOfListIndex = -1L;
    }

    public ModifiedToken(String token, Long startOfListIndex) {
        this.token = token;
        this.startOfListIndex = startOfListIndex;
    }

    public Comparator<ModifiedToken> getStartIndexComparator(){
        return new StartIndexComparator();
    }

    @Override
    public boolean equals(Object obj) {
        return token.equals(obj);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getStartOfListIndex() {
        return startOfListIndex;
    }

    public void setStartOfListIndex(Long startOfListIndex) {
        this.startOfListIndex = startOfListIndex;
    }

    public int getOriginalListSize() {
        return originalListSize;
    }

    public void setOriginalListSize(int originalListSize) {
        this.originalListSize = originalListSize;
    }

    @Override
    public int compareTo(ModifiedToken o) {
        if(this.getStartOfListIndex() == null || this.getStartOfListIndex() == -1) return 1;
        if(o.getStartOfListIndex() == null || o.getStartOfListIndex() == -1) return -1;

        return this.getStartOfListIndex().compareTo(o.getStartOfListIndex());
    }

    private class StartIndexComparator implements Comparator<ModifiedToken>{
        @Override
        public int compare(ModifiedToken o1, ModifiedToken o2) {

            return o1.getStartOfListIndex().compareTo(o2.getStartOfListIndex());
            /*
            if(o1.getStartOfListIndex() == null || o1.getStartOfListIndex() == -1) return 1;
            if(o2.getStartOfListIndex() == null || o2.getStartOfListIndex() == -1) return -1;
            if(o1.getStartOfListIndex().equals(o2.getStartOfListIndex())) return 0;
            if(o1.getStartOfListIndex() < o2.getStartOfListIndex()) return -1;
            return 1;

             */

        }
    }
}
