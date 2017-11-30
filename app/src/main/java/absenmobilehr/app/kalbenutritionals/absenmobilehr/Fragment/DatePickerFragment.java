package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Robert on 20/11/2017.
 */

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private int year, month, day;
    public DatePickerFragment(){}

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate){
        onDateSetListener = onDate;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
//        dpd.getDatePicker().setMaxDate(System.currentTimeMillis()-24*60*60*1000);
        return dpd;
    }
}