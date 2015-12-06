package com.heinrichreimersoftware.singleinputform.steps;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.heinrichreimersoftware.singleinputform.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateStep extends TextStep{

	public static final String DATA_YEAR = "data_year";
	public static final String DATA_MONTH = "data_month";
	public static final String DATA_DAY = "data_day";

	private int mYear;
	private int mMonth;
	private int mDay;

	private StepChecker mChecker;

	public DateStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId, StepChecker checker, TextView.OnEditorActionListener l){
		super(context, dataKey, InputType.TYPE_NULL, titleResId, errorResId, detailsResId, new TextStep.StepChecker(){
			@Override
			public boolean check(String input){
				return !TextUtils.isEmpty(input);
			}
		}, l);

		mChecker = checker;

		if(!(context instanceof FragmentActivity)){
			throw new ClassCastException("context has to implement FragmentActivity");
		}

		final FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

		setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){

                Calendar initial = new GregorianCalendar();

                if(mYear >= 0){
                    initial.set(Calendar.YEAR, mYear);
                }
                if(mMonth >= 0){
                    initial.set(Calendar.MONTH, mMonth);
                }
                if(mDay >= 0){
                    initial.set(Calendar.DAY_OF_MONTH, mDay);
                }

				new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        updateText();
                    }
                }, initial.get(Calendar.YEAR), initial.get(Calendar.MONTH), initial.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	public DateStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId, TextView.OnEditorActionListener l){
		this(context, dataKey, titleResId, errorResId, detailsResId, new StepChecker(){
			@Override
			public boolean check(int year, int month, int day){
				return true;
			}
		}, l);
	}

	public DateStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId, StepChecker checker){
		this(context, dataKey, titleResId, errorResId, detailsResId, checker, null);
	}

	public DateStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId){
		this(context, dataKey, titleResId, errorResId, detailsResId, new StepChecker(){
			@Override
			public boolean check(int year, int month, int day){
				return true;
			}
		}, null);
	}

	public DateStep(Context context, String dataKey, String title, String error, String details, StepChecker checker, TextView.OnEditorActionListener l){
		super(context, dataKey, InputType.TYPE_NULL, title, error, details, new TextStep.StepChecker(){
			@Override
			public boolean check(String input){
				return !TextUtils.isEmpty(input);
			}
		}, l);

		mChecker = checker;

		if(!(context instanceof FragmentActivity)){
			throw new ClassCastException("context has to implement FragmentActivity");
		}

		final FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

		setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){

                Calendar initial = new GregorianCalendar();

                if(mYear >= 0){
                    initial.set(Calendar.YEAR, mYear);
                }
                if(mMonth >= 0){
                    initial.set(Calendar.MONTH, mMonth);
                }
                if(mDay >= 0){
                    initial.set(Calendar.DAY_OF_MONTH, mDay);
                }

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        updateText();
                    }
                }, initial.get(Calendar.YEAR), initial.get(Calendar.MONTH), initial.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	public DateStep(Context context, String dataKey, String title, String error, String details, TextView.OnEditorActionListener l){
		this(context, dataKey, title, error, details, new StepChecker(){
			@Override
			public boolean check(int year, int month, int day){
				return true;
			}
		}, l);
	}

	public DateStep(Context context, String dataKey, String title, String error, String details, StepChecker checker){
		this(context, dataKey, title, error, details, checker, null);
	}

	public DateStep(Context context, String dataKey, String title, String error, String details){
		this(context, dataKey, title, error, details, new StepChecker(){
			@Override
			public boolean check(int year, int month, int day){
				return true;
			}
		}, null);
	}

	public static int year(Bundle data, String dataKey){
		int year = Integer.MIN_VALUE;
		if(data != null && data.containsKey(dataKey)){
			Bundle bundleYear = data.getBundle(dataKey);
			if(bundleYear != null){
				year = bundleYear.getInt(DATA_YEAR, Integer.MIN_VALUE);
			}
		}
		return year;
	}

	public static int month(Bundle data, String dataKey){
		int month = Integer.MIN_VALUE;
		if(data != null && data.containsKey(dataKey)){
			Bundle bundleMonth = data.getBundle(dataKey);
			if(bundleMonth != null){
				month = bundleMonth.getInt(DATA_MONTH, Integer.MIN_VALUE);
			}
		}
		return month;
	}

	public static int day(Bundle data, String dataKey){
		int day = Integer.MIN_VALUE;
		if(data != null && data.containsKey(dataKey)){
			Bundle bundleDay = data.getBundle(dataKey);
			if(bundleDay != null){
				day = bundleDay.getInt(DATA_DAY, Integer.MIN_VALUE);
			}
		}
		return day;
	}

	private void updateText(){
		String output = "";
		if(mYear >= 0 && mMonth >= 0 && mDay >= 0){
			output = getContext().getString(R.string.date_format, mYear, mMonth + 1, mDay);
		}
		setText(output);
	}

	@Override
	public boolean check(){
		return mChecker.check(mYear, mMonth, mDay);
	}

	@Override
	protected void onSave(){
		data().putInt(DATA_YEAR, mYear);
		data().putInt(DATA_MONTH, mMonth);
		data().putInt(DATA_DAY, mDay);
	}

	@Override
	protected void onRestore(){
		mYear = data().getInt(DATA_YEAR, -1);
		mMonth = data().getInt(DATA_MONTH, -1);
		mDay = data().getInt(DATA_DAY, -1);
		updateText();
	}

	public interface StepChecker{
		boolean check(int year, int month, int day);
	}
}
