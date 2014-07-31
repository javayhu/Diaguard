package com.faltenreich.diaguard.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.helpers.PreferenceHelper;

/**
 * Created by Filip on 16.03.14.
 */
public class DrawerListViewAdapter extends BaseAdapter {

    private Context context;
    private String[] titles;
    private int[] icons;
    public int fragmentCount;

    PreferenceHelper preferenceHelper;

    public DrawerListViewAdapter(Context context, String[] titles, int[] icons, int fragmentCount) {
        this.context = context;
        this.titles = titles;
        this.icons = icons;
        this.fragmentCount = fragmentCount;
        this.preferenceHelper = new PreferenceHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView;
        if(position < fragmentCount) {
            itemView = inflater.inflate(R.layout.drawer_list_item_fragment, parent, false);
        }
        else {
            itemView = inflater.inflate(R.layout.drawer_list_item_activity, parent, false);
            ImageView imgIcon = (ImageView) itemView.findViewById(R.id.icon);
            imgIcon.setImageResource(icons[position - fragmentCount]);
        }

        TextView txtTitle = (TextView) itemView.findViewById(R.id.title);
        txtTitle.setText(titles[position]);
        if(position == preferenceHelper.getStartFragment().ordinal())
            txtTitle.setTypeface(null, Typeface.BOLD);

        return itemView;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}