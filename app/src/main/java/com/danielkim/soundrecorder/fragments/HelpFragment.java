package com.danielkim.soundrecorder.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.danielkim.soundrecorder.R;

public class HelpFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View helpView = dialogInflater.inflate(R.layout.fragment_help, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(helpView)
                .setTitle((getString(R.string.dialog_title_help)))
                .setNeutralButton(android.R.string.ok, null);
        return dialogBuilder.create();
    }
}
