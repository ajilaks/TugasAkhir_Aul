package telu.aul.AppsTA;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
    public void toMonitor(View view) {
        Intent i = new Intent(MainMenu.this,MainActivity.class);
        startActivity(i);
    }

    public void toCurrent(View view) {
        Intent i = new Intent(MainMenu.this,Current_Full.class);
        startActivity(i);
    }
}
