package ${localnamespace};

import ${namespace}.R;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ${namespace}.entity.${name};

public class ${name}ShowFragment extends Fragment {

    <#foreach field in fields>
    private ${field}; 
    </#foreach>

	/**
     * Sets up the UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
    	// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_${name?lower_case}_show, container, false);
        
        this.initializeComponent(view);
        
        return view;
    }
    
    protected void initializeComponent(View view) {
    
    }
}