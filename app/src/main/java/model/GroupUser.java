package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen on 12-Jan-18.
 */

public class GroupUser implements Parcelable{

    String coi_gid="";
    String coi_gmname="";
    String coi_gmdepartment="";
    String coi_gmprofilepic="";
    String coi_gmuserid="";

    public String getIsprivatestatus() {
        return isprivatestatus;
    }

    public void setIsprivatestatus(String isprivatestatus) {
        this.isprivatestatus = isprivatestatus;
    }

    String isprivatestatus;

    public String getCoi_gid() {
        return coi_gid;
    }

    public void setCoi_gid(String coi_gid) {
        this.coi_gid = coi_gid;
    }

    public String getCoi_gmname() {
        return coi_gmname;
    }

    public void setCoi_gmname(String coi_gmname) {
        this.coi_gmname = coi_gmname;
    }

    public String getCoi_gmdepartment() {
        return coi_gmdepartment;
    }

    public void setCoi_gmdepartment(String coi_gmdepartment) {
        this.coi_gmdepartment = coi_gmdepartment;
    }

    public String getCoi_gmprofilepic() {
        return coi_gmprofilepic;
    }

    public void setCoi_gmprofilepic(String coi_gmprofilepic) {
        this.coi_gmprofilepic = coi_gmprofilepic;
    }

    public String getCoi_gmuserid() {
        return coi_gmuserid;
    }

    public void setCoi_gmuserid(String coi_gmuserid) {
        this.coi_gmuserid = coi_gmuserid;
    }

    public String getCoi_gmisadmin() {
        return coi_gmisadmin;
    }

    public void setCoi_gmisadmin(String coi_gmisadmin) {
        this.coi_gmisadmin = coi_gmisadmin;
    }

    public String getCoi_gmaddeddate() {
        return coi_gmaddeddate;
    }

    public void setCoi_gmaddeddate(String coi_gmaddeddate) {
        this.coi_gmaddeddate = coi_gmaddeddate;
    }

    public String getMxway() {
        return mxway;
    }

    public void setMxway(String mxway) {
        this.mxway = mxway;
    }

    public static Creator<GroupUser> getCREATOR() {
        return CREATOR;
    }

    String coi_gmisadmin="";
    String coi_gmaddeddate="";
    String mxway="";


    public GroupUser() {

    }

    public GroupUser(Parcel in) {
        coi_gid = in.readString();
        coi_gmname = in.readString();
        coi_gmdepartment = in.readString();
        coi_gmprofilepic = in.readString();
        coi_gmuserid = in.readString();
        coi_gmisadmin = in.readString();
        coi_gmaddeddate = in.readString();
        mxway = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coi_gid);
        dest.writeString(coi_gmname);
        dest.writeString(coi_gmdepartment);
        dest.writeString(coi_gmprofilepic);
        dest.writeString(coi_gmuserid);
        dest.writeString(coi_gmisadmin);
        dest.writeString(coi_gmaddeddate);
        dest.writeString(mxway);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupUser> CREATOR = new Creator<GroupUser>() {
        @Override
        public GroupUser createFromParcel(Parcel in) {
            return new GroupUser(in);
        }

        @Override
        public GroupUser[] newArray(int size) {
            return new GroupUser[size];
        }
    };
}
