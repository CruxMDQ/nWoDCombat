package com.emi.nwodcombat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.rules.Rule;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * Implements item 1 from Effective Java, 2nd Edition
 */
final public class MeritsAdapter extends RecyclerView.Adapter<MeritsAdapter.ViewHolder> {
    private final Context context;
    private final int idLayout;
    private final Bus bus;

    private OrderedRealmCollection<Rule> merits;

    public static MeritsAdapter newInstance(OrderedRealmCollection<Rule> data, Context context, Bus busInstance) {
        return new MeritsAdapter(data, context, busInstance);
    }

    private MeritsAdapter(OrderedRealmCollection<Rule> data, Context context, Bus bus) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        final Rule merit = merits.get(position);

        holder.displayCurrentAndPossiblePointValues(inflater, merit, 0);

        holder.btnMeritIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new Events.MeritValueChanged(holder, merit, true));
            }
        });

        // This button should ONLY be visible if the character already has the merit
         holder.btnMeritDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new Events.MeritValueChanged(holder, merit, false));
            }
        });
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

    public void setMerits(OrderedRealmCollection<Rule> merits) {
        this.merits = merits;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RealmViewHolder {
        Context context;

        @BindView(R.id.btnMeritDecrease) Button btnMeritDecrease;
        @BindView(R.id.btnMeritIncrease) Button btnMeritIncrease;
        @BindView(R.id.panelEditMerit) LinearLayout panelEditMerit;
        @BindView(R.id.panelMeritValue) LinearLayout panelMeritValue;
        @BindView(R.id.txtMeritName) TextView txtMeritName;
        @BindView(R.id.txtMeritDescription) TextView txtMeritDescription;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        public void displayCurrentAndPossiblePointValues(LayoutInflater inflater, Rule merit, int currentValue) {
            txtMeritName.setText(merit.getName());
            txtMeritDescription.setText(merit.getHint());

            panelMeritValue.removeAllViews();

            // TODO Consider whether this value here should be a constant
            int margin = calculateMargin(5);

            LinearLayout container = createContainer(generateParams(margin, margin, margin, margin));

            showCurrentValue(inflater, currentValue, margin, container);

            TextView txtOpen = new TextView(context);
            txtOpen.setText("(");

            container.addView(txtOpen);

            if (ArrayHelper.isIncreasingAndContiguous(merit.getLevels()) && merit.getLevels().size() > 1) {
                assembleContiguous(inflater, merit, margin, container);
            }
            else
            {
                assembleNonContiguous(inflater, merit, container);
            }

            TextView txtClose = new TextView(context);
            txtClose.setText(")");

            container.addView(txtClose);

            panelMeritValue.addView(container);
        }

        private void assembleNonContiguous(LayoutInflater inflater, Rule merit, LinearLayout container) {
            for (Integer level : merit.getLevels()) {
                for (int i = 0; i < level; i++) {
                    inflater.inflate(R.layout.dot_empty, container, true);
                }

                if (merit.getLevels().indexOf(level) < merit.getLevels().size() - 1) {
                    TextView txtSeparator = new TextView(context);
                    txtSeparator.setText(", ");

                    container.addView(txtSeparator);
                }
            }
        }

        private void assembleContiguous(LayoutInflater inflater, Rule merit, int margin, LinearLayout container) {
            int min = Collections.min(merit.getLevels());
            int max = Collections.max(merit.getLevels());

            for (int i = 0; i < min; i++) {
                inflater.inflate(R.layout.dot_empty, container, true);
            }

            TextView txtTo = new TextView(context);
            txtTo.setText(R.string.to);
            txtTo.setLayoutParams(generateParams(margin, 0, margin, 0));

            container.addView(txtTo);

            for (int i = 0; i < max; i++) {
                inflater.inflate(R.layout.dot_empty, container, true);
            }
        }

        private void showCurrentValue(LayoutInflater inflater, int currentValue, int margin, LinearLayout container) {
            if (currentValue > 0) {

                LinearLayout subContainer = createContainer(generateParams(margin, 0, margin, 0));

                for (int i = 0; i < currentValue; i++) {
                    inflater.inflate(R.layout.dot_solid, subContainer, true);
                }

                container.addView(subContainer);
            }
        }

        private int calculateMargin(int dpValue) {
            float d = context.getResources().getDisplayMetrics().density;
            return (int)(dpValue * d);
        }
    }
}
