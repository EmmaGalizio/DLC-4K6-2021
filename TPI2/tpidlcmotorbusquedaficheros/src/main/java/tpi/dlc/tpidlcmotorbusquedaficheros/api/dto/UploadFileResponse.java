package emma.galzio.tpidlcmotorbusquedaficheros.api.dto;

public class UploadFileResponse {

    private String fileName;
    private String fileUrl;
    private String fileUri;
    private String fileType;

    public UploadFileResponse(String fileName, String fileUrl, String fileUri, String fileType) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileUri = fileUri;
        this.fileType = fileType;
    }

    public UploadFileResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
