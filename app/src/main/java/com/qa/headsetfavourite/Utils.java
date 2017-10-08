package com.qa.headsetfavourite;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 19-May-15.
 */
public class Utils {

    public static List<AppDetail> getAppDetails(Context context){
        List<AppDetail> appDetails = new ArrayList<>();

        PackageManager manager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> infos = manager.queryIntentActivities(intent,0);
        for (ResolveInfo info: infos){

            Drawable drawable = info.activityInfo.loadIcon(manager);
            String name = info.loadLabel(manager).toString();
            String pkg = info.activityInfo.applicationInfo.packageName;

            Log.v("qnv96:" , "pkg:" + pkg);
            if(pkg.contains("music") || pkg.contains("radio") || pkg.contains("fm")
                    || pkg.contains("phone") || pkg.contains("FM") || pkg.contains("Radio")
                    || name.contains("Phone") || name.contains("Music")) {
                appDetails.add(new AppDetail(name,pkg, drawable));
            }
        }

        return appDetails;
    }

    public static void showToast(Context context, String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
        // toast = null;
    }
}
