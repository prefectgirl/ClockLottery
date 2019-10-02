package com.example.clocklottery;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Observable;

public class Time extends BaseObservable {
    public String time;

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    public Time(String time) {
        this.time = time;
    }

    public Time() {

    }


}
