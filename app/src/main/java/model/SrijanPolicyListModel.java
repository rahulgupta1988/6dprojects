package model;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class SrijanPolicyListModel{

    String policyid;
    String policyname;
    String policyaddeddate;
    String policyaddeddatedisplayvalue;
    String policyaddedtimedisplayvalue;
    String policydisplayname;

    public String getPolicyid() {
        return policyid;
    }

    public void setPolicyid(String policyid) {
        this.policyid = policyid;
    }

    public String getPolicyname() {
        return policyname;
    }

    public void setPolicyname(String policyname) {
        this.policyname = policyname;
    }

    public String getPolicyaddeddate() {
        return policyaddeddate;
    }

    public void setPolicyaddeddate(String policyaddeddate) {
        this.policyaddeddate = policyaddeddate;
    }

    public String getPolicyaddeddatedisplayvalue() {
        return policyaddeddatedisplayvalue;
    }

    public void setPolicyaddeddatedisplayvalue(String policyaddeddatedisplayvalue) {
        this.policyaddeddatedisplayvalue = policyaddeddatedisplayvalue;
    }

    public String getPolicyaddedtimedisplayvalue() {
        return policyaddedtimedisplayvalue;
    }

    public void setPolicyaddedtimedisplayvalue(String policyaddedtimedisplayvalue) {
        this.policyaddedtimedisplayvalue = policyaddedtimedisplayvalue;
    }

    public String getPolicydisplayname() {
        return policydisplayname;
    }

    public void setPolicydisplayname(String policydisplayname) {
        this.policydisplayname = policydisplayname;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }


    public List<PolicyModel> getPolicyemolist() {
        return policyemolist;
    }

    public void setPolicyemolist(List<PolicyModel> policyemolist) {
        this.policyemolist = policyemolist;
    }

    String likes;
    String comments;
    String views;

    List<PolicyModel> policyemolist;



    public static Comparator<SrijanPolicyListModel> policyComparator = new Comparator<SrijanPolicyListModel>() {

        public int compare(SrijanPolicyListModel s1, SrijanPolicyListModel s2) {
            String policy1 = s1.getPolicyname().toUpperCase();
            String policy2 = s2.getPolicyname().toUpperCase();

            //ascending order
            return policy1.compareTo(policy2);

            //descending order
           // return policy2.compareTo(policy1);
        }};



}
