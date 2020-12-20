package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import condor.sales.Activities.VenteActivity;
import condor.sales.Models.Incentive;
import condor.sales.R;
import condor.sales.Utils.Utils;


public class DialogCheckIncentive extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    Button close_btn , yes_btn;
    TextView text;
    ArrayList<Incentive> togetherList , checkedArrayIncentive ,notcheckedArrayIncentive;
    int postion ;


    public DialogCheckIncentive(Activity a  , ArrayList<Incentive> togetherList , ArrayList<Incentive> notcheckedArrayIncentive ,ArrayList<Incentive>  checkedArrayIncentive, int postion ) {

        super(a);
        this.c = a;
        this.togetherList = togetherList;
        this.checkedArrayIncentive = checkedArrayIncentive;
        this.notcheckedArrayIncentive = notcheckedArrayIncentive;
        this.postion = postion;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogcheckincentive);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        close_btn = findViewById(R.id.close_btn);
        yes_btn = findViewById(R.id.ok_btn);
        text = findViewById(R.id.text);
        Incentive cuerrnetincentive = notcheckedArrayIncentive.get(postion);
        text.setText("Voulez-vous confirmer la réception de l'incentive du : \n "+getMonthName(cuerrnetincentive.getMonth())+" ,"+cuerrnetincentive.getYear()+" \n "+cuerrnetincentive.getIncentive()+" DZD ");
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Incentive cuerrnetincentive = notcheckedArrayIncentive.get(postion);
                notcheckedArrayIncentive.remove(cuerrnetincentive);
                checkedArrayIncentive.add(cuerrnetincentive);
                for (int i = 0; i < togetherList.size(); i++) {
                    if(togetherList.get(i) == cuerrnetincentive){
                        togetherList.get(i).setChecked(true);
                        Utils.saveIncentive(togetherList,c);
                        Utils.saveSync("false", c);

                    }

                }

                dismiss();
            }
        });


        // TODO: 23-03-2020  implement incentive history list interface and controller
    }


    private String getMonthName (int x){


        switch(x) {
            case 0:
                return "Janvier";

            case 1:
                return "Février";

            case 2:
                return  "Mars";

            case 3:
                return "Avril";

            case 4:
                return "Mai";

            case 5:
                return "Juin";

            case 6:
                return "Juillet";

            case 7:
                return "août";

            case 8:
                return "Septembre";

            case 9:
                return "Octobre";

            case 10:
                return "Novembre";

            case 11:
                return "Décembre";

            default:
                return "";
        }   }

        @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:

                break;

            default:
                break;
        }
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
