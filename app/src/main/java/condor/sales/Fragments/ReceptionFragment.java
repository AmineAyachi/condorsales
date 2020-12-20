package condor.sales.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import condor.sales.Activities.HistoriqueActivity;
import condor.sales.Adapters.HistoryReceptionAdapter;
import condor.sales.Models.Command;
import condor.sales.Models.Reception;
import condor.sales.Models.model;
import condor.sales.R;
import condor.sales.Utils.Utils;
import condor.sales.Utils.UtilsKotlin;

import static condor.sales.Utils.UtilsKotlin.getFilterdRecHis;


public class ReceptionFragment extends Fragment {
    HistoryReceptionAdapter historyReceptionAdapter;
    ListView customListView;
    private View rootView;
    ArrayList<model> h_rec_list =new ArrayList<model>() ;
    ArrayList<Reception> h_reception_list,second_h_reception_list;
    PrimeDatePickerBottomSheet datePickerR ;
    public static ReceptionFragment newInstance(FragmentActivity activity) {
        List<Fragment> frags = activity.getSupportFragmentManager().getFragments();

        for (Fragment f : frags) {
            if(f instanceof ReceptionFragment){
                return (ReceptionFragment) f;
            }

        }


        return new ReceptionFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        h_reception_list= new ArrayList<Reception>();
        second_h_reception_list= new ArrayList<Reception>();
        second_h_reception_list= Utils.getReception(getActivity());
        h_reception_list= Utils.getHistorique(getActivity());
        for (final Reception reception : second_h_reception_list) {
            if(reception.getDate_rec() != null){
                h_reception_list.add(reception);
            }
        }
        Calendar sc = Calendar.getInstance();
        sc.add(Calendar.MONTH, -1);
        Date s_date = sc.getTime();
        Calendar ec = Calendar.getInstance();
        Date e_date = ec.getTime();


        h_rec_list.clear();
        for(Map.Entry<String, Integer> entry : getFilterdRecHis(h_reception_list,s_date,e_date).entrySet()) {
            model m = new model(entry.getKey(),entry.getValue());
            h_rec_list.add(m);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView =   inflater.inflate(R.layout.fragment_reception, container, false);

        customListView = rootView.findViewById(R.id.h_reception_list_view);

        Log.e("ReceptionFragement", "h_reception_list size"+h_reception_list.size());



        historyReceptionAdapter = new HistoryReceptionAdapter(h_rec_list, this.getActivity());
        customListView.setAdapter(historyReceptionAdapter);



        return rootView ;

    }
    public void onViewClicked() {

        datePickerR = PrimeDatePickerBottomSheet.newInstance(
                new CivilCalendar(), // for example: new PersianCalendar()
                PickType.RANGE_START // for example: PickType.SINGLE
        );



        datePickerR.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {

            @Override
            public void onSingleDayPicked(@NotNull PrimeCalendar singleDay) {


            }

            @Override
            public void onRangeDaysPicked(@NotNull PrimeCalendar startDay, @NotNull PrimeCalendar endDay) {


                // Toast.makeText(CommandActivity.this, "p : "+ UtilsAmine.getCustomers(currentCommandDetails),
                // Toast.LENGTH_LONG).show();
                Log.e("ReceptionFragement", "h_reception_list size");
                 h_rec_list.clear();
                for(Map.Entry<String, Integer> entry : getFilterdRecHis(h_reception_list,startDay.getTime(),endDay.getTime()).entrySet()) {
                   model m = new model(entry.getKey(),entry.getValue());
                    h_rec_list.add(m);
                }
                historyReceptionAdapter.notifyDataSetChanged();



            }

            @Override
            public void onMultipleDaysPicked(@NotNull List<? extends PrimeCalendar> multipleDays) {
                // TODO
            }
        });

        datePickerR.show(getActivity().getSupportFragmentManager(), "DatePickerRF");
//        Toast.makeText(getActivity(), "RF",
//                Toast.LENGTH_LONG).show();
    }



}
