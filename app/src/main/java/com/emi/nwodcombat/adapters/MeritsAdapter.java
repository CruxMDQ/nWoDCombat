package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 02/06/2016.
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(idLayout, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rule merit = merits.get(position);

        holder.txtMeritName.setText(merit.getName());
        holder.txtMeritDescription.setText(merit.getHint());

        holder.panelMeritValue.removeAllViews();

        for (Integer level : merit.getLevels()) {
            LinearLayout container = new LinearLayout(context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            int dpValue = 5;
            float d = context.getResources().getDisplayMetrics().density;
            int margin = (int)(dpValue * d);

            params.gravity = Gravity.CENTER_VERTICAL;
            params.setMargins(margin, 0, 0, margin);

            container.setLayoutParams(params);

            container.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < level; i++) {
                RadioButton rdb = new RadioButton(context);

                rdb.setChecked(false);

                rdb.setButtonDrawable(
                    context.getResources().getDrawable(R.drawable.selector_points));

                rdb.setClickable(false);

                container.addView(rdb);
            }

            holder.panelMeritValue.addView(container);
        }
    }

    @Override
    public int getItemCount() {
        if (merits == null) return 0;
        return merits.size();
    }

    public class ViewHolder extends RealmViewHolder {
        Context context;

        @Bind(R.id.chkMerit) CheckBox checkBox;
        @Bind(R.id.panelMeritValue) LinearLayout panelMeritValue;
        @Bind(R.id.txtMeritName) TextView txtMeritName;
        @Bind(R.id.txtMeritDescription) TextView txtMeritDescription;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    public void setMerits(OrderedRealmCollection<Rule> merits) {
        this.merits = merits;
        this.notifyDataSetChanged();
    }
}
