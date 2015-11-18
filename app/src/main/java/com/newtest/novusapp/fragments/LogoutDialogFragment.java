package com.newtest.novusapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtest.novusapp.LoginActivity;
import com.newtest.novusapp.MainActivity;
import com.newtest.novusapp.R;
import com.newtest.novusapp.util.SQLiteHandler;
import com.newtest.novusapp.util.SessionManager;

/**
 * Created by ROOT-PC on 28.09.2015.
 */
public class LogoutDialogFragment extends android.support.v4.app.DialogFragment {

    private SQLiteHandler db;

    private SessionManager sessionManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.logout_dialog_fragment, null);
        ((MainActivity)getActivity()).setActionBarTitle(R.string.logout);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            logoutUser();
        }

        v.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        v.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToProfile();
            }
        });

        return v;
    }

    private void logoutUser() {

        sessionManager.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void backToProfile(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }



//    public void onClick(DialogInterface dialog, int which) {
//        int i = 0;
//        switch (which) {
//            case Dialog.BUTTON_POSITIVE:
//                i = R.string.yes;
//                sqLiteHandler.deleteUsers();
//                Intent i = new Intent(,);
//
//                break;
//            case Dialog.BUTTON_NEGATIVE:
//                i = R.string.no;
//
//                break;
//        }
//
//    }

//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        Log.d(LOG_TAG, "Dialog 2: onDismiss");
//    }
//
//    public void onCancel(DialogInterface dialog) {
//        super.onCancel(dialog);
//        Log.d(LOG_TAG, "Dialog 2: onCancel");
//    }
}
