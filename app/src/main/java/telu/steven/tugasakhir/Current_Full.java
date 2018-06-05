package telu.steven.tugasakhir;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Current_Full extends AppCompatActivity {
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
    int delay = 2 * 1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    //--------------
    private List<model> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private adapter mAdapter;

    //--------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__full);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new adapter(this, menuList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());       //menginisiasi adapter untuk recycleView
        recyclerView.setLayoutManager(mLayoutManager);      //menghubungkan adapter dan layout
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        t = (TextView) findViewById(R.id.t);
        f = (TextView) findViewById(R.id.f);
        b = new Connect_In(this);
        a = new Connect_Cm(this);
        a.conn();
        b.conn();
        //addData();
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible


        updateA();
        updateB();
        //   b.disconn();
        super.onResume();

    }

    public void updateA() {
        g.postDelayed(new Runnable() {
            public void run() {
                //do something
                menuList.clear();
                a.conn();
                f.setText(String.valueOf(a.dataSub_A));

                if (a.dataSub_A < 10) {
                    addData("Node A",a.dataSub_A);

                } else {
                    //t.setEnabled(false);
                }


                //  Toast.makeText(getApplicationContext(),String.valueOf(a.dataSub_A),Toast.LENGTH_SHORT).show();
                runnableG = this;

                g.postDelayed(runnableG, delay);
            }
        }, delay);
    }

    public void updateB() {
        h.postDelayed(new Runnable() {
            public void run() {
                //do something

                b.conn();
                t.setText(String.valueOf(b.dataSub_B));

                if (b.dataSub_B < 10) {
                    addData("Node B",b.dataSub_B);
                } else {
                    //t.setEnabled(false);
                }


                runnable = this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

    }

    public void updateC() {
    }

//    @Override
//    protected void onPause() {
//        h.removeCallbacks(runnable);
//        g.removeCallbacks(runnableG);//stop handler when activity not visible
//        b.disconn();
//        a.disconn();
//        super.onPause();
//    }

    @Override
    public void onBackPressed() {
        h.removeCallbacks(runnable);
        g.removeCallbacks(runnableG);//stop handler when activity not visible
        b.disconn();
        a.disconn();
        super.onBackPressed();
    }

    public void addData(String node, int jarak) {


        menuList.add(new model(node, String.valueOf(jarak)));
        mAdapter.notifyDataSetChanged();

    }


}
