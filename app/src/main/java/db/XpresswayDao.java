package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "XPRESSWAY".
 */
public class XpresswayDao extends AbstractDao<Xpressway, Void> {

    public static final String TABLENAME = "XPRESSWAY";

    /**
     * Properties of entity Xpressway.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Recognition_id = new Property(0, String.class, "recognition_id", false, "RECOGNITION_ID");
        public final static Property Nominator_name = new Property(1, String.class, "nominator_name", false, "NOMINATOR_NAME");
        public final static Property Subject = new Property(2, String.class, "subject", false, "SUBJECT");
        public final static Property Isstory = new Property(3, String.class, "isstory", false, "ISSTORY");
        public final static Property Nominee = new Property(4, String.class, "nominee", false, "NOMINEE");
        public final static Property Other = new Property(5, String.class, "other", false, "OTHER");
        public final static Property Award = new Property(6, String.class, "award", false, "AWARD");
        public final static Property Recognise_date = new Property(7, String.class, "recognise_date", false, "RECOGNISE_DATE");
        public final static Property Image_url = new Property(8, String.class, "image_url", false, "IMAGE_URL");
    }

    ;


    public XpresswayDao(DaoConfig config) {
        super(config);
    }

    public XpresswayDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"XPRESSWAY\" (" + //
                "\"RECOGNITION_ID\" TEXT," + // 0: recognition_id
                "\"NOMINATOR_NAME\" TEXT," + // 1: nominator_name
                "\"SUBJECT\" TEXT," + // 2: subject
                "\"ISSTORY\" TEXT," + // 3: isstory
                "\"NOMINEE\" TEXT," + // 4: nominee
                "\"OTHER\" TEXT," + // 5: other
                "\"AWARD\" TEXT," + // 6: award
                "\"RECOGNISE_DATE\" TEXT," + // 7: recognise_date
                "\"IMAGE_URL\" TEXT);"); // 8: image_url
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"XPRESSWAY\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Xpressway entity) {
        stmt.clearBindings();

        String recognition_id = entity.getRecognition_id();
        if (recognition_id != null) {
            stmt.bindString(1, recognition_id);
        }

        String nominator_name = entity.getNominator_name();
        if (nominator_name != null) {
            stmt.bindString(2, nominator_name);
        }

        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(3, subject);
        }

        String isstory = entity.getIsstory();
        if (isstory != null) {
            stmt.bindString(4, isstory);
        }

        String nominee = entity.getNominee();
        if (nominee != null) {
            stmt.bindString(5, nominee);
        }

        String other = entity.getOther();
        if (other != null) {
            stmt.bindString(6, other);
        }

        String award = entity.getAward();
        if (award != null) {
            stmt.bindString(7, award);
        }

        String recognise_date = entity.getRecognise_date();
        if (recognise_date != null) {
            stmt.bindString(8, recognise_date);
        }

        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(9, image_url);
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
    public Xpressway readEntity(Cursor cursor, int offset) {
        Xpressway entity = new Xpressway( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // recognition_id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nominator_name
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // subject
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // isstory
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nominee
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // other
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // award
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // recognise_date
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // image_url
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Xpressway entity, int offset) {
        entity.setRecognition_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNominator_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSubject(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsstory(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNominee(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOther(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAward(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRecognise_date(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setImage_url(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(Xpressway entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(Xpressway entity) {
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
