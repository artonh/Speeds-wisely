package game.fastgenius;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;

public class HighScore extends AppCompatActivity {
    TextView rekordiPiket, rekordiUser,tvFshijDB;
    String strUser = "", strPiket = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        rekordiPiket=(TextView)findViewById(R.id.rekordiPiket);
        rekordiUser=(TextView)findViewById(R.id.rekordiUser);
        tvFshijDB=(TextView)findViewById(R.id.tvFshijDB);

        SelectNeDB("SELECT * FROM "+Parameters.TabelaPerdoruesi + " order by "+Parameters.vcRekordi);//slecton dhe mbush tabelen ton

        tvFshijDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FshijTabelen(Parameters.TabelaPerdoruesi);
                SelectNeDB("SELECT * FROM "+Parameters.TabelaPerdoruesi + " order by "+Parameters.vcRekordi);
            }
        });
    }

    public void SelectNeDB(String query){   //kjo metod selecon ne DB me kete query dhe shfaq rezultatin ne TextView e percaktuara
        strUser = ""; strPiket = "";
        databaza objDatabaza = new databaza(HighScore.this);
        SQLiteDatabase db=objDatabaza.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            strUser += cursor.getString(1) +"\n";
            strPiket += cursor.getString(2) + " sec\n";
            cursor.moveToNext();
        }
        Log.i("strUser",strUser);
        Log.i("strPiket",strPiket);
        rekordiUser.setText(strUser);
        rekordiPiket.setText(strPiket);
    }

    public void FshijTabelen(String emri){ //ky funksion fshin brenda databazes tone, at tabel qe e percaktojme
        databaza objDatabaza = new databaza(HighScore.this);
        SQLiteDatabase db=objDatabaza.getWritableDatabase();

        db.delete(emri,null,null);
        db.close();
    }
}
