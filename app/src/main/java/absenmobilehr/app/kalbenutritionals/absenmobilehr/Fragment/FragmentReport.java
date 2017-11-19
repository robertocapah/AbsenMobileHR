package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;

/**
 * Created by Robert on 29/09/2017.
 */

public class FragmentReport extends Fragment {
    View v;
    TableLayout tlReport;
    Button btnSearch, btnHide;
    Spinner spnPeriode;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frament_report, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = getActivity().getApplicationContext();
        spnPeriode = (Spinner) v.findViewById(R.id.spnPeriode);
        btnSearch = (Button) v.findViewById(R.id.btnsearch);
        btnHide = (Button) v.findViewById(R.id.btnHide);
        tlReport = (TableLayout) v.findViewById(R.id.tlResume);

        List<String> arrData = new ArrayList<>();
        arrData.add(0, "Today");
        arrData.add(1, "Last Week");
        arrData.add(2, "Last Month");
        spnPeriode.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));

            List<clsLastCheckingData> datasChecking = null;
            try {
                datasChecking = new clsLastCheckingDataRepo(context).findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            List<clsReportData> datas = new clsReportDataRepo(context).findAll();
            for (clsLastCheckingData data :
                    datasChecking) {
                TableRow tr = new TableRow(getActivity());
                TextView TxtLocation = new TextView(getActivity());
                TextView txtDateCheckin = new TextView(getActivity());
                TextView txtDateCheckout = new TextView(getActivity());
//                TextView txtLongitude = new TextView(getActivity());
//                TextView txtLatitude = new TextView(getActivity());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                TxtLocation.setPadding(5,5,5,5);
                TxtLocation.setText(data.getTxtOutletName());
                TxtLocation.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                txtDateCheckin.setText(data.getDtCheckin());
                txtDateCheckin.setPadding(5,5,5,5);
                txtDateCheckin.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                txtDateCheckout.setText(data.getDtCheckout());
                txtDateCheckout.setPadding(5,5,5,5);
                txtDateCheckout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
//                txtLongitude.setPadding(5,5,5,5);
//                txtLongitude.setText(data.());
//                txtLongitude.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
//                txtLatitude.setText(data.getTxtLatitude());
//                txtLatitude.setPadding(5,5,5,5);
//                txtLatitude.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));

                tr.addView(TxtLocation);
                tr.addView(txtDateCheckin);
                tr.addView(txtDateCheckout);
//                tr.addView(txtLongitude);
//                tr.addView(txtLatitude);
                tlReport.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                String dtCheckin = data.getDtCheckin();
                String dtChekout = data.getDtCheckout();

                Calendar aCalendar = Calendar.getInstance();
                // add -1 month to current month
                aCalendar.add(Calendar.MONTH, -1);
                // set DATE to 1, so first date of previous month
                aCalendar.set(Calendar.DATE, 1);
                Date firstDateOfPreviousMonth = aCalendar.getTime();

                Date cal = Calendar.getInstance().getTime();
//                String c = dateFormat.format(dtCheckin);
//                try {
//                    Date d = dateFormat.parse(c);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = spnPeriode.getSelectedItemPosition();
//                new clsMainActivity().showCustomToast(context, index+"", false);

            }
        });

        return v;
    }

    public class MyAdapter extends ArrayAdapter<String> {
        private List<String> arrayDataAdapyter;
        private Context Ctx;

        public List<String> getArrayDataAdapyter() {
            return arrayDataAdapyter;
        }

        public void setArrayDataAdapyter(List<String> arrayDataAdapyter) {
            this.arrayDataAdapyter = arrayDataAdapyter;
        }

        public Context getCtx() {
            return Ctx;
        }

        public void setCtx(Context ctx) {
            Ctx = ctx;
        }

        public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            if (getArrayDataAdapyter().size() > 0) {
                TextView label = (TextView) row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub = (TextView) row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }
}
