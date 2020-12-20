package condor.sales.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import condor.sales.Fragments.CommandFragment;
import condor.sales.Fragments.ReceptionFragment;
import condor.sales.Fragments.VenteFragment;
import condor.sales.R;

public class HistoriqueViewPagerAdapter extends FragmentPagerAdapter implements SmartTabLayout.TabProvider {
 public Context context ;
 public static FragmentActivity a ;

    @Override
    public View createTabView(ViewGroup container, int position, androidx.viewpager.widget.PagerAdapter adapter) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.activity_main_tab, null);
        if(v != null) {
            ImageView icon = v.findViewById(R.id.activity_main_tab_icon);
            icon.setImageResource(MainActivityPages.values()[position].resourceId);
        }

        return v;
    }

    public enum MainActivityPages {


        TAB_1(0, "Tab #1", R.drawable.h_command,CommandFragment.newInstance(a)),
        TAB_2(1, "Tab #2", R.drawable.h_reception, ReceptionFragment.newInstance(a)),
        TAB_3(2, "Tab #3", R.drawable.h_vente, VenteFragment.newInstance(a));

        public int index;
        public String title;
        public int resourceId;
        public Fragment fragment;


        MainActivityPages(int index, String title, int resourceId, Fragment fragment) {
            this.index = index;
            this.title = title;
            this.resourceId = resourceId;
            this.fragment = fragment;
        }
    }

    public HistoriqueViewPagerAdapter(FragmentManager fm, Context context , FragmentActivity activity) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        a = activity ;
    }

    @Override
    public Fragment getItem(int position) {
        return MainActivityPages.values()[position].fragment;
    }

    @Override
    public int getCount() {
        return MainActivityPages.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivityPages.values()[position].title;
    }


}