
package telu.aul.AppsTA;

        import android.content.Intent;
        import android.media.Ringtone;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Vibrator;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.eclipse.paho.android.service.MqttAndroidClient;
        import org.eclipse.paho.client.mqttv3.IMqttActionListener;
        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.IMqttToken;
        import org.eclipse.paho.client.mqttv3.MqttCallback;
        import org.eclipse.paho.client.mqttv3.MqttClient;
        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Activity_In extends AppCompatActivity {
   //  Main main;

    TextView txtSub,txtTopic;
    static String MQTTHOST = "tcp://128.199.141.4:1883";
    static String USERNAME = "AccessNet";
    static String PASSWORD = "accessnet";
    String topicStr = "data2";
    Button btndc;
    // String data;
    private final String TAG = this.getClass().getName();
//    String data = new String();

    MqttAndroidClient client;
    //    TextView txtSub,txtTopic;
    MqttConnectOptions options;
    Vibrator vibrator;
    Ringtone myRingtone;
    int[] queData = new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__in);
        queData[0] = 0;
        queData[1] = 0;
        txtSub=(TextView)findViewById(R.id.txtIn);
        txtTopic=(TextView)findViewById(R.id.txtTop2);
        btndc=(Button) findViewById(R.id.dc);

        vibrator =(Vibrator)getSystemService(VIBRATOR_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(getApplicationContext(),uri);

        String clientId = MqttClient.generateClientId();
        client =  new MqttAndroidClient(this.getApplicationContext(),MQTTHOST, clientId);

        options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        txtSub.setText(String.valueOf(MainActivity.dataSub_B)+" cm");
        txtTopic.setText(MainActivity.dataTop_B);

        conn();
        disconnect();

    }


    public void conn(){
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                  //  Toast.makeText(Activity_In.this,"konek",Toast.LENGTH_LONG).show();
                    setSub();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //Toast.makeText(Activity_In.this,"gagal konek",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("topic", topic);
                if (topic.equals("data2")) {
                    String messages = new String(message.getPayload())+" cm";
                    txtSub.setText(messages);//message.getPayload --> Ambil datanya
                    txtTopic.setText(topic);
                    int dataSub_B = Integer.parseInt(new String (message.getPayload()));
                    if (queData[1]!=0){
                        queData[1] = queData[0];
                        queData[0] = dataSub_B;
                        if ( dataSub_B < 10 && queData[1]>10) {
                            vibrator.vibrate(100);
                            myRingtone.play();


                        }else {
                            queData[1] = dataSub_B;
                            if (dataSub_B < 10) {
                                vibrator.vibrate(100);
                                myRingtone.play();
                            }
                        }
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void pub(View v) {
        String topic = topicStr;
        String message = "test123";
        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void setSub(){
        try{
            client.subscribe(topicStr,0);
        }
        catch (MqttException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconn();
    }

    public void disconn(){
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //  Toast.makeText(MainActivity.this,"ga konek",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Toast.makeText(MainActivity.this,"gabisa gagal konek",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void disconnect()
    {
        btndc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    IMqttToken token = client.disconnect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                           // Toast.makeText(Activity_In.this,"Disconnected",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Activity_In.this, MainActivity.class);
//                            startActivity(intent);
                            // finish();
//                            moveTaskToBack(true);

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                           // Toast.makeText(Activity_In.this,"gabisa gagal konek",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
