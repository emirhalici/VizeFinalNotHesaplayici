package com.emirhalici.vizefinalhesaplayici;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        TextInputLayout tilVize = view.findViewById(R.id.til_vize);
        TextInputLayout tilFinal = view.findViewById(R.id.til_final);
        SeekBar sbVize = view.findViewById(R.id.sb_vize);
        SeekBar sbFinal = view.findViewById(R.id.sb_final);

        tilVize.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilVize.setError(getString(R.string.GradeOutOfBorder));
                s = s.toString()=="" ? "0" : s.toString();
                if (s.toString()=="" || s.toString().isEmpty()) {s="0";}
                boolean isErrorEnabled = Integer.parseInt(s.toString()) > 100;
                tilVize.setErrorEnabled(isErrorEnabled);
                if (!isErrorEnabled) {
                    sbVize.setProgress(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilFinal.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilFinal.setError(getString(R.string.GradeOutOfBorder));
                s = s.toString()=="" ? "0" : s.toString();
                if (s.toString()=="" || s.toString().isEmpty()) {s="0";}
                boolean isErrorEnabled = Integer.parseInt(s.toString()) > 100;
                tilFinal.setErrorEnabled(isErrorEnabled);
                if (!isErrorEnabled) {
                    sbFinal.setProgress(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}