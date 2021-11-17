package com.vrctech.aproundup;

import android.app.ProgressDialog;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class NotifyHelper {

    private static ProgressDialog dialog;
    private static boolean isDialogOpen = false;

    public static void showLoading(Context context){
        showLoading(context, R.string.loading);
    }

    public static void showLoading(Context context, int stringId){
        if(!isDialogOpen) {
            try {
                dialog = ProgressDialog.show(context, "", context.getResources().getString(stringId));
            } catch (WindowManager.BadTokenException e){
                toast(context, stringId);
            }
        }
    }

    public static void hideLoading(){
        if(dialog != null) {
            dialog.dismiss();
        }
        isDialogOpen = false;
    }

    public static void toast(Context context, int message){
        if(context != null) {
            try {
                Toast toast = Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundColor(context.getColor(R.color.colorPrimary));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(context.getColor(R.color.white));
                toast.show();
            } catch (RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    public static void snackBar(View contextView, int message){
        Snackbar snackbar = Snackbar.make(contextView, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(contextView.getContext().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
