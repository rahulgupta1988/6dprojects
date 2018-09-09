package db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RECOGNITION_RECIEVED_LIST".
 */
public class RecognitionRecievedList {

    private String nominee_name;
    private String nominee;
    private String nomineedesignation;
    private String recognition_id;
    private String details;
    private String count;
    private String imageurl;
    private String recognise_date;
    private String recognition_name;
    private String is_story;
    private String isxpress;

    public RecognitionRecievedList() {
    }

    public RecognitionRecievedList(String nominee_name, String nominee, String nomineedesignation, String recognition_id, String details, String count, String imageurl, String recognise_date, String recognition_name, String is_story, String isxpress) {
        this.nominee_name = nominee_name;
        this.nominee = nominee;
        this.nomineedesignation = nomineedesignation;
        this.recognition_id = recognition_id;
        this.details = details;
        this.count = count;
        this.imageurl = imageurl;
        this.recognise_date = recognise_date;
        this.recognition_name = recognition_name;
        this.is_story = is_story;
        this.isxpress = isxpress;
    }

    public String getNominee_name() {
        return nominee_name;
    }

    public void setNominee_name(String nominee_name) {
        this.nominee_name = nominee_name;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineedesignation() {
        return nomineedesignation;
    }

    public void setNomineedesignation(String nomineedesignation) {
        this.nomineedesignation = nomineedesignation;
    }

    public String getRecognition_id() {
        return recognition_id;
    }

    public void setRecognition_id(String recognition_id) {
        this.recognition_id = recognition_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getRecognise_date() {
        return recognise_date;
    }

    public void setRecognise_date(String recognise_date) {
        this.recognise_date = recognise_date;
    }

    public String getRecognition_name() {
        return recognition_name;
    }

    public void setRecognition_name(String recognition_name) {
        this.recognition_name = recognition_name;
    }

    public String getIs_story() {
        return is_story;
    }

    public void setIs_story(String is_story) {
        this.is_story = is_story;
    }

    public String getIsxpress() {
        return isxpress;
    }

    public void setIsxpress(String isxpress) {
        this.isxpress = isxpress;
    }

}
