package condor.sales.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Map;

import condor.sales.Adapters.HistoryVenteAdapter;
import condor.sales.Models.Reception;
import condor.sales.Models.model;
import condor.sales.R;
import condor.sales.Utils.Utils;

import static condor.sales.Utils.UtilsKotlin.getFilterdRecHis;

public class VenteFragment extends Fragment {

    HistoryVenteAdapter historyVenteAdapter;
    ListView customListView;
    private View rootView;
    ArrayList<Reception> h_vente_list = new ArrayList<Reception>();
    ArrayList<model> h_ven_list =new ArrayList<model>() ;
    PrimeDatePickerBottomSheet datePickerV ;
    public static VenteFragment newInstance(FragmentActivity activity) {
        List<Fragment> frags = activity.getSupportFragmentManager().getFragments();

        for (Fragment f : frags) {
            if(f instanceof VenteFragment){
                return (VenteFragment) f;
            }

        }
        return new VenteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        h_vente_list  = Utils.getHistorique(getActivity());

        Calendar sc = Calendar.getInstance();
        sc.add(Calendar.MONTH, -1);
        Date s_date = sc.getTime();
        Calendar ec = Calendar.getInstance();
        Date e_date = ec.getTime();


        h_ven_list.clear();
        for(Map.Entry<String, Integer> entry : getFilterdRecHis(h_vente_list,s_date,e_date).entrySet()) {
            model m = new model(entry.getKey(),entry.getValue());
            h_ven_list.add(m);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =   inflater.inflate(R.layout.fragment_vente, container, false);

        customListView = rootView.findViewById(R.id.h_vente_list_view);



        historyVenteAdapter = new HistoryVenteAdapter(h_ven_list, this.getActivity());
        customListView.setAdapter(historyVenteAdapter);


        return rootView ;

    }


    public void onViewClicked() {


        datePickerV = PrimeDatePickerBottomSheet.newInstance(
                new CivilCalendar(), // for example: new PersianCalendar()
                PickType.RANGE_START // for example: PickType.SINGLE
        );



        datePickerV.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {

            @Override
            public void onSingleDayPicked(@NotNull PrimeCalendar singleDay) {


            }

            @Override
            public void onRangeDaysPicked(@NotNull PrimeCalendar startDay, @NotNull PrimeCalendar endDay) {

                h_ven_list.clear();
                for(Map.Entry<String, Integer> entry : getFilterdRecHis(h_vente_list,startDay.getTime(),endDay.getTime()).entrySet()) {
                    model m = new model(entry.getKey(),entry.getValue());
                    h_ven_list.add(m);
                }
                historyVenteAdapter.notifyDataSetChanged();


            }

            @Override
            public void onMultipleDaysPicked(@NotNull List<? extends PrimeCalendar> multipleDays) {
                // TODO
            }
        });
        datePickerV.show(getActivity().getSupportFragmentManager(), "DatePickerVF");
//        Toast.makeText(getActivity(), "VF",
//                Toast.LENGTH_LONG).show();
    }


}
