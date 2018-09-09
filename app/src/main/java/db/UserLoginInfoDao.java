package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "USER_LOGIN_INFO".
 */
public class UserLoginInfoDao extends AbstractDao<UserLoginInfo, String> {

    public static final String TABLENAME = "USER_LOGIN_INFO";

    /**
     * Properties of entity UserLoginInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Userid = new Property(0, String.class, "userid", true, "USERID");
        public final static Property Empno = new Property(1, String.class, "empno", false, "EMPNO");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Username = new Property(3, String.class, "username", false, "USERNAME");
        public final static Property Dob = new Property(4, String.class, "dob", false, "DOB");
        public final static Property Location = new Property(5, String.class, "location", false, "LOCATION");
        public final static Property Role = new Property(6, String.class, "role", false, "ROLE");
        public final static Property Department = new Property(7, String.class, "department", false, "DEPARTMENT");
        public final static Property Designation = new Property(8, String.class, "designation", false, "DESIGNATION");
        public final static Property Quote = new Property(9, String.class, "quote", false, "QUOTE");
        public final static Property Hobbies = new Property(10, String.class, "hobbies", false, "HOBBIES");
        public final static Property Imageurl = new Property(11, String.class, "imageurl", false, "IMAGEURL");
        public final static Property Company = new Property(12, String.class, "company", false, "COMPANY");
        public final static Property Experience = new Property(13, String.class, "experience", false, "EXPERIENCE");
        public final static Property Is_admin = new Property(14, String.class, "is_admin", false, "IS_ADMIN");
        public final static Property EmailId = new Property(15, String.class, "emailId", false, "EMAIL_ID");
        public final static Property Reg_mngr = new Property(16, String.class, "reg_mngr", false, "REG_MNGR");
        public final static Property Post_xpress = new Property(17, String.class, "post_xpress", false, "POST_XPRESS");
        public final static Property Rrecognition = new Property(18, String.class, "rrecognition", false, "RRECOGNITION");
        public final static Property Grecognition = new Property(19, String.class, "grecognition", false, "GRECOGNITION");
        public final static Property Isdonor = new Property(20, String.class, "isdonor", false, "ISDONOR");
        public final static Property Isrijan = new Property(21, String.class, "isrijan", false, "ISRIJAN");
    }

    ;


    public UserLoginInfoDao(DaoConfig config) {
        super(config);
    }

    public UserLoginInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_LOGIN_INFO\" (" + //
                "\"USERID\" TEXT PRIMARY KEY NOT NULL ," + // 0: userid
                "\"EMPNO\" TEXT," + // 1: empno
                "\"NAME\" TEXT," + // 2: name
                "\"USERNAME\" TEXT," + // 3: username
                "\"DOB\" TEXT," + // 4: dob
                "\"LOCATION\" TEXT," + // 5: location
                "\"ROLE\" TEXT," + // 6: role
                "\"DEPARTMENT\" TEXT," + // 7: department
                "\"DESIGNATION\" TEXT," + // 8: designation
                "\"QUOTE\" TEXT," + // 9: quote
                "\"HOBBIES\" TEXT," + // 10: hobbies
                "\"IMAGEURL\" TEXT," + // 11: imageurl
                "\"COMPANY\" TEXT," + // 12: company
                "\"EXPERIENCE\" TEXT," + // 13: experience
                "\"IS_ADMIN\" TEXT," + // 14: is_admin
                "\"EMAIL_ID\" TEXT," + // 15: emailId
                "\"REG_MNGR\" TEXT," + // 16: reg_mngr
                "\"POST_XPRESS\" TEXT," + // 17: post_xpress
                "\"RRECOGNITION\" TEXT," + // 18: rrecognition
                "\"GRECOGNITION\" TEXT," + // 19: grecognition
                "\"ISDONOR\" TEXT," + // 20: isdonor
                "\"ISRIJAN\" TEXT);"); // 21: isrijan
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_LOGIN_INFO\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserLoginInfo entity) {
        stmt.clearBindings();

        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }

        String empno = entity.getEmpno();
        if (empno != null) {
            stmt.bindString(2, empno);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }

        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }

        String dob = entity.getDob();
        if (dob != null) {
            stmt.bindString(5, dob);
        }

        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(6, location);
        }

        String role = entity.getRole();
        if (role != null) {
            stmt.bindString(7, role);
        }

        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(8, department);
        }

        String designation = entity.getDesignation();
        if (designation != null) {
            stmt.bindString(9, designation);
        }

        String quote = entity.getQuote();
        if (quote != null) {
            stmt.bindString(10, quote);
        }

        String hobbies = entity.getHobbies();
        if (hobbies != null) {
            stmt.bindString(11, hobbies);
        }

        String imageurl = entity.getImageurl();
        if (imageurl != null) {
            stmt.bindString(12, imageurl);
        }

        String company = entity.getCompany();
        if (company != null) {
            stmt.bindString(13, company);
        }

        String experience = entity.getExperience();
        if (experience != null) {
            stmt.bindString(14, experience);
        }

        String is_admin = entity.getIs_admin();
        if (is_admin != null) {
            stmt.bindString(15, is_admin);
        }

        String emailId = entity.getEmailId();
        if (emailId != null) {
            stmt.bindString(16, emailId);
        }

        String reg_mngr = entity.getReg_mngr();
        if (reg_mngr != null) {
            stmt.bindString(17, reg_mngr);
        }

        String post_xpress = entity.getPost_xpress();
        if (post_xpress != null) {
            stmt.bindString(18, post_xpress);
        }

        String rrecognition = entity.getRrecognition();
        if (rrecognition != null) {
            stmt.bindString(19, rrecognition);
        }

        String grecognition = entity.getGrecognition();
        if (grecognition != null) {
            stmt.bindString(20, grecognition);
        }

        String isdonor = entity.getIsdonor();
        if (isdonor != null) {
            stmt.bindString(21, isdonor);
        }

        String isrijan = entity.getIsrijan();
        if (isrijan != null) {
            stmt.bindString(22, isrijan);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public UserLoginInfo readEntity(Cursor cursor, int offset) {
        UserLoginInfo entity = new UserLoginInfo( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userid
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // empno
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // username
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // dob
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // location
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // role
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // department
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // designation
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // quote
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // hobbies
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // imageurl
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // company
                cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // experience
                cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // is_admin
                cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // emailId
                cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // reg_mngr
                cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // post_xpress
                cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // rrecognition
                cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // grecognition
                cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // isdonor
                cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21) // isrijan
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, UserLoginInfo entity, int offset) {
        entity.setUserid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEmpno(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUsername(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDob(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocation(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRole(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDepartment(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDesignation(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setQuote(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setHobbies(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setImageurl(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCompany(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setExperience(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIs_admin(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setEmailId(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setReg_mngr(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setPost_xpress(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setRrecognition(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setGrecognition(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setIsdonor(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setIsrijan(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected String updateKeyAfterInsert(UserLoginInfo entity, long rowId) {
        return entity.getUserid();
    }

    /**
     * @inheritdoc
     */
    @Override
    public String getKey(UserLoginInfo entity) {
        if (entity != null) {
            return entity.getUserid();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
