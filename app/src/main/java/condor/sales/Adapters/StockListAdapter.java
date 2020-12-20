package condor.sales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import condor.sales.Models.Stock;
import condor.sales.R;

public class StockListAdapter extends BaseAdapter {
    private ArrayList<Stock> stockinfos;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;

    public StockListAdapter(ArrayList<Stock> stockinfo, Context context) {
        this.stockinfos = stockinfo;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }


    @Override
    public int getCount() {
        return stockinfos.size();
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
        view = layoutInflater.inflate(R.layout.stock_list_view_layout, null);

        Stock stockinfo = stockinfos.get(position);


        TextView productname = view.findViewById(R.id.comercial_name);
        TextView productquantity = view.findViewById(R.id.qt);
        TextView ref = view.findViewById(R.id.ref);

        productquantity.setText( ""+stockinfo.getQuantity());
        productname.setText( ""+stockinfo.getProductname());
        ref.setText( ""+stockinfo.getProductreference());
        this.setAnimation(view, position);
        return view;
    }

    //file search result
    public void filterResult(ArrayList<Stock> newStockInfos) {
        stockinfos = new ArrayList<>();
        stockinfos.addAll(newStockInfos);
        notifyDataSetChanged();
    }

    private void setAnimation(View var1, int var2) {
        if (var2 > this.mLastPosition) {
            var1.startAnimation(this.mAnimation);
            this.mLastPosition = var2;
        }
    }
}
