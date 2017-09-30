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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        v = inflater.inflate(R.layout.frament_report,container,false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = getActivity().getApplicationContext();
        spnPeriode = (Spinner) v.findViewById(R.id.spnPeriode);
        btnSearch = (Button) v.findViewById(R.id.btnsearch);
        btnHide = (Button) v.findViewById(R.id.btnHide);

        List<String> arrData=new ArrayList<>();
        arrData.add(0, "Today");
        arrData.add(1, "Last Week");
        arrData.add(2, "Last Month");
        spnPeriode.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = spnPeriode.getSelectedItemPosition();
//                new clsMainActivity().showCustomToast(context, index+"", false);

            }
        });

        return v;
    }
    public class MyAdapter extends ArrayAdapter<String>
    {
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
        public MyAdapter(Context context, int textViewResourceId, List<String> objects)
        {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
            // TODO Auto-generated constructor stub
        }
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.custom_spinner, parent, false);
            if(getArrayDataAdapyter().size()>0){
                TextView label=(TextView)row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub=(TextView)row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }
}
