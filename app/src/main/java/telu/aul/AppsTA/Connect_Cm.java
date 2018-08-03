package telu.aul.AppsTA;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by OJI on 13/04/2018.
 */

public class Connect_Cm extends MainActivity{
    static String MQTTHOST = "tcp://128.199.141.4:1883";
    static String USERNAME = "AccessNet";
    static String PASSWORD = "accessnet";
    String topicStr = "data1";
    MqttAndroidClient client;
    //    TextView txtSub,txtTopic;
    MqttConnectOptions options;
    public static Context mCtx;
    Vibrator vibrator;
    Ringtone myRingtone;
    ImageView alertA;
    LinearLayout layout_A;
    int[] queData = new int[2];

    Connect_Cm(Context context){
        queData[0] = 0;
        queData[1] = 0;
        mCtx=context;
        String clientId = MqttClient.generateClientId();
        client =  new MqttAndroidClient(context,MQTTHOST, clientId);

        options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        vibrator =(Vibrator)mCtx.getSystemService(VIBRATOR_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(mCtx,uri);
        alertA = (ImageView) ((Activity)mCtx).findViewById(R.id.alertA);
        layout_A = (LinearLayout) ((Activity)mCtx).findViewById(R.id.layout_A);

       // conn();

    }

    public void conn(){
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                   // Toast.makeText(MainActivity.this,"konek",Toast.LENGTH_LONG).show();
                    setSub();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                   // Toast.makeText(MainActivity.this,"gagal konek",Toast.LENGTH_LONG).show();
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
                // if (topic.equals("ambil/a")) {
                // txtSub.setText(new String (message.getPayload()));//message.getPayload --> Ambil datanya
                // txtTopic.setText(topic);
                dataSub_A = Integer.parseInt(new String (message.getPayload()));
                dataTop_A = topic;

                if (queData[1]!=0){
                    queData[1] = queData[0];
                    queData[0] = dataSub_A;
                    if ( dataSub_A < 10 && queData[1]>10) {
                        vibrator.vibrate(100);
                        myRingtone.play();
                        alertA.setVisibility(View.VISIBLE);
                        layout_A.getLayoutParams().width = 600;
                        layout_A.requestLayout();
                    }else if(dataSub_A < 10){
                        alertA.setVisibility(View.VISIBLE);
                        layout_A.getLayoutParams().width = 600;
                        layout_A.requestLayout();
                    }else{
                        layout_A.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout_A.requestLayout();
                        alertA.setVisibility(View.INVISIBLE);
                    }
                }else{
                    queData[1] = dataSub_A;
                    if ( dataSub_A < 10 ) {
                        vibrator.vibrate(100);
                        myRingtone.play();
                        alertA.setVisibility(View.VISIBLE);
                        layout_A.getLayoutParams().width = 600;
                        layout_A.requestLayout();
                    }else{
                        layout_A.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout_A.requestLayout();
                        alertA.setVisibility(View.INVISIBLE);
                    }

                }


                //    }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void setSub(){
        try{
            client.subscribe(topicStr,0);
        }
        catch (MqttException e){
            e.printStackTrace();
        }
    }

     public void disconn(){
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Toast.makeText(MainActivity.this,"ga konek",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //Toast.makeText(MainActivity.this,"gabisa gagal konek",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
