package telu.steven.tugasakhir;

/**
 * Created by OJI on 01/06/2018.
 */

public class model {

    String sub;
    String node;
    model(){
    }
    model(String node, String sub){
        this.sub = sub;
        this.node = node;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
