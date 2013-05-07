package ${project_namespace}.harmony.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ${project_namespace}.R;

public class DeleteDialog extends AlertDialog 
									implements DialogInterface.OnClickListener {
	/** A fragment which implements DeletableList. */
	private DeletableList fragment;
	/** DeleteDialog ID. */
	private int id;
	
	/**
	 * Constructor.
	 * @param ctx context
	 * @param fragment fragment
	 * @param id id
	 */
	public DeleteDialog(Context ctx, DeletableList fragment, int id) {
		super(ctx);
		this.fragment = fragment;
		this.id = id;
	}
	
	/* (non-Javadoc).
	 * @see android.app.AlertDialog#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(R.string.dialog_delete_title);
		this.setCancelable(true);
		this.setIcon(0);
		
		LayoutInflater inflater = getLayoutInflater();
		View alertDialogView = 
					inflater.inflate(R.layout.dialog_delete_confirmation, null);	
		
		((TextView) alertDialogView.findViewById(
								 R.id.dialog_delete_confirmation_text)).setText(
				   this.getContext().getString(R.string.dialog_delete_message));
		
		this.setView(alertDialogView);
		
		this.setPositiveButton(android.R.string.ok, this);
		this.setNegativeButton(android.R.string.cancel, this);
		
		super.onCreate(savedInstanceState);
	}

	/** Set a listener to be invoked when the positive button of the dialog is 
	 * pressed. 
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setPositiveButton(int resId, 
									 DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_POSITIVE,
				this.getContext().getString(resId), 
				listener);
	}
	
	/** Set a listener to be invoked when the negative button of the dialog is
	 * pressed. 
	 * @param resId ressource ID
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setNegativeButton(int resId, 
									 DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_NEGATIVE, 
				this.getContext().getString(resId), 
				listener);
	}
	
	/** Called when user clicks. */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
			case BUTTON_POSITIVE:
				this.fragment.delete(this.id);
				break;
				
			case BUTTON_NEGATIVE:
				this.dismiss();
				break;
			default:
				break;
		}
	}
}
