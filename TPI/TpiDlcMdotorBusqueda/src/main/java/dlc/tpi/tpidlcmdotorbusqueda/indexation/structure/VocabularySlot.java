package dlc.tpi.tpidlcmdotorbusqueda.indexation.structure;

import java.util.ArrayList;
import java.util.List;

public class VocabularySlot {

    /*Se puede almacenar la tabla de dispersion que contenga todos los VocabularySlot en un archivo
    de acceso aleatorio exceptuando la lista de posteo.
    Cuando inicia el programa se crea la tabla de dispersion leyendo el archivo del vocabulario
    y cuando se solicite un término de la tabla se busca la lista de posteo de la base de tados y se carga en el slot

    * */

    private String token;
    private Integer maxTf;
    private Integer nr;
    private List<PostingSlot> postingList;
    private boolean postingIndexUpToDate;
    //Sirve para conocer el índice en el que comienta la lista de posteo en el RAF
    private Long postingListStartIndex;

    public VocabularySlot() {
        maxTf = 0;
        nr = 0;
        postingIndexUpToDate = true;

    }


    public void addPostingSlot(String documentUrl){
        if(documentUrl != null && !documentUrl.isEmpty()){
            if(postingList == null) postingList = new ArrayList<>();
            PostingSlot postingSlot = new PostingSlot();
            postingSlot.setDocumentUrl(documentUrl);
            postingSlot.incrementTokenFrecuency();
            postingList.add(postingSlot);
            this.sortListByTf(postingSlot);
            this.setNr(postingList.size());
            this.setMaxTf(postingList.get(0).getTokenFrecuency());
        }
    }
    /**
     * @Returns Return true if exists a PostingSlot in the list for the especified document and increments the tf
     * @Returns false if does not exists  PostingSlot for the document*/
    public boolean incrementPostingSlotForDocument(String documentUrl){
        boolean result = false;
        for(PostingSlot postingSlot : postingList){
            if(postingSlot.getDocumentUrl().equals(documentUrl)){
                postingSlot.incrementTokenFrecuency();
                result = true;
                break;
            }
        }
        if(result) {
            this.sortListByTf(postingList.get(0));
            this.setNr(postingList.size());
            this.setMaxTf(postingList.get(0).getTokenFrecuency());
        }
        return result;
    }

    public void sortListByTf(PostingSlot postingSlot){
        postingList.sort(postingSlot.getDescendingFrecuencyComparator());
    }

    public boolean existsInDocument(String documentUrl){
        if(postingList != null){
            PostingSlot postingSlot = new PostingSlot();
            postingSlot.setDocumentUrl(documentUrl);
            return postingList.contains(postingSlot);
        }
        return false;
    }

    public PostingSlot getPostingSlot(String documentUrl){
        if(postingList != null){
            for(PostingSlot postingSlot : postingList){
                if(postingSlot.getDocumentUrl().equals(documentUrl)){
                    return  postingSlot;
                }
            }
        }
        return null;
    }

    public boolean postingListIsLoaded(){
        return postingList != null;
    }

    public boolean existInVocabulary(){
        return token != null;
    }

    public void clearPostingList(){
        postingList = null;
    }


    //<editor-fold desc="GETTERS AND SETTERS">
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMaxTf() {
        return maxTf;
    }

    public void setMaxTf(Integer maxTf) {
        this.maxTf = maxTf;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public List<PostingSlot> getPostingList() {
        return postingList;
    }

    public void setPostingList(List<PostingSlot> postingList) {
        this.postingList = postingList;
        if(postingList != null) nr = postingList.size();
    }
    public boolean isPostingIndexUpToDate() {
        return postingIndexUpToDate;
    }

    public void setPostingIndexUpToDate(boolean postingIndexUpToDate) {
        this.postingIndexUpToDate = postingIndexUpToDate;
    }

    public Long getPostingListStartIndex() {
        return postingListStartIndex;
    }

    public void setPostingListStartIndex(Long postingListStartIndex) {
        this.postingListStartIndex = postingListStartIndex;
    }
    //</editor-fold>
}
