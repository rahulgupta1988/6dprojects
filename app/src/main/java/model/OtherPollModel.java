package model;

/**
 * Created by Praveen on 02-Sep-17.
 */

public class OtherPollModel {

    String opinionpollid;

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    String questiontitle;
    String enddate;

    public String getOpinionpollid() {
        return opinionpollid;
    }

    public void setOpinionpollid(String opinionpollid) {
        this.opinionpollid = opinionpollid;
    }

    public String getQuestiontitle() {
        return questiontitle;
    }

    public void setQuestiontitle(String questiontitle) {
        this.questiontitle = questiontitle;
    }
}
