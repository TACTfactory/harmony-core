package ${controller_namespace};

import ${namespace}.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ${name}EditActivity extends FragmentActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${name?lower_case}_edit);

        this.setResult(0);
        
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
