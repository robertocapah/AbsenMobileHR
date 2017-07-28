package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDisplayPictureRepo;

/**
 * Created by ASUS ZE on 23/08/2016.
 */
public class tDisplayPictureBL extends clsMainBL{

    public void saveData(Context context,List<clsDisplayPicture> Listdata){
        /*SQLiteDatabase db=getDb();
        tDisplayPictureDA _tDisplayPictureDA = new tDisplayPictureDA(db);
        for(tDisplayPictureData data:Listdata){
            _tDisplayPictureDA.SaveData(db, data);
        }*/
        clsDisplayPictureRepo displayPictureRepo = new clsDisplayPictureRepo(context);
        for (clsDisplayPicture data : Listdata){
            try {
                displayPictureRepo.createOrUpdate(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public clsDisplayPicture getData(Context context){
//        SQLiteDatabase db=getDb();
//        tDisplayPictureDA _tDisplayPictureDA = new tDisplayPictureDA(db);
//        tDisplayPictureData data=_tDisplayPictureDA.getData(db);
        List<clsDisplayPicture> data = new ArrayList<>();
        try {
            data = (List<clsDisplayPicture>) new clsDisplayPictureRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data.get(0);
    }
}
