package st_app_pbru.latteeater.itpbru2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lap324-09 on 6/7/2016 AD.
 */
public class MyManager {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;


    public MyManager(Context context) {
        myOpenHelper= new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }//nConstructor
}//nClass
