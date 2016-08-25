package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.rules.Rule;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsAdapter extends RecyclerView.Adapter<MeritsAdapter.ViewHolder> {
    final Context context;
    final int idLayout;
    final Bus bus;

    OrderedRealmCollection<Rule> merits;

    public MeritsAdapter(OrderedRealmCollection<Rule> data, Context context, int idLayout, Bus bus) {
        this.context = context;
        this.merits = data;
        this.idLayout = idLayout;
        this.bus = bus;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//
//        if (convertView == null) {
//            convertView =  inflater.inflate(R.layout.row_merit, parent, false);
//
//            holder = new ViewHolder(context);
//
//            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
//            holder.panelMeritValue = (LinearLayout) convertView.findViewById(R.id.panelMeritValue);
//            holder.txtMeritDescription = (TextView) convertView.findViewById(R.id.txtMeritDescription);
//            holder.txtMeritName = (TextView) convertView.findViewById(R.id.txtMeritName);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        Rule item = adapterData.get(position);
//
//        holder.setValue(item.getLevel());
//        holder.txtMeritName.setText(item.getName());
//        holder.txtMeritDescription.setText(item.getDescription());
//
//        return null;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(idLayout, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RealmViewHolder {
        Context context;

        CheckBox checkBox;
        LinearLayout panelMeritValue;
        TextView txtMeritName;
        TextView txtMeritDescription;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
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
