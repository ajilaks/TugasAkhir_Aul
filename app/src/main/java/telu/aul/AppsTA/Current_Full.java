package telu.aul.AppsTA;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Current_Full extends AppCompatActivity {
//    TextView t;
//    TextView f;
    Connect_Cm a;
    Connect_In b;
    Connect_DataC c;
    //--------------
    Handler g = new Handler();
    //int delay = 2*1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnableG;
    //--------------
    Handler h = new Handler();
    int delay = 1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    //--------------
    Handler i = new Handler();
    //int delay = 2*1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnableI;
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
        b = new Connect_In(this);
        a = new Connect_Cm(this);
        c = new Connect_DataC(this);
        a.conn();
        b.conn();
        c.conn();
        //addData();
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible


        updateA();
        updateB();
        updateC();
        //   b.disconn();
        super.onResume();

    }

    public void updateA() {
        g.postDelayed(new Runnable() {
            public void run() {
                //do something
                menuList.clear();
                a.conn();
              //  f.setText(String.valueOf(a.dataSub_A));

                if (a.dataSub_A < 10 && a.dataSub_A != 0) {
                    addData("Node A","Status: Penuh");

                }else if(a.dataSub_A == 0) {
                    //addData("Node A","Status: Not Connected");
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
                menuList.clear();
                b.conn();
          //      t.setText(String.valueOf(b.dataSub_B));

                if (b.dataSub_B < 10 && b.dataSub_B != 0 ) {
                    addData("Node B","Status: Penuh");
                } else if(b.dataSub_B == 0) {
                   // addData("Node B","Status: Not Connected");
                }


                runnable = this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

    }

    public void updateC() {

        i.postDelayed(new Runnable() {
            public void run() {
                //do something
                menuList.clear();
                c.conn();
                //      t.setText(String.valueOf(b.dataSub_B));

                if (c.dataSub_C < 10 && c.dataSub_C!=0 ) {
                    addData("Node C","Status: Penuh");
                }else if(c.dataSub_C == 0) {
                   // addData("Node C","Status: Not Connected");
                }


                runnableI = this;

                i.postDelayed(runnableI, delay);
            }
        }, delay);

    }

    @Override
    protected void onPause() {
//        h.removeCallbacks(runnable);
//        g.removeCallbacks(runnableG);
//        i.removeCallbacks(runnableI);
//        b.disconn();
//        a.disconn();
//        c.disconn();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        h.removeCallbacks(runnable);
        g.removeCallbacks(runnableG);
        i.removeCallbacks(runnableI);
        b.disconn();
        a.disconn();
        c.disconn();
        super.onDestroy();
    }

    public void addData(String node, String status) {


        menuList.add(new model(node, status));
        mAdapter.notifyDataSetChanged();

    }


}
