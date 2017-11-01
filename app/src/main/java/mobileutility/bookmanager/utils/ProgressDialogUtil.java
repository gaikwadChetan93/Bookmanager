package mobileutility.bookmanager.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by chegaikw on 7/4/2016.
 */
public class ProgressDialogUtil {
    private ProgressDialog progressDialog;
    public ProgressDialogUtil(Activity activity, String message) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
    }
    public void show(){
        progressDialog.show();
    }
    public void dismiss(){
        progressDialog.dismiss();
    }
}
