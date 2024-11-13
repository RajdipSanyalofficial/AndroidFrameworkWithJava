package com.example.applicationpage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This is a dialog from a fragment.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle OK button action
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle Cancel action
                });
        return builder.create();
    }
}

