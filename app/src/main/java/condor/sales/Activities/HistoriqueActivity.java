package condor.sales.Activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import condor.sales.Adapters.HistoriqueViewPagerAdapter;
import condor.sales.Fragments.CommandFragment;
import condor.sales.Fragments.ReceptionFragment;
import condor.sales.Fragments.VenteFragment;
import condor.sales.R;

public class HistoriqueActivity extends FragmentActivity {
    private ImageView back_btn;
    public int selectedItem ;
    ImageView timepicker;
    TextView historique_title ;
    ViewPager mViewPager;
    HistoriqueViewPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);


        mViewPager = findViewById(R.id.viewpager);
        historique_title = findViewById(R.id.historique_title);
        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);

        mPagerAdapter = new HistoriqueViewPagerAdapter(getSupportFragmentManager(), this,HistoriqueActivity.this);
        viewPagerTab.setCustomTabView(mPagerAdapter);
        mViewPager.setAdapter(mPagerAdapter);
        viewPagerTab.setViewPager(mViewPager);
        timepicker = findViewById(R.id.time_picker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFragmentViewClick();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              if(position == 0 ){
                  historique_title.setText("Historique Commandes");
              }
                if(position == 1 ){
                    historique_title.setText("Historique Reception");
                }
                if(position == 2 ){
                    historique_title.setText("Historique Ventes");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoriqueActivity.super.onBackPressed();

            }
        });


    }

    public void onFragmentViewClick() {
        Fragment fragment = mPagerAdapter.getItem(mViewPager.getCurrentItem());
        if ( fragment != null && fragment.isVisible() && fragment instanceof CommandFragment){
            ((CommandFragment) fragment).onViewClicked();
        }

        if (fragment != null && fragment.isVisible() && fragment instanceof ReceptionFragment){
            ((ReceptionFragment) fragment).onViewClicked();
        }

        if (fragment != null && fragment.isVisible() && fragment instanceof VenteFragment){
            ((VenteFragment) fragment).onViewClicked();
        }

    }



}