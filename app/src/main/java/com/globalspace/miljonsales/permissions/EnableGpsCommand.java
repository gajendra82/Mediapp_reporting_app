package com.globalspace.miljonsales.permissions;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

public class EnableGpsCommand extends CancelCommand{
	public EnableGpsCommand( Activity activity) {
        super(activity);
    }
	public void execute()
    {
        // take the user to the phone gps settings and then start the asyncronous logic.
        m_activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        super.execute();
    }

}
