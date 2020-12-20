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

public class HistoryVenteAdapter extends BaseAdapter {
    private ArrayList<model> hventeinfos;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;

    public HistoryVenteAdapter(ArrayList<model> hventeinfos, Context context) {
        this.hventeinfos = hventeinfos;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    @Override
    public int getCount() {
        return hventeinfos.size();
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
        view = layoutInflater.inflate(R.layout.h_vente_list_view_layout, null);

        model hventeinfo = hventeinfos.get(position);
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM);

        TextView ref = view.findViewById(R.id.comercial_name);
        TextView price  = view.findViewById(R.id.qt);

        ref.setText( hventeinfo.getName());
        price.setText(""+hventeinfo.getQuantity());
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
