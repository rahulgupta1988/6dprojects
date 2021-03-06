package db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "COMMENTS".
 */
public class Comments {

    private String recognition_id;
    private String commentid;
    private String userid;
    private String userName;
    private String date;
    private String designation;
    private String department;
    private String location;
    private String imageurl;
    private String comments;
    private String count;
    private String tagcount;
    private String coi_gid;
    private String coi_gpid;

    public Comments() {
    }

    public Comments(String recognition_id, String commentid, String userid, String userName, String date, String designation, String department, String location, String imageurl, String comments, String count, String tagcount, String coi_gid, String coi_gpid) {
        this.recognition_id = recognition_id;
        this.commentid = commentid;
        this.userid = userid;
        this.userName = userName;
        this.date = date;
        this.designation = designation;
        this.department = department;
        this.location = location;
        this.imageurl = imageurl;
        this.comments = comments;
        this.count = count;
        this.tagcount = tagcount;
        this.coi_gid = coi_gid;
        this.coi_gpid = coi_gpid;
    }

    public String getRecognition_id() {
        return recognition_id;
    }

    public void setRecognition_id(String recognition_id) {
        this.recognition_id = recognition_id;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTagcount() {
        return tagcount;
    }

    public void setTagcount(String tagcount) {
        this.tagcount = tagcount;
    }

    public String getCoi_gid() {
        return coi_gid;
    }

    public void setCoi_gid(String coi_gid) {
        this.coi_gid = coi_gid;
    }

    public String getCoi_gpid() {
        return coi_gpid;
    }

    public void setCoi_gpid(String coi_gpid) {
        this.coi_gpid = coi_gpid;
    }

}
