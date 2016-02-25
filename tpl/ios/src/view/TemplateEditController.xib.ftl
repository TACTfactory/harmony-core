<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign backgroundView = "${curr.name?cap_first}BackgroundView" />
<#assign scrollView = "${curr.name?cap_first}ScrollView" />
<#assign mainView = "${curr.name?cap_first}MainView" />
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<document type="com.apple.InterfaceBuilder3.CocoaTouch.XIB" version="3.0" toolsVersion="8191" systemVersion="14F27" targetRuntime="iOS.CocoaTouch" propertyAccessControl="none" useAutolayout="YES" useTraitCollections="YES">
    <dependencies>
        <deployment identifier="iOS"/>
        <plugIn identifier="com.apple.InterfaceBuilder.IBCocoaTouchPlugin" version="8154"/>
    </dependencies>
    <objects>
        <placeholder placeholderIdentifier="IBFilesOwner" id="-1" userLabel="File's Owner" customClass="${curr.name?cap_first}EditViewController">
            <connections>
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (field.harmony_type?lower_case == "boolean")>
                        <#assign typeElement = "Switch" />
                    <#else>
                        <#assign typeElement = "TextField" />
                    </#if>
                <outlet property="${field.name}${typeElement}" destination="${curr.name?cap_first}${field.name?cap_first}${typeElement}" id="${curr.name?cap_first}${field.name?cap_first}${typeElement}EditController"/>
                </#if>
            </#list>
                <outlet property="view" destination="${backgroundView}" id="${curr.name?cap_first}EditControllerBackgroundView"/>
            </connections>
        </placeholder>
        <placeholder placeholderIdentifier="IBFirstResponder" id="-2" customClass="UIResponder"/>
        <view clearsContextBeforeDrawing="NO" contentMode="scaleToFill" id="${backgroundView}">
            <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
            <autoresizingMask key="autoresizingMask" widthSizable="YES" heightSizable="YES"/>
            <subviews>
                <scrollView clipsSubviews="YES" multipleTouchEnabled="YES" contentMode="scaleToFill" keyboardDismissMode="onDrag" translatesAutoresizingMaskIntoConstraints="NO" id="${scrollView}">
                    <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
                    <subviews>
                        <view contentMode="scaleToFill" translatesAutoresizingMaskIntoConstraints="NO" id="${mainView}" userLabel="Main View">
                            <rect key="frame" x="0.0" y="0.0" width="414" height="582"/>
                            <subviews>
                                <#assign y = 5 />
                                <#list fields?values as field>
                                    <#if (!field.internal && !field.hidden)>
                                <label opaque="NO" userInteractionEnabled="NO" contentMode="left" horizontalHuggingPriority="251" verticalHuggingPriority="251" text="${field.name}" textAlignment="natural" lineBreakMode="tailTruncation" baselineAdjustment="alignBaselines" adjustsFontSizeToFit="NO" translatesAutoresizingMaskIntoConstraints="NO" id="${curr.name?cap_first}${field.name?cap_first}LabelTitle">
                                    <rect key="frame" x="10" y="${y}" width="100" height="21"/>
                                    <fontDescription key="fontDescription" type="system" pointSize="17"/>
                                    <color key="textColor" red="0.0" green="0.0" blue="0.0" alpha="1" colorSpace="calibratedRGB"/>
                                    <nil key="highlightedColor"/>
                                </label>
                                    <#assign y = y + 26/>
                                        <#if (field.harmony_type?lower_case == "boolean")>
                                <switch opaque="NO" contentMode="scaleToFill" horizontalHuggingPriority="251" verticalHuggingPriority="251" fixedFrame="YES" contentHorizontalAlignment="center" contentVerticalAlignment="center" on="YES" translatesAutoresizingMaskIntoConstraints="NO" id="${curr.name?cap_first}${field.name?cap_first}Switch" >
                                    <rect key="frame" x="10" y="${y}" width="51" height="21"/>
                                </switch>
                                        <#else>
                                <textField opaque="NO" clipsSubviews="YES" contentMode="scaleToFill" contentHorizontalAlignment="left" contentVerticalAlignment="center" borderStyle="roundedRect" textAlignment="natural" minimumFontSize="17" translatesAutoresizingMaskIntoConstraints="NO" id="${curr.name?cap_first}${field.name?cap_first}TextField">
                                    <rect key="frame" x="15" y="${y}" width="384" height="30"/>
                                    <fontDescription key="fontDescription" type="system" pointSize="14"/>
                                    <textInputTraits key="textInputTraits"/>
                                </textField>
                                        </#if>
                                    <#assign y = y + 31/>
                                    </#if>
                                </#list>
                            </subviews>
                            <color key="backgroundColor" white="1" alpha="1" colorSpace="calibratedWhite"/>
                            <constraints>
                                <#assign lastElement = "" />
                                <#list fields?values as field>
                                    <#if (!field.internal && !field.hidden)>
                                        <#if (field.harmony_type?lower_case == "boolean")>
                                            <#assign typeElement = "Switch" />
                                        <#else>
                                            <#assign typeElement = "TextField" />
                                        </#if>
                                        <#if lastElement == "">
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}LabelTitle" firstAttribute="leading" secondItem="${mainView}" secondAttribute="leading" constant="10" id="${curr.name?cap_first}${field.name?cap_first}LabelTitleLeading${mainView}"/>
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}LabelTitle" firstAttribute="top" secondItem="${mainView}" secondAttribute="top" constant="5" id="${curr.name?cap_first}${field.name?cap_first}LabelTitleTop${mainView}"/>
                                        <#else>
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}LabelTitle" firstAttribute="leading" secondItem="${mainView}" secondAttribute="leading" constant="10" id="${curr.name?cap_first}${field.name?cap_first}LabelTitleLeading${mainView}"/>
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}LabelTitle" firstAttribute="top" secondItem="${lastElement}" secondAttribute="bottom" constant="5" id="${curr.name?cap_first}${field.name?cap_first}LabelTitleTop${lastElement}"/>
                                        </#if>
                                <constraint firstAttribute="trailing" secondItem="${curr.name?cap_first}${field.name?cap_first}${typeElement}" secondAttribute="trailing" constant="15" id="${curr.name?cap_first}${field.name?cap_first}${typeElement}Trailing"/>
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}${typeElement}" firstAttribute="leading" secondItem="${mainView}" secondAttribute="leading" constant="15" id="${curr.name?cap_first}${field.name?cap_first}${typeElement}Leading${mainView}"/>
                                <constraint firstItem="${curr.name?cap_first}${field.name?cap_first}${typeElement}" firstAttribute="top" secondItem="${curr.name?cap_first}${field.name?cap_first}LabelTitle" secondAttribute="bottom" constant="5" id="${curr.name?cap_first}${field.name?cap_first}${typeElement}Top${curr.name?cap_first}${field.name?cap_first}LabelTitle"/>
                                        <#assign lastElement = "${curr.name?cap_first}${field.name?cap_first}${typeElement}" />
                                        <#if !(field_has_next)>
                                <constraint firstAttribute="bottom" secondItem="${curr.name?cap_first}${field.name?cap_first}${typeElement}" secondAttribute="bottom" constant="10" id="${curr.name?cap_first}${field.name?cap_first}${typeElement}Bottom"/>
                                        </#if>
                                    </#if>
                                </#list>
                            </constraints>
                        </view>
                    </subviews>
                    <constraints>
                        <constraint firstItem="${mainView}" firstAttribute="top" secondItem="${scrollView}" secondAttribute="top" id="${mainView}Top${scrollView}" />
                        <constraint firstAttribute="trailing" secondItem="${mainView}" secondAttribute="trailing" id="${mainView}Trailing"/>
                        <constraint firstItem="${mainView}" firstAttribute="centerX" secondItem="${scrollView}" secondAttribute="centerX" id="${mainView}CenterX${scrollView}"/>
                        <constraint firstItem="${mainView}" firstAttribute="leading" secondItem="${scrollView}" secondAttribute="leading" id="${mainView}Leading${scrollView}"/>
                        <constraint firstAttribute="bottom" secondItem="${mainView}" secondAttribute="bottom" id="${mainView}Bottom"/>
                    </constraints>
                </scrollView>
            </subviews>
            <color key="backgroundColor" white="1" alpha="1" colorSpace="custom" customColorSpace="calibratedWhite"/>
            <constraints>
                <constraint firstAttribute="bottom" secondItem="${scrollView}" secondAttribute="bottom" id="${scrollView}Bottom"/>
                <constraint firstItem="${scrollView}" firstAttribute="leading" secondItem="${backgroundView}" secondAttribute="leading" id="${scrollView}Leading${backgroundView}"/>
                <constraint firstAttribute="trailing" secondItem="${scrollView}" secondAttribute="trailing" id="${scrollView}Trailing"/>
                <constraint firstItem="${scrollView}" firstAttribute="centerX" secondItem="${backgroundView}" secondAttribute="centerX" id="${scrollView}CenterX${backgroundView}"/>
                <constraint firstItem="${scrollView}" firstAttribute="top" secondItem="${backgroundView}" secondAttribute="top" id="${scrollView}Top${backgroundView}"/>
                <constraint firstItem="${scrollView}" firstAttribute="centerY" secondItem="${backgroundView}" secondAttribute="centerY" id="${scrollView}CenterY${backgroundView}"/>
            </constraints>
            <simulatedScreenMetrics key="simulatedDestinationMetrics" type="retina55"/>
            <point key="canvasLocation" x="450" y="391"/>
        </view>
    </objects>
</document>