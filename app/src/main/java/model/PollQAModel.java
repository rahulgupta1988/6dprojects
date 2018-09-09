package model;

import java.util.List;

/**
 * Created by Praveen on 01-Sep-17.
 */

public class PollQAModel {

    String opinionpollid;
    String questiontitle;
    String noofparticipants;
    String isanswer;
    String polltype;
    String end_date;
    List<PollOptionModel> pollOptionModels;

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

    public String getNoofparticipants() {
        return noofparticipants;
    }

    public void setNoofparticipants(String noofparticipants) {
        this.noofparticipants = noofparticipants;
    }

    public String getIsanswer() {
        return isanswer;
    }

    public void setIsanswer(String isanswer) {
        this.isanswer = isanswer;
    }

    public String getPolltype() {
        return polltype;
    }

    public void setPolltype(String polltype) {
        this.polltype = polltype;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public List<PollOptionModel> getPollOptionModels() {
        return pollOptionModels;
    }

    public void setPollOptionModels(List<PollOptionModel> pollOptionModels) {
        this.pollOptionModels = pollOptionModels;
    }
}
