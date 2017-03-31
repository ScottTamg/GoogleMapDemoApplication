package com.example.demo.googlemapdemoapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker year;
    private NumberPicker month;
    private NumberPicker day;
    private Button button;

    private Calendar mDate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(this);

        year = (NumberPicker)findViewById(R.id.year);
        month = (NumberPicker)findViewById(R.id.month);
        day = (NumberPicker)findViewById(R.id.day);

        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        month.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        day.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        getDate();

        year.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    mDate.set(Calendar.YEAR, view.getValue());
                    mDate.set(Calendar.MONTH, month.getValue() - 1);
                    mDate.set(Calendar.DATE, 1);
                    mDate.roll(Calendar.DATE, -1);
                    day.setMaxValue(mDate.get(Calendar.DATE));
                    day.invalidate();
                }
            }
        });

        month.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    Log.e("scrollState" , "NumberPicker.OnScrollListener.SCROLL_STATE_IDLE");
                    mDate.set(Calendar.YEAR, year.getValue());
                    mDate.set(Calendar.MONTH, view.getValue() - 1);
                    mDate.set(Calendar.DATE, 1);
                    mDate.roll(Calendar.DATE, -1);
                    day.setMaxValue(mDate.get(Calendar.DATE));
                    Log.e("scrollState" , "day == " + mDate.get(Calendar.DATE));
                } else if(scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_FLING) {
                    Log.e("scrollState" , "NumberPicker.OnScrollListener.SCROLL_STATE_FLING");
                } else if(scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Log.e("scrollState" , "NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL");
                }
            }
        });

        day.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {

            }
        });
    }

    private void getDate() {
        mDate = Calendar.getInstance(Locale.CHINA);

        year.setMaxValue(2017);
        year.setMinValue(2016);
        year.setValue(mDate.get(Calendar.YEAR));

        month.setMaxValue(12);
        month.setMinValue(1);
        month.setValue(mDate.get(Calendar.MONTH));

        day.setMaxValue(31);
        day.setMinValue(1);
        day.setValue(mDate.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        TimePickerView pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .build();
        pickerView.setDate(Calendar.getInstance());
        pickerView.show();
    }
}
