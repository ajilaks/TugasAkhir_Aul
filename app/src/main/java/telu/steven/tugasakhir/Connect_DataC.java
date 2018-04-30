package telu.steven.tugasakhir;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Connect_DataC extends MainActivity{
    static String MQTTHOST = "tcp://128.199.141.4:1883";
    static String USERNAME = "AccessNet";
    static String PASSWORD = "accessnet";
    String topicStr = "data3";
    MqttAndroidClient client;
    //    TextView txtSub,txtTopic;
    MqttConnectOptions options;
    public static Context mCtx;
    Vibrator vibrator;
    Ringtone myRingtone;
    ImageView alertC;
    Connect_DataC(Context context){

        mCtx=context;
        String clientId = MqttClient.generateClientId();
        client =  new MqttAndroidClient(context,MQTTHOST, clientId);

        options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        vibrator =(Vibrator)mCtx.getSystemService(VIBRATOR_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(mCtx,uri);
        alertC= (ImageView) ((Activity)mCtx).findViewById(R.id.alertC);

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
                dataSub_C = Integer.parseInt(new String (message.getPayload()));
                dataTop_C = topic;
                if ( dataSub_C < 10) {
                    vibrator.vibrate(Integer.parseInt(new String (message.getPayload())));
                    myRingtone.play();
                    alertC.setVisibility(View.VISIBLE);
                }else{

                    alertC.setVisibility(View.INVISIBLE);
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