package condor.sales.Login;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.R;
import static condor.sales.Utils.Utils.custom_font;


public class NewPasswordActivity extends AppCompatActivity {
    Button btn_updatePassword;
    EditText newPasswordConfirmation, newPassword;
    boolean operationEncors = false;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        newPasswordConfirmation = findViewById(R.id.newPasswordConfirmation);
        newPassword = findViewById(R.id.newPassword);
        btn_updatePassword = findViewById(R.id.btn_updatePassword);

    }

    private void showDialogLoading() {
        pDialog = new SweetAlertDialog(NewPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setContentText(getString(R.string.laoding));
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
        pDialog.setCancelable(false);
        pDialog.show();

        TextView messageView = pDialog.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(custom_font);

    }
}
