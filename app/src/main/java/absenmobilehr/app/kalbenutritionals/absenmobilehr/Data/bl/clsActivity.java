package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.UUID;


/**
 * Created by Rian Andrivani on 11/23/2017.
 */

public class clsActivity extends Activity {
    public Bitmap resizeImageForBlob(Bitmap photo){
        int width = photo.getWidth();
        int height = photo.getHeight();

        int maxHeight = 800;
        int maxWidth = 800;

        Bitmap bitmap;

        if(height > width){
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        }
        else if(height < width){
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        }
        else{
            width = maxWidth;
            height = maxHeight;
        }

        bitmap = Bitmap.createScaledBitmap(photo, width, height, true);

        return bitmap;
    }

    public String GenerateGuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    public String greetings(){
        Calendar today = Calendar.getInstance();
        int hour = today.get(Calendar.HOUR_OF_DAY);
        String greeting = "Welcome, ";
        if(hour > 2 && hour < 12){
            greeting = "Good Morning, ";
        }
        else if(hour >= 12 && hour < 16){
            greeting = "Good Afternoon, ";
        }
        else if(hour >= 16 && hour < 19){
            greeting = "Good Evening, ";
        }
        else if(hour >= 19 && hour < 2){
            greeting = "Good Night, ";
        }
        else{
            greeting = "Welcome, ";
        }

        return greeting;
    }

    public String convertNumberDec(double dec) {
        double harga = dec;
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        String hsl = df.format(harga);

        return hsl;
    }

    private static int dp2px(Context _ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, _ctx.getResources().getDisplayMetrics());
    }


}
