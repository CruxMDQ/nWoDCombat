package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.model.realm.PersonalityArchetype;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public class PersonalityRealmAdapter extends RealmBaseAdapter<PersonalityArchetype> {
    public PersonalityRealmAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PersonalityArchetype item = realmResults.get(position);
        viewHolder.text.setText(item.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView text;
    }

}
