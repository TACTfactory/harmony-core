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
</resources>
