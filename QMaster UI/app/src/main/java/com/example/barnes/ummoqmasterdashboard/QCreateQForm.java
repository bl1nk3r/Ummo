package com.example.barnes.ummoqmasterdashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.heinrichreimersoftware.singleinputform.SingleInputFormActivity;
import com.heinrichreimersoftware.singleinputform.steps.CheckBoxStep;
import com.heinrichreimersoftware.singleinputform.steps.SeekBarStep;
import com.heinrichreimersoftware.singleinputform.steps.Step;
import com.heinrichreimersoftware.singleinputform.steps.TextStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barnes on 10/27/15.
 */
public class QCreateQForm extends SingleInputFormActivity
{
    private static final String DATA_QUE_NAME = "Que Name";
    private static final String DATA_QUE_TAG = "Que Tag";
    private static final String DATA_TIME_FRAME_START = "Time Frame Start";
    private static final String DATA_TIME_FRAME_END = "Time Frame End";
    private static final String DATA_QUE_INFO = "Service Name";
    private static final String DATA_LIMIT = "Service Name";

    @Override
    protected List<Step> getSteps(Context context)
    {
        List<Step> steps = new ArrayList<Step>();

        steps.add(
                new TextStep(context, DATA_QUE_NAME, InputType.TYPE_CLASS_TEXT, R.string.qname, R.string.qname_error, R.string.qname_details,new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input)
                    {
                        return input.length() >= 5;
                    }
                })
        );

        steps.add(
                new TextStep(context, DATA_QUE_TAG, InputType.TYPE_CLASS_TEXT, R.string.qTag, R.string.qTag_error, R.string.qTag_details,new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input)
                    {
                        return input.length() >= 5;
                    }
                })
        );

        steps.add(
                new SeekBarStep(context, DATA_TIME_FRAME_START, 150, 180, R.string.qTimeFarmeStart, R.string.qTimeFrameStart_error, R.string.qTimeFrameStart_details, new SeekBarStep.StepChecker() {
                    @Override
                    public boolean check(int progress)
                    {
                        return progress >= 160;
                    }
                })
        );

        steps.add(
                new SeekBarStep(context, DATA_TIME_FRAME_END, 150, 180, R.string.qTimeFarmeEnd, R.string.qTimeFrameEnd_error, R.string.qTimeFrameEnd_details, new SeekBarStep.StepChecker() {
                    @Override
                    public boolean check(int progress)
                    {
                        return progress >= 160;
                    }
                })
        );

        steps.add(
                new TextStep(context, DATA_QUE_INFO, InputType.TYPE_CLASS_TEXT, R.string.qInfo, R.string.qInfo_error, R.string.qInfo_details,new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input)
                    {
                        return input.length() >= 5;
                    }
                })
        );

        steps.add(
                new CheckBoxStep(context, DATA_LIMIT, R.string.q_limit_yes, R.string.qLimit_title, R.string.qLimit_error, R.string.qLimit_details, new CheckBoxStep.StepChecker() {
                    @Override
                    public boolean check(boolean input)
                    {
                        if (!input)
                        {

                        }
                        return input;
                    }
                })
        );

        if (DATA_LIMIT.equals("Service Name"))
        {
            steps.add(
                    new CheckBoxStep(context, DATA_LIMIT, R.string.q_limit_yes, R.string.qLimit_title, R.string.qLimit_error, R.string.qLimit_details, new CheckBoxStep.StepChecker() {
                        @Override
                        public boolean check(boolean input)
                        {
                            return input;
                        }
                    })
            );
        }
        /*steps.add(
                new DateStep(context, DATA_KEY_BIRTHDAY, R.string.birthday, R.string.birthday_error, R.string.birthday_details, new DateStep.StepChecker(){
                    @Override
                    public boolean check(int year, int month, int day){
                        Calendar today = new GregorianCalendar();
                        Calendar birthday = new GregorianCalendar(year, month, day);
                        today.add(Calendar.YEAR, -14);
                        return today.after(birthday);
                    }
                })
        );
        steps.add(
                new SeekBarStep(context, DATA_KEY_HEIGHT, 150, 180, R.string.height, R.string.height_error, R.string.height_details, new SeekBarStep.StepChecker() {
                    @Override
                    public boolean check(int progress) {
                        return progress >= 160;
                    }
                })
        );*/
        return steps;
    }

    @Override
    protected void onFormFinished(Bundle data)
    {
        Toast.makeText(this, "Form finished: " +
                        CheckBoxStep.checked(data, DATA_LIMIT) + ", " +
                        TextStep.text(data, DATA_QUE_NAME) + ", " +
                        TextStep.text(data, DATA_QUE_TAG) + ", " +
                        SeekBarStep.progress(data, DATA_TIME_FRAME_START) + ", " +
                        SeekBarStep.progress(data, DATA_TIME_FRAME_END),
                Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "data: " + data.toString());
    }
}
