public class InversionSlot {

    private String fileName;
    private long inversionCount;

    public InversionSlot(String fileName, long inversionCount) {
        this.fileName = fileName;
        this.inversionCount = inversionCount;
    }

    public String getFileName() {
        return fileName;
    }

    public long getInversionCount() {
        return inversionCount;
    }

    @Override
    public String toString() {
        return "InversionSlot{" +
                "fileName='" + fileName + '\'' +
                ", inversionCount=" + inversionCount +
                '}';
    }
}
