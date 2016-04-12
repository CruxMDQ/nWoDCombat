package com.emi.nwodcombat.characterlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.widgets.TypeFacedTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class RealmCharacterAdapter extends RealmBasedRecyclerViewAdapter<com.emi.nwodcombat.model.realm.Character, RealmCharacterAdapter.ViewHolder> {
//    public List<com.emi.nwodcombat.model.realm.Character> mItems;
    private Activity activity;
    private int idLayout;

    public RealmCharacterAdapter(RealmResults<Character> results,
                                 Activity activity, int idLayout,
                                 boolean automaticUpdate, boolean animateIdType) {
        super(activity, results, automaticUpdate, animateIdType);
        this.activity = activity;
        this.idLayout = idLayout;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View view = inflater.inflate(idLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        com.emi.nwodcombat.model.realm.Character character = realmResults.get(position);

        String name = character.getName();

        viewHolder.rowCharacterName.setText(name);
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(activity);
//        final View view = inflater.inflate(idLayout, parent, false);
//        return new ViewHolder(view);
//    }

//    @Override
//    public void onBindViewHolder(RealmCharacterAdapter.ViewHolder holder, int position) {
//        com.emi.nwodcombat.model.realm.Character character = mItems.get(position);
//
//        String name = character.getName();
//
//        holder.rowCharacterName.setText(name);
//    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class ViewHolder extends RealmViewHolder {
        // TODO VSM: take a look Calligraphy library https://github.com/chrisjenx/Calligraphy
        @Bind(R.id.rowCharacterName)
        TypeFacedTextView rowCharacterName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // TODO Create onClickListener
        }

        @OnClick(R.id.rowCharacterName)
        public void characterNameChick() {
            // TODO VSM: do what ever your want
        }
    }
}
