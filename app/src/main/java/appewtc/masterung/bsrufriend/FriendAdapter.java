package appewtc.masterung.bsrufriend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Phobia on 2/17/2017.
 */

public class FriendAdapter extends BaseAdapter{

    private Context context;
    private String[] iconStrings, nameStrings;
    private TextView textView;
    private ImageView imageView;

    public FriendAdapter(Context context, String[] iconStrings, String[] nameStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.nameStrings = nameStrings;
    }

    @Override
    public int getCount() {
        return iconStrings.length;//นับ list view
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);
        //Bind Widget
        textView = (TextView) view1.findViewById(R.id.name);
        imageView = (ImageView) view1.findViewById(R.id.imageView10);

        //Show View
        textView.setText(nameStrings[i]);
        Picasso.with(context).load(iconStrings[i]).into(imageView);

        Log.d("17febV4", "name(" + i + ")==>" + nameStrings);
        Log.d("17febV4", "image(" + i + ")==>" + iconStrings);
        return view1;
    }
}//Main Class
