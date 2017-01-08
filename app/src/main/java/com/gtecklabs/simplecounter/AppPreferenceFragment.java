package com.gtecklabs.simplecounter;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.os.Bundle;
import android.text.TextUtils;
import com.gtecklabs.simplecounter.util.IntentUtils;
import de.psdev.licensesdialog.LicensesDialog;

public class AppPreferenceFragment extends PreferenceFragmentCompat {

  private Preference mAppVersionPref;
  private Preference mSupportPref;
  private Preference mRatePref;
  private Preference mLicensesPref;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mAppVersionPref = findPreference(R.string.settings_key_app_version);
    mSupportPref = findPreference(R.string.settings_key_support);
    mRatePref = findPreference(R.string.settings_key_rate);
    mLicensesPref = findPreference(R.string.settings_key_licenses);

    mAppVersionPref.setSummary(BuildConfig.VERSION_NAME);
  }

  @Override
  public void onCreatePreferences(Bundle bundle, String rootKey) {
    addPreferencesFromResource(R.xml.app_settings);
  }

  private Preference findPreference(@StringRes int prefKey) {
    return findPreference(getString(prefKey));
  }

  @Override
  public boolean onPreferenceTreeClick(Preference preference) {
    if (!super.onPreferenceTreeClick(preference)) {
      final String prefKey = preference.getKey();
      if (TextUtils.equals(prefKey, mSupportPref.getKey())) {
        final Intent emailIntent = IntentUtils.getEmailIntent(
            BuildConfig.SUPPORT_EMAIL, String.format("%s %s support",
                getString(R.string.app_name), BuildConfig.VERSION_NAME)
        );

        if (IntentUtils.isAvailable(getActivity(), emailIntent)) {
          startActivity(emailIntent);
        }
        return true;
      } else if (TextUtils.equals(prefKey, mRatePref.getKey())) {
        final Intent playStoreIntent = IntentUtils.getPlayStoreIntent();
        if (IntentUtils.isAvailable(getActivity(), playStoreIntent)) {
          startActivity(playStoreIntent);
        }

        return true;
      } else if (TextUtils.equals(prefKey, mLicensesPref.getKey())) {
        new LicensesDialog.Builder(getActivity())
            .setTitle(R.string.settings_title_licenses)
            .setIncludeOwnLicense(true)
            .setNotices(R.raw.licenses)
            .build()
            .show();
        return true;
      }
    }

    return false;
  }

}
