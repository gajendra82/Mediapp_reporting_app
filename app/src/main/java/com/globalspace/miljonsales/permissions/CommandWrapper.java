package com.globalspace.miljonsales.permissions;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class CommandWrapper implements OnClickListener {

	private CancelCommand command;

	public CommandWrapper(CancelCommand cancelCommand) {
	    this.command = cancelCommand;
	  }
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		    command.execute();
	}

}
