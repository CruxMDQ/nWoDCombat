package com.emi.nwodcombat.charactercreator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.adapters.RadioAdapter;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.interfaces.OnChoicePickedListener;
import com.emi.nwodcombat.model.pojos.Rule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class CategorySettingDialog extends DialogFragment implements OnChoicePickedListener {
    @Bind(R.id.rvCategoryOptions) RecyclerView rvCategoryOptions;

    private String title;
    private AfterSettingRulesListener listener;
    private ArrayList<Rule> categories;

    AlertDialog dialog;

    public static CategorySettingDialog newInstance(String title, ArrayList<Rule> categories, AfterSettingRulesListener listener) {
        CategorySettingDialog fragment = new CategorySettingDialog();
        fragment.title = title;
        fragment.listener = listener;
        fragment.categories = categories;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.dialog_categories, null);
        ButterKnife.bind(this, root);

        final RadioAdapter adapter = new RadioAdapter<>(getActivity(), categories, this);

        rvCategoryOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategoryOptions.setAdapter(adapter);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Rule rule = (Rule) adapter.mItems.get(adapter.mSelectedItem);

                rule.setContentDescription(title);

                listener.afterSettingRules(rule);
            }
        });

        alertDialogBuilder.setTitle(title);

        dialog = alertDialogBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setEnabled(false);
            }
        });

        return dialog;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onChoicePicked(Object object) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }
}
