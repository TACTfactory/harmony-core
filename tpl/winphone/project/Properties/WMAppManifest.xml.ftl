<?xml version="1.0" encoding="utf-8"?>
<Deployment xmlns="http://schemas.microsoft.com/windowsphone/2012/deployment" AppPlatformVersion="8.0">
  <DefaultLanguage xmlns="" code="en-US" />
  <Languages xmlns="">
    <Language code="en" />
  </Languages>
  <App xmlns="" ProductID="{572bb699-01d8-459e-995e-6ee408f5d2dd}" Title="${project_name?cap_first}"
        RuntimeType="Silverlight" Version="0.4.0" Genre="apps.normal" Author="TactFactory"
        Description="" Publisher="Fimor" PublisherID="{b13cefda-cb70-41b3-8d8f-5d37902c45cc}">
    <IconPath IsRelative="true" IsResource="false">Resources\Drawable\launcher.png</IconPath>
    <Capabilities>
    </Capabilities>
    <Tasks>
      <DefaultTask Name="_default" NavigationPage="View/Home/ScanPage.xaml" />
    </Tasks>
    <Tokens>
      <PrimaryToken TokenID="${project_name}Token" TaskName="_default">
        <TemplateFlip>
            <SmallImageURI IsRelative="true" IsResource="false">
                Resources\Drawable\launcher.png
            </SmallImageURI>
            <Count>0</Count>
            <BackgroundImageURI IsRelative="true" IsResource="false">
                Resources\Drawable\launcher.png
            </BackgroundImageURI>
            <Title>${project_name?cap_first}</Title>
            <BackContent>
            </BackContent>
            <BackBackgroundImageURI IsRelative="true" IsResource="false">
            </BackBackgroundImageURI>
            <BackTitle>
            </BackTitle>
            <DeviceLockImageURI IsRelative="true" IsResource="false">
            </DeviceLockImageURI>
            <HasLarge>False</HasLarge>
        </TemplateFlip>
      </PrimaryToken>
    </Tokens>
    <ScreenResolutions>
      <ScreenResolution Name="ID_RESOLUTION_WVGA" />
      <ScreenResolution Name="ID_RESOLUTION_WXGA" />
      <ScreenResolution Name="ID_RESOLUTION_HD720P" />
    </ScreenResolutions>
  </App>
</Deployment>