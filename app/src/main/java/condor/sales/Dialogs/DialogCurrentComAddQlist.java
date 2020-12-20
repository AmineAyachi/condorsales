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
import condor.sales.Models.CommandDetails;
import condor.sales.R;
import condor.sales.Utils.Utils;
import it.sephiroth.android.library.numberpicker.NumberPicker;


public class DialogCurrentComAddQlist extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    TextView title;
    ArrayList<CommandDetails> commanddetails ;
    int position ;
    Button annuler , save;
    NumberPicker numberpicker ;


    public DialogCurrentComAddQlist(Activity a , ArrayList<CommandDetails> commanddetails , int position) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.commanddetails = commanddetails ;
        this.position = position ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.currentcommandaddquantity);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        title = findViewById(R.id.title);
        annuler = findViewById(R.id.close_btn);
        save = findViewById(R.id.save_btn);
        numberpicker = findViewById(R.id.quantity);
        numberpicker.setProgress(commanddetails.get(position).getQauntite());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberpicker.getProgress()> 0 && commanddetails.size() >= position ){
                    commanddetails.get(position).setQauntite(numberpicker.getProgress());
                    Utils.saveCurrentCommandDetails(commanddetails , getContext());
                    dismiss();
                }

            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        // TODO: 23-03-2020  implement quantity add interface and controller
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_cancel:
                dismiss();
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
