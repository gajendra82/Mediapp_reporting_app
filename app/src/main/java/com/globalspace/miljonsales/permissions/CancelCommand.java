package com.globalspace.miljonsales.permissions;

import android.app.Activity;

public class CancelCommand implements ICommand {
	
	protected Activity m_activity;

    public CancelCommand(Activity activity)
    {
        m_activity = activity;
    }


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
