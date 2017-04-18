package game.fastgenius;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class databaza extends SQLiteOpenHelper{


    public databaza(Context context)
    {
        super(context,Parameters.Databaza, null, Parameters.Versioni); //versioni eshte 1 per insert, 2 per update
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create Table "+Parameters.TabelaPerdoruesi+"("+Parameters.PerdoruesiID+" Integer not null PRIMARY KEY,"+
                Parameters.vcEmri+" varchar(20),"+Parameters.vcRekordi+" varchar(30))");

        //ContentValues cv = new ContentValues();

      //  cv.put(Parameters.vcEmri,"Filan Fisteku");
       // cv.put(Parameters.vcRekordi,"29");
       // long affectedRows =db.insert(Parameters.TabelaPerdoruesi,null,cv);
       // Log.i("AffectedRows",affectedRows+"");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* ContentValues cv = new ContentValues();

        cv.put(Parameters.vcEmri,"Arton Hoti");
        cv.put(Parameters.vcRekordi,"19");
        long affectedRows =db.insert(Parameters.TabelaPerdoruesi,null,cv);
        Log.i("AffectedRows",affectedRows+"");*/
    }
}
