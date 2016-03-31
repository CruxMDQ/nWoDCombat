package com.emi.nwodcombat.characterlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.widgets.TypeFacedTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class CharacterAdapter<Character> extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    public ArrayList<Character> mItems;
    private Activity activity;
    private int idLayout;

    public CharacterAdapter(Activity activity, int idLayout, ArrayList<Character> mItems) {
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
    public void onBindViewHolder(CharacterAdapter.ViewHolder holder, int position) {
        com.emi.nwodcombat.model.db.Character character = (com.emi.nwodcombat.model.db.Character) mItems.get(position);

        String name = character.getName();

        holder.rowCharacterName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rowCharacterName) TypeFacedTextView rowCharacterName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // TODO Create onClickListener
        }
    }
}