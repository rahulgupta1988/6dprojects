package db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ALL_WEB_NOTIFICATION".
 */
public class AllWebNotification {

    private String id;
    private String recognition_id;
    private String image_url;
    private String NotificationTypeId;
    private String is_story_status;
    private String messagetext;
    private String recognize_date;

    public AllWebNotification() {
    }

    public AllWebNotification(String id, String recognition_id, String image_url, String NotificationTypeId, String is_story_status, String messagetext, String recognize_date) {
        this.id = id;
        this.recognition_id = recognition_id;
        this.image_url = image_url;
        this.NotificationTypeId = NotificationTypeId;
        this.is_story_status = is_story_status;
        this.messagetext = messagetext;
        this.recognize_date = recognize_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecognition_id() {
        return recognition_id;
    }

    public void setRecognition_id(String recognition_id) {
        this.recognition_id = recognition_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNotificationTypeId() {
        return NotificationTypeId;
    }

    public void setNotificationTypeId(String NotificationTypeId) {
        this.NotificationTypeId = NotificationTypeId;
    }

    public String getIs_story_status() {
        return is_story_status;
    }

    public void setIs_story_status(String is_story_status) {
        this.is_story_status = is_story_status;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getRecognize_date() {
        return recognize_date;
    }

    public void setRecognize_date(String recognize_date) {
        this.recognize_date = recognize_date;
    }

}
