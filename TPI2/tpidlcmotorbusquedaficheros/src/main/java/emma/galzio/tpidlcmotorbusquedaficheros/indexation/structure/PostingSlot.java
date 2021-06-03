package emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure;

import java.util.Comparator;

/**
 * The first element of each PostingSlot for a token is the token itself, wich isn't included
 * in the PostingSlotObjetc, then:
 * The order of the fields defined in this class is the same order in wich the postint lists are
 * stored in the file
 *
 * */
public class PostingSlot {

    private String token;
    private String documentUrl;
    private Integer tokenFrecuency;
    private boolean isNew;
    private boolean needsUpdate;

    public PostingSlot() {
        tokenFrecuency = 0;
        isNew = false;
        needsUpdate = false;
    }

    public PostingSlot(String documentUrl, Integer tokenFrecuency) {
        this.documentUrl = documentUrl;
        this.tokenFrecuency = tokenFrecuency;
        isNew = false;
        needsUpdate = false;
    }

    public void incrementTokenFrecuency(){
        ++tokenFrecuency;
    }

    public Comparator<PostingSlot> getDescendingFrecuencyComparator(){
        return new DescendigFrecuencyComparator();
    }

    @Override
    public boolean equals(Object obj) {
        return documentUrl.equals(((PostingSlot) obj).getDocumentUrl());
    }

    @Override
    public String toString() {
        return "Token: "+token+"\nTF: " + tokenFrecuency+ "\nUrl: " + documentUrl;
    }

    //<editor-fold desc="GETTERS AND SETTERS">
    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Integer getTokenFrecuency() {
        return tokenFrecuency;
    }

    public void setTokenFrecuency(Integer tokenFrecuency) {
        this.tokenFrecuency = tokenFrecuency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    //</editor-fold>

    private class DescendigFrecuencyComparator implements Comparator<PostingSlot>{

        @Override
        public int compare(PostingSlot o1, PostingSlot o2) {
            if(o1.getTokenFrecuency() > o2.getTokenFrecuency()) return -1;
            else if(o1.getTokenFrecuency() < o2.getTokenFrecuency() ) return 1;
            else return 0;
        }
    }


}

