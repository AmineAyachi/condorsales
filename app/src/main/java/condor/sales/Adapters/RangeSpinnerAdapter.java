package condor.sales.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import condor.sales.Models.ProductRange;
import condor.sales.R;

public class RangeSpinnerAdapter extends ArrayAdapter<ProductRange> {

    LayoutInflater flater;

    public RangeSpinnerAdapter (Activity context, int resouceId, int textviewId, ArrayList<ProductRange> ProductRangelist){

        super(context,resouceId,textviewId, ProductRangelist);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductRange rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.spinner_droplist,null,true);

        TextView txtTitle = rowview.findViewById(R.id.modelname);
        txtTitle.setText(rowItem.getName());


        return rowview;
    }


    private View rowview(View convertView , int position){

        ProductRange rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_droplist, null, false);

            holder.txtTitle = rowview.findViewById(R.id.modelname);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        holder.txtTitle.setText(rowItem.getName());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
    }

}
