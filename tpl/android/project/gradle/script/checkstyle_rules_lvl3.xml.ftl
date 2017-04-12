<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
          "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
          "http://www.puppycrawl.com/dtds/configuration_1_3.dtd">

<!--

  Checkstyle configuration that checks the sun coding conventions from:

    - the Java Language Specification at
      http://java.sun.com/docs/books/jls/second_edition/html/index.html

    - the Sun Code Conventions at http://java.sun.com/docs/codeconv/

    - the Javadoc guidelines at
      http://java.sun.com/j2se/javadoc/writingdoccomments/index.html

    - the JDK Api documentation http://java.sun.com/j2se/docs/api/index.html

    - some best practices

  Checkstyle is very configurable. Be sure to read the documentation at
  http://checkstyle.sf.net (or in your downloaded distribution).

  Most Checks are configurable, be sure to consult the documentation.

  To completely disable a check, just comment it out or delete it from the file.

  Finally, it is worth reading the documentation.

-->

<module name="Checker">
    <!--
        If you set the basedir property below, then all reported file
        names will be relative to the specified directory. See
        http://checkstyle.sourceforge.net/5.x/config.html#Checker

        <property name="basedir" value="${r"${basedir}"}"/>
    -->

    <!-- Checks that a package-info.java file exists for each package.     -->
    <!-- See http://checkstyle.sf.net/config_javadoc.html#JavadocPackage -->
    <module name="JavadocPackage"/>

    <!-- Checks whether files end with a new line.                        -->
    <!-- See http://checkstyle.sf.net/config_misc.html#NewlineAtEndOfFile -->
    <module name="NewlineAtEndOfFile"/>

    <!-- Checks that property files contain the same keys.         -->
    <!-- See http://checkstyle.sf.net/config_misc.html#Translation -->
    <module name="Translation"/>

    <!-- Checks for Headers                                -->
    <!-- See http://checkstyle.sf.net/config_header.html   -->
    <!-- <module name="Header"> -->
    <!--   <property name="headerFile" value="${r"${checkstyle.header.file}"}"/> -->
    <!--   <property name="fileExtensions" value="java"/> -->
    <!-- </module> -->
    <module name="RegexpHeader">
        <property
            name="headerFile"
            value="java.header"/>
    </module>

    <module name="TreeWalker">

        <property name="tabWidth" value="4"/>

        <!-- Checks for Javadoc comments.                     -->
        <!-- See http://checkstyle.sourceforge.net/config_javadoc.html#JavadocMethod -->
        <module name="JavadocMethod">
            <property name="scope" value="public"/>
        </module>
        <module name="JavadocMethod">
            <property name="scope" value="protected"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_javadoc.html#JavadocStyle -->
        <module name="JavadocStyle">
            <property name="scope" value="public"/>
            <property name="checkEmptyJavadoc" value="true"/>
            <property name="endOfSentenceFormat" value="[\.]$"/>
        </module>
        <module name="JavadocStyle">
            <property name="scope" value="protected"/>
            <property name="checkEmptyJavadoc" value="true"/>
            <property name="endOfSentenceFormat" value="[\.]$"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_javadoc.html#JavadocType -->
        <module name="JavadocType">
            <property name="scope" value="public"/>
        </module>
        <module name="JavadocType">
            <property name="scope" value="protected"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_javadoc.html#JavadocVariable -->
        <module name="JavadocVariable">
            <property name="scope" value="public"/>
        </module>
        <module name="JavadocVariable">
            <property name="scope" value="protected"/>
        </module>



        <!-- Checks for Naming Conventions.                  -->
        <!-- See http://checkstyle.sourceforge.net/config_naming.html#ConstantName -->
        <module name="ConstantName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#LocalFinalVariableName -->
        <module name="LocalFinalVariableName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#LocalVariableName -->
        <module name="LocalVariableName">
            <!--
            <property name="allowOneCharVarInForLoop" value="true"/>
            Require Release 6.12.1 -->
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#MemberName -->
        <module name="MemberName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#MethodName -->
        <module name="MethodName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#PackageName -->
        <module name="PackageName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#ParameterName -->
        <module name="ParameterName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#ParameterName#StaticVariableName -->
        <module name="StaticVariableName"/>

        <!-- See http://checkstyle.sourceforge.net/config_naming.html#ParameterName#TypeName -->
        <module name="TypeName"/>


        <!-- Checks for imports                              -->
        <!-- See http://checkstyle.sourceforge.net/config_imports.html#AvoidStarImport -->
        <module name="AvoidStarImport">
            <property name="allowClassImports" value="false"/>
            <property name="allowStaticMemberImports" value="false"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_imports.html#AvoidStarImport#AvoidStaticImport -->
        <module name="AvoidStaticImport"/>

        <!-- See http://checkstyle.sourceforge.net/config_imports.html#IllegalImport -->
        <module name="IllegalImport"/> <!-- defaults to sun.* packages -->

        <!-- See http://checkstyle.sourceforge.net/config_imports.html#RedundantImport -->
        <module name="RedundantImport"/>

        <!-- See http://checkstyle.sourceforge.net/config_imports.html#UnusedImports -->
        <module name="UnusedImports"/>


        <!-- Checks for Size Violations.                    -->
        <!-- See http://checkstyle.sourceforge.net/config_sizes.html#LineLength -->
        <module name="LineLength">
            <property name="ignorePattern" value="^import"/>
            <property name="max" value="120"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_sizes.html#LineLength#MethodCount -->
        <module name="MethodCount">
            <property name="maxTotal" value="80"/>
            <property name="maxPrivate" value="50"/>
            <property name="maxPackage" value="40"/>
            <property name="maxProtected" value="40"/>
            <property name="maxPublic" value="40"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_sizes.html#MethodLength -->
        <module name="MethodLength">
            <property name="countEmpty" value="false"/>
            <property name="max" value="60"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_sizes.html#ParameterNumber -->
        <module name="ParameterNumber">
            <property name="max" value="6"/>
            <!--
            <property name="ignoreOverriddenMethods" value="false"/>
            Require Release 6.12.1 -->
            <property name="tokens" value="METHOD_DEF"/>
        </module>
        <module name="ParameterNumber">
            <!--
            <property name="ignoreOverriddenMethods" value="true"/>
            Require Release 6.12.1 -->
            <property name="tokens" value="CTOR_DEF"/>
        </module>

        <!-- Checks for whitespace                               -->
        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#EmptyForInitializerPad -->
        <module name="EmptyForIteratorPad"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#EmptyLineSeparator -->
        <!--
        <module name="EmptyLineSeparator">
            <property name="allowNoEmptyLineBetweenFields" value="true"/>
        </module>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#EmptyLineSeparator -->
        <!--
        <module name="EmptyLineSeparator">
            <property name="allowNoEmptyLineBetweenFields" value="true"/>
        </module>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#FileTabCharacter -->
        <!--
        <module name="FileTabCharacter"/>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#GenericWhitespace -->
        <module name="GenericWhitespace"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#MethodParamPad -->
        <module name="MethodParamPad"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#NoWhitespaceAfter -->
        <module name="NoWhitespaceAfter"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#NoWhitespaceBefore -->
        <module name="NoWhitespaceBefore"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#OperatorWrap -->
        <module name="OperatorWrap"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#ParenPad -->
        <module name="ParenPad"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#SeparatorWrap -->
        <!--
        <module name="SeparatorWrap"/>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#TypecastParenPad -->
        <module name="TypecastParenPad"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#WhitespaceAfter -->
        <module name="WhitespaceAfter"/>

        <!-- See http://checkstyle.sourceforge.net/config_whitespace.html#WhitespaceAround -->
        <module name="WhitespaceAround"/>


        <!-- Modifier Checks                                    -->
        <!-- See http://checkstyle.sourceforge.net/config_modifier.html#ModifierOrder -->
        <!-- correct order is:
            1. public
            2. protected
            3. private
            4. abstract
            5. static
            6. final
            7. transient
            8. volatile
            9. synchronized
            10. native
            11. strictfp
        -->
        <module name="ModifierOrder"/>

        <!-- See http://checkstyle.sourceforge.net/config_modifier.html#RedundantModifier -->
        <module name="RedundantModifier"/>


        <!-- Checks for blocks. You know, those {}'s         -->
        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#AvoidNestedBlocks -->
        <module name="AvoidNestedBlocks"/>

        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#EmptyBlock -->
        <module name="EmptyBlock"/>

        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#EmptyCatchBlock -->
        <!--
        <module name="EmptyCatchBlock"/>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#LeftCurly -->
        <module name="LeftCurly"/>

        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#NeedBraces -->
        <module name="NeedBraces"/>

        <!-- See http://checkstyle.sourceforge.net/config_blocks.html#RightCurly -->
        <module name="RightCurly"/>


        <!-- Checks for common coding problems               -->
        <!-- See http://checkstyle.sourceforge.net/config_coding.html#ArrayTrailingComma -->
        <module name="ArrayTrailingComma"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#AvoidInlineConditionals -->
        <module name="AvoidInlineConditionals"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#CovariantEquals -->
        <module name="CovariantEquals"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#DefaultComesLast -->
        <module name="DefaultComesLast"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#EmptyStatement -->
        <module name="EmptyStatement"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#EqualsAvoidNull -->
        <module name="EqualsAvoidNull"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#EqualsHashCode -->
        <module name="EqualsHashCode"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#FallThrough -->
        <module name="FallThrough">
            <property name="checkLastCaseGroup" value="true"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#FinalLocalVariable -->
        <module name="FinalLocalVariable">
            <!--
            <property name="validateEnhancedForLoopVariable" value="true"/>
            Require Release 6.12.1 -->
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#HiddenField -->
        <module name="HiddenField">
            <property name="ignoreConstructorParameter" value="true"/>
            <property name="ignoreSetter" value="true"/>
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#IllegalInstantiation -->
        <module name="IllegalInstantiation"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#InnerAssignment -->
        <module name="InnerAssignment"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#MissingSwitchDefault -->
        <module name="MissingSwitchDefault"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#MultipleVariableDeclarations -->
        <module name="MultipleVariableDeclarations"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#OverloadMethodsDeclarationOrder -->
        <!--
        <module name="OverloadMethodsDeclarationOrder"/>
        Require Release 6.12.1 -->

        <!-- See ??? -->
        <module name="RedundantThrows"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#SimplifyBooleanExpression#SimplifyBooleanExpression -->
        <module name="SimplifyBooleanExpression"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#SimplifyBooleanExpression#SimplifyBooleanReturn -->
        <module name="SimplifyBooleanReturn"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#StringLiteralEquality -->
        <module name="StringLiteralEquality"/>

        <!-- See http://checkstyle.sourceforge.net/config_coding.html#UnnecessaryParentheses -->
        <module name="UnnecessaryParentheses"/>


        <!-- Checks for class design                         -->
        <!-- See http://checkstyle.sourceforge.net/config_design.html#FinalClass -->
        <module name="FinalClass"/>

        <!-- See http://checkstyle.sourceforge.net/config_design.html#HideUtilityClassConstructor -->
        <module name="HideUtilityClassConstructor"/>

        <!-- See http://checkstyle.sourceforge.net/config_design.html#HideUtilityClassConstructor#InterfaceIsType -->
        <module name="InterfaceIsType"/>

        <!-- See http://checkstyle.sourceforge.net/config_design.html#HideUtilityClassConstructor#VisibilityModifier -->
        <module name="VisibilityModifier"/>


        <!-- Miscellaneous other checks.                   -->
        <!-- See http://checkstyle.sourceforge.net/config_misc.html#ArrayTypeStyle -->
        <module name="ArrayTypeStyle"/>

        <!-- See http://checkstyle.sourceforge.net/config_misc.html#CommentsIndentation -->
        <!--
        <module name="CommentsIndentation"/>
        Require Release 6.12.1 -->

        <!-- See http://checkstyle.sourceforge.net/config_misc.html#TodoComment -->
        <module name="TodoComment">
            <property name="format" value="TODO|FIXME" />
        </module>

        <!-- See http://checkstyle.sourceforge.net/config_misc.html#UpperEll -->
        <module name="UpperEll"/>

        <!-- See http://checkstyle.sourceforge.net/apidocs/com/puppycrawl/tools/checkstyle/checks/coding/RequireThisCheck.html -->
        <module name="RequireThisCheck" />

    </module>
    <!-- See -->
    <module name="SuppressionFilter">
        <property name="file" value="gradle/script/checkstyle_exclude.xml" />
    </module>

</module>
