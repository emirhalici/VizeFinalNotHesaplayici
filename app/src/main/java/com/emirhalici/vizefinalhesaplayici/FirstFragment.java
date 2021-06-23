package com.emirhalici.vizefinalhesaplayici;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class FirstFragment extends Fragment {

    int MIDTERM_MULTIPLIER = 30;
    int FINAL_MULTIPLIER = 80;

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

        view.findViewById(R.id.fab_settings).setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));


        TextInputLayout tilVize = view.findViewById(R.id.til_vize);
        TextInputLayout tilFinal = view.findViewById(R.id.til_final);
        SeekBar sbVize = view.findViewById(R.id.sb_vize);
        SeekBar sbFinal = view.findViewById(R.id.sb_final);
        TextView tv = view.findViewById(R.id.tv);


        // TextInputLayout - seekBar logic to link the value to each other.
        // i.e. when sbVize gets changed by user, tilVize text is changed accordingly.
        sbVize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Objects.requireNonNull(tilVize.getEditText()).setText(String.valueOf(progress));
                    getAndSetGrades(sbVize, sbFinal, tv);
                    Log.e("vize seekbar", "i've been changed to value " + progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbFinal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Objects.requireNonNull(tilFinal.getEditText()).setText(String.valueOf(progress));
                    getAndSetGrades(sbVize, sbFinal, tv);
                    Log.e("final seekbar", "i've been changed to value " + progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Objects.requireNonNull(tilVize.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilVize.setError(getString(R.string.GradeOutOfBorder));
                s = s.toString().equals("") ? "0" : s.toString();
                if (s.toString().equals("") || s.toString().isEmpty()) {s="0";}
                boolean isErrorEnabled = Integer.parseInt(s.toString()) > 100;
                tilVize.setErrorEnabled(isErrorEnabled);
                if (!isErrorEnabled) {
                    sbVize.setProgress(Integer.parseInt(s.toString()));
                    getAndSetGrades(sbVize, sbFinal, tv);
                    Log.e("vize textbox", "i've been changed to value " + s);
                } else {
                    tv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Objects.requireNonNull(tilFinal.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilFinal.setError(getString(R.string.GradeOutOfBorder));
                s = s.toString().equals("") ? "0" : s.toString();
                if (s.toString().equals("") || s.toString().isEmpty()) {s="0";}
                boolean isErrorEnabled = Integer.parseInt(s.toString()) > 100;
                tilFinal.setErrorEnabled(isErrorEnabled);
                if (!isErrorEnabled) {
                    sbFinal.setProgress(Integer.parseInt(s.toString()));
                    getAndSetGrades(sbVize, sbFinal, tv);
                    Log.e("final textbox", "i've been changed to value " + s);
                } else {
                    tv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void getAndSetGrades(SeekBar sbMidterm, SeekBar sbFinal, TextView textView) {
        int midtermGrade = sbMidterm.getProgress();
        int finalGrade = sbFinal.getProgress();
        float grade = calculateGrade(midtermGrade, finalGrade);
        String gradeLetter = getLetterGrade(grade);
        float gradeToA = gradeNeededForA(midtermGrade);
        setGradeToView(grade, finalGrade, gradeToA, gradeLetter, textView);
    }

    void setGradeToView(float grade, int finalGrade, float gradeForA, String letterGrade, TextView textView) {
        String tv_string = String.format("Grade: %.2f\nGrade letter: %s\n", grade, letterGrade);
        if (finalGrade>gradeForA) {
            tv_string = tv_string + "Congrats on that A.";
        } else if (gradeForA>100) {
            tv_string = tv_string + "Getting an A is impossible at this moment.";
        } else if (gradeForA>0) {
            tv_string = tv_string + String.format("To get an A, final grade should be at least: %.2f\n", gradeForA);
        }
        textView.setText(tv_string);
    }

    String getLetterGrade(float grade) {
        String letterGrade;
        if (grade>=100) {
            letterGrade = "A+";
        } else if (grade>=90) {
            letterGrade = "A";
        } else if (grade>=85) {
            letterGrade = "B1";
        } else if (grade>=80) {
            letterGrade = "B2";
        } else if (grade>=75) {
            letterGrade = "B3";
        } else if (grade>=70) {
            letterGrade = "C1";
        } else if (grade>=65) {
            letterGrade = "C2";
        } else if (grade>=60) {
            letterGrade = "C3";
        } else {
            letterGrade = "F";
        }
        return letterGrade;
    }

    float gradeNeededForA(int midtermGrade) {
        int midtermMultiplier = getActivity().getPreferences(Context.MODE_PRIVATE).getInt("vizeMultiplier", 30);
        int finalMultiplier = getActivity().getPreferences(Context.MODE_PRIVATE).getInt("finalMultiplier", 80);
        return ( ( (float) 90 -  (float) midtermGrade * midtermMultiplier / 100 ) / ( (float) finalMultiplier / 100 ));
    }

    float calculateGrade(int midtermGrade, int finalGrade) {
        int midtermMultiplier = requireActivity().getPreferences(Context.MODE_PRIVATE).getInt("vizeMultiplier", 30);
        int finalMultiplier = requireActivity().getPreferences(Context.MODE_PRIVATE).getInt("finalMultiplier", 80);
        float returnGrade = (float) midtermGrade * midtermMultiplier / 100 + finalGrade * finalMultiplier / 100;
        Log.e("calculateGrade", "calculated grade: " + returnGrade);
        return returnGrade;
    }

}