package com.google.android.pinnedheader.util;

import android.view.View;
import android.widget.ListView;

import ${project_namespace}.R;

public class ComponentUtils {
    
    public static void configureVerticalScrollbar(ListView listView, int verticalScrollbarPosition) {
        boolean hasScrollbar = true;//isVisibleScrollbarEnabled() && isSectionHeaderDisplayEnabled();

        if (listView != null) {
            listView.setFastScrollEnabled(hasScrollbar);
            listView.setFastScrollAlwaysVisible(hasScrollbar);
            listView.setVerticalScrollbarPosition(verticalScrollbarPosition);
            listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
            int leftPadding = 0;
            int rightPadding = 0;
            if (verticalScrollbarPosition == View.SCROLLBAR_POSITION_LEFT) {
                leftPadding = listView.getContext().getResources().getDimensionPixelOffset(
                        R.dimen.list_visible_scrollbar_padding);
            } else {
                rightPadding = listView.getContext().getResources().getDimensionPixelOffset(
                        R.dimen.list_visible_scrollbar_padding);
            }
            listView.setPadding(leftPadding, listView.getPaddingTop(),
                    rightPadding, listView.getPaddingBottom());
        }
    }
    
    public static boolean isIceCreamSandwich() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        return (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH);
    }
}
