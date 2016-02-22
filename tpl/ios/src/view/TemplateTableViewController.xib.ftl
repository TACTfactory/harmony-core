<#assign curr = entities[current_entity] />
<#include utilityPath + "all_imports.ftl" />
<#assign backgroundView = "${curr.name?cap_first}BackgroundView" />
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<document type="com.apple.InterfaceBuilder3.CocoaTouch.XIB" version="3.0" toolsVersion="8191" systemVersion="14F27" targetRuntime="iOS.CocoaTouch" propertyAccessControl="none" useAutolayout="YES" useTraitCollections="YES">
    <dependencies>
        <deployment identifier="iOS"/>
        <plugIn identifier="com.apple.InterfaceBuilder.IBCocoaTouchPlugin" version="8154"/>
    </dependencies>
    <objects>
        <placeholder placeholderIdentifier="IBFilesOwner" id="-1" userLabel="File's Owner" customClass="${curr.name?cap_first}TableViewController">
            <connections>
                <outlet property="${curr.name?lower_case}TableView" destination="${curr.name?cap_first}${curr.name?lower_case}TableView" id="${curr.name?cap_first}${curr.name?lower_case}TableViewController"/>
                <outlet property="view" destination="${backgroundView}" id="${curr.name?cap_first}${field.name?cap_first}TableViewControllerBackgroundView"/>
            </connections>
        </placeholder>
        <placeholder placeholderIdentifier="IBFirstResponder" id="-2" customClass="UIResponder"/>
        <view clearsContextBeforeDrawing="NO" contentMode="scaleToFill" id="${backgroundView}">
            <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
            <autoresizingMask key="autoresizingMask" widthSizable="YES" heightSizable="YES"/>
            <subviews>
                <tableView clipsSubviews="YES" contentMode="scaleToFill" alwaysBounceVertical="YES" style="plain" separatorStyle="default" rowHeight="44" sectionHeaderHeight="28" sectionFooterHeight="28" translatesAutoresizingMaskIntoConstraints="NO" id="${curr.name?cap_first}${curr.name?lower_case}TableView">
                    <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
                    <color key="backgroundColor" white="1" alpha="1" colorSpace="calibratedWhite"/>
                    <connections>
                        <outlet property="dataSource" destination="-1" id="${curr.name?cap_first}${curr.name?lower_case}TableViewDatasource"/>
                        <outlet property="delegate" destination="-1" id="${curr.name?cap_first}${curr.name?lower_case}TableViewDelegate"/>
                    </connections>
                </tableView>
            </subviews>
            <color key="backgroundColor" white="1" alpha="1" colorSpace="custom" customColorSpace="calibratedWhite"/>
            <constraints>
                <constraint firstAttribute="bottom" secondItem="${curr.name?cap_first}${curr.name?lower_case}TableView" secondAttribute="bottom" id=""${curr.name?cap_first}${curr.name?lower_case}TableViewBottom"/>
                <constraint firstItem="${curr.name?cap_first}${curr.name?lower_case}TableView" firstAttribute="leading" secondItem="${backgroundView}" secondAttribute="leading" id="${curr.name?cap_first}${curr.name?lower_case}TableViewLeading${backgroundView}"/>
                <constraint firstAttribute="trailing" secondItem="${curr.name?cap_first}${curr.name?lower_case}TableView" secondAttribute="trailing" id=""${curr.name?cap_first}${curr.name?lower_case}TableViewTrailing"/>
                <constraint firstItem="${curr.name?cap_first}${curr.name?lower_case}TableView" firstAttribute="top" secondItem="${backgroundView}" secondAttribute="top" id="${curr.name?cap_first}${curr.name?lower_case}TableViewTop${backgroundView}"/>
            </constraints>
            <simulatedScreenMetrics key="simulatedDestinationMetrics" type="retina55"/>
        </view>
    </objects>
</document>