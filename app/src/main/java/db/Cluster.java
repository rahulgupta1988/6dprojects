package db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CLUSTER".
 */
public class Cluster {

    private String clusterid;
    private String clustername;

    public Cluster() {
    }

    public Cluster(String clusterid, String clustername) {
        this.clusterid = clusterid;
        this.clustername = clustername;
    }

    public String getClusterid() {
        return clusterid;
    }

    public void setClusterid(String clusterid) {
        this.clusterid = clusterid;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

}
