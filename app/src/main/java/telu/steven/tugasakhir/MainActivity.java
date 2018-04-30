package telu.steven.tugasakhir;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.util.Strings;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btncm, btnin, btnsave;
    CardView bIn,bCm,bC;
    Connect_Cm a;
    Connect_In b;
    Connect_DataC c;
    static int dataSub_A, dataSub_B, dataSub_C;
    static String dataTop_A, dataTop_B,dataTop_C;
    private final String TAG = this.getClass().getName();


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

    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setMessage("Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MainActivity.this.finish();
                        moveTaskToBack(true);
                        System.exit(0);
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    public void TampilCm()
    {
        bCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activity_Cm.class);
                startActivity(intent);
                a.disconn();
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
                b.disconn();
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
                c.disconn();
            }
        });

    }




}


