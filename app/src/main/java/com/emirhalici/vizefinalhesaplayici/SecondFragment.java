package com.emirhalici.vizefinalhesaplayici;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_second).setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));

        String VIZEMULTIPLIER = "vizeMultiplier";
        String FINALMULTIPLIER = "finalMultiplier";

        TextInputLayout tilvize = view.findViewById(R.id.til_vizemult);
        TextInputLayout tilfinal = view.findViewById(R.id.til_finalmult);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int vizeM = sharedPref.getInt(VIZEMULTIPLIER, 30);
        int finalM = sharedPref.getInt(FINALMULTIPLIER, 80);

        tilvize.getEditText().setText(String.valueOf(vizeM));
        tilfinal.getEditText().setText(String.valueOf(finalM));

        FloatingActionButton fab = view.findViewById(R.id.fab_save);

        fab.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            boolean fail = false;
            try {
                tilvize.setErrorEnabled(false);
                int vizemult = Integer.parseInt(tilvize.getEditText().getText().toString());
                editor.putInt(VIZEMULTIPLIER, vizemult);
            } catch (NumberFormatException e) {
                tilvize.setError("Please enter a valid number");
                tilvize.setErrorEnabled(true);
                fail = true;
            }

            try {
                tilfinal.setErrorEnabled(false);
                int finalmult = Integer.parseInt(tilfinal.getEditText().getText().toString());
                editor.putInt(FINALMULTIPLIER, finalmult);
            } catch (NumberFormatException e) {
                tilfinal.setError("Please enter a valid number");
                tilfinal.setErrorEnabled(true);
                fail = true;
            }
            if (!fail) {
                editor.apply();
                Toast.makeText(getContext(),"Multiplier is set successfully", Toast.LENGTH_SHORT).show();
            }

        });

    }
}