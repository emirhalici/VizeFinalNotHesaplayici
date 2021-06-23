package com.emirhalici.vizefinalhesaplayici;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class mBottomSheetDialog extends BottomSheetDialogFragment {
    String VIZEMULTIPLIER = "vizeMultiplier";
    String FINALMULTIPLIER = "finalMultiplier";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        int vizeM = sharedPref.getInt(VIZEMULTIPLIER, 30);
        int finalM = sharedPref.getInt(FINALMULTIPLIER, 80);
        TextInputLayout vizemu = v.findViewById(R.id.vizemult);
        TextInputLayout finalmu = v.findViewById(R.id.finalmult);

        vizemu.getEditText().setText(String.valueOf(vizeM));
        finalmu.getEditText().setText(String.valueOf(finalM));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton saveButton = view.findViewById(R.id.fab_save2);
        TextInputLayout tilvize = view.findViewById(R.id.vizemult);
        TextInputLayout tilfinal = view.findViewById(R.id.finalmult);

        tilvize.setError(null);
        tilfinal.setError(null);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        tilvize.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String d = s.toString().isEmpty() || s.toString().equals("") ? "0" : s.toString();
                int nr = Integer.parseInt(d);
                tilvize.setError(nr>100 || nr==0 ? getString(R.string.MultiplierOutOfBorder) : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilfinal.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String d = s.toString().isEmpty() || s.toString().equals("") ? "0" : s.toString();
                int nr = Integer.parseInt(d);
                tilfinal.setError(nr>100 || nr==0 ? getString(R.string.MultiplierOutOfBorder) : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        saveButton.setOnClickListener(v -> {
            // save it
            SharedPreferences.Editor editor = sharedPref.edit();
            boolean fail = TextUtils.isEmpty(tilvize.getError()) && TextUtils.isEmpty(tilfinal.getError());
            if (fail) {
                int vizemult = Integer.parseInt(tilvize.getEditText().getText().toString());
                editor.putInt(VIZEMULTIPLIER, vizemult);
                int finalmult = Integer.parseInt(tilfinal.getEditText().getText().toString());
                editor.putInt(FINALMULTIPLIER, finalmult);
                editor.apply();
                Log.e("fab save", "saved succesfully");
                Toast.makeText(getContext(),getString(R.string.MultiplierSetToast), Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(),getString(R.string.MultiplierSaveError), Toast.LENGTH_SHORT).show();
            }

        });

    }
}
