package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "EXPOSSOR".
 */
public class ExpossorDao extends AbstractDao<Expossor, Void> {

    public static final String TABLENAME = "EXPOSSOR";

    /**
     * Properties of entity Expossor.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Sno = new Property(0, String.class, "sno", false, "SNO");
        public final static Property Xpressor_id = new Property(1, String.class, "xpressor_id", false, "XPRESSOR_ID");
        public final static Property Xpressor_text = new Property(2, String.class, "xpressor_text", false, "XPRESSOR_TEXT");
        public final static Property Xpressor_rmtxt = new Property(3, String.class, "xpressor_rmtxt", false, "XPRESSOR_RMTXT");
        public final static Property AwardID = new Property(4, String.class, "awardID", false, "AWARD_ID");
    }

    ;

    private Query<Expossor> award_ExpossorListQuery;

    public ExpossorDao(DaoConfig config) {
        super(config);
    }

    public ExpossorDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"EXPOSSOR\" (" + //
                "\"SNO\" TEXT," + // 0: sno
                "\"XPRESSOR_ID\" TEXT," + // 1: xpressor_id
                "\"XPRESSOR_TEXT\" TEXT," + // 2: xpressor_text
                "\"XPRESSOR_RMTXT\" TEXT," + // 3: xpressor_rmtxt
                "\"AWARD_ID\" TEXT NOT NULL );"); // 4: awardID
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EXPOSSOR\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Expossor entity) {
        stmt.clearBindings();

        String sno = entity.getSno();
        if (sno != null) {
            stmt.bindString(1, sno);
        }

        String xpressor_id = entity.getXpressor_id();
        if (xpressor_id != null) {
            stmt.bindString(2, xpressor_id);
        }

        String xpressor_text = entity.getXpressor_text();
        if (xpressor_text != null) {
            stmt.bindString(3, xpressor_text);
        }

        String xpressor_rmtxt = entity.getXpressor_rmtxt();
        if (xpressor_rmtxt != null) {
            stmt.bindString(4, xpressor_rmtxt);
        }
        stmt.bindString(5, entity.getAwardID());
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
    public Expossor readEntity(Cursor cursor, int offset) {
        Expossor entity = new Expossor( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sno
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // xpressor_id
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // xpressor_text
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // xpressor_rmtxt
                cursor.getString(offset + 4) // awardID
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Expossor entity, int offset) {
        entity.setSno(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setXpressor_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setXpressor_text(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setXpressor_rmtxt(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAwardID(cursor.getString(offset + 4));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(Expossor entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(Expossor entity) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Internal query to resolve the "expossorList" to-many relationship of Award.
     */
    public List<Expossor> _queryAward_ExpossorList(String awardID) {
        synchronized (this) {
            if (award_ExpossorListQuery == null) {
                QueryBuilder<Expossor> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.AwardID.eq(null));
                award_ExpossorListQuery = queryBuilder.build();
            }
        }
        Query<Expossor> query = award_ExpossorListQuery.forCurrentThread();
        query.setParameter(0, awardID);
        return query.list();
    }

}
