package model;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class PolicyModel {

    String emoid;
    String emopercentage;
    String emocount;
    String policyid;

    public String getEmoid() {
        return emoid;
    }

    public void setEmoid(String emoid) {
        this.emoid = emoid;
    }

    public String getEmopercentage() {
        return emopercentage;
    }

    public void setEmopercentage(String emopercentage) {
        this.emopercentage = emopercentage;
    }

    public String getEmocount() {
        return emocount;
    }

    public void setEmocount(String emocount) {
        this.emocount = emocount;
    }

    public String getPolicyid() {
        return policyid;
    }

    public void setPolicyid(String policyid) {
        this.policyid = policyid;
    }
}
