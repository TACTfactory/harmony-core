<?xml version="1.0" encoding="utf-8"?>
<!-- Copyright (C) 2009 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
-->

<!-- Layout used for list section separators. -->
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"    
    android:layout_width="match_parent"
    android:layout_height="wrap_content"    
    android:background="@drawable/list_section_divider_holo_custom"    >
<!--     android:paddingLeft="?attr/list_item_padding_left" -->
<!--     android:paddingRight="?attr/list_item_padding_right"> -->
<!--     style="@style/DirectoryHeader" -->
<!--     android:minHeight="@dimen/list_section_divider_min_height" -->
<!--     android:paddingTop="@dimen/contact_browser_list_top_margin"> -->
    <TextView
        android:id="@+id/label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:layout_marginLeft="8dip"
        android:textColor="#33B5E5"
        android:singleLine="true"
        android:textStyle="bold"
         />
<!--    android:textAllCaps="true"     android:textAppearance="?android:attr/textAppearanceSmall"/> -->
    <TextView
        android:id="@+id/count"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignBaseline="@id/label"
        android:singleLine="true"
        android:textSize="12sp"/>
<!--         android:textColor="@color/contact_count_text_color" /> -->
    <TextView
        android:id="@+id/display_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@id/label"
        android:layout_toLeftOf="@id/count"
        android:layout_alignBaseline="@id/label"
        android:layout_marginLeft="8dip"
        android:layout_marginRight="8dip"
        android:textColor="#33B5E5"
        android:singleLine="true"
        android:textStyle="bold"
         />
<!--   android:textAllCaps="true"      android:textAppearance="?android:attr/textAppearanceSmall"/> -->
</RelativeLayout>
