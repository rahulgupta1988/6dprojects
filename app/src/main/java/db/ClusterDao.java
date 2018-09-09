package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CLUSTER".
 */
public class ClusterDao extends AbstractDao<Cluster, Void> {

    public static final String TABLENAME = "CLUSTER";

    /**
     * Properties of entity Cluster.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Clusterid = new Property(0, String.class, "clusterid", false, "CLUSTERID");
        public final static Property Clustername = new Property(1, String.class, "clustername", false, "CLUSTERNAME");
    }

    ;


    public ClusterDao(DaoConfig config) {
        super(config);
    }

    public ClusterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"CLUSTER\" (" + //
                "\"CLUSTERID\" TEXT," + // 0: clusterid
                "\"CLUSTERNAME\" TEXT);"); // 1: clustername
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CLUSTER\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Cluster entity) {
        stmt.clearBindings();

        String clusterid = entity.getClusterid();
        if (clusterid != null) {
            stmt.bindString(1, clusterid);
        }

        String clustername = entity.getClustername();
        if (clustername != null) {
            stmt.bindString(2, clustername);
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
    public Cluster readEntity(Cursor cursor, int offset) {
        Cluster entity = new Cluster( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // clusterid
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // clustername
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Cluster entity, int offset) {
        entity.setClusterid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setClustername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(Cluster entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(Cluster entity) {
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
