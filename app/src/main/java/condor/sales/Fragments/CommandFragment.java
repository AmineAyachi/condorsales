package condor.sales.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import condor.sales.Adapters.HistoryCommandAdapter;
import condor.sales.Dialogs.DialogComHistoryDetails;
import condor.sales.Models.Command;
import condor.sales.Models.CommandDetails;
import condor.sales.R;
import condor.sales.Utils.Utils;



public class CommandFragment extends Fragment implements View.OnClickListener {

    HistoryCommandAdapter commandDetailsAdapter;
    ListView customListView;
    private View rootView;

    PrimeDatePickerBottomSheet datePickerC ;
    ArrayList<Command> commandlist;
    ArrayList<Command> seccommandlist;

    public static CommandFragment newInstance(FragmentActivity activity) {
        List<Fragment> frags = activity.getSupportFragmentManager().getFragments();

        for (Fragment f : frags) {
            if(f instanceof CommandFragment){
                return (CommandFragment) f;
            }

        }
        return new CommandFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_command, container, false);
        customListView = rootView.findViewById(R.id.h_command_list_view);
        commandlist  = Utils.getCommand(getContext());
        seccommandlist = Utils.getCommand(getContext()) ;
        Gson gson = new Gson();

        Log.e("CommandFragment", "commandlist"+gson.toJson(seccommandlist) );
        commandDetailsAdapter = new HistoryCommandAdapter(seccommandlist, this.getActivity());
        customListView.setAdapter(commandDetailsAdapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("seccommandlist", " "+i);

               ArrayList<CommandDetails> co = seccommandlist.get(i).getCommandDetails();
if (co != null){
    DialogComHistoryDetails cdd = new DialogComHistoryDetails(getActivity(), co);
    cdd.setCancelable(false);
    cdd.show();
}
            }
        });



        return rootView ;
    }

    @Override
    public void onClick(View v) {

    }

    public void onViewClicked() {

        datePickerC = PrimeDatePickerBottomSheet.newInstance(
                new CivilCalendar(), // for example: new PersianCalendar()
                PickType.SINGLE // for example: PickType.SINGLE
        );



        datePickerC.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {

            @Override
            public void onSingleDayPicked(@NotNull PrimeCalendar singleDay) {

                //  Toast.makeText(getContext(), "itemclicked" +singleDay.getTime(), Toast.LENGTH_SHORT).show();
                Log.e("hello i am here ", "onSingleDayPicked: ");
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(singleDay.getTime());
                seccommandlist.clear();
                for (int i = 0; i < commandlist.size(); i++) {
                    cal1.setTime(commandlist.get(i).getCreated_at());
                    if((cal2.get(Calendar.DAY_OF_YEAR) == cal1.get(Calendar.DAY_OF_YEAR)) && (  cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) ){
                        seccommandlist.add(commandlist.get(i));
                    }

                }
                commandDetailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRangeDaysPicked(@NotNull PrimeCalendar startDay, @NotNull PrimeCalendar endDay) {
                // TODO
            }

            @Override
            public void onMultipleDaysPicked(@NotNull List<? extends PrimeCalendar> multipleDays) {
                // TODO
            }
        });
        datePickerC.show(getActivity().getSupportFragmentManager(), "DatePickerCF");
//        Toast.makeText(getActivity(), "CF",
//                Toast.LENGTH_LONG).show();
    }

}
