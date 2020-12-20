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
import java.util.Date;
import condor.sales.Models.Incentive;
import condor.sales.R;

public class IncentiveNotCheckedAdapter extends BaseAdapter {
    private ArrayList<Incentive> incentiveinfos;
    private ArrayList<Incentive> incentiveinfossecd;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;

    public IncentiveNotCheckedAdapter(ArrayList<Incentive> incentiveinfos, Context context) {
        this.incentiveinfos = incentiveinfos;
        this.incentiveinfossecd = incentiveinfos;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    @Override
    public int getCount() {
        return incentiveinfos.size();
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
        view = layoutInflater.inflate(R.layout.incentive_not_checked_lv_layout, null);

        Incentive incentiveinfo = incentiveinfos.get(position);


        TextView month = view.findViewById(R.id.month);
        TextView incentive = view.findViewById(R.id.incentive);


        month.setText(""+getMonthName (incentiveinfo.getMonth()));

        incentive.setText("" + incentiveinfo.getIncentive());

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


    private String getMonthName (int x){


        switch(x) {
            case 1:
                return "Janv";

            case 2:
                return "Févr";

            case 3:
                return  "Mars";

            case 4:
                return "Avr";

            case 5:
                return "Mai";

            case 6:
                return "Juin";

            case 7:
                return "Juil";

            case 8:
                return "août";

            case 9:
                return "Sept";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Déc";

            default:
                return "";
        }


    }


    private void setAnimation(View var1, int var2) {
        if (var2 > this.mLastPosition) {
            var1.startAnimation(this.mAnimation);
            this.mLastPosition = var2;
        }
    }
}
