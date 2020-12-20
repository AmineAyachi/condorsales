package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import condor.sales.Adapters.IncentiveCheckedAdapter;
import condor.sales.Adapters.IncentiveNotCheckedAdapter;
import condor.sales.Dialogs.DialogCheckIncentive;
import condor.sales.Models.Incentive;
import condor.sales.R;
import condor.sales.Utils.Utils;


public class IncentiveActivity extends AppCompatActivity {
    ImageView back_btn ;
    IncentiveNotCheckedAdapter incentiveNotCheckedAdapter ;
    IncentiveCheckedAdapter incentiveCheckedAdapter ;
    ListView checked_list,Notchecked_list;
    ArrayList<Incentive> notCheckedArrayIncentive ,checkedArrayIncentive ,togetherList ;
    boolean dialogOpened = false ;
    TextView currentMonthTV , currentIncentiveTV ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_incentive);
        back_btn = findViewById(R.id.back_btn);
        checked_list = findViewById(R.id.list_view_checked);
        currentMonthTV = findViewById(R.id.month);

        currentIncentiveTV = findViewById(R.id.incentive_value);
        Notchecked_list = findViewById(R.id.list_view_notchecked);
        checkedArrayIncentive = new ArrayList<Incentive>();
        notCheckedArrayIncentive = new ArrayList<Incentive>();

        togetherList = Utils.getIncentive(IncentiveActivity.this);
        Log.e("incentive activity ", "incentive list"+togetherList);
        filterIncenitve(togetherList,notCheckedArrayIncentive,checkedArrayIncentive);



        incentiveCheckedAdapter = new IncentiveCheckedAdapter(checkedArrayIncentive, IncentiveActivity.this);
        checked_list.setAdapter(incentiveCheckedAdapter);

        incentiveNotCheckedAdapter = new IncentiveNotCheckedAdapter(notCheckedArrayIncentive, IncentiveActivity.this);
        Notchecked_list.setAdapter(incentiveNotCheckedAdapter);


        Notchecked_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(!dialogOpened){
                    dialogOpened =true;
                    DialogCheckIncentive cdd = new DialogCheckIncentive(IncentiveActivity.this,togetherList ,notCheckedArrayIncentive,checkedArrayIncentive, i);
                    cdd.setCancelable(false);
                    cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            dialogOpened =false;
                            incentiveCheckedAdapter.notifyDataSetChanged();
                            incentiveNotCheckedAdapter.notifyDataSetChanged();
                        }
                    });
                    cdd.show();
                }


            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncentiveActivity.super.onBackPressed();
            }
        });

    }


    private void filterIncenitve(ArrayList<Incentive> together , ArrayList <Incentive> notchecked , ArrayList<Incentive> checked ){
        notchecked.clear();
        checked.clear();

        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);

        for (final Incentive inc : together) {
            Log.e("inc", " inc.getMonth"+inc.getMonth()+"Calendar getMonth"+month);


            if(inc.getMonth() == month && inc.getYear() == year ){

                Log.e("found", "IncenivteActivity "+inc);
                 currentMonthTV.setText(""+getMonthName (inc.getMonth()));
                 currentIncentiveTV.setText(""+inc.getIncentive());

            }else{

                if (inc.isChecked()) {

                    checked.add(inc);
                }else{

                    notchecked.add(inc);
                }
            }


        }




    }
    private String getMonthName (int x){


        switch(x) {
            case 1:
                return "Janvier";

            case 2:
                return "Février";

            case 3:
                return  "Mars";

            case 4:
                return "Avril";

            case 5:
                return "Mai";

            case 6:
                return "Juin";

            case 7:
                return "Juillet";

            case 8:
                return "août";

            case 9:
                return "Septembre";

            case 10:
                return "Octobre";

            case 11:
                return "Novembre";

            case 12:
                return "Décembre";

            default:
                return "";
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }  }

}
