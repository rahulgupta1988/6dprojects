package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALL_USERS".
*/
public class AllUsersDao extends AbstractDao<AllUsers, Void> {

    public static final String TABLENAME = "ALL_USERS";

    /**
     * Properties of entity AllUsers.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property Department = new Property(1, String.class, "department", false, "DEPARTMENT");
        public final static Property Designation = new Property(2, String.class, "designation", false, "DESIGNATION");
        public final static Property User_id = new Property(3, String.class, "user_id", false, "USER_ID");
        public final static Property Email = new Property(4, String.class, "email", false, "EMAIL");
        public final static Property Location = new Property(5, String.class, "location", false, "LOCATION");
        public final static Property Imageurl = new Property(6, String.class, "imageurl", false, "IMAGEURL");
        public final static Property CoiMisAdmin = new Property(7, String.class, "coiMisAdmin", false, "COI_MIS_ADMIN");
        public final static Property Mxway = new Property(8, String.class, "mxway", false, "MXWAY");
    };


    public AllUsersDao(DaoConfig config) {
        super(config);
    }
    
    public AllUsersDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_USERS\" (" + //
                "\"NAME\" TEXT," + // 0: name
                "\"DEPARTMENT\" TEXT," + // 1: department
                "\"DESIGNATION\" TEXT," + // 2: designation
                "\"USER_ID\" TEXT," + // 3: user_id
                "\"EMAIL\" TEXT," + // 4: email
                "\"LOCATION\" TEXT," + // 5: location
                "\"IMAGEURL\" TEXT," + // 6: imageurl
                "\"COI_MIS_ADMIN\" TEXT," + // 7: coiMisAdmin
                "\"MXWAY\" TEXT);"); // 8: mxway
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_USERS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AllUsers entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(2, department);
        }
 
        String designation = entity.getDesignation();
        if (designation != null) {
            stmt.bindString(3, designation);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(4, user_id);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(5, email);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(6, location);
        }
 
        String imageurl = entity.getImageurl();
        if (imageurl != null) {
            stmt.bindString(7, imageurl);
        }
 
        String coiMisAdmin = entity.getCoiMisAdmin();
        if (coiMisAdmin != null) {
            stmt.bindString(8, coiMisAdmin);
        }
 
        String mxway = entity.getMxway();
        if (mxway != null) {
            stmt.bindString(9, mxway);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public AllUsers readEntity(Cursor cursor, int offset) {
        AllUsers entity = new AllUsers( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // department
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // designation
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // user_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // email
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // location
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // imageurl
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // coiMisAdmin
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // mxway
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AllUsers entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDepartment(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDesignation(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUser_id(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEmail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocation(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImageurl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCoiMisAdmin(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMxway(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(AllUsers entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(AllUsers entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
