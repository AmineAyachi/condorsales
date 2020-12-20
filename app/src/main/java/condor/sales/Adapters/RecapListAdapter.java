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
        import condor.sales.Models.Recap;
        import condor.sales.R;

public class RecapListAdapter  extends BaseAdapter {
    private ArrayList<Recap> commandinfos;
    private Context context;
    private int mLastPosition = -1;
    private final Animation mAnimation;
    public RecapListAdapter(ArrayList<Recap> commandinfos, Context context) {
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
        Recap commandinfo = commandinfos.get(position);
        if(commandinfo.getState().equals("found")){
            view = layoutInflater.inflate(R.layout.recap_positif, null);

            TextView sn = view.findViewById(R.id.sn);
            TextView name = view.findViewById(R.id.name);

            sn.setText( ""+commandinfo.getSerial());
            name.setText( ""+commandinfo.getProduct_name());
        }else{
            view = layoutInflater.inflate(R.layout.recap_negative, null);

            TextView sn = view.findViewById(R.id.sn);
            TextView name = view.findViewById(R.id.name);

            sn.setText( ""+commandinfo.getSerial());
            name.setText( "Le sn n'est pas disponible!");
        }






        this.setAnimation(view, position);
        return view;
    }


    //file search result
    public void filterResult(ArrayList<Recap> newcommandInfos) {
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
