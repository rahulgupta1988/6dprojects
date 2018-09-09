package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import db.AllUsers;

/**
 * Created by Praveen on 01-Feb-18.
 */

public class GPUserListModel implements Serializable {


    public List<AllUsers> allUsersList=new ArrayList<>();

    public List<AllUsers> getAllUsersList() {
        return allUsersList;
    }

    public void setAllUsersList(List<AllUsers> allUsersList) {
        this.allUsersList = allUsersList;
    }
}
