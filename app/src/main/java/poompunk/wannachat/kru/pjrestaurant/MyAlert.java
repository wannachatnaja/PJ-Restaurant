package poompunk.wannachat.kru.pjrestaurant;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by icakeeeeeee on 18/3/2559.
 */
public class MyAlert {

    public  void  myDialog(Context context,String strMessage) {
        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
    }
}
