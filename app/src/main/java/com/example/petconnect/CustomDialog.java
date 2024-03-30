package com.example.petconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

//public class CustomDialog {
//    public static void showDialog(Context context, String title, String message) {
//        // Inflate layout custom_dialog_layout.xml into a View
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.update_comment_dialog, null);
//
//        // Find views within the custom layout
//        CustomTextfield commentBox = dialogView.findViewById(R.id.comment_box);
//
//        // Create AlertDialog.Builder and set the custom layout
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(dialogView)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false); // Disable closing dialog when clicking outside or pressing back button
//
//        // Create and show the dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//}
//public class CustomDialog {
//    public static void showDialog(Context context, String title, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do something when the "OK" button is clicked
//                    }
//                })
//                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do something when the "Cancel" button is clicked
//                    }
//                })
//                .show();
//    }
//}
