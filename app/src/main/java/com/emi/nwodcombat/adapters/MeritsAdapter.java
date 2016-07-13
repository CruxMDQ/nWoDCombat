package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Merit;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsAdapter extends RealmBaseAdapter<Merit> {
    private final Context context;

    public MeritsAdapter(Context context, OrderedRealmCollection<Merit> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView =  inflater.inflate(R.layout.row_merit, parent, false);

            holder = new ViewHolder(context);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.panelMeritValue = (LinearLayout) convertView.findViewById(R.id.panelMeritValue);
            holder.txtMeritDescription = (TextView) convertView.findViewById(R.id.txtMeritDescription);
            holder.txtMeritName = (TextView) convertView.findViewById(R.id.txtMeritName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Merit item = adapterData.get(position);

        holder.setValue(item.getCost());
        holder.txtMeritName.setText(item.getName());
        holder.txtMeritDescription.setText(item.getDescription());

        return null;
    }

    private static class ViewHolder {
        final Context context;

        CheckBox checkBox;
        LinearLayout panelMeritValue;
        TextView txtMeritName;
        TextView txtMeritDescription;

        public ViewHolder(Context context) {
            this.context = context;
        }

        public void setValue(int value) {
            panelMeritValue.removeAllViews();

            for (int i = 0; i < value; i++) {
                RadioButton rdb = new RadioButton(context);

                rdb.setChecked(true);

                rdb.setButtonDrawable(context.getResources().getDrawable(R.drawable.selector_points));

                panelMeritValue.addView(rdb);
            }
        }
    }
}
