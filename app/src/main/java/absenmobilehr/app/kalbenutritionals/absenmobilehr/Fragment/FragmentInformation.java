package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owater.library.CircleTextView;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Robert on 22/06/2017.
 */

public class FragmentInformation extends Fragment {
    View v;
    TextView tvTotalReso, tvTotalActivity, tvTotalCustomerBase, tvUsername, tvBranchOutlet, tvEmail, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;
    Context context;
    CircleTextView ctvStatus;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_new,container,false);
        context = getActivity().getApplicationContext();
        CircleImageView ivProfile = (CircleImageView) v.findViewById(R.id.profile_image);
        tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        tvBranchOutlet = (TextView) v.findViewById(R.id.tvBranchOutlet);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        ctvStatus = (CircleTextView) v.findViewById(R.id.status);
        clsUserLogin data = new clsUserLoginRepo(context).getDataLogin(context);
        clsAbsenData dataAbsen = new clsAbsenDataRepo(context).getDataCheckinActive(context);

        if (data != null){
            tvUsername.setText(data.getTxtName().toString());
            tvEmail.setText(data.getTxtEmail().toString());
        }
        if (dataAbsen!=null){
            ctvStatus.setText("Checkin");
        }else{
            ctvStatus.setText("Checkout");
        }

        return v;
    }
}
