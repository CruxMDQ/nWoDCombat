package com.emi.nwodcombat.charactercreator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.AfterCreatingRecordListener;
import com.emi.nwodcombat.model.pojos.PersonalityArchetypePojo;
import com.emi.nwodcombat.model.realm.PersonalityArchetype;
import com.emi.nwodcombat.persistence.PersistenceLayer;
import com.emi.nwodcombat.persistence.RealmHelper;

import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Crux on 3/26/2016.
 */
public class AddRecordDialog<T> extends DialogFragment {
    @Bind(R.id.editRecordName) EditText editRecordName;

    private Class<T> klass;
    private String hint;
    private String title;
    private AfterCreatingRecordListener listener;

    AlertDialog dialog;

    public static AddRecordDialog newInstance (
        Class klass,
        String title,
        String hint,
        String[] varArgs
    ) {
        AddRecordDialog fragment = new AddRecordDialog();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_CLASS, klass);
        args.putString(Constants.ARG_TITLE, title);
        args.putString(Constants.ARG_HINT, hint);
        args.putStringArray(Constants.ARG_CLASS_ARGS, varArgs);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddRecordDialog newInstance (
        Class klass,
        String title,
        String hint
    ) {
        AddRecordDialog fragment = new AddRecordDialog();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_CLASS, klass);
        args.putString(Constants.ARG_TITLE, title);
        args.putString(Constants.ARG_HINT, hint);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(getLayout(), null);
        ButterKnife.bind(this, root);

        final Bundle args = getArguments();
        this.klass = (Class) args.getSerializable(Constants.ARG_CLASS);
        this.title = args.getString(Constants.ARG_TITLE);
        this.hint = args.getString(Constants.ARG_HINT);

        editRecordName.setHint(hint);
        editRecordName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] varArgs = args.getStringArray(Constants.ARG_CLASS_ARGS);

                Object record = null;

                if (varArgs != null) {
                    Class[] types = new Class[varArgs.length];
                    for (int i = 0; i < types.length; i++) {
                        types[i] = varArgs[i].getClass();
                    }

                    try {
                        record = Class.forName(klass.getName()).getConstructor(types)
                            .newInstance(varArgs);

                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        record = Class.forName(klass.getName()).newInstance();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                exportRecord(record);
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

    private void exportRecord(Object record) {
        if (record instanceof PersonalityArchetypePojo) {
            PersistenceLayer helper = RealmHelper.getInstance(getActivity());

            ((PersonalityArchetypePojo) record)
                .setName(editRecordName.getText().toString());
            ((PersonalityArchetypePojo) record).setId(helper.getLastId(PersonalityArchetype.class));
        }
        listener.afterCreatingRecord(record);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private int getLayout() {
        return R.layout.dialog_new_record_name;
    }

    public T getInstanceOfT() {
        try {
            return klass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AfterCreatingRecordListener getListener() {
        return listener;
    }

    public void setListener(AfterCreatingRecordListener listener) {
        this.listener = listener;
    }
}
