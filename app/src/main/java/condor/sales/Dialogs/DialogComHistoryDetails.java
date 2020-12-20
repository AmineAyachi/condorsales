package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import condor.sales.Adapters.DialogComHistoryDetailsAdapter;
import condor.sales.Models.CommandDetails;
import condor.sales.R;

public class DialogComHistoryDetails extends Dialog implements android.view.View.OnClickListener{
    public Activity c;
    public Dialog d;
    Button close ;
    TextView title;
    DialogComHistoryDetailsAdapter dialogComHistoryDetailsAdapter;
    private ArrayList<CommandDetails> commanddetails ;
    ListView CoommandHistoryDetailsLv ;

    public DialogComHistoryDetails(Activity a , ArrayList<CommandDetails> commanddetails ) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.commanddetails = commanddetails;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogcomhistorydetails);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        CoommandHistoryDetailsLv = findViewById(R.id.command_h_d_list_view);
        close = findViewById(R.id.close_btn);
        title = findViewById(R.id.coderef);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title.setText("Les details de la commande");
        dialogComHistoryDetailsAdapter = new DialogComHistoryDetailsAdapter(commanddetails,c);
        CoommandHistoryDetailsLv.setAdapter(dialogComHistoryDetailsAdapter);


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
