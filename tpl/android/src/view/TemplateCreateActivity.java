package ${controller_namespace};

import ${namespace}.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ${name}CreateActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${name?lower_case}_create);
        
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
}
