package game.fastgenius;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class hyrja extends AppCompatActivity {

    Button btnHap, btnHighScore, btnOpsione, btnExit;
    EditText etEmri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyrja);
        btnHap = (Button)findViewById(R.id.btnHapLojen);
        btnHighScore = (Button)findViewById(R.id.btnHighScore);
        btnOpsione = (Button)findViewById(R.id.btnOpsione);
        btnExit = (Button)findViewById(R.id.btnExit);
        Toolbar nToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(nToolbar);

        etEmri = (EditText) findViewById(R.id.user);


        btnHap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(etEmri.getText().toString().length() > 2){
                Vazhdo();
                Intent HapLojen = new Intent(hyrja.this, MainActivity.class);
                Parameters.emri = etEmri.getText().toString();
                startActivity(HapLojen);
            }
            else{
                Toast.makeText(getApplicationContext(),"Shkruani emrin tuaj", Toast.LENGTH_LONG).show();
            }
            }
        });
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HighScore = new Intent(hyrja.this, HighScore.class);
                startActivity(HighScore);
            }
        });
        btnOpsione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Opsione = new Intent(hyrja.this, Opsione.class);
                startActivity(Opsione);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater nMenuInflater = getMenuInflater();
        nMenuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting)
        {
            Intent Opsione = new Intent(hyrja.this, Opsione.class);
            startActivity(Opsione);
        }
        if(item.getItemId() == R.id.action_about_us)
        {
            Intent Rreth = new Intent(hyrja.this, RrethNesh.class);
            startActivity(Rreth);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Vazhdo(){
        if(Parameters.Audio){
            MediaPlayer HyrjaSound = MediaPlayer.create(this, R.raw.hyrja);
            HyrjaSound.start();
        }

    }
}
