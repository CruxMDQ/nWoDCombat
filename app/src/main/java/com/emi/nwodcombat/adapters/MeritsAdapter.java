package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.rules.Rule;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.squareup.otto.Bus;

import java.util.Collections;

import butterknife.BindView;
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

    public MeritsAdapter(OrderedRealmCollection<Rule> data, Context context, Bus bus) {
        this.context = context;
        this.merits = data;
        this.idLayout = R.layout.row_merit;
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
        LayoutInflater inflater = LayoutInflater.from(context);

        Rule merit = merits.get(position);

        holder.txtMeritName.setText(merit.getName());
        holder.txtMeritDescription.setText(merit.getHint());

        holder.panelMeritValue.removeAllViews();

        int dpValue = 5;
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d);

        if (ArrayHelper.isIncreasingAndContiguous(merit.getLevels()) && merit.getLevels().size() > 1) {
            LinearLayout container = createContainer(generateParams(margin, margin, margin, margin));

            int min = Collections.min(merit.getLevels());
            int max = Collections.max(merit.getLevels());

            TextView txtOpen = new TextView(context);
            txtOpen.setText("(");
            
            container.addView(txtOpen);
            
            for (int i = 0; i < min; i++) {
                inflater.inflate(R.layout.dot_empty, container, true);
            }

            TextView txtTo = new TextView(context);
            txtTo.setText("to");
            txtTo.setLayoutParams(generateParams(margin, 0, margin, 0));
            
            container.addView(txtTo);

            for (int i = 0; i < max; i++) {
                inflater.inflate(R.layout.dot_empty, container, true);
            }

            TextView txtClose = new TextView(context);
            txtClose.setText(")");

            container.addView(txtClose);

            holder.panelMeritValue.addView(container);
        }
        else
        {
            LinearLayout container = createContainer(generateParams(margin, margin, margin, margin));

            for (Integer level : merit.getLevels()) {
                for (int i = 0; i < level; i++) {
                    inflater.inflate(R.layout.dot_empty, container, true);
                }
            }
            holder.panelMeritValue.addView(container);
        }
    }

    @NonNull
    private LinearLayout createContainer(LinearLayout.LayoutParams params) {
        LinearLayout container = new LinearLayout(context);

        container.setLayoutParams(params);

        container.setOrientation(LinearLayout.HORIZONTAL);

        return container;
    }

    @NonNull
    private LinearLayout.LayoutParams generateParams(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(left, top, right, bottom);
        return params;
    }

    @Override
    public int getItemCount() {
        if (merits == null) return 0;
        return merits.size();
    }

    public class ViewHolder extends RealmViewHolder {
        Context context;

        @BindView(R.id.chkMerit) CheckBox checkBox;
        @BindView(R.id.panelMeritValue) LinearLayout panelMeritValue;
        @BindView(R.id.txtMeritName) TextView txtMeritName;
        @BindView(R.id.txtMeritDescription) TextView txtMeritDescription;

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
