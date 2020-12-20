package condor.sales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.ArrayList;
import condor.sales.Models.model;
import condor.sales.R;

public class HistoryReceptionAdapter extends BaseAdapter {
    private ArrayList<model> hreceptioninfos;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;

    public HistoryReceptionAdapter(ArrayList<model> hreceptioninfos, Context context) {
        this.hreceptioninfos = hreceptioninfos;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }
    @Override
    public int getCount() {
        return hreceptioninfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.h_reception_list_view_layout, null);

        model hreceptioninfo = hreceptioninfos.get(position);
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM);

        TextView ref = view.findViewById(R.id.comercial_name);
        TextView price  = view.findViewById(R.id.qt);


        ref.setText( hreceptioninfo.getName());
        price.setText(""+hreceptioninfo.getQuantity());
        this.setAnimation(view, position);
        return view;
    }
    private void setAnimation(View var1, int var2) {
        if (var2 > this.mLastPosition) {
            var1.startAnimation(this.mAnimation);
            this.mLastPosition = var2;
        }
    }


}
