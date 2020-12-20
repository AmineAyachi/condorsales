package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.wang.avi.AVLoadingIndicatorView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Adapters.CommandListAdapter;
import condor.sales.Constants;
import condor.sales.Dialogs.DialogCurrentComAddQlist;
import condor.sales.R;
import condor.sales.Dialogs.DialogAddCommand;
import condor.sales.Utils.Utils;



import static condor.sales.Constants.currentCommandDetails;
import static condor.sales.Utils.Utils.getAllProducts;
import static condor.sales.Utils.Utils.getProductModel;
import static condor.sales.Utils.Utils.getProductRange;


public class CommandActivity extends AppCompatActivity {
    private ImageView back_btn, add_btn;
    private Button done_btn;
    CommandListAdapter commandDetailsAdapter;
    ListView customListView;
    TextView total ;
    boolean operationEnCours = false;
    private AVLoadingIndicatorView progressBar;
    SweetAlertDialog mDialogdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        progressBar = findViewById(R.id.loading_bar);
        back_btn = findViewById(R.id.back_btn);
        add_btn = findViewById(R.id.add_btn);
        total = findViewById(R.id.total);
        done_btn = findViewById(R.id.done_btn);
        Constants.productmodel = getProductModel(CommandActivity.this);
        Constants.productrange = getProductRange(CommandActivity.this);
        Constants.all_Product = getAllProducts(CommandActivity.this);

        customListView = findViewById(R.id.command_list_view);


        currentCommandDetails = Utils.getCurrentCommandDtails(CommandActivity.this);

        if(currentCommandDetails.isEmpty()){

            Utils.setCCnotsyncable(CommandActivity.this);
        }

        commandDetailsAdapter = new CommandListAdapter(currentCommandDetails, CommandActivity.this);
        customListView.setAdapter(commandDetailsAdapter);
        commandDetailsAdapter.notifyDataSetChanged();

        float totalnbr = 0 ;
        for (int i = 0; i < currentCommandDetails.size(); i++){
            totalnbr += currentCommandDetails.get(i).getQauntite() * currentCommandDetails.get(i).getPrice();

        }
        total.setText(""+totalnbr+" DZD");

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DialogCurrentComAddQlist cdd = new DialogCurrentComAddQlist(CommandActivity.this,currentCommandDetails,i);
                cdd.setCancelable(false);
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        commandDetailsAdapter.notifyDataSetChanged();
                        float totalnbr = 0 ;
                        for (int i = 0; i < currentCommandDetails.size(); i++){
                            totalnbr += currentCommandDetails.get(i).getQauntite() * currentCommandDetails.get(i).getPrice();

                        }
                          total.setText(""+totalnbr+" DZD");

                    }
                });
                cdd.show();
            }
        });

        customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int i, long id) {

                mDialogdelete = new SweetAlertDialog(CommandActivity.this, SweetAlertDialog.WARNING_TYPE);

                mDialogdelete.setTitleText("Suppression")
                        .setContentText("Voulez-vous vraiment Supprimer ce produit de votre commande")
                        .setConfirmText("Non")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                mDialogdelete.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Oui, Supprimer")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                Toast.makeText(CommandActivity.this, "Produit supprimé", Toast.LENGTH_LONG).show();
                                currentCommandDetails.remove(i);
                                float totalnbr = 0 ;
                                for (int i = 0; i < currentCommandDetails.size(); i++){
                                    totalnbr += currentCommandDetails.get(i).getQauntite() * currentCommandDetails.get(i).getPrice();

                                }
                                total.setText(""+totalnbr+" DZD");
                                Utils.saveCurrentCommandDetails(currentCommandDetails , CommandActivity.this);
                                commandDetailsAdapter.notifyDataSetChanged();
                                mDialogdelete.dismissWithAnimation();
                            }
                        });

                CommandActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialogdelete.show();
                    }

                });

                return true;
            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommandActivity.super.onBackPressed();

            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentCommandDetails.isEmpty()){
                    Utils.setCCsyncable(CommandActivity.this);
                    Utils.saveSync("false", CommandActivity.this);
                }

                CommandActivity.super.onBackPressed();

            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddCommand cdd = new DialogAddCommand(CommandActivity.this,Constants.productmodel,Constants.productrange,Constants.all_Product,currentCommandDetails);
                cdd.setCancelable(false);
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        commandDetailsAdapter.notifyDataSetChanged();

                        float totalnbr = 0 ;
                        for (int i = 0; i < currentCommandDetails.size(); i++){
                            totalnbr += currentCommandDetails.get(i).getQauntite() * currentCommandDetails.get(i).getPrice();

                        }
                        total.setText(""+totalnbr+" DZD");
                    }
                });
                cdd.show();

            }
        });
    }



}
