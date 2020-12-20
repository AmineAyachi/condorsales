package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import condor.sales.Adapters.RecapListAdapter;
import condor.sales.Models.Recap;
import condor.sales.R;


public class DialogRecapReception extends Dialog {
    public Activity mContext;
    public Dialog d;
    public ListView lv;
    Button ok;
    RecapListAdapter recapAdapter;
    ArrayList<Recap> recaplist;

    public DialogRecapReception(Activity context ,ArrayList<Recap> recaplist) {

        super(context);
        this.mContext = context;
        this.recaplist = recaplist;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reception_recap);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        lv = findViewById(R.id.snlv);
        recapAdapter = new RecapListAdapter(recaplist, mContext);
        lv.setAdapter(recapAdapter);
        ok = findViewById(R.id.btn_dialog_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRecapReception.this.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }




}
