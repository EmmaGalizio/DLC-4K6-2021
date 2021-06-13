package emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class VocabularySlot {

    private String token;
    private Integer maxTf;
    private Integer nr;
    private List<PostingSlot> postingList;

    public VocabularySlot() {
        maxTf = 0;
        nr = 0;
    }

    public void addPostingSlot(String documentUrl){
        if(documentUrl != null && !documentUrl.isEmpty()){
            if(postingList == null) postingList = new ArrayList<>();
            PostingSlot postingSlot = new PostingSlot();
            postingSlot.setDocumentUrl(documentUrl);
            postingSlot.incrementTokenFrecuency();
            postingSlot.setToken(token);
            postingList.add(postingSlot);

            this.setNr(postingList.size());
            postingSlot.setNew(true);
            //postingSlot.setNeedsUpdate(false);

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
                postingSlot.setNeedsUpdate(true);
                result = true;
                break;
            }
        }
        if(result) {
            this.setNr(postingList.size());
        }
        return result;
    }

    private void sortListByTf(PostingSlot postingSlot){
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

    /**
     * This method must be called when the posting list is about to be saved in the database
     * */
    public void sortPostingList(){
        this.sortListByTf(postingList.get(0));
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
    public void setMaxTfValue(){
        maxTf = postingList.get(0).getTokenFrecuency();
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

    public Comparator<VocabularySlot> nrComparator(){
        return new NrComparator();
    }

    //</editor-fold>

    public Iterator<PostingSlot> getPostingSlotIterator(){
        return postingList.iterator();
    }

    public void incrementMaxTf(){
        if(maxTf == null) maxTf = 0;
        ++maxTf;
    }

    private class NrComparator implements Comparator<VocabularySlot>{
        @Override
        public int compare(VocabularySlot o1, VocabularySlot o2) {
            return o1.getNr().compareTo(o2.getNr());
        }
    }


}
