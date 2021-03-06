package com.emi.nwodcombat.combat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.interfaces.OnChoicePickedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/23/16.
 */
public class RadioAdapter<Rule> extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    private final OnChoicePickedListener mListener;
    public int mSelectedItem = -1;
    public final ArrayList<Rule> mItems;
    private final Context mContext;

    public RadioAdapter(Context context, ArrayList<Rule> items, OnChoicePickedListener listener) {
        mListener = listener;
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.mRadio.setText(Html.fromHtml(mItems.get(position).toString()));
        viewHolder.mRadio.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.row_choice_single, viewGroup, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rdbChoice)

        public RadioButton mRadio;

        public ViewHolder(final View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);

            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, mItems.size());
                    mListener.onChoicePicked(mItems.get(mSelectedItem));
                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }
}
