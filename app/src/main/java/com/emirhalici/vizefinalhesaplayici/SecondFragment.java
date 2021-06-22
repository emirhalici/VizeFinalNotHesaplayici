package com.emirhalici.vizefinalhesaplayici;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

        TextInputLayout tilvize = view.findViewById(R.id.til_vizemult);
        TextInputLayout tilfinal = view.findViewById(R.id.til_finalmult);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int vizeM = sharedPref.getInt("vizeMultiplier", 30);
        int finalM = sharedPref.getInt("finalMultiplier", 80);

        tilvize.getEditText().setText(String.valueOf(vizeM));
        tilfinal.getEditText().setText(String.valueOf(finalM));


    }
}