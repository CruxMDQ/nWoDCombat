package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.model.realm.Virtue;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Crux on 4/10/2016.
 */
public class VirtueRealmAdapter extends RealmBaseAdapter<Virtue> {
    public VirtueRealmAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Virtue item = realmResults.get(position);
        viewHolder.text.setText(item.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView text;
    }
}
