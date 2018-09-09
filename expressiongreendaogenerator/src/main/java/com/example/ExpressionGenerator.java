package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Praveen on 7/4/2016.
 */
public class ExpressionGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema=new Schema(24,"db");
        schema.enableKeepSectionsByDefault();
        createServiceAppSchema(schema);
        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createServiceAppSchema(Schema schema) {

        Entity userlogininfo=schema.addEntity("UserLoginInfo");
        userlogininfo.addStringProperty("userid").primaryKey();
        userlogininfo.addStringProperty("empno");
        userlogininfo.addStringProperty("name");
        userlogininfo.addStringProperty("username");
        userlogininfo.addStringProperty("dob");
        userlogininfo.addStringProperty("location");
        userlogininfo.addStringProperty("role");
        userlogininfo.addStringProperty("department");
        userlogininfo.addStringProperty("designation");
        userlogininfo.addStringProperty("quote");
        userlogininfo.addStringProperty("hobbies");
        userlogininfo.addStringProperty("imageurl");
        userlogininfo.addStringProperty("company");
        userlogininfo.addStringProperty("experience");
        userlogininfo.addStringProperty("is_admin");
        userlogininfo.addStringProperty("emailId");
        userlogininfo.addStringProperty("reg_mngr");
        userlogininfo.addStringProperty("post_xpress");
        userlogininfo.addStringProperty("rrecognition");
        userlogininfo.addStringProperty("grecognition");
        userlogininfo.addStringProperty("isdonor");
        userlogininfo.addStringProperty("isrijan");


    /*    Entity appVisitors=schema.addEntity("APPVisitors");
        appVisitors.addStringProperty("vistor_counter");
*/



        Entity allUserEvent=schema.addEntity("AllUserEvent");
        allUserEvent.addStringProperty("name");
        allUserEvent.addStringProperty("event_date");
        allUserEvent.addStringProperty("image_url");
        allUserEvent.addStringProperty("event_desc");
        allUserEvent.addStringProperty("event_id");
        allUserEvent.addStringProperty("likecount");
        allUserEvent.addStringProperty("commentcount");
        allUserEvent.addStringProperty("event_title");
        allUserEvent.addStringProperty("eventmaster_id");
        allUserEvent.addStringProperty("eventicon");



        Entity userWedding=schema.addEntity("UserWedding");
        userWedding.addStringProperty("userid");
        userWedding.addStringProperty("name");
        userWedding.addStringProperty("dob");
        userWedding.addStringProperty("doj");
        userWedding.addStringProperty("dow");
        userWedding.addStringProperty("image_url");
        userWedding.addStringProperty("issubcribe");
        userWedding.addStringProperty("isbirthday_subscribe");
        userWedding.addStringProperty("iswork_subscribe");
        userWedding.addStringProperty("iswedding_subscribe");



        Entity allUsers=schema.addEntity("AllUsers");
        allUsers.addStringProperty("name");
        allUsers.addStringProperty("department");
        allUsers.addStringProperty("designation");
        allUsers.addStringProperty("user_id");
        allUsers.addStringProperty("email");
        allUsers.addStringProperty("location");
        allUsers.addStringProperty("imageurl");
        allUsers.addStringProperty("coiMisAdmin");
        allUsers.addStringProperty("mxway");


      /*  Entity allWebNotification=schema.addEntity("AllWebNotification");
        allWebNotification.addStringProperty("recognition_id");
        allWebNotification.addStringProperty("nominator_name");
        allWebNotification.addStringProperty("image_url");
        allWebNotification.addStringProperty("total_count");
        allWebNotification.addStringProperty("is_story_status");
        allWebNotification.addStringProperty("nominee_name");
        allWebNotification.addStringProperty("recognize_date");
        allWebNotification.addStringProperty("NotificationTypeId");
        allWebNotification.addStringProperty("id");*/

        Entity allWebNotification=schema.addEntity("AllWebNotification");
        allWebNotification.addStringProperty("id");
        allWebNotification.addStringProperty("recognition_id");
        allWebNotification.addStringProperty("image_url");
        allWebNotification.addStringProperty("NotificationTypeId");
        allWebNotification.addStringProperty("is_story_status");
        allWebNotification.addStringProperty("messagetext");
        allWebNotification.addStringProperty("recognize_date");


        Entity getmorepost = schema.addEntity("GetMorePost");
        getmorepost.addStringProperty("userid");
        getmorepost.addStringProperty("nominator_name");
        getmorepost.addStringProperty("nominator_designation");
        getmorepost.addStringProperty("nominator_location");
        getmorepost.addStringProperty("nominator_imageurl");
        getmorepost.addStringProperty("nominee_name");
        getmorepost.addStringProperty("nominee_designation");
        getmorepost.addStringProperty("nominee_location");
        getmorepost.addStringProperty("nominee_imageurl");
        getmorepost.addStringProperty("title");
        getmorepost.addStringProperty("description");
        getmorepost.addStringProperty("details");
        getmorepost.addStringProperty("challengesfaced");
        getmorepost.addStringProperty("is_story");
        getmorepost.addStringProperty("recognise_date");
        getmorepost.addStringProperty("event_date");
        getmorepost.addStringProperty("type");
        getmorepost.addStringProperty("url");
        getmorepost.addStringProperty("icon");
        getmorepost.addStringProperty("count");
        getmorepost.addStringProperty("likes");
        getmorepost.addStringProperty("comments");
        getmorepost.addStringProperty("ulikes");
        getmorepost.addStringProperty("ucomments");
        getmorepost.addStringProperty("recognition_id").primaryKey();
        getmorepost.addStringProperty("nominee");
        getmorepost.addStringProperty("subject");
        getmorepost.addStringProperty("isxpress");
      /*  getmorepost.addStringProperty("totalrows");*/
        getmorepost.addStringProperty("awardid");
        getmorepost.addStringProperty("date");
        getmorepost.addStringProperty("isNomineeDonor");
        getmorepost.addStringProperty("isNominatorDonar");
        getmorepost.addStringProperty("taguserid");
        getmorepost.addStringProperty("tagcount");




        Entity getXpresswayPost = schema.addEntity("GetXpresswayPost");
        getXpresswayPost.addStringProperty("userid");
        getXpresswayPost.addStringProperty("nominator_name");
        getXpresswayPost.addStringProperty("nominator_designation");
        getXpresswayPost.addStringProperty("nominator_location");
        getXpresswayPost.addStringProperty("nominator_imageurl");
        getXpresswayPost.addStringProperty("nominee_name");
        getXpresswayPost.addStringProperty("nominee_designation");
        getXpresswayPost.addStringProperty("nominee_location");
        getXpresswayPost.addStringProperty("nominee_imageurl");
        getXpresswayPost.addStringProperty("title");
        getXpresswayPost.addStringProperty("description");
        getXpresswayPost.addStringProperty("details");
        getXpresswayPost.addStringProperty("challengesfaced");
        getXpresswayPost.addStringProperty("is_story");
        getXpresswayPost.addStringProperty("recognise_date");
        getXpresswayPost.addStringProperty("event_date");
        getXpresswayPost.addStringProperty("type");
        getXpresswayPost.addStringProperty("url");
        getXpresswayPost.addStringProperty("icon");
        getXpresswayPost.addStringProperty("count");
        getXpresswayPost.addStringProperty("likes");
        getXpresswayPost.addStringProperty("comments");
        getXpresswayPost.addStringProperty("ulikes");
        getXpresswayPost.addStringProperty("ucomments");
        getXpresswayPost.addStringProperty("recognition_id").primaryKey();
        getXpresswayPost.addStringProperty("nominee");
        getXpresswayPost.addStringProperty("subject");
        getXpresswayPost.addStringProperty("isxpress");
      /*  getXpresswayPost.addStringProperty("totalrows");*/
        getXpresswayPost.addStringProperty("awardid");
        getXpresswayPost.addStringProperty("date");
        getXpresswayPost.addStringProperty("isNomineeDonor");
        getXpresswayPost.addStringProperty("isNominatorDonar");
        getXpresswayPost.addStringProperty("taguserid");
        getXpresswayPost.addStringProperty("tagcount");



        Entity recentActivity = schema.addEntity("RecentActivity");
        recentActivity.addStringProperty("nominator_name");
        recentActivity.addStringProperty("nominee_name");
        recentActivity.addStringProperty("recognise_date");
        recentActivity.addStringProperty("recognition_name");
        recentActivity.addStringProperty("is_story");
        recentActivity.addStringProperty("count");
        recentActivity.addStringProperty("recognition_id");
        recentActivity.addStringProperty("nominee");
        recentActivity.addStringProperty("isxpress");
        recentActivity.addStringProperty("award");
        recentActivity.addStringProperty("type_view");







        Entity comments = schema.addEntity("Comments");
        comments.addStringProperty("recognition_id");
        comments.addStringProperty("commentid");
        comments.addStringProperty("userid");
        comments.addStringProperty("userName");
        comments.addStringProperty("date");
        comments.addStringProperty("designation");
        comments.addStringProperty("department");
        comments.addStringProperty("location");
        comments.addStringProperty("imageurl");
        comments.addStringProperty("comments");
        comments.addStringProperty("count");
        comments.addStringProperty("tagcount");
        comments.addStringProperty("coi_gid");
        comments.addStringProperty("coi_gpid");


        Entity xpressway = schema.addEntity("Xpressway");
        xpressway.addStringProperty("recognition_id");
        xpressway.addStringProperty("nominator_name");
        xpressway.addStringProperty("subject");
        xpressway.addStringProperty("isstory");
        xpressway.addStringProperty("nominee");
        xpressway.addStringProperty("other");
        xpressway.addStringProperty("award");
        xpressway.addStringProperty("recognise_date");
        xpressway.addStringProperty("image_url");

        Entity award = schema.addEntity("Award");
        award.addStringProperty("awardid").primaryKey();
        award.addStringProperty("awardname");
        award.addStringProperty("awardicon");
        award.addStringProperty("isactive");


        Entity expossor = schema.addEntity("Expossor");
        expossor.addStringProperty("sno");
        expossor.addStringProperty("xpressor_id");
        expossor.addStringProperty("xpressor_text");
        expossor.addStringProperty("xpressor_rmtxt");
        Property awardID=expossor.addStringProperty("awardID").notNull().getProperty();
        award.addToMany(expossor, awardID);


        Entity currentOpinion = schema.addEntity("CurrentOpinion");
        currentOpinion.addStringProperty("opinionid");
        currentOpinion.addStringProperty("question");
        currentOpinion.addStringProperty("answertype");
        currentOpinion.addStringProperty("enddate");
        currentOpinion.addStringProperty("isactive");
        currentOpinion.addStringProperty("type");


        Entity pollResult = schema.addEntity("PollResult");
        pollResult.addStringProperty("opinionid");
        pollResult.addStringProperty("question");
        pollResult.addStringProperty("answertype");
        pollResult.addStringProperty("enddate");
        pollResult.addStringProperty("isactive");
        pollResult.addStringProperty("agree_perc");
        pollResult.addStringProperty("disagree_perc");
        pollResult.addStringProperty("cantsay_perc");
        pollResult.addStringProperty("status");

        Entity bulletinCategory = schema.addEntity("BulletinCategory");
        bulletinCategory.addStringProperty("id");
        bulletinCategory.addStringProperty("name");
        bulletinCategory.addStringProperty("isactive");

        Entity recognitiongiven = schema.addEntity("RecognitionGiven");
        recognitiongiven.addStringProperty("awardid");
        recognitiongiven.addStringProperty("awardName");
        recognitiongiven.addStringProperty("count");
        recognitiongiven.addStringProperty("total");

        Entity recognitionRecieved = schema.addEntity("RecognitionRecieved");
        recognitionRecieved.addStringProperty("awardid");
        recognitionRecieved.addStringProperty("awardName");
        recognitionRecieved.addStringProperty("count");
        recognitionRecieved.addStringProperty("total");

        Entity recognitionGivenList = schema.addEntity("RecognitionGivenList");
        recognitionGivenList.addStringProperty("nominee_name");
        recognitionGivenList.addStringProperty("nominee");
        recognitionGivenList.addStringProperty("nomineedesignation");
        recognitionGivenList.addStringProperty("recognition_id");
        recognitionGivenList.addStringProperty("details");
        recognitionGivenList.addStringProperty("count");
        recognitionGivenList.addStringProperty("imageurl");
        recognitionGivenList.addStringProperty("recognise_date");
        recognitionGivenList.addStringProperty("recognition_name");
        recognitionGivenList.addStringProperty("is_story");
        recognitionGivenList.addStringProperty("isxpress");


        Entity recognitionRecievedList = schema.addEntity("RecognitionRecievedList");
        recognitionRecievedList.addStringProperty("nominee_name");
        recognitionRecievedList.addStringProperty("nominee");
        recognitionRecievedList.addStringProperty("nomineedesignation");
        recognitionRecievedList.addStringProperty("recognition_id");
        recognitionRecievedList.addStringProperty("details");
        recognitionRecievedList.addStringProperty("count");
        recognitionRecievedList.addStringProperty("imageurl");
        recognitionRecievedList.addStringProperty("recognise_date");
        recognitionRecievedList.addStringProperty("recognition_name");
        recognitionRecievedList.addStringProperty("is_story");
        recognitionRecievedList.addStringProperty("isxpress");

        // celebration moments
        Entity celebrationMoments = schema.addEntity("CelebrationMoments");
        celebrationMoments.addStringProperty("userid");
        celebrationMoments.addStringProperty("name");
        celebrationMoments.addStringProperty("eventmaster_id");
        celebrationMoments.addStringProperty("event_id");
        celebrationMoments.addStringProperty("department");
        celebrationMoments.addStringProperty("event_desc");
        celebrationMoments.addStringProperty("work_year");
        celebrationMoments.addStringProperty("imageurl");
        celebrationMoments.addStringProperty("emailId");
        celebrationMoments.addStringProperty("title");



        // given recived count
        Entity countRG = schema.addEntity("CountRG");
        countRG.addStringProperty("grecognition");
        countRG.addStringProperty("rrecognition");
        countRG.addStringProperty("tgrecognition");
        countRG.addStringProperty("trrecognition");


        // given Like
        Entity GivenLike = schema.addEntity("GivenLike");
        GivenLike.addStringProperty("recognition_id");
        GivenLike.addStringProperty("userid");
        GivenLike.addStringProperty("userName");
        GivenLike.addStringProperty("designation");
        GivenLike.addStringProperty("department");
        GivenLike.addStringProperty("location");
        GivenLike.addStringProperty("imageurl");


        // Bulletin List
        Entity bulletinList = schema.addEntity("BulletinList");
        bulletinList.addStringProperty("postedbyuser");
        bulletinList.addStringProperty("postbyemail");
        bulletinList.addStringProperty("details");
        bulletinList.addStringProperty("create_date");
        bulletinList.addStringProperty("groupemail");
        bulletinList.addStringProperty("type");
        bulletinList.addStringProperty("fileurl");
        bulletinList.addStringProperty("url");



        // Srijan
        Entity srijanTOPComment = schema.addEntity("SrijanTOPComment");
        srijanTOPComment.addStringProperty("policyid");
        srijanTOPComment.addStringProperty("policyname");
        srijanTOPComment.addStringProperty("policynamedisplayvalue");
        srijanTOPComment.addStringProperty("usercommentdisplayvalue");
        srijanTOPComment.addStringProperty("commentdatedisplayvalue");
        srijanTOPComment.addStringProperty("commenttimedisplayvalue");
        srijanTOPComment.addStringProperty("commentuserid");
        srijanTOPComment.addStringProperty("commentusername");
        srijanTOPComment.addStringProperty("commentuserdepartment");
        srijanTOPComment.addStringProperty("commentuserprofilepic");
        srijanTOPComment.addStringProperty("commentlevel");
        srijanTOPComment.addStringProperty("parentcommentid");
        srijanTOPComment.addStringProperty("usercomment");
        srijanTOPComment.addStringProperty("taguser");
        srijanTOPComment.addStringProperty("childcommentid");

        // cluster

        Entity cluster = schema.addEntity("Cluster");
        cluster.addStringProperty("clusterid");
        cluster.addStringProperty("clustername");



        // Group data

        Entity gpWALLPOST = schema.addEntity("GPWALLPOST");
        gpWALLPOST.addStringProperty("gpostid").primaryKey();
        gpWALLPOST.addStringProperty("gid");
        gpWALLPOST.addStringProperty("details");
        gpWALLPOST.addStringProperty("type");
        gpWALLPOST.addStringProperty("userid");
        gpWALLPOST.addStringProperty("event_date");
        gpWALLPOST.addStringProperty("isxpress");
        gpWALLPOST.addStringProperty("taguserid");
        gpWALLPOST.addStringProperty("tabusername");
        gpWALLPOST.addStringProperty("nominee_imageurl");
        gpWALLPOST.addStringProperty("nominee_designation");
        gpWALLPOST.addStringProperty("nominator_name");
        gpWALLPOST.addStringProperty("likes");
        gpWALLPOST.addStringProperty("comments");
        gpWALLPOST.addStringProperty("tagcount");
        gpWALLPOST.addStringProperty("subject");
        gpWALLPOST.addStringProperty("url");
        gpWALLPOST.addStringProperty("devicetype");
        gpWALLPOST.addStringProperty("vcount");
        gpWALLPOST.addStringProperty("themeid");
        gpWALLPOST.addStringProperty("isstorystatus");


        Entity xpgpWALLPOST = schema.addEntity("XPGPWALLPOST");
        xpgpWALLPOST.addStringProperty("gpostid").primaryKey();
        xpgpWALLPOST.addStringProperty("gid");
        xpgpWALLPOST.addStringProperty("details");
        xpgpWALLPOST.addStringProperty("type");
        xpgpWALLPOST.addStringProperty("userid");
        xpgpWALLPOST.addStringProperty("event_date");
        xpgpWALLPOST.addStringProperty("isxpress");
        xpgpWALLPOST.addStringProperty("taguserid");
        xpgpWALLPOST.addStringProperty("tabusername");
        xpgpWALLPOST.addStringProperty("nominee_imageurl");
        xpgpWALLPOST.addStringProperty("nominee_designation");
        xpgpWALLPOST.addStringProperty("nominator_name");
        xpgpWALLPOST.addStringProperty("likes");
        xpgpWALLPOST.addStringProperty("comments");
        xpgpWALLPOST.addStringProperty("tagcount");
        xpgpWALLPOST.addStringProperty("subject");
        xpgpWALLPOST.addStringProperty("url");
        xpgpWALLPOST.addStringProperty("devicetype");
        xpgpWALLPOST.addStringProperty("vcount");
        xpgpWALLPOST.addStringProperty("themeid");


    }

}
