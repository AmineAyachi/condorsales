package condor.sales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import condor.sales.Models.CommandDetails;
import condor.sales.R;

public class DialogComHistoryDetailsAdapter extends BaseAdapter {
    private ArrayList<CommandDetails> commandinfos;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;
    public DialogComHistoryDetailsAdapter(ArrayList<CommandDetails> commandinfos, Context context) {
        this.commandinfos = commandinfos;
        this.context = context;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    @Override
    public int getCount() {
        return commandinfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.command_list_view_layout, null);

       CommandDetails commandinfo = commandinfos.get(position);


        TextView productname = view.findViewById(R.id.comercial_name);
        TextView productquantity = view.findViewById(R.id.qt);

        productquantity.setText( ""+commandinfo.getQauntite());
        productname.setText( ""+commandinfo.getProduct_name());
        this.setAnimation(view, position);
        return view;
    }


    //file search result
    public void filterResult(ArrayList<CommandDetails> newcommandInfos) {
        commandinfos = new ArrayList<>();
        commandinfos.addAll(newcommandInfos);
        notifyDataSetChanged();
    }

    private void setAnimation(View var1, int var2) {
        if (var2 > this.mLastPosition) {
            var1.startAnimation(this.mAnimation);
            this.mLastPosition = var2;
        }
    }
}
