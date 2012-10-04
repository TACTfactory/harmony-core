package ${localnamespace};

import com.tactfactory.mda.test.demact.R;
import com.tactfactory.mda.test.demact.view.comment.CommentListFragment;

import ${namespace}.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ${name}ListActivity extends FragmentActivity {

	public static Context context;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${name?lower_case}_list);
        
        ${name}ListActivity.context = this;
        // Google Analytics
        //GoogleAnalyticsSessionManager.getInstance(getApplication()).incrementActivityCount();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// Google Analytics
		/*GoogleAnalyticsTracker.getInstance().dispatch();
        GoogleAnalyticsSessionManager.getInstance().decrementActivityCount();*/
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    	${name}ListFragment fragment = (${name}ListFragment)  getSupportFragmentManager().findFragmentById(R.id.fragment);
    	fragment.getLoaderManager().restartLoader(0, null, fragment);
    }
	
}