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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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
        view.findViewById(R.id.fab_back).setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));


        String VIZEMULTIPLIER = "vizeMultiplier";
        String FINALMULTIPLIER = "finalMultiplier";

        TextInputLayout tilvize = view.findViewById(R.id.vizemult);
        TextInputLayout tilfinal = view.findViewById(R.id.finalmult);

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        int vizeM = sharedPref.getInt(VIZEMULTIPLIER, 30);
        int finalM = sharedPref.getInt(FINALMULTIPLIER, 80);

        Objects.requireNonNull(tilvize.getEditText()).setText(String.valueOf(vizeM));
        Objects.requireNonNull(tilfinal.getEditText()).setText(String.valueOf(finalM));

        tilvize.setError(null);
        tilfinal.setError(null);


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



        FloatingActionButton fab = view.findViewById(R.id.fab_save);

        fab.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            boolean fail = TextUtils.isEmpty(tilvize.getError()) && TextUtils.isEmpty(tilfinal.getError());

            Log.e("fab onclicklistener", "bool fail " + fail);
            /*try {
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
            }*/
            if (fail) {
                int vizemult = Integer.parseInt(tilvize.getEditText().getText().toString());
                editor.putInt(VIZEMULTIPLIER, vizemult);
                int finalmult = Integer.parseInt(tilfinal.getEditText().getText().toString());
                editor.putInt(FINALMULTIPLIER, finalmult);
                editor.apply();
                Log.e("fab save", "saved succesfully");
                Toast.makeText(getContext(),getString(R.string.MultiplierSetToast), Toast.LENGTH_SHORT).show();
//                getActivity().getSupportFragmentManager().popBackStack();
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);

            } else {
                Toast.makeText(getContext(),getString(R.string.MultiplierSaveError), Toast.LENGTH_SHORT).show();
            }

        });

    }
}