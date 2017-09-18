package hr.i2petrovicetfos.letsbarbecue;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    //imena polja
    public static final String ID = "_id";
    public static final String VRSTA_ITEMA = "item";
    public static final String CIJENA_ITEMA = "cijena";
    public static final String JEDINICA = "jedinica";
    public static final String KOLICINA = "kolicina";

    public static final String[] SVI_PODACI = new String[]{ID, VRSTA_ITEMA, CIJENA_ITEMA, JEDINICA, KOLICINA};

    //podaci o bazi podataka
    public static final String DATABASE_NAME = "dbLetsBarbecue.db";
    public static final String TABLE_NAME = "dogadaj";
    public static final int DATABASE_VERSION = 8;


    //SQL naredbe za kreiranje baze podataka
    public static final String CREATE_SQL =
            "CREATE TABLE " + TABLE_NAME
            + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VRSTA_ITEMA + " TEXT, "
            + CIJENA_ITEMA + " REAL, "
            + JEDINICA + " TEXT, "
            + KOLICINA + " INTEGER"
            + ");";



    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        myDBHelper = new DatabaseHelper(context);
    }

    //otvori konekciju sa bazom
    public DBAdapter open(){
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    //zatvori konekciju sa bazom

    public void close(){
        myDBHelper.close();
    }

    //dodaj novi set vrijednosti koji se trebac ubaciti u bazu
    public long insertRow(String vrstaItema, String cijenaItema, String jedinica, String kolicina ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(VRSTA_ITEMA,vrstaItema);
        initialValues.put(CIJENA_ITEMA,cijenaItema);
        initialValues.put(JEDINICA,jedinica);
        initialValues.put(KOLICINA,kolicina);

        //unesi vrijednosti u bazu podataka
        return db.insert(TABLE_NAME,null,initialValues);
    }

    public float getCalculated(){
        float total = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + CIJENA_ITEMA + " * " + KOLICINA + ") FROM " + TABLE_NAME,null);
        if(cursor.moveToFirst()){
            total = cursor.getFloat(0);
        }
        return total;
    }


    //izbrisi red iz baze po ID-u
    public boolean deleteRow(long id){
        String where = ID + "=" + id;
        return db.delete(TABLE_NAME, where, null)!=0;
    }

    public void deleteAll(){
        Cursor cursor = getAllRows();
        long id = cursor.getColumnIndexOrThrow(ID);
        if (cursor.moveToFirst()){
            do{
                deleteRow(cursor.getLong((int)id));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    //vrati sve podatke iz baze
    public Cursor getAllRows(){
        String where = null;
        Cursor cursor = db.query(true,TABLE_NAME, SVI_PODACI, where, null,null,null,null,null);
        if(cursor!= null){
            cursor.moveToFirst();
        }
        return cursor;
    }




    public class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SQL);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        }
    }
}
