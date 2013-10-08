<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="DateTimeWidget">
        <attr name="dateTimeWidget_format24H" format="enum">
        	<enum name="h24" value="1" />
        	<enum name="am_pm" value="2" />
        	<enum name="androidConfig" value="3" />
        </attr>
        <attr name="dateTimeWidget_dateDialogTitle" format="reference|string" />
        <attr name="dateTimeWidget_timeDialogTitle" format="reference|string" />
    </declare-styleable>
    <declare-styleable name="DateWidget">
        <attr name="dateWidget_dialogTitle" format="reference|string" />
    </declare-styleable>
    <declare-styleable name="TimeWidget">
        <attr name="timeWidget_dialogTitle" format="reference|string" />
		<attr name="timeWidget_format24H" format="enum">
        	<enum name="h24" value="1" />
        	<enum name="am_pm" value="2" />
        	<enum name="androidConfig" value="3" />
        </attr>
    </declare-styleable>

	<declare-styleable name="ViewGroup_MarginLayout">
        <attr name="android:layout_margin" />
        <attr name="android:layout_marginLeft" />
        <attr name="android:layout_marginRight" />
        <attr name="android:layout_marginTop" />
        <attr name="android:layout_marginBottom" />
    </declare-styleable>

    <!-- Alignment constants. -->
    <attr name="alignmentMode">

        <!--
        Align the bounds of the children.
        See {@link android.widget.GridLayout#ALIGN_BOUNDS}.
        -->
        <enum name="alignBounds" value="0" />
        <!--
        Align the margins of the children.
        See {@link android.widget.GridLayout#ALIGN_MARGINS}.
        -->
        <enum name="alignMargins" value="1" />
    </attr>

    <declare-styleable name="GridLayout">

        <!--
        The orientation property is not used during layout. It is only used to
        allocate row and column parameters when they are not specified by its children's
        layout paramters. GridLayout works like LinearLayout in this case;
        putting all the components either in a single row or in a single column -
        depending on the value of this flag. In the horizontal case, a columnCount
        property may be additionally supplied to force new rows to be created when a
        row is full. The rowCount attribute may be used similarly in the vertical case.
        The default is horizontal.
        -->
        <attr name="android:orientation" />
        <!-- The maxmimum number of rows to create when automatically positioning children. -->
        <attr format="integer" name="rowCount" />
        <!-- The maxmimum number of columns to create when automatically positioning children. -->
        <attr format="integer" name="columnCount" />
        <!--
        When set to true, tells GridLayout to use default margins when none are specified
        in a view's layout parameters.
        The default value is false.
        See {@link android.widget.GridLayout#setUseDefaultMargins(boolean)}.
        -->
        <attr format="boolean" name="useDefaultMargins" />
        <!--
        When set to alignMargins, causes alignment to take place between the outer
        boundary of a view, as defined by its margins. When set to alignBounds,
        causes alignment to take place between the edges of the view.
        The default is alignMargins.
        See {@link android.widget.GridLayout#setAlignmentMode(int)}.
        -->
        <attr name="alignmentMode" />
        <!--
        When set to true, forces row boundaries to appear in the same order
        as row indices.
        The default is true.
        See {@link android.widget.GridLayout#setRowOrderPreserved(boolean)}.
        -->
        <attr format="boolean" name="rowOrderPreserved" />
        <!--
        When set to true, forces column boundaries to appear in the same order
        as column indices.
        The default is true.
        See {@link android.widget.GridLayout#setColumnOrderPreserved(boolean)}.
        -->
        <attr format="boolean" name="columnOrderPreserved" />
    </declare-styleable>
    
    <declare-styleable name="GridLayout_Layout">

        <!--
        The row boundary delimiting the top of the group of cells
        occupied by this view.
        -->
        <attr format="integer" name="layout_row" />
        <!--
        The row span: the difference between the bottom and top
        boundaries delimiting the group of cells occupied by this view.
        The default is one.
        See {@link android.widget.GridLayout.Spec}.
        -->
        <attr format="integer" min="1" name="layout_rowSpan" />
        <!--
        The column boundary delimiting the left of the group of cells
        occupied by this view.
        -->
        <attr name="layout_column" />
        <!--
        The column span: the difference between the right and left
        boundaries delimiting the group of cells occupied by this view.
        The default is one.
        See {@link android.widget.GridLayout.Spec}.
        -->
        <attr format="integer" min="1" name="layout_columnSpan" />
        <!--
        Gravity specifies how a component should be placed in its group of cells.
        The default is LEFT | BASELINE.
        See {@link android.widget.GridLayout.LayoutParams#setGravity(int)}.
        -->
        <attr name="android:layout_gravity" />
    </declare-styleable>
    
    <declare-styleable name="TableRow_Cell">

        <!-- The index of the column in which this child should be. -->
        <attr format="integer" name="layout_column" />
        <!-- Defines how many columns this child should span.  Must be >= 1. -->
        <attr format="integer" name="layout_span" />
    </declare-styleable><declare-styleable name="ContactListItemView">
        <attr name="list_item_height" format="dimension"/>
        <attr name="list_section_header_height" format="dimension"/>
        <attr name="activated_background" format="reference"/>
        <attr name="section_header_background" format="reference"/>
        <attr name="list_item_divider" format="reference"/>
        <attr name="list_item_padding_top" format="dimension"/>
        <attr name="list_item_padding_right" format="dimension"/>
        <attr name="list_item_padding_bottom" format="dimension"/>
        <attr name="list_item_padding_left" format="dimension"/>
        <attr name="list_item_gap_between_image_and_text" format="dimension"/>
        <attr name="list_item_gap_between_label_and_data" format="dimension"/>
        <attr name="list_item_call_button_padding" format="dimension"/>
        <attr name="list_item_vertical_divider_margin" format="dimension"/>
        <attr name="list_item_presence_icon_margin" format="dimension"/>
        <attr name="list_item_presence_icon_size" format="dimension"/>
        <attr name="list_item_photo_size" format="dimension"/>
        <attr name="list_item_profile_photo_size" format="dimension"/>
        <attr name="list_item_prefix_highlight_color" format="color"/>
        <attr name="list_item_header_text_indent" format="dimension" />
        <attr name="list_item_header_text_color" format="color" />
        <attr name="list_item_header_text_size" format="dimension" />
        <attr name="list_item_header_height" format="dimension" />
        <attr name="list_item_header_underline_height" format="dimension" />
        <attr name="list_item_header_underline_color" format="color" />
        <attr name="list_item_contacts_count_text_color" format="color" />
        <attr name="list_item_text_indent" format="dimension" />
        <attr name="list_item_contacts_count_text_size" format="dimension" />
        <attr name="list_item_data_width_weight" format="integer" />
        <attr name="list_item_label_width_weight" format="integer" />
    </declare-styleable>
</resources>
