package com.emi.nwodcombat.characterwizard.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Entry;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmViewHolder;

/**
 * Created by emiliano.desantis on 03/06/2016.
 */
public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>{
    private Activity activity;
    private int idLayout;
    private Bus bus;

    private RealmList<Entry> specialties;

    public SpecialtyAdapter(RealmList<Entry> specialties, Activity activity, int idLayout, Bus bus) {
//        super (activity, specialties, automaticUpdate, animateIdType);
        this.specialties = specialties;
        this.activity = activity;
        this.idLayout = idLayout;
        this.bus = bus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View view = inflater.inflate(idLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Entry specialty = specialties.get(position);
//        final Long id = specialty.getId();
        viewHolder.rowSpecialtyName.setText(specialty.getValue());
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }

    public class ViewHolder extends RealmViewHolder {
        @Bind(R.id.rowSpecialtyName) TextView rowSpecialtyName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
