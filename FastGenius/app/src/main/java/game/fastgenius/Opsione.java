package game.fastgenius;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Opsione extends AppCompatActivity {

    Button btnSound, btnSilent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opsione);

        btnSound=(Button)findViewById(R.id.rbSound);
        btnSilent=(Button)findViewById(R.id.rbSilent);
        if(Parameters.Audio) {
            btnSilent.setEnabled(true);
            btnSound.setEnabled(false);
        }
        else{
            btnSilent.setEnabled(false);
            btnSound.setEnabled(true);
        }

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parameters.Audio=true;
                btnSilent.setEnabled(true);
                btnSound.setEnabled(false);
            }
        });
        btnSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parameters.Audio=false;
                btnSilent.setEnabled(false);
                btnSound.setEnabled(true);
            }
        });
        //me rujt ne Parameters me ze dhe pa ze
        //rbSilent.che
    }
}
