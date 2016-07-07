package com.emi.nwodcombat.combat.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.dialogs.SingleDiceCalcDialog;
import com.emi.nwodcombat.interfaces.AfterChoosingValueAndIndexListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/29/16.
 */
public class ValuesAdapter<Value> extends RecyclerView.Adapter<ValuesAdapter.ViewHolder> {
    private final ArrayList<Value> mItems;
    private final Activity activity;
    private final int idLayout;

    public ValuesAdapter(Activity activity, int idLayout, ArrayList<Value> mItems) {
        this.activity = activity;
        this.idLayout = idLayout;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View view = inflater.inflate(idLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ValuesAdapter.ViewHolder holder, final int position) {
        com.emi.nwodcombat.model.pojos.Value value = (com.emi.nwodcombat.model.pojos.Value) mItems.get(position);

        String text = value.getName();
        Integer dice = value.getValue();

        holder.txtName.setText(text);
        holder.rowPanelDice.removeAllViews();

        for (int i = 0; i < dice; i++) {
            RadioButton rdb = new RadioButton(activity);

            rdb.setChecked(true);

            rdb.setButtonDrawable(activity.getResources().getDrawable(R.drawable.selector_points));

            holder.rowPanelDice.addView(rdb);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public int getSum() {
        int total = 0;

        for (Value v:
             mItems) {
            total += ((com.emi.nwodcombat.model.pojos.Value) v).getValue();
        }

        return total;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AfterChoosingValueAndIndexListener {
        @Bind(R.id.rowTxtName) public TextView txtName;
        @Bind(R.id.rowPanelDice) public LinearLayout rowPanelDice;

        com.emi.nwodcombat.model.pojos.Value value;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    value = (com.emi.nwodcombat.model.pojos.Value) mItems.get(position);

                    SingleDiceCalcDialog dialog = SingleDiceCalcDialog.newInstance(value.getName(), value.getValue(), position, ViewHolder.this);

                    dialog.show(activity.getFragmentManager(), "tag");
                }
            };
            itemView.setOnClickListener(clickListener);
        }

        @Override
        public void afterChoosingValueAndIndex(int index, int number) {
            ((com.emi.nwodcombat.model.pojos.Value) mItems.get(index)).setValue(number);
            notifyDataSetChanged();
        }
    }
}
