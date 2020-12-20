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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import condor.sales.Models.Command;
import condor.sales.R;

public class HistoryCommandAdapter  extends BaseAdapter {
    private ArrayList<Command> commandinfos;
    private ArrayList<Command> commandinfossecd;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;

    public HistoryCommandAdapter(ArrayList<Command> commandinfos, Context context) {
        this.commandinfos = commandinfos;
        this.commandinfossecd = commandinfos;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    @Override
    public int getCount() {
        return commandinfos.size();
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
        view = layoutInflater.inflate(R.layout.h_command_list_view_layout, null);

        Command commandinfo = commandinfos.get(position);
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM);

        TextView ref = view.findViewById(R.id.coderef_val);
        TextView price  = view.findViewById(R.id.price_val);
        TextView date = view.findViewById(R.id.date_val);

        ref.setText( commandinfo.getCoderef());

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String numberAsString = decimalFormat.format(commandinfo.getPrice());
        price.setText(numberAsString);
        date.setText(mediumDateFormat.format(commandinfo.getCreated_at()));
        this.setAnimation(view, position);
        return view;
    }


    //file search result
    public void filterResult(Date date) {
//        Log.e("filterResult", "filterResult");
//
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//
//        commandinfos.clear();
//
//        for (final Command co : commandinfossecd) {
//            Log.e("before boolean", "same day ");
//            cal1.setTime(co.getCreated_at());
//            cal2.setTime(date);
//            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
//                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
//
//            Log.e("boolean", "same day ");
//            if(sameDay){
//                Log.e("sameday", "same day ");
//                commandinfos.add(co);
//            }else{
//                Log.e("notsameday", "not same day ");
//
//            }
//        }
//      notifyDataSetChanged();
    }

    private void setAnimation(View var1, int var2) {
        if (var2 > this.mLastPosition) {
            var1.startAnimation(this.mAnimation);
            this.mLastPosition = var2;
        }
    }
}
