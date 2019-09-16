package com.eq.eq_world.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class SelectDateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private String selectedDate = "";
    private Button button;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        m++;
        if(m < 10 && d < 10){
            selectedDate = y + "-0" + m + "-0" + d;
        }
        else if(m < 10){
            selectedDate = y + "-0" + m + "-" + d;
        }
        else if(d < 10){
            selectedDate = y + "-" + m + "-0" + d;
        }
        else {
            selectedDate = y + "-" + (m + 1) + "-" + d;
        }
        button.setText(selectedDate);
    }

    public void buttonToShow(Button b){
        button = b;
    }
}
