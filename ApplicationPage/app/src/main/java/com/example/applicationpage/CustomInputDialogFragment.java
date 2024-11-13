package com.example.applicationpage;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomInputDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input, null);
        EditText inputField = view.findViewById(R.id.inputField);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Input Dialog")
                .setPositiveButton("Submit", (dialog, which) -> {
                    String input = inputField.getText().toString();
                    // Handle input
                })
                .setNegativeButton("Cancel", null);
        return builder.create();
    }
}

