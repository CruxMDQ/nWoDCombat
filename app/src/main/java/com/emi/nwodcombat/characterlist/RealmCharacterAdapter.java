package com.emi.nwodcombat.characterlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class RealmCharacterAdapter extends RealmBasedRecyclerViewAdapter<Character, RealmCharacterAdapter.CharacterViewHolder> {
    private Activity activity;
    private int idLayout;
    private Bus bus;

    public RealmCharacterAdapter(RealmResults<Character> results,
                                 Activity activity, int idLayout,
                                 boolean automaticUpdate, boolean animateIdType, Bus bus) {
        super(activity, results, automaticUpdate, animateIdType);
        this.activity = activity;
        this.idLayout = idLayout;
        this.bus = bus;
    }

    @Override
    public CharacterViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View view = inflater.inflate(idLayout, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(CharacterViewHolder viewHolder, int position) {
        Character character = realmResults.get(position);
        final Long id = character.getId();
        viewHolder.rowCharacterName.setText(character.getValue(Constants.CHARACTER_NAME, String.class).toString());
        viewHolder.rowCharacterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new Events.CharacterDetail(id));
            }
        });
    }

    public static class CharacterViewHolder extends RealmViewHolder {
        @Bind(R.id.rowCharacterName) TextView rowCharacterName;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
