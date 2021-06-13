package tpi.dlc.tpidlcmotorbusquedaficheros.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;

public class DocumentResult implements Comparable<DocumentResult> {

    private String url;
    private String uri;
    private String name;
    private double ir;

    public DocumentResult() {
        ir = 0;
    }

    public DocumentResult(String url, String uri, String name, double ir) {
        this.url = url;
        this.uri = uri;
        this.name = name;
        this.ir = ir;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIr() {
        return ir;
    }

    public void setIr(double ir) {
        this.ir = ir;
    }

    @JsonIgnore
    public Comparator<DocumentResult> getIrComparator(){
        return new IrComparator();
    }

    @Override
    public int compareTo(DocumentResult o) {
        return ((int)this.getIr() - (int)o.getIr());
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    private class IrComparator implements  Comparator<DocumentResult>{
        @Override
        public int compare(DocumentResult o1, DocumentResult o2) {
            return ((int)o1.getIr() - (int)o2.getIr());
        }
    }



    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        stringBuilder.append("Nombre: " + name);
        stringBuilder.append("\nUrl: " + url);
        stringBuilder.append("\nURI: " + uri);
        stringBuilder.append("\nIR: " + ir);
        stringBuilder.append("\n}");

        return stringBuilder.toString();
    }
}
