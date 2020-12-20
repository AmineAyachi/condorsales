package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.List;

import condor.sales.R;

public class DialogLogout  extends Dialog implements android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;
    Button close_btn;


    public DialogLogout(Activity a ) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialoglogout);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // TODO: 23-03-2020  implement incentive history list interface and controller
    }

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
