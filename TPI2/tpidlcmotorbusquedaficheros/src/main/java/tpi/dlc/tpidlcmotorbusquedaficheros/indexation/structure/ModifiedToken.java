package tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure;

public class ModifiedToken{

    private String token;
    private boolean isNewToken;
    private boolean needUpdate;

    public ModifiedToken() {
        isNewToken = false;
        needUpdate = false;
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


    public boolean isNewToken() {
        return isNewToken;
    }

    public void setNewToken(boolean newToken) {
        isNewToken = newToken;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

}
