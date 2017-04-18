package game.fastgenius;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView txtKoha, txTimeri, txtPerfundimi;
    Button btnFillo, btn1, btn2, btn3, btn4, btn5, btn6;
    ImageView imageView1;
    String nrRadhes;
    long kohafillimit, kohaPerfundimit, koha, kohaMomentale,kohaH;
    double koha_ne_Sekond;
    int koha_ne_minut=0, koha_ne_sec=0;
    Timer tmr; //per cdo tick me shkrujt ne txView
    DecimalFormat dec = new DecimalFormat("#");
    boolean hera=false;
    String[] strIDuhur = new String[6];
    List<String> list2;
    int rasti=1;

    public void GjeneroRastesite(){
        String[] strlejuar = new String[]{"1","2","3","4","5","6","7","8","9"}; //nga ky string lejohet te formohet vargu prej 6nr
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, strlejuar);

        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            String c1 =  list.get(random1.nextInt(list.size()));  //merr rastesisht brenda listes
            strIDuhur[i] = c1; //mbushet ky varg[i] me keto elemente te lejuara
            list.remove(c1);//mos me hy ma ne ata numra qe ka kalu
        }
        /*String a="";
        for (int i=0; i<6;i++)
        {            a += strIDuhur[i];        }
        Log.i("stringu random:",""+a);*/

        list2 = new ArrayList<String>();
        Collections.addAll(list2, strIDuhur);

        btn1.setText(strIDuhur[0]);
        btn2.setText(strIDuhur[1]);
        btn3.setText(strIDuhur[2]);
        btn4.setText(strIDuhur[3]);
        btn5.setText(strIDuhur[4]);
        btn6.setText(strIDuhur[5]);
    }
    public String Minimumi(){
        int minIndex = list2.indexOf(Collections.min(list2)); //Gjen indeksin e numrit me te vogel
        String c2 =  list2.get(minIndex);  //merr vleren e atij indeksi (pra numrin Minimal)
        list2.remove(c2);//largon nga lista kete numer minimal
        /* String b="";
        Log.i("nrMinimal:",""+c2);
        for (int i=0; i<list2.size();i++)
        {            b += list2.get(i);        }
        Log.i("str PanumerMinimal:",""+b);*/
        return c2;
    }
    public boolean AeshteNumriiFundit(){
        boolean x=false;
        if(list2.size()==1) x = true;   //nese ne list ka mbutr veq 1 anetar
        return x;
    }
    public void ruajNeDB(String emri, String rekodi){ //rekordiTani > rekordiMeHeret ? ruajneDB,""
        databaza objDatabaza = new databaza(MainActivity.this);
        SQLiteDatabase db=objDatabaza.getWritableDatabase();
/*
        Cursor cursor = null;
        String sql ="SELECT * FROM "+Parameters.TabelaPerdoruesi+" WHERE "+Parameters.vcEmri+"='"+emri+"'";
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0)//krahaso nese eshte me i madh rekordi nese po inserto nese jo JO
        {Log.i("RRESHTAT",cursor.getCount()+"");
            String DBrek="", id=""; String[] vgkoha, DBvgKoha;
            DBrek = cursor.getString(2);
            id=cursor.getString(0);
            vgkoha=rekodi.split(":");
            DBvgKoha=DBrek.split(":");

            int nrmin, DBnrmin;
            double nrsec, DBnrsec;
            nrmin=Integer.parseInt(vgkoha[0]);
            nrsec=Double.parseDouble(vgkoha[1]);

            DBnrmin=Integer.parseInt(DBvgKoha[0]);
            DBnrsec=Double.parseDouble(DBvgKoha[1]);
            Log.i("Id e lojtarit",id+"");
            Log.i("DB rekordi",DBrek+"");
            Log.i("nrMinutave",nrmin+"");
            Log.i("DBnrMinutave",DBnrmin+"");
            Log.i("nrSekodnd",nrsec+"");
            Log.i("DBnrsec",DBnrsec+"");
            if (nrmin < DBnrmin)
            {//upd
                ContentValues cvup = new ContentValues();
                cvup.put(Parameters.vcRekordi,rekodi);
                db.update(Parameters.TabelaPerdoruesi, cvup, Parameters.PerdoruesiID+id, null);
            }
            else if(nrmin == DBnrmin)
            {
                if(nrsec < DBnrsec)
                {//update ne db
                    ContentValues cvup = new ContentValues();
                    cvup.put(Parameters.vcRekordi,rekodi);
                    db.update(Parameters.TabelaPerdoruesi, cvup, Parameters.PerdoruesiID+id, null);
                }
            }
        }

        else
        {//Ky perdorues nuk ekziston, e shtojme
*/
            ContentValues cv = new ContentValues();
            cv.put(Parameters.vcEmri,emri);
            cv.put(Parameters.vcRekordi,rekodi);

            long affectedRows = db.insert(Parameters.TabelaPerdoruesi, null, cv);
            Log.i("AffectedRows",affectedRows+"");
        //}
    }

    public String Koha(long kohamoment){
        koha = kohamoment - kohafillimit ;
        koha_ne_Sekond = koha / 1000.0;
        koha_ne_Sekond=Double.valueOf(dec.format(koha_ne_Sekond));
        if(koha_ne_Sekond >59)
        {
            koha_ne_minut +=1;
            koha_ne_Sekond=0;
        }
        return ""+koha_ne_minut+":"+koha_ne_Sekond;
    }

    public void startoTimerin(){
        //Declare the timer
        tmr = new Timer();
        //Set the schedule function and rate
        tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kohaMomentale= SystemClock.elapsedRealtime();
                        kohaH=kohaMomentale-kohafillimit;
                        koha_ne_Sekond=kohaH / 1000.0;
                        if(koha_ne_Sekond >59)
                        {
                            koha_ne_minut +=1;
                            koha_ne_Sekond=0;
                        }
                        txTimeri.setVisibility(View.VISIBLE);
                        txTimeri.setText(String.valueOf(koha_ne_minut)+":"+Double.valueOf(dec.format(koha_ne_Sekond)));
                    }
                });
            }
        }, 0, 1000);

        btn1.setBackgroundResource(R.drawable.gradnr);
        btn2.setBackgroundResource(R.drawable.gradnr);
        btn3.setBackgroundResource(R.drawable.gradnr);
        btn4.setBackgroundResource(R.drawable.gradnr);
        btn5.setBackgroundResource(R.drawable.gradnr);
        btn6.setBackgroundResource(R.drawable.gradnr);
    }

    public void fshehiButonat(boolean vl){
        if(vl){
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            btn5.setVisibility(View.VISIBLE);
            btn6.setVisibility(View.VISIBLE);
        }
        else {
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            btn5.setVisibility(View.INVISIBLE);
            btn6.setVisibility(View.INVISIBLE);
        }
    }
    public void EnableButtons(){

            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            btn5.setEnabled(true);
            btn6.setEnabled(true);
    }

    public void HumbetLojen(){
        rasti=1; //
        tmr.cancel();
        txtPerfundimi.setText("GABIM! Radha tjeter ishte tek numri: "+nrRadhes+"\nHumbet per kaq kohe: "+Koha(kohaPerfundimit));

        kohafillimit = 0;  // per te mos pasur mundesi me te vazhdoj lojen!
    }
    public void FitoiLojen(){
        tmr.cancel();
        txtPerfundimi.setText("Urime keni fituar!! Keni perfunduar per kaq kohe: "+Koha(kohaPerfundimit));
        ruajNeDB(Parameters.emri,Koha(kohaPerfundimit));
        kohafillimit=0;  // per te mos pasur mundesi me te vazhdoj lojen!
        rasti=1; //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFillo=(Button)findViewById(R.id.btnFillo);
        txtKoha=(TextView)findViewById(R.id.txtKoha);
        txtPerfundimi=(TextView)findViewById(R.id.txtPerfundimi);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.btn6);
        imageView1=(ImageView) findViewById(R.id.imageView1);

        final TextView txtEmri;
        txtEmri =(TextView)findViewById(R.id.txtUseri);
        txtEmri.setText(""+Parameters.emri);
        txTimeri=(TextView)findViewById(R.id.txTimeri);
        //fshehiButonat(hera);

        btnFillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rasti==1) {  //qe te mos shtypet me gjate lojes
                    imageView1.setVisibility(View.GONE);
                    hera = true; //heren tjeter ky funksion me nderru vetin visibility Visible
                    Toast.makeText(getApplicationContext(), "Loja FILLOI", Toast.LENGTH_LONG).show();
                    nrRadhes = "";
                    fshehiButonat(hera);
                    EnableButtons();
                    startoTimerin();
                    kohafillimit = SystemClock.elapsedRealtime();
                    txtPerfundimi.setText("");
                    txtKoha.setText("");
                    GjeneroRastesite(); //gjeneron numrin random, rregullon poziten e butonave
                    rasti=0; //derisa te humb lojetari, mos te mundet ta shtyp me kete buton ...per shkak te Timerit
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn1.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nrRadhes = Minimumi(); //ktu duhet me shiku a eshte nr minimal ky  qe klikuat nga vargu
                        if (nrRadhes != btn1.getText().toString()) //nese nuk eshte nrMinimum, ka humbur
                        {
                            HumbetLojen();
                            btn1.setBackgroundColor(Color.RED);
                        } else btn1.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nrRadhes = Minimumi();
                        if (nrRadhes != btn2.getText().toString()) {
                            HumbetLojen();
                            btn2.setBackgroundColor(Color.RED);
                        } else btn2.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn3.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nrRadhes = Minimumi();
                        if (nrRadhes != btn3.getText().toString()) {
                            HumbetLojen();
                            btn3.setBackgroundColor(Color.RED);
                        } else btn3.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn4.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nrRadhes = Minimumi();
                        if (nrRadhes != btn4.getText().toString()) {
                            HumbetLojen();
                            btn4.setBackgroundColor(Color.RED);
                        } else btn4.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn5.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nrRadhes = Minimumi();
                        if (nrRadhes != btn5.getText().toString()) {
                            HumbetLojen();
                            btn5.setBackgroundColor(Color.RED);
                        } else btn5.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kohaPerfundimit= SystemClock.elapsedRealtime();
                if (kohafillimit != 0) //a eshte startuar loja
                {
                    if(AeshteNumriiFundit()) //a eshte true?
                    {
                        FitoiLojen();
                        btn6.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        nrRadhes = Minimumi();
                        if(nrRadhes != btn6.getText().toString())
                        {
                            HumbetLojen();
                            btn6.setBackgroundColor(Color.RED);
                        }
                        else btn6.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ju duhet te filloni LOJEN", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}