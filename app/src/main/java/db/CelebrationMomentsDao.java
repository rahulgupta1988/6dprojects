package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CELEBRATION_MOMENTS".
 */
public class CelebrationMomentsDao extends AbstractDao<CelebrationMoments, Void> {

    public static final String TABLENAME = "CELEBRATION_MOMENTS";

    /**
     * Properties of entity CelebrationMoments.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Userid = new Property(0, String.class, "userid", false, "USERID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Eventmaster_id = new Property(2, String.class, "eventmaster_id", false, "EVENTMASTER_ID");
        public final static Property Event_id = new Property(3, String.class, "event_id", false, "EVENT_ID");
        public final static Property Department = new Property(4, String.class, "department", false, "DEPARTMENT");
        public final static Property Event_desc = new Property(5, String.class, "event_desc", false, "EVENT_DESC");
        public final static Property Work_year = new Property(6, String.class, "work_year", false, "WORK_YEAR");
        public final static Property Imageurl = new Property(7, String.class, "imageurl", false, "IMAGEURL");
        public final static Property EmailId = new Property(8, String.class, "emailId", false, "EMAIL_ID");
        public final static Property Title = new Property(9, String.class, "title", false, "TITLE");
    }

    ;


    public CelebrationMomentsDao(DaoConfig config) {
        super(config);
    }

    public CelebrationMomentsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"CELEBRATION_MOMENTS\" (" + //
                "\"USERID\" TEXT," + // 0: userid
                "\"NAME\" TEXT," + // 1: name
                "\"EVENTMASTER_ID\" TEXT," + // 2: eventmaster_id
                "\"EVENT_ID\" TEXT," + // 3: event_id
                "\"DEPARTMENT\" TEXT," + // 4: department
                "\"EVENT_DESC\" TEXT," + // 5: event_desc
                "\"WORK_YEAR\" TEXT," + // 6: work_year
                "\"IMAGEURL\" TEXT," + // 7: imageurl
                "\"EMAIL_ID\" TEXT," + // 8: emailId
                "\"TITLE\" TEXT);"); // 9: title
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CELEBRATION_MOMENTS\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, CelebrationMoments entity) {
        stmt.clearBindings();

        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }

        String eventmaster_id = entity.getEventmaster_id();
        if (eventmaster_id != null) {
            stmt.bindString(3, eventmaster_id);
        }

        String event_id = entity.getEvent_id();
        if (event_id != null) {
            stmt.bindString(4, event_id);
        }

        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(5, department);
        }

        String event_desc = entity.getEvent_desc();
        if (event_desc != null) {
            stmt.bindString(6, event_desc);
        }

        String work_year = entity.getWork_year();
        if (work_year != null) {
            stmt.bindString(7, work_year);
        }

        String imageurl = entity.getImageurl();
        if (imageurl != null) {
            stmt.bindString(8, imageurl);
        }

        String emailId = entity.getEmailId();
        if (emailId != null) {
            stmt.bindString(9, emailId);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(10, title);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public CelebrationMoments readEntity(Cursor cursor, int offset) {
        CelebrationMoments entity = new CelebrationMoments( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userid
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // eventmaster_id
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // event_id
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // department
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // event_desc
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // work_year
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // imageurl
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // emailId
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // title
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, CelebrationMoments entity, int offset) {
        entity.setUserid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEventmaster_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEvent_id(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDepartment(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEvent_desc(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWork_year(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setImageurl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setEmailId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTitle(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(CelebrationMoments entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(CelebrationMoments entity) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
