package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "SRIJAN_TOPCOMMENT".
 */
public class SrijanTOPCommentDao extends AbstractDao<SrijanTOPComment, Void> {

    public static final String TABLENAME = "SRIJAN_TOPCOMMENT";

    /**
     * Properties of entity SrijanTOPComment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Policyid = new Property(0, String.class, "policyid", false, "POLICYID");
        public final static Property Policyname = new Property(1, String.class, "policyname", false, "POLICYNAME");
        public final static Property Policynamedisplayvalue = new Property(2, String.class, "policynamedisplayvalue", false, "POLICYNAMEDISPLAYVALUE");
        public final static Property Usercommentdisplayvalue = new Property(3, String.class, "usercommentdisplayvalue", false, "USERCOMMENTDISPLAYVALUE");
        public final static Property Commentdatedisplayvalue = new Property(4, String.class, "commentdatedisplayvalue", false, "COMMENTDATEDISPLAYVALUE");
        public final static Property Commenttimedisplayvalue = new Property(5, String.class, "commenttimedisplayvalue", false, "COMMENTTIMEDISPLAYVALUE");
        public final static Property Commentuserid = new Property(6, String.class, "commentuserid", false, "COMMENTUSERID");
        public final static Property Commentusername = new Property(7, String.class, "commentusername", false, "COMMENTUSERNAME");
        public final static Property Commentuserdepartment = new Property(8, String.class, "commentuserdepartment", false, "COMMENTUSERDEPARTMENT");
        public final static Property Commentuserprofilepic = new Property(9, String.class, "commentuserprofilepic", false, "COMMENTUSERPROFILEPIC");
        public final static Property Commentlevel = new Property(10, String.class, "commentlevel", false, "COMMENTLEVEL");
        public final static Property Parentcommentid = new Property(11, String.class, "parentcommentid", false, "PARENTCOMMENTID");
        public final static Property Usercomment = new Property(12, String.class, "usercomment", false, "USERCOMMENT");
        public final static Property Taguser = new Property(13, String.class, "taguser", false, "TAGUSER");
        public final static Property Childcommentid = new Property(14, String.class, "childcommentid", false, "CHILDCOMMENTID");
    }

    ;


    public SrijanTOPCommentDao(DaoConfig config) {
        super(config);
    }

    public SrijanTOPCommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"SRIJAN_TOPCOMMENT\" (" + //
                "\"POLICYID\" TEXT," + // 0: policyid
                "\"POLICYNAME\" TEXT," + // 1: policyname
                "\"POLICYNAMEDISPLAYVALUE\" TEXT," + // 2: policynamedisplayvalue
                "\"USERCOMMENTDISPLAYVALUE\" TEXT," + // 3: usercommentdisplayvalue
                "\"COMMENTDATEDISPLAYVALUE\" TEXT," + // 4: commentdatedisplayvalue
                "\"COMMENTTIMEDISPLAYVALUE\" TEXT," + // 5: commenttimedisplayvalue
                "\"COMMENTUSERID\" TEXT," + // 6: commentuserid
                "\"COMMENTUSERNAME\" TEXT," + // 7: commentusername
                "\"COMMENTUSERDEPARTMENT\" TEXT," + // 8: commentuserdepartment
                "\"COMMENTUSERPROFILEPIC\" TEXT," + // 9: commentuserprofilepic
                "\"COMMENTLEVEL\" TEXT," + // 10: commentlevel
                "\"PARENTCOMMENTID\" TEXT," + // 11: parentcommentid
                "\"USERCOMMENT\" TEXT," + // 12: usercomment
                "\"TAGUSER\" TEXT," + // 13: taguser
                "\"CHILDCOMMENTID\" TEXT);"); // 14: childcommentid
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SRIJAN_TOPCOMMENT\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, SrijanTOPComment entity) {
        stmt.clearBindings();

        String policyid = entity.getPolicyid();
        if (policyid != null) {
            stmt.bindString(1, policyid);
        }

        String policyname = entity.getPolicyname();
        if (policyname != null) {
            stmt.bindString(2, policyname);
        }

        String policynamedisplayvalue = entity.getPolicynamedisplayvalue();
        if (policynamedisplayvalue != null) {
            stmt.bindString(3, policynamedisplayvalue);
        }

        String usercommentdisplayvalue = entity.getUsercommentdisplayvalue();
        if (usercommentdisplayvalue != null) {
            stmt.bindString(4, usercommentdisplayvalue);
        }

        String commentdatedisplayvalue = entity.getCommentdatedisplayvalue();
        if (commentdatedisplayvalue != null) {
            stmt.bindString(5, commentdatedisplayvalue);
        }

        String commenttimedisplayvalue = entity.getCommenttimedisplayvalue();
        if (commenttimedisplayvalue != null) {
            stmt.bindString(6, commenttimedisplayvalue);
        }

        String commentuserid = entity.getCommentuserid();
        if (commentuserid != null) {
            stmt.bindString(7, commentuserid);
        }

        String commentusername = entity.getCommentusername();
        if (commentusername != null) {
            stmt.bindString(8, commentusername);
        }

        String commentuserdepartment = entity.getCommentuserdepartment();
        if (commentuserdepartment != null) {
            stmt.bindString(9, commentuserdepartment);
        }

        String commentuserprofilepic = entity.getCommentuserprofilepic();
        if (commentuserprofilepic != null) {
            stmt.bindString(10, commentuserprofilepic);
        }

        String commentlevel = entity.getCommentlevel();
        if (commentlevel != null) {
            stmt.bindString(11, commentlevel);
        }

        String parentcommentid = entity.getParentcommentid();
        if (parentcommentid != null) {
            stmt.bindString(12, parentcommentid);
        }

        String usercomment = entity.getUsercomment();
        if (usercomment != null) {
            stmt.bindString(13, usercomment);
        }

        String taguser = entity.getTaguser();
        if (taguser != null) {
            stmt.bindString(14, taguser);
        }

        String childcommentid = entity.getChildcommentid();
        if (childcommentid != null) {
            stmt.bindString(15, childcommentid);
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
    public SrijanTOPComment readEntity(Cursor cursor, int offset) {
        SrijanTOPComment entity = new SrijanTOPComment( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // policyid
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // policyname
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // policynamedisplayvalue
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // usercommentdisplayvalue
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // commentdatedisplayvalue
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // commenttimedisplayvalue
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // commentuserid
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // commentusername
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // commentuserdepartment
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // commentuserprofilepic
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // commentlevel
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // parentcommentid
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // usercomment
                cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // taguser
                cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // childcommentid
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, SrijanTOPComment entity, int offset) {
        entity.setPolicyid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPolicyname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPolicynamedisplayvalue(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUsercommentdisplayvalue(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCommentdatedisplayvalue(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCommenttimedisplayvalue(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCommentuserid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCommentusername(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCommentuserdepartment(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCommentuserprofilepic(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCommentlevel(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setParentcommentid(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUsercomment(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTaguser(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setChildcommentid(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(SrijanTOPComment entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(SrijanTOPComment entity) {
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
