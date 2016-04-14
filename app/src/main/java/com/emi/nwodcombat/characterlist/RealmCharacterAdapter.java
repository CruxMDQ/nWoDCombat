package com.emi.nwodcombat.characterlist;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterviewer.CharacterViewerFragment;
import com.emi.nwodcombat.model.realm.Character;

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

        Long id = character.getId();
        String name = character.getName();

        viewHolder.idCharacter = id;
        viewHolder.rowCharacterName.setText(name);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class ViewHolder extends RealmViewHolder {
        long idCharacter;

        // TODO VSM: take a look Calligraphy library https://github.com/chrisjenx/Calligraphy
        @Bind(R.id.rowCharacterName)
        TextView rowCharacterName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.rowCharacterName)
        public void onCharacterNameClick() {
            Bundle args = new Bundle();
            args.putLong(Constants.FIELD_ID, idCharacter);

            CharacterViewerFragment fragment = new CharacterViewerFragment();
            fragment.setArguments(args);

            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment)
                .addToBackStack(Constants.TAG_FRAG_CHARACTER_VIEWER).commit();
        }
    }
}
