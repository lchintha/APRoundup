package com.vrctech.aproundup;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInstance {

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference databaseReferenceTelugu = FirebaseDatabase.getInstance().getReference().child("telugu");

    public static DatabaseReference getDatabaseReference(Activity context){
        if(GlobalMethods.getPreferredLanguage(context).equals("te")) {
            Log.d("LANGUAGE", "TELUGU");
            return databaseReferenceTelugu;
        }else{
            Log.d("LANGUAGE", "ENGLISH");
            return databaseReference;
        }
    }
}
