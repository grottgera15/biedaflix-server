package bestworkingconditions.biedaflix.server.vod.hdd;

public class HddResponse {

    private double freeSpace;
    private double maxSpace;
    private double usedSpace;

    public HddResponse() {
    }

    public HddResponse(double usedSpace, double maxSpace){
        this.maxSpace = maxSpace;
        this.usedSpace = usedSpace;
        freeSpace = maxSpace - usedSpace;
    }

    public void setFreeSpace(double freeSpace){
        this.freeSpace = freeSpace;
    }

    public double getFreeSpace(){
        return freeSpace;
    }

    public void setUsedSpace(double usedSpace){
        this.usedSpace = usedSpace;
    }

    public double getUsedSpace(){
        return usedSpace;
    }

    public void setMaxSpace(double maxSpace){
        this.maxSpace = maxSpace;
    }

    public double getMaxSpace(){
        return maxSpace;
    }
}
