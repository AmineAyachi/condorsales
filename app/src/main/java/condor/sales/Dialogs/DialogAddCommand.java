package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Adapters.ModelSpinnerAdapter;
import condor.sales.Adapters.ProductSpinnerAdapter;
import condor.sales.Adapters.RangeSpinnerAdapter;
import condor.sales.Models.CommandDetails;
import condor.sales.Models.Product;
import condor.sales.Models.ProductModel;
import condor.sales.Models.ProductRange;
import condor.sales.R;
import condor.sales.Utils.Utils;
import it.sephiroth.android.library.numberpicker.NumberPicker;


public class DialogAddCommand extends Dialog implements android.view.View.OnClickListener{
    public Activity c;
    public Dialog d;
    NumberPicker quantity ;
    ArrayList<ProductModel> models = new ArrayList<ProductModel>();
    ArrayList<ProductRange> ranges = new ArrayList<ProductRange>();
    private Activity activity;
    public Button btn_dialog_ajouter, btn_dialog_cancel;
    Spinner spinnerGamme,spinnerModele,spinnerProduit;
    ModelSpinnerAdapter ModeleAdapter ;
    RangeSpinnerAdapter GammeAdapter ;
    ProductSpinnerAdapter ProduitAdapter;
    public static ArrayList<ProductRange> gamme_list = new ArrayList<ProductRange>();
    public static ArrayList<ProductModel> modele_list = new ArrayList<ProductModel>();
    public static ArrayList<Product> Produit_list = new ArrayList<Product>();

    public static ArrayList<ProductModel> local_modele_list = new ArrayList<ProductModel>();
    public static ArrayList<Product> local_Produit_list = new ArrayList<Product>();
    ArrayList<CommandDetails> currentCommandlist ;
    SweetAlertDialog mDialogexit;

    public DialogAddCommand(Activity a ,ArrayList<ProductModel> models,ArrayList<ProductRange> ranges,ArrayList<Product> products,ArrayList<CommandDetails> arraycommandDetails) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.activity = a;
        currentCommandlist = arraycommandDetails ;
        this.models = models ;
        this.ranges = ranges ;
        gamme_list = ranges ;
        modele_list = models ;
        Produit_list = products ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogaddcommand);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        btn_dialog_ajouter = findViewById(R.id.btn_dialog_valider);
        btn_dialog_ajouter = findViewById(R.id.btn_dialog_valider);
        btn_dialog_cancel = findViewById(R.id.btn_dialog_cancel);
        quantity = findViewById(R.id.quantity);
        btn_dialog_cancel.setOnClickListener(this);
        btn_dialog_ajouter.setOnClickListener(this);
        spinnerGamme= findViewById(R.id.spinnerGamme);
        spinnerModele= findViewById(R.id.spinnerModele);
        spinnerProduit= findViewById(R.id.spinnerProduit);


        GammeAdapter = new RangeSpinnerAdapter(activity,R.layout.spinner_droplist , R.id.modelname ,gamme_list);
        spinnerGamme.setAdapter(GammeAdapter);

        ModeleAdapter = new ModelSpinnerAdapter(activity,R.layout.spinner_droplist , R.id.modelname ,local_modele_list);
        spinnerModele.setAdapter(ModeleAdapter);

       ProduitAdapter = new ProductSpinnerAdapter(activity,R.layout.spinner_droplist , R.id.modelname ,local_Produit_list);
       spinnerProduit.setAdapter(ProduitAdapter);


        spinnerGamme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                local_Produit_list.clear();
                ProduitAdapter.notifyDataSetChanged();
                setmodellocallist(gamme_list.get(i));
                ModeleAdapter.notifyDataSetChanged();
                if(!local_modele_list.isEmpty()){
                    spinnerModele.setSelection(0);
                    setproductlocallist(local_modele_list.get(0));
                    ProduitAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerModele.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               setproductlocallist(local_modele_list.get(i));
                ProduitAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_valider:
                if(local_Produit_list.isEmpty() || local_Produit_list == null || local_Produit_list.size() < spinnerProduit.getSelectedItemPosition() || spinnerProduit.getSelectedItemPosition() < 0 ){

                    break;
                }
                Log.e("list size", ""+local_Produit_list.size() );
                Log.e("spinner", ""+spinnerProduit.getSelectedItemPosition());
                CommandDetails found =  found(currentCommandlist , local_Produit_list.get(spinnerProduit.getSelectedItemPosition()).getProduct_id()) ;
                if(found != null){

                    showsweetdialog();

                    this.dismiss();

              }else{

                 Product p = local_Produit_list.get(spinnerProduit.getSelectedItemPosition()); //getproduct(spinnerProduit.getSelectedItem().toString());

                 if(p != null){

                     ProductModel m = getproductmodelname(p) ;
                     ProductRange r = getproductrangename(m) ;
                     CommandDetails currentcommand = new CommandDetails(p.getProduct_id(),p.getProductname(),m.getName(),r.getName(),quantity.getProgress(),p.getPrix());
                     Log.e("add product with price", ""+p.getPrix());
                     currentCommandlist.add(currentcommand);
                     Utils.saveCurrentCommandDetails(currentCommandlist , getContext());
                     dismiss();
                 }

              }

                break;

            case R.id.btn_dialog_cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    private ProductModel getproductmodelname(Product p) {



        for (final ProductModel m : models) {
            // Access properties of person, usage of getter methods would be good
            if (m.getModel_id().equals(p.getModel_id())) {
                return m;
            }

        }

        return null;
    }

    private ProductRange getproductrangename(ProductModel pm) {



        for (final ProductRange r : ranges) {
            // Access properties of person, usage of getter methods would be good
            if (pm.getRange_id().equals(r.getRange_id())) {
                return r;
            }

        }

        return null;
    }

    private void showsweetdialog() {

       mDialogexit = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);

        mDialogexit.setTitleText("Le produit existe déjà")
                .setContentText("Vous pouvez modifier la quantité en appuyant sur l'élément souhaité dans la liste un appui long supprimera l'élément")
                .setConfirmText("ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        mDialogexit.dismissWithAnimation();
                    }
                });


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMutilLineMesseage();
                mDialogexit.show();
            }

        });

    }

    private CommandDetails found(ArrayList<CommandDetails> currentCommandlist, String pn) {
CommandDetails cdd = null ;
        for (final CommandDetails cd : currentCommandlist) {
            // Access properties of person, usage of getter methods would be good
            if (cd.getProduct_id().equals(pn)) {
                // Found matching person
                cdd =  cd;
                break;
            }
        }
        return cdd ;
    }



    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    private  void setmodellocallist (ProductRange r){

        local_modele_list.clear();
        for (final ProductModel m : modele_list) {
            // Access properties of person, usage of getter methods would be good
            if (r.getRange_id().equals(m.getRange_id())) {
                local_modele_list.add(m);
            }

        }



    }

    private  void setproductlocallist (ProductModel m){

        local_Produit_list.clear();
        for (final Product p : Produit_list) {
            // Access properties of person, usage of getter methods would be good
            if (m.getModel_id().equals(p.getModel_id())) {
                local_Produit_list.add(p);
            }

        }


    }

    private  void setMutilLineMesseage() {
        mDialogexit.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                TextView text = mDialogexit.findViewById(R.id.content_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                text.setSingleLine(false);
                text.setMaxLines(10);
                text.setLines(6);
            }
        });
    }





}
