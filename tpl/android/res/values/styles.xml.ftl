<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:android="http://schemas.android.com/apk/res/android">

	<style name="TextAppearance.Edit_Spinner" parent="android:style/Widget.Holo.Spinner">
        <item name="android:textSize">18sp</item>
        <!--item name="android:textColor">#FF333333</item-->
        <item name="android:paddingLeft">12dp</item>
        <item name="android:paddingRight">12dp</item>
    </style>
	
	<style name="PinnedTheme" parent="@android:style/Theme.Holo.Light.DarkActionBar">        
        <item name="android:textColorPrimary">@color/primary_text_color</item>
        <item name="android:textColorSecondary">@color/secondary_text_color</item>
<!--         <item name="android:listViewStyle">@android:style/Widget.Holo.Light.ListView</item> -->
<!--         <item name="list_item_height">?android:attr/listPreferredItemHeight</item> -->
        <item name="activated_background">@drawable/list_item_activated_background</item>
        <item name="section_header_background">@drawable/list_title_holo</item>
        <item name="list_section_header_height">24dip</item>
        <item name="list_item_divider">?android:attr/listDivider</item>
        <item name="list_item_padding_top">0dip</item>
        <item name="list_item_padding_right">0dip</item>
        <item name="list_item_padding_bottom">0dip</item>
        <item name="list_item_padding_left">0dip</item>
        <item name="list_item_gap_between_image_and_text">8dip</item>
        <item name="list_item_gap_between_label_and_data">5dip</item>
        <item name="list_item_vertical_divider_margin">5dip</item>
        <item name="list_item_presence_icon_margin">4dip</item>
        <item name="list_item_presence_icon_size">16dip</item>
        <item name="list_item_prefix_highlight_color">#99cc00</item>
        <item name="list_item_header_text_color">#33B5E5</item>
        <item name="list_item_header_text_size">14sp</item>
        <item name="list_item_header_height">26dip</item>
        <item name="list_item_header_underline_height">1dip</item>
        <item name="list_item_header_underline_color">#33B5E5</item>
        <item name="list_item_data_width_weight">5</item>
        <item name="list_item_label_width_weight">3</item>
<!--         <item name="list_item_contacts_count_text_color">@color/contact_count_text_color</item> -->
        <item name="list_item_header_text_indent">8dip</item>
<!--         <item name="contact_browser_list_padding_left">16dip</item> -->
<!--         <item name="contact_browser_list_padding_right">0dip</item> -->
<!--         <item name="contact_browser_background">@android:color/transparent</item> -->
<!--         <item name="list_item_text_indent">@dimen/contact_browser_list_item_text_indent</item> -->
        <item name="list_item_contacts_count_text_size">12sp</item>
    </style>

</resources>
