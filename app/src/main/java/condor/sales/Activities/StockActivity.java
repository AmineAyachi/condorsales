package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.Locale;
import condor.sales.Adapters.StockListAdapter;
import condor.sales.Constants;
import condor.sales.Models.Stock;
import condor.sales.R;


import static condor.sales.Constants.stock;
import static condor.sales.Utils.Utils.getStock;

public class StockActivity extends AppCompatActivity {
    private ImageView back_btn ;
    public static ImageView mBack, mSearch;
    public static SearchView mSearchView;
    public static TextView mText;
    public static EditText mSearchText;
    public static ImageView mCloseButton;
    StockListAdapter AdapterValideToday;
    ListView customListView;
    private AVLoadingIndicatorView progressBar;
    private ShimmerFrameLayout mShimmerViewContainer;
    boolean operationEnCours = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        stock = getStock(StockActivity.this);
        Log.e("stock List", ""+ Constants.stock.size());
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        progressBar = findViewById(R.id.loading_bar);

        mText = findViewById(R.id.text);
        mBack = findViewById(R.id.back);
        mSearch = findViewById(R.id.search);
        mSearchView = findViewById(R.id.searchView);

        mSearch.setVisibility(View.VISIBLE);
        mText.setVisibility(View.VISIBLE);
        mSearchView.setVisibility(View.GONE);
        mSearchText = mSearchView.findViewById(R.id.search_src_text);

        Typeface mFont = ResourcesCompat.getFont(this, R.font.montserratr);
        mSearchText.setTypeface(mFont);
        mSearchText.setHighlightColor(getResources().getColor(R.color.colorSelection));

        mCloseButton = mSearchView.findViewById(R.id.search_close_btn);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("operationEnCours", "operationEnCours " + operationEnCours);

                if (!operationEnCours) {
                    closeSearch();


                }
            }
        });

        mSearchView.setQueryHint(getString(R.string.hint_recherche_pv));
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.e("operationEnCours", "operationEnCours " + operationEnCours);

                if (!operationEnCours) {

                    /// closeSearch();
                }

                return false;
            }
        });




        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText;
                ArrayList<Stock> newUserInfos = new ArrayList<Stock>();
                for (Stock stock : stock) {
                    String name = stock.getProductname().toLowerCase();
                    String prenom = stock.getProductreference().toLowerCase();
                    String nameprenom = name + " " + prenom;

                    if (nameprenom.contains(newText)) {
                        newUserInfos.add(stock);
                    }
                }

                AdapterValideToday.filterResult(newUserInfos);
                AdapterValideToday.notifyDataSetChanged();

                return false;
            }
        });

        customListView = findViewById(R.id.stock_list_view);

        AdapterValideToday = new StockListAdapter(stock, this);
        customListView.setAdapter(AdapterValideToday);
        Log.e("AdapterValideToday", ""+AdapterValideToday.getCount());
        AdapterValideToday.notifyDataSetChanged();

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             //   Toast.makeText(StockActivity.this,"itemclicked"+i, Toast.LENGTH_SHORT).show();
               // showDialogInformations(Utils.mPointagesList.get(i));

            }
        });

        Locale.setDefault(Locale.FRANCE);
        mBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!operationEnCours) {
                    onBackPressed();
                }

            }
        });


        mSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!operationEnCours) {

                    mSearch.setVisibility(View.GONE);
                    mText.setVisibility(View.GONE);
                    mSearchView.setVisibility(View.VISIBLE);
                    mSearchText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mSearchText, InputMethodManager.SHOW_IMPLICIT);
                    //mSearchView.onActionViewCollapsed();
                }

            }
        });
    }

    private void closeSearch() {

        mSearchView.setVisibility(View.GONE);
        //Find EditText view
        // mSearchText = (EditText) mSearchView.findViewById(R.id.search_src_text);
        //Clear the text from EditText view
        // mSearchText.setText("");

        //Clear query
        mSearchView.setQuery("", false);
        //Collapse the action view
        // mSearchView.onActionViewCollapsed();
        //Collapse the search widget

        mSearchView.clearFocus();
        mSearchText.clearFocus();
        mSearch.setClickable(false);
        mSearch.setVisibility(View.VISIBLE);
        mText.setVisibility(View.VISIBLE);
        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                mSearch.setClickable(true);

            }
        }, 1500);

    }

    @Override
    protected void onResume() {
        stock = getStock(StockActivity.this);
        super.onResume();
    }
}
