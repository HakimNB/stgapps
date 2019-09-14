package com.hakim.singtel.apiconsumerapp;

import android.util.Log;

public class LogManager {

    public static boolean m_bEnable = true;

    public static void Debug(String szTag, String szMessage) {
        if (m_bEnable) {
            Log.d(szTag, szMessage);
        }
    }

    public static void Error(String szTag, String szMessage, Throwable t) {
        if (m_bEnable) {
            Log.e(szTag, szMessage, t);
        }
    }

}
