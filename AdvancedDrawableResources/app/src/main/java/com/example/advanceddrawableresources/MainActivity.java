package com.example.advanceddrawableresources;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);

        // Set the image view to use the composite drawable
        imageView.setBackgroundResource(R.drawable.composite_drawable);

        // Set button click listeners
        Button btnCopy = findViewById(R.id.btnCopy);
        Button btnPaste = findViewById(R.id.btnPaste);

        btnCopy.setOnClickListener(v -> copyToClipboard());
        btnPaste.setOnClickListener(v -> pasteFromClipboard());
    }

    private void copyToClipboard() {
        String textToCopy = editText.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", textToCopy);
        clipboard.setPrimaryClip(clip);
    }

    private void pasteFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();

        if (clip != null && clip.getItemCount() > 0) {
            String pastedText = clip.getItemAt(0).getText().toString();
            editText.setText(pastedText);
        }
    }
}

