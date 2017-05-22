package com.uni7.estagio.antifurto.funcionalidade;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Breno Luan on 22/05/2017.
 */

public class InformacaoAparelho {
    Context context;
    public InformacaoAparelho(Context context){
        this.context = context;
    }

    public String getIMEI(){
        String imei;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();

        return imei;
    }

}
