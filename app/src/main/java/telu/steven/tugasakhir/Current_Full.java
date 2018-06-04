package telu.steven.tugasakhir;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Current_Full extends AppCompatActivity{
    TextView t;
    TextView f;
    Connect_Cm a;
    Connect_In b;
    String dataB;
    //--------------
    Handler g = new Handler();
    //int delay = 2*1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnableG;
    //--------------
    Handler h = new Handler();
    int delay = 2*1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    //--------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__full);
        t= (TextView) findViewById(R.id.t);
        f = (TextView) findViewById(R.id.f);
        b = new Connect_In(this);
        a = new Connect_Cm(this);
        a.conn();
        b.conn();
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible

        updateB();
        updateA();

     //   b.disconn();
        super.onResume();

    }
    public void updateA(){
        g.postDelayed(new Runnable() {
            public void run() {
                //do something

                a.conn();
                f.setText(String.valueOf(a.dataSub_A));
          //  Toast.makeText(getApplicationContext(),String.valueOf(a.dataSub_A),Toast.LENGTH_SHORT).show();
                runnableG=this;

                g.postDelayed(runnableG, delay);
            }
        }, delay);
    }

    public void updateB(){
        h.postDelayed(new Runnable() {
            public void run() {
                //do something

                b.conn();
                if(b.dataSub_B<10){
                    t.setEnabled(true);
                    t.setText(String.valueOf(b.dataSub_B));
                }else{
                    t.setEnabled(false);
                }


                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

    }
    public void updateC(){}

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable);
        g.removeCallbacks(runnableG);//stop handler when activity not visible
        b.disconn();
        a.disconn();
        super.onPause();
    }


}
