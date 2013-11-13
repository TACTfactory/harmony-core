package com.google.android.pinnedheader.headerlist;

import android.text.TextUtils;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A section indexer that is configured with precomputed section titles and
 * their respective counts.
 */
public abstract class HeaderSectionIndexer<T> implements SectionIndexer {

	private List<T> items;
    private ArrayList<String> mSections = new ArrayList<String>();
    private ArrayList<Integer> mPositions = new ArrayList<Integer>();
    private static final String BLANK_HEADER_STRING = " ";
    private int totalCount;
    
    protected HeaderSectionIndexer(List<T> items) {  
    	this.items = items;
    	this.totalCount = items.size();
    	        
    	int size = items.size();
        for (int i = 0; i < size; i++) {
        	String header = this.getHeaderText(items.get(i));
        	        
        	if (!mSections.contains(header)) {        		
	            if (TextUtils.isEmpty(header)) {
	                mSections.add(BLANK_HEADER_STRING);
	            } else if (!header.equals(BLANK_HEADER_STRING)) {
	                mSections.add(header.trim());
	            } else {
	            	mSections.add(header);
	            }
	            
	            mPositions.add(i);
        	}
        }
    }
    
    protected abstract String getHeaderText(T item);
    
    public Object[] getSections() {
        return mSections.toArray();
    }
    
    public List<T> getItems(){
    	return this.items;
    }

    public int getPositionForSection(int section) {
    	if (section < 0 || section >= mPositions.size()) {
            return -1;
        }
    	
    	return mPositions.get(section);
    }

    public int getSectionForPosition(int position) {
        if (position < 0 || position >= this.totalCount) {
            return -1;
        }

        int index = Arrays.binarySearch(mPositions.toArray(), position);

        /*
         * Consider this example: section positions are 0, 3, 5; the supplied
         * position is 4. The section corresponding to position 4 starts at
         * position 3, so the expected return value is 1. Binary search will not
         * find 4 in the array and thus will return -insertPosition-1, i.e. -3.
         * To get from that number to the expected value of 1 we need to negate
         * and subtract 2.
         */
        return index >= 0 ? index : -index - 2;
    }

//    public void setProfileHeader(String header) {
//        if (mSections != null) {
//            // Don't do anything if the header is already set properly.
//            if (mSections.length > 0 && header.equals(mSections[0])) {
//                return;
//            }
//
//            // Since the section indexer isn't aware of the profile at the top, we need to add a
//            // special section at the top for it and shift everything else down.
//            String[] tempSections = new String[mSections.length + 1];
//            int[] tempPositions = new int[mPositions.length + 1];
//            tempSections[0] = header;
//            tempPositions[0] = 0;
//            for (int i = 1; i <= mPositions.length; i++) {
//                tempSections[i] = mSections[i - 1];
//                tempPositions[i] = mPositions[i - 1] + 1;
//            }
//            mSections = tempSections;
//            mPositions = tempPositions;
//            mCount++;
//        }
//    }
}
