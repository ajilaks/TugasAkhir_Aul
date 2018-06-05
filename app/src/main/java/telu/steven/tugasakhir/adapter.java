package telu.steven.tugasakhir;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OJI on 05/06/2018.
 */

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<model> menuList;
    //CardView cv;

    public adapter(Context context, List<model> menuList) {
        mInflater = LayoutInflater.from(context); //inisiasi inflater
        this.menuList = menuList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_content, parent, false);
        //method untuk menginflate dengan class lainnya
        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        model menu = menuList.get(position);
        holder.node.setText(menu.getNode());
        holder.jarak.setText(menu.getSub());//get value ke textView
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { // class MyCiewHolder
        public TextView node, jarak;
        ConstraintLayout layout;


        public MyViewHolder(View view) {
            super(view);//menginisiasi variable2 attribute
            node = (TextView) view.findViewById(R.id.node);
            jarak = (TextView) view.findViewById(R.id.status);

        }
}

}
