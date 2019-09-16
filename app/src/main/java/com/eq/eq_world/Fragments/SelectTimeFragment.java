package com.eq.eq_world.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String selectedTime = "";
    private Button button;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        if(h < 10 && m < 10){
            selectedTime = "0" + h + ":0" + m;
        }
        else if(h < 10){
            selectedTime = "0" + h + ":" + m;
        }
        else if(m < 10){
            selectedTime = h + ":0" + m;
        }
        else{
            selectedTime = h + ":" + m;
        }
        button.setText(selectedTime);
    }

    public void buttonToShow(Button b){
        button = b;
    }
}
