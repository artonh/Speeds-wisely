package game.fastgenius;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class hyrja extends AppCompatActivity {

    Button btnHap;
    TextView txtrekordi, rekordiPiket, rekordiUser, tvFshijDB;
    EditText etEmri;
    TableLayout tlPjesaRekordeve;
    String strUser = "", strPiket = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyrja);
        btnHap = (Button)findViewById(R.id.btnHapLojen);

        etEmri = (EditText) findViewById(R.id.user);
        txtrekordi=(TextView)findViewById(R.id.lblRekordi);
        rekordiPiket=(TextView)findViewById(R.id.rekordiPiket);
        rekordiUser=(TextView)findViewById(R.id.rekordiUser);
        tlPjesaRekordeve=(TableLayout) findViewById(R.id.tlPjesaRekordeve);
        tvFshijDB=(TextView)findViewById(R.id.tvFshijDB);


        btnHap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(etEmri.getText().toString().length() > 0){
                Intent HapLojen = new Intent(hyrja.this, MainActivity.class);
                Parameters.emri = etEmri.getText().toString();
                startActivity(HapLojen);
            }
            else{
                Toast.makeText(getApplicationContext(),"Shkruani emrin tuaj", Toast.LENGTH_LONG).show();
            }
            }
        });

        txtrekordi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectNeDB("SELECT * FROM "+Parameters.TabelaPerdoruesi + " order by "+Parameters.vcRekordi);//slecton dhe mbush tabelen ton
                tlPjesaRekordeve.setVisibility(View.VISIBLE); //per tu shfaqur kjo tabel
            }
        });

        tvFshijDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FshijTabelen(Parameters.TabelaPerdoruesi);
                SelectNeDB("SELECT * FROM "+Parameters.TabelaPerdoruesi + " order by "+Parameters.vcRekordi);

                tlPjesaRekordeve.setVisibility(View.VISIBLE); //per tu shfaqur kjo tabel
            }
        });
    }
    public void SelectNeDB(String query){   //kjo metod selecon ne DB me kete query dhe shfaq rezultatin ne TextView e percaktuara
        strUser = ""; strPiket = "";
        databaza objDatabaza = new databaza(hyrja.this);
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
        databaza objDatabaza = new databaza(hyrja.this);
        SQLiteDatabase db=objDatabaza.getWritableDatabase();

        db.delete(emri,null,null);
        db.close();
    }
}
