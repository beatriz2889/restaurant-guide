package ca.gbc.comp3074.personalrestaurantguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private static final String DATABASE_NAME="entry";
    public static final String TABLE_ENTRIES="entries";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_ADDRESS="address";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_RATING="rating";
    public static final String COLUMN_TAGS="tags";
    public SqliteDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String	CREATE_ENTRIES_TABLE = "CREATE	TABLE " + TABLE_ENTRIES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_ADDRESS + " TEXT,"  + COLUMN_PHONE + " TEXT," + COLUMN_DESCRIPTION +" TEXT,"+ COLUMN_RATING +" TEXT," + COLUMN_TAGS +" TEXT" +")";
        db.execSQL(CREATE_ENTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENTRIES);
        onCreate(db);
    }

    public ArrayList<Entries>listEntries(){
        String sql="select * from " + TABLE_ENTRIES;
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Entries> storeEntries=new ArrayList<>();
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){

                int id=Integer.parseInt(cursor.getString(0));
                String name=cursor.getString(1);
                String address=cursor.getString(2);
                String phone=cursor.getString(3);
                String description=cursor.getString(4);
                String rating=cursor.getString(5);
                String tags=cursor.getString(6);
                storeEntries.add(new Entries(id,name,address,phone,description,rating,tags));

            }
        cursor.close();
        System.out.println("store entries list:"+storeEntries);
        return storeEntries;
        }


    public void addEntries(Entries entries) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, entries.getName());
        values.put(COLUMN_ADDRESS, entries.getAddress());
        values.put(COLUMN_PHONE, entries.getPhone());
        values.put(COLUMN_DESCRIPTION, entries.getDescription());
        values.put(COLUMN_RATING, entries.getRating());
        values.put(COLUMN_TAGS, entries.getTags());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ENTRIES, null, values);
    }

    public void updateEntries(Entries entries){
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,entries.getName());
        values.put(COLUMN_ADDRESS,entries.getAddress());
        values.put(COLUMN_PHONE,entries.getPhone());
        values.put(COLUMN_DESCRIPTION,entries.getDescription());
        values.put(COLUMN_RATING,entries.getRating());
        values.put(COLUMN_TAGS,entries.getTags());
        SQLiteDatabase db=this.getWritableDatabase();
        db.update(TABLE_ENTRIES,values,COLUMN_ID	+ "	= ?", new String[] { String.valueOf(entries.getId())});
    }

    public Entries findEntries(String name){
        String query="Select * FROM "	+ TABLE_ENTRIES + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db=this.getWritableDatabase();
        Entries entries=null;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            int id=Integer.parseInt(cursor.getString(0));
            String entriesName=cursor.getString(1);
            String entriesAddress=cursor.getString(2);
            String entriesPhone=cursor.getString(3);
            String entriesDescription=cursor.getString(4);
            String entriesRating=cursor.getString(5);
            String entriesTags=cursor.getString(6);
            entries=new Entries(id,entriesName,entriesAddress,entriesPhone,entriesDescription,entriesRating,entriesTags);
        }
        cursor.close();
        return entries;
    }

    public void deleteEntry(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_ENTRIES, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
