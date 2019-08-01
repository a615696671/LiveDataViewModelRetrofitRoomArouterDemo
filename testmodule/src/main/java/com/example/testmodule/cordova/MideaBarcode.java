package com.example.testmodule.cordova;

import android.app.Dialog;
import android.content.DialogInterface;


import androidx.appcompat.app.AlertDialog;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class MideaBarcode extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {
        try {
            if ("scan".equals(action)) {
                //调用android的dialog
                startDialog(callbackContext,args);
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }


    /**
     * 通过js调用java，实现js触发android功能
     * @param callbackContext
     */
    private void startDialog(final CallbackContext callbackContext,JSONArray jsonArray) {
        new AlertDialog.Builder(cordova.getActivity())
                .setTitle("提示")
                .setMessage("接收到js的触发"+jsonArray.toString())
                .setPositiveButton("确定", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }


}
