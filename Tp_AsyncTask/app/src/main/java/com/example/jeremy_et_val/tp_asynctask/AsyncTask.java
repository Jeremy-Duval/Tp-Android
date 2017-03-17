package com.example.jeremy_et_val.tp_asynctask;

import android.widget.TextView;

/**
 * Created by jeremy on 10/03/17.
 */

public class AsyncTask extends android.os.AsyncTask<Object,Void,String> {
    String chaine;
    TextView tv;

    @Override
    protected String doInBackground(Object... params) {
        chaine = (String) params[0];
        tv = (TextView) params[1];
        // connexions http, traitements lourds, etc.

        return null;
    }
}
