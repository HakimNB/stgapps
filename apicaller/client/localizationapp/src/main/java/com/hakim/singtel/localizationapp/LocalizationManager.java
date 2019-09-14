package com.hakim.singtel.localizationapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class LocalizationManager
{
    public enum LANGUAGE_SELECTION {
        LANG_EN,
        LANG_ZH,
        LANG_HI,

        LANG_COUNT,
    }

    public String[] lang_name = { "english", "chinese", "hindi" };

    public class LocalizationEntry {
        private String m_key;
        private String[] m_textEntries;

        public LocalizationEntry(){ m_textEntries = new String[LANGUAGE_SELECTION.LANG_COUNT.ordinal()]; }
        public void parseFromJson(JSONObject json) {
            try {
                m_key = json.getString("key");
            } catch ( Exception e ) { }
            for ( int i = 0; i < LANGUAGE_SELECTION.LANG_COUNT.ordinal(); i++ ) {
                String szKey = lang_name[i];
                try {
                    String szVal = json.getString(szKey);
                    m_textEntries[i] = szVal;
                } catch ( Exception e ) { }
            }
        }
    }

    private Context m_context = null;
    private Vector<LocalizationEntry> m_allEntries = new Vector<LocalizationEntry>();

    private static LocalizationManager m_instance = null;
    public static LocalizationManager GetInstance() {return m_instance;}

    public LocalizationManager(Context activityContext)
    {
        m_context = activityContext;
        if ( m_instance != null ) {
            LogManager.Error(this.getClass().getName(), "Created multiple LocalizationManager!", new IllegalStateException("multiple locman"));
            return;
        }
        m_instance = this;
    }

    public void Initialize(String locFileContent)
    {
        try {
            JSONArray jsonArray = new JSONArray(locFileContent);
            int iLength = jsonArray.length();
            for ( int i = 0; i < iLength; i++ ) {
                JSONObject jsObj = jsonArray.getJSONObject(i);
                if ( jsObj != null ) {
                    LocalizationEntry entry = new LocalizationEntry();
                    entry.parseFromJson(jsObj);
                    m_allEntries.add(entry);
                }
            }
        }
        catch ( Exception e ) {
            LogManager.Error(this.getClass().getName(), "unable to parse localization file", e);
        }
    }

    private Locale getLocaleFromLang(LocalizationManager.LANGUAGE_SELECTION lang) {
        Locale locale = null;
        switch ( lang ) {
            case LANG_EN:
                locale = new Locale("en");
                break;
            case LANG_ZH:
                locale = new Locale("zh");
                break;
            case LANG_HI:
                locale = new Locale("hi");
                break;
            default:
                locale = new Locale("en");
                break;
        }
        return locale;
    }

    private String getStringFromAndroidResource(LocalizationManager.LANGUAGE_SELECTION lang, String szKey)
    {
        Context context = m_context;
        Locale locale = getLocaleFromLang(lang);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        Context localizedContext = context.createConfigurationContext(configuration);
        Resources resources = localizedContext.getResources();
        int iTextResId = context.getResources().getIdentifier(szKey, "string", context.getPackageName());
        String szLocalizedString = resources.getString(iTextResId);
        return szLocalizedString;
    }

    private String getStringFromLocalizationFile(LocalizationManager.LANGUAGE_SELECTION lang, String szKey)
    {
        String szResult = "";
        for ( int i = 0; i < m_allEntries.size(); i++ ) {
            LocalizationEntry entry = m_allEntries.elementAt(i);
            if ( entry != null ) {
                if ( entry.m_key.equalsIgnoreCase(szKey) ) {
                    szResult = entry.m_textEntries[lang.ordinal()];
                    break;
                }
            }
        }
        return szResult;
    }

    public String GetLocalizedString(LocalizationManager.LANGUAGE_SELECTION lang, String szKey)
    {
        String szLocalized = getStringFromLocalizationFile(lang, szKey);
        if ( szLocalized == null || szLocalized.length() == 0 ) {
            szLocalized = getStringFromAndroidResource(lang, szKey);
        }
        return szLocalized;
    }
}
