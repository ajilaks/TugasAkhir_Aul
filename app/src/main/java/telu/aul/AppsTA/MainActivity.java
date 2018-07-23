package telu.aul.tugasakhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Button btncm, btnin, btnsave;
    CardView bIn,bCm,bC;
    Connect_Cm a;
    Connect_In b;
    Connect_DataC c;
    static int dataSub_A, dataSub_B, dataSub_C;
    static String dataTop_A, dataTop_B,dataTop_C;
    private final String TAG = this.getClass().getName();
    TextView ce;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bIn = (CardView) findViewById(R.id.bIn);
        bCm = (CardView) findViewById(R.id.bCm);
        bC = (CardView) findViewById(R.id.bC);


       // btnsave = (Button) findViewById(R.id.save);
        a = new Connect_Cm(this);
        b = new Connect_In(this);
        c = new Connect_DataC(this);
        a.conn();
        b.conn();
        c.conn();
        TampilCm();TampilIn();TampilDataC();
    }


    @Override
    public void onRestart()
    {
        super.onRestart();

       a.conn();
       b.conn();
       c.conn();
        // do some stuff here
    }



    public void TampilCm()
    {
        bCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activity_Cm.class);
                startActivity(intent);
                //a.disconn();
            }
        });

    }

    public void TampilIn()
    {
        bIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Activity_In.class);
                startActivity(intent);
           //    b.disconn();
            }
        });
    }

    public void TampilDataC()
    {
        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activity_DataC.class);
                startActivity(intent);
                //c.disconn();
            }
        });

    }

    @Override
    protected void onPause() {
        //h.removeCallbacks(runnable); //stop handler when activity not visible
        a.disconn();
        b.disconn();
        c.disconn();
        super.onPause();
    }


}



