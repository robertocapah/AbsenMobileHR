package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;


public class FragmentHome extends Fragment {

    View v;
    private Toolbar toolbar;
//    private SortableReportTableView ReportTableView;
    TextView username, branch, outlet, statusAbsen, totalBrand, totalProduct, totalReso, totalActivity, totalCustomerBase, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_home,container,false);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);

        username = (TextView) v.findViewById(R.id.username);
        branch = (TextView) v.findViewById(R.id.branch);
        outlet = (TextView) v.findViewById(R.id.outlet);
        statusAbsen = (TextView) v.findViewById(R.id.statusAbsen);
        totalBrand = (TextView) v.findViewById(R.id.totalBrand);
        totalProduct = (TextView) v.findViewById(R.id.totalProduct);
        totalReso = (TextView) v.findViewById(R.id.totalReso);
        totalActivity = (TextView) v.findViewById(R.id.totalActivity);
        totalCustomerBase = (TextView) v.findViewById(R.id.totalCustomerBase);
        tv_reso1 = (TextView) v.findViewById(R.id.tv_reso1);
        tv_reso2 = (TextView) v.findViewById(R.id.tv_reso2);
        tv_act1 = (TextView) v.findViewById(R.id.tv_act1);
        tv_act2 = (TextView) v.findViewById(R.id.tv_act2);
        tv_cb1 = (TextView) v.findViewById(R.id.tv_cb1);
        tv_cb2 = (TextView) v.findViewById(R.id.tv_cb2);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onHiddenFirstShowcase() {
        Toast.makeText(getContext(), "Jump", Toast.LENGTH_LONG).show();
    }

}
