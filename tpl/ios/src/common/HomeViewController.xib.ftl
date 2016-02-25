<#include utilityPath + "all_imports.ftl" />
<#assign backgroundView = "HomeBackgroundView" />
<#assign scrollView = "HomeScrollView" />
<#assign mainView = "HomeMainView" />
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<document type="com.apple.InterfaceBuilder3.CocoaTouch.XIB" version="3.0" toolsVersion="8191" systemVersion="14F27" targetRuntime="iOS.CocoaTouch" propertyAccessControl="none" useAutolayout="YES" useTraitCollections="YES">
    <dependencies>
        <deployment identifier="iOS"/>
        <plugIn identifier="com.apple.InterfaceBuilder.IBCocoaTouchPlugin" version="8154"/>
    </dependencies>
    <objects>
        <placeholder placeholderIdentifier="IBFilesOwner" id="-1" userLabel="File's Owner" customClass="HomeViewController">
            <connections>
                <outlet property="view" destination="${backgroundView}" id="${backgroundView}Controller"/>
            </connections>
        </placeholder>
        <placeholder placeholderIdentifier="IBFirstResponder" id="-2" customClass="UIResponder"/>
        <view clearsContextBeforeDrawing="NO" contentMode="scaleToFill" id="${backgroundView}">
            <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
            <autoresizingMask key="autoresizingMask" widthSizable="YES" heightSizable="YES"/>
            <subviews>
                <scrollView clipsSubviews="YES" multipleTouchEnabled="YES" contentMode="scaleToFill" translatesAutoresizingMaskIntoConstraints="NO" id="${scrollView}">
                    <rect key="frame" x="0.0" y="0.0" width="414" height="736"/>
                    <subviews>
                        <view contentMode="scaleToFill" translatesAutoresizingMaskIntoConstraints="NO" id="${mainView}" userLabel="MainView">
                            <rect key="frame" x="0.0" y="0.0" width="414" height="241"/>
                            <subviews>
                                <label opaque="NO" userInteractionEnabled="NO" contentMode="left" horizontalHuggingPriority="251" verticalHuggingPriority="251" text="Home View Controller" textAlignment="natural" lineBreakMode="tailTruncation" baselineAdjustment="alignBaselines" adjustsFontSizeToFit="NO" translatesAutoresizingMaskIntoConstraints="NO" id="HomeViewControllerTitleLabel">
                                    <rect key="frame" x="124" y="30" width="167" height="21"/>
                                    <fontDescription key="fontDescription" type="system" pointSize="17"/>
                                    <color key="textColor" red="0.0" green="0.0" blue="0.0" alpha="1" colorSpace="calibratedRGB"/>
                                    <nil key="highlightedColor"/>
                                </label>
                                <#assign y = 91 />
                                <#assign tag = 1 />
                                <#list entities?values as entity>
                                    <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>

                                <button opaque="NO" tag="${tag}" contentMode="scaleToFill" contentHorizontalAlignment="center" contentVerticalAlignment="center" buttonType="roundedRect" lineBreakMode="middleTruncation" translatesAutoresizingMaskIntoConstraints="NO" id="${entity.name}Button">
                                    <rect key="frame" x="10" y="${y}" width="394" height="30"/>
                                    <state key="normal" title="${entity.name}"/>
                                    <connections>
                                        <action selector="onClickButton:" destination="-1" eventType="touchUpInside" id="${entity.name}ButtonTag${tag}"/>
                                    </connections>
                                </button>
                                        <#assign y = y + 61 />
                                        <#assign tag = tag + 1 />
                                    </#if>
                                </#list>
                            </subviews>
                            <color key="backgroundColor" white="1" alpha="1" colorSpace="calibratedWhite"/>
                            <constraints>
                            <#assign lastElement = "" />
                                <#list entities?values as entity>
                                    <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
                                        <#if lastElement == "">
                                <constraint firstItem="${entity.name}Button" firstAttribute="top" secondItem="HomeViewControllerTitleLabel" secondAttribute="bottom" constant="40" id="${entity.name}ButtonTopHomeViewControllerTitleLabelConstraint"/>
                                        <#else>
                                <constraint firstItem="${entity.name}Button" firstAttribute="top" secondItem="${lastElement}" secondAttribute="bottom" constant="20" id="${entity.name}ButtonTop${lastElement}Constraint"/>
                                        </#if>
                                    <#assign lastElement = "${entity.name}Button" />
                                        <#if !(entity_has_next)>
                                <constraint firstAttribute="bottom" secondItem="${entity.name}Button" secondAttribute="bottom" constant="20" id="${entity.name}ButtonBottomConstraint"/>
                                        </#if>
                                <constraint firstItem="${entity.name}Button" firstAttribute="leading" secondItem="${mainView}" secondAttribute="leading" constant="10" id="${entity.name}ButtonLeading${mainView}Constraint"/>
                                <constraint firstAttribute="trailing" secondItem="${entity.name}Button" secondAttribute="trailing" constant="10" id="${entity.name}ButtonTrailingConstraint"/>
                                    </#if>
                                </#list>
                                <constraint firstItem="HomeViewControllerTitleLabel" firstAttribute="centerX" secondItem="${mainView}" secondAttribute="centerX" id="HomeViewControllerTitleLabelCenterX${mainView}Constraint"/>
                                <constraint firstItem="HomeViewControllerTitleLabel" firstAttribute="top" secondItem="${mainView}" secondAttribute="top" constant="30" id="HomeViewControllerTitleLabelTop${mainView}Constraint"/>
                            </constraints>
                        </view>
                    </subviews>
                    <constraints>
                        <constraint firstItem="${mainView}" firstAttribute="top" secondItem="${scrollView}" secondAttribute="top" id="${mainView}Top${scrollView}Constraint"/>
                        <constraint firstAttribute="trailing" secondItem="${mainView}" secondAttribute="trailing" id="${mainView}TrailingConstraint"/>
                        <constraint firstAttribute="bottom" secondItem="${mainView}" secondAttribute="bottom" id="${mainView}BottomConstraint"/>
                        <constraint firstItem="${mainView}" firstAttribute="centerX" secondItem="${scrollView}" secondAttribute="centerX" id="${mainView}CenterX${scrollView}Constraint"/>
                        <constraint firstItem="${mainView}" firstAttribute="leading" secondItem="${scrollView}" secondAttribute="leading" id="${mainView}Leading${scrollView}Constraint"/>
                    </constraints>
                </scrollView>
            </subviews>
            <color key="backgroundColor" white="1" alpha="1" colorSpace="custom" customColorSpace="calibratedWhite"/>
            <constraints>
                <constraint firstAttribute="trailing" secondItem="${scrollView}" secondAttribute="trailing" id="${scrollView}TrailingConstraint"/>
                <constraint firstItem="${scrollView}" firstAttribute="top" secondItem="${backgroundView}" secondAttribute="top" id="${scrollView}Top${backgroundView}Constraint"/>
                <constraint firstItem="${scrollView}" firstAttribute="centerY" secondItem="${backgroundView}" secondAttribute="centerY" id="${scrollView}CenterY${backgroundView}Constraint"/>
                <constraint firstItem="${scrollView}" firstAttribute="leading" secondItem="${backgroundView}" secondAttribute="leading" id="${scrollView}Leading${backgroundView}Constraint"/>
                <constraint firstAttribute="bottom" secondItem="${scrollView}" secondAttribute="bottom" id="${scrollView}BottomConstraint"/>
                <constraint firstItem="${scrollView}" firstAttribute="centerX" secondItem="${backgroundView}" secondAttribute="centerX" id="${scrollView}CenterX${backgroundView}Constraint"/>
            </constraints>
            <simulatedScreenMetrics key="simulatedDestinationMetrics" type="retina55"/>
            <point key="canvasLocation" x="641" y="416"/>
        </view>
    </objects>
</document>