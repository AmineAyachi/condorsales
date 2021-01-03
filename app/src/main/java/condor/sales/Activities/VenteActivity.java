package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Constants;
import condor.sales.Models.Incentive;
import condor.sales.Models.Reception;
import condor.sales.Models.Stock;
import condor.sales.R;
import condor.sales.Utils.Utils;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;


import static condor.sales.Utils.Utils.getHistorique;
import static condor.sales.Utils.Utils.getReception;
import static condor.sales.Utils.Utils.saveHistorique;
import static condor.sales.Utils.Utils.saveIncentive;
import static condor.sales.Utils.Utils.saveReception;
import static condor.sales.Utils.Utils.saveStock;

public class VenteActivity extends AppCompatActivity {
    public static ArrayList<String> vente = new ArrayList<String>();
    SweetAlertDialog mDialogexit;
    ImageView closevente;
    EditText serialnumber;
    Button done;
    String sn ;
    TextView message;
    Reception rec = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente);
        Constants.Reception = getReception(VenteActivity.this);
        Constants.Historique = getHistorique(VenteActivity.this);
        serialnumber= findViewById(R.id.serailnumber);
        closevente = findViewById(R.id.close_vente);
        done = findViewById(R.id.done_btn);
        message = findViewById(R.id.messagetext);
        sn="";
        sacn();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setEnabled(false);
                String snum = serialnumber.getText().toString();
                if(snum.length() > 5 ){


                    rec = rec(snum ,Constants.Reception);
                    if( rec == null){
//                        message.setTextColor(getResources().getColor(R.color.orange));
//                        message.setText("Le produit scanné n'existe pas dans votre stock");
                        Toast.makeText(VenteActivity.this, "Le produit scanné n'existe pas dans votre stock", Toast.LENGTH_SHORT).show();
                    }else{
                        if( rec.getDate_rec() != null && rec.getDate_vente() == null){
//                            message.setTextColor(getResources().getColor(R.color.green));
//                            message.setText("Le produit "+rec.getCommecial_name()+" a été scanné");
                            Toast.makeText(VenteActivity.this, "L'opération de vente du produit "+rec.getCommecial_name()+" est enregistré avec succès", Toast.LENGTH_SHORT).show();
                            saveVente(snum,Constants.Reception,Constants.Historique);
                            saveInStock(rec);
                            if(rec.isFree()){
                                Log.e("vent activity ", "rec is free");
                            }else{
                                Log.e("vent activity ", "rec is not free incentive is : "+rec.getIncentive());
                                addValueToCurrentIncentive(rec.getIncentive(),Constants.Incentive);
                            }
                            finish();


                        }else{
                            message.setTextColor(getResources().getColor(R.color.orange));
                            Toast.makeText(VenteActivity.this, "Le produit scanné n'existe pas dans votre stock", Toast.LENGTH_SHORT).show();
                        }


                    }


                }
                Log.e("vent activity ", "recpetion list in donebtn"+Constants.Reception);
                finish();

            }
        });

//        serialnumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                String snum = serialnumber.getText().toString();
////                if(snum.length() == 17 ){
////
////
////                    rec = rec(snum ,Constants.Reception);
////                    if( rec == null){
////                        message.setTextColor(getResources().getColor(R.color.orange));
////                        message.setText("Le produit scanné n'existe pas dans votre stock");
////                    }else{
////                        if( rec.getDate_rec() != null && rec.getDate_vente() == null){
////                            message.setTextColor(getResources().getColor(R.color.green));
////                            message.setText("Le produit "+rec.getCommecial_name()+" a été scanné");
////                            sn = snum ;
////
////                        }else{
////                            message.setTextColor(getResources().getColor(R.color.orange));
////                            message.setText("Le produit scanné n'existe pas dans votre stock");
////                        }
////
////
////                    }
////
////
////                }
//
//            }
//        });

        closevente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit ();

            }
        });



    }

    public Reception rec (String refe , ArrayList<Reception> recs) {
        if(refe == null || recs == null){
            return null;

        }else{

            for (final Reception rec : recs) {
                // Access properties of person, usage of getter methods would be good
                if (rec.getSerialnumber().equals(refe)) {
                    // Found matching person
                    return rec;
                }
            }
            return null;
        }

    }
    public void saveVente (String sn, ArrayList<Reception> recs ,ArrayList<Reception> historiquelist) {

        if(sn == null || recs == null){


        }else{
            for (final Reception rec : recs) {
                // Access properties of person, usage of getter methods would be good
                if (rec.getSerialnumber().equals(sn)) {
                    rec.setDate_vente( Calendar.getInstance().getTime());
                    historiquelist.add(rec);
                    recs.remove(rec);
                    Utils.saveSync("false", VenteActivity.this);
                    break;
                }

            }
            saveReception(recs,VenteActivity.this);
            saveHistorique(historiquelist,VenteActivity.this);
        }


    }





    public void saveInStock ( Reception  reception) {

        if( reception == null){


        }else{


            Stock stc = null ;
         for (final Stock stock : Constants.stock) {

             Log.e("val1 ", ""+stock.getProductname() );
             Log.e("val2 ", ""+reception.getCommecial_name() );

             if (reception.getCommecial_name().equals(stock.getProductname())) {

                 stc=stock ;
             }

         }

         if(stc != null){
             if(stc.getQuantity() == 1){
                 Constants.stock.remove(stc);

             }else{
                 if(stc.getQuantity() < 0){

                     Constants.stock.remove(stc);
                 }else{
                     stc.setQuantity(stc.getQuantity()-1);
                 }

             }
         }


            saveStock( Constants.stock,VenteActivity.this);
        }


    }


    @Override
    public void onBackPressed() {
        exit ();


    }
    private void exit () {


        mDialogexit = new SweetAlertDialog(VenteActivity.this, SweetAlertDialog.WARNING_TYPE);

        mDialogexit.setTitleText("Annuler l'operation")
                .setContentText("Voulez-vous vraiment annuler l'operation sans enregistré les données")
                .setConfirmText("Non")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        mDialogexit.dismissWithAnimation();
                    }
                })
                .setCancelText("Oui, Annuler")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        mDialogexit.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        mDialogexit.setContentText(getString(R.string.logout_laoding));
                        mDialogexit.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
                        mDialogexit.setCancelable(false);
                        vente.clear();
                        Toast.makeText(VenteActivity.this, "Opération annulé", Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

        VenteActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialogexit.show();
            }

        });




    }

    public void sacn(){
        ZxingOrient integrator = new ZxingOrient(VenteActivity.this);
        integrator.setIcon(R.drawable.conwhite)  // Sets the custom icon.setIcon(R.drawable.custom_icon)
                .setToolbarColor("#051A42")
                .setInfoBoxColor("#051A42")
                .setInfo("Scanner le numero de serie du produit.")
                .initiateScan();
    }

    public void addValueToCurrentIncentive (float value , ArrayList<Incentive> incetives){

        Log.e("vent activity ", "rec is not free");
        Calendar c = Calendar.getInstance();
        boolean finder = false ;
        for (final Incentive incentive : incetives) {


            if(incentive.getMonth() == c.get(Calendar.MONTH)+1 && incentive.getYear() == c.get(Calendar.YEAR)+1){
                finder= true;
                incentive.setIncentive(incentive.getIncentive()+value);
                Log.e(" 1 Calendar.MONTH", "c.get(Calendar.MONTH)"+c.get(Calendar.MONTH)+"incentive.getMonth()"+incentive.getMonth());
                Log.e(" 1 Calendar.YEAR", "c.get(Calendar.YEAR)"+c.get(Calendar.YEAR)+"incentive.getYear()"+incentive.getYear());
            }

        }
        if(finder == false){
            Incentive incentive = new Incentive(c.get(Calendar.YEAR)+1,c.get(Calendar.MONTH)+1 ,value,false);
            Constants.Incentive.add(incentive);
            Log.e(" 2 Calendar.MONTH", "c.get(Calendar.MONTH)"+c.get(Calendar.MONTH)+"incentive.getMonth()"+incentive.getMonth());
            Log.e(" 2 Calendar.YEAR", "c.get(Calendar.YEAR)"+c.get(Calendar.YEAR)+"incentive.getYear()"+incentive.getYear());
        }

        saveIncentive( Constants.Incentive,VenteActivity.this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {




            if(scanResult.getContents() == null) {
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_LONG).show();

            } else {

                serialnumber.setText(scanResult.getContents());

            }



        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }


    }
}
