<?xml version="1.0"?>
<ruleset name="rule set"
    xmlns="http://pmd.sf.net/ruleset/1.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sf.net/ruleset/1.0.0 http://pmd.sf.net/ruleset_xml_schema.xsd"
    xsi:noNamespaceSchemaLocation="http://pmd.sf.net/ruleset_xml_schema.xsd">

    <description>
        rules
    </description>

    <!-- imports -->

    <!-- See  http://pmd.sourceforge.net/pmd-5.0.1/rules/java/android.html -->
    <rule ref="rulesets/java/android.xml/CallSuperFirst"></rule>
    <rule ref="rulesets/java/android.xml/DoNotHardCodeSDCard"></rule>

    <!-- See  http://pmd.sourceforge.net/pmd-5.0.1/rules/java/basic.html -->
    <rule ref="rulesets/java/basic.xml/JumbledIncrementer"></rule>
    <rule ref="rulesets/java/basic.xml/ForLoopShouldBeWhileLoop"></rule>
    <rule ref="rulesets/java/basic.xml/OverrideBothEqualsAndHashcode"></rule>
    <rule ref="rulesets/java/basic.xml/DoubleCheckedLocking"></rule>
    <rule ref="rulesets/java/basic.xml/ReturnFromFinallyBlock"></rule>
    <rule ref="rulesets/java/basic.xml/UnconditionalIfStatement"></rule>
    <rule ref="rulesets/java/basic.xml/BooleanInstantiation"></rule>
    <rule ref="rulesets/java/basic.xml/CollapsibleIfStatements"></rule>
    <rule ref="rulesets/java/basic.xml/ClassCastExceptionWithToArray"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidDecimalLiteralsInBigDecimalConstructor"></rule>
    <rule ref="rulesets/java/basic.xml/MisplacedNullCheck"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidThreadGroup"></rule>
    <rule ref="rulesets/java/basic.xml/BrokenNullCheck"></rule>
    <rule ref="rulesets/java/basic.xml/BigIntegerInstantiation"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidUsingOctalValues"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidUsingHardCodedIP"></rule>
    <rule ref="rulesets/java/basic.xml/CheckResultSet"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidMultipleUnaryOperators"></rule>
    <rule ref="rulesets/java/basic.xml/ExtendsObject"></rule>
    <rule ref="rulesets/java/basic.xml/CheckSkipResult"></rule>
    <rule ref="rulesets/java/basic.xml/AvoidBranchingStatementAsLastInLoop"></rule>
    <rule ref="rulesets/java/basic.xml/DontCallThreadRun"></rule>
    <rule ref="rulesets/java/basic.xml/DontUseFloatTypeForLoopIndices"></rule>

    <!-- See  http://pmd.sourceforge.net/pmd-5.0.1/rules/java/braces.html -->
    <rule ref="rulesets/java/braces.xml/IfStmtsMustUseBraces"></rule>
    <rule ref="rulesets/java/braces.xml/WhileLoopsMustUseBraces"></rule>
    <rule ref="rulesets/java/braces.xml/IfElseStmtsMustUseBraces"></rule>
    <rule ref="rulesets/java/braces.xml/ForLoopsMustUseBraces"></rule>

    <!-- See  http://pmd.sourceforge.net/pmd-5.0.1/rules/java/clone.html -->
    <rule ref="rulesets/java/clone.xml/ProperCloneImplementation"></rule>
    <rule ref="rulesets/java/clone.xml/CloneThrowsCloneNotSupportedException"></rule>
    <rule ref="rulesets/java/clone.xml/CloneMethodMustImplementCloneable"></rule>

    <!-- See  http://pmd.sourceforge.net/pmd-5.0.1/rules/java/codesize.html -->
    <rule ref="rulesets/java/codesize.xml/NPathComplexity">
        <properties>
            <property name="minimum" value="200"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/ExcessiveMethodLength">
        <properties>
            <property name="minimum" value="160"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/ExcessiveParameterList">
        <properties>
            <property name="minimum" value="10"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/ExcessiveClassLength">
        <properties>
            <property name="minimum" value="1000"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/CyclomaticComplexity">
        <properties>
            <property name="showMethodsComplexity" value="true"/>      <!-- Add method average violations to the report -->
            <property name="showClassesComplexity" value="true"/>      <!-- Add class average violations to the report -->
            <property name="reportLevel" value="10"/>      <!-- Cyclomatic Complexity reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/ExcessivePublicCount">
        <properties>
            <property name="minimum" value="45"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/TooManyFields">
        <properties>
            <property name="maxfields" value="6"/>      <!-- Max allowable fields -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/NcssMethodCount">
        <properties>
            <property name="minimum" value="80"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/NcssTypeCount">
        <properties>
            <property name="minimum" value="1500"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/NcssConstructorCount">
        <properties>
            <property name="minimum" value="100"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/codesize.xml/TooManyMethods">
        <properties>
            <property name="maxmethods" value="30"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/comments.html -->
    <rule ref="rulesets/java/comments.xml/CommentRequired"></rule>
    <rule ref="rulesets/java/comments.xml/CommentSize">
        <properties>
            <property name="maxLines" value="20"/>      <!-- Maximum lines -->
            <property name="maxLineLength" value="120"/>      <!-- Maximum lines length -->
        </properties>
    </rule>
    <rule ref="rulesets/java/comments.xml/CommentContent"></rule>   <!-- Use for offending langage -->

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/controversial.html -->
    <rule ref="rulesets/java/controversial.xml/UnnecessaryConstructor"></rule>
    <rule ref="rulesets/java/controversial.xml/NullAssignment"></rule>
    <rule ref="rulesets/java/controversial.xml/OnlyOneReturn"></rule>
    <rule ref="rulesets/java/controversial.xml/AssignmentInOperand">
        <properties>
            <property name="allowIncrementDecrement" value="false"/>      <!-- Allow increment or decrement operators within the conditional expression of an if, for, or while statement -->
            <property name="allowWhile" value="false"/>      <!-- Allow assignment within the conditional expression of a while statement -->
            <property name="allowFor" value="false"/>      <!-- Allow assignment within the conditional expression of a for statement -->
            <property name="allowIf" value="false"/>      <!-- Allow assignment within the conditional expression of an if statement -->
        </properties>
    </rule>
    <rule ref="rulesets/java/controversial.xml/AtLeastOneConstructor"></rule>
    <rule ref="rulesets/java/controversial.xml/DontImportSun"></rule>
    <rule ref="rulesets/java/controversial.xml/SuspiciousOctalEscape"></rule>
    <rule ref="rulesets/java/controversial.xml/CallSuperInConstructor"></rule>
    <rule ref="rulesets/java/controversial.xml/UnnecessaryParentheses"></rule>
    <rule ref="rulesets/java/controversial.xml/DefaultPackage"></rule>
    <rule ref="rulesets/java/controversial.xml/BooleanInversion"></rule>
    <rule ref="rulesets/java/controversial.xml/DataflowAnomalyAnalysis">
        <properties>
            <property name="maxViolations" value="0"/>      <!-- Maximum number of anomalies per class -->
        </properties>
    </rule>
    <rule ref="rulesets/java/controversial.xml/AvoidFinalLocalVariable"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidUsingShortType"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidUsingVolatile"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidUsingNativeCode"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidAccessibilityAlteration"></rule>
    <rule ref="rulesets/java/controversial.xml/DoNotCallGarbageCollectionExplicitly"></rule>
    <rule ref="rulesets/java/controversial.xml/OneDeclarationPerLine"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidPrefixingMethodParameters"></rule>
    <rule ref="rulesets/java/controversial.xml/AvoidLiteralsInIfCondition"></rule>
    <rule ref="rulesets/java/controversial.xml/UseObjectForClearerAPI"></rule>
    <rule ref="rulesets/java/controversial.xml/UseConcurrentHashMap"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/coupling.html -->
    <rule ref="rulesets/java/coupling.xml/CouplingBetweenObjects"></rule>
    <rule ref="rulesets/java/coupling.xml/ExcessiveImports">
        <properties>
            <property name="minimum" value="30"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/coupling.xml/LooseCoupling"></rule>
    <rule ref="rulesets/java/coupling.xml/LawOfDemeter"></rule>

    <!-- http://pmd.sourceforge.net/pmd-5.0.1/rules/java/design.html -->
    <rule ref="rulesets/java/design.xml/UseSingleton"></rule>
    <rule ref="rulesets/java/design.xml/SimplifyBooleanReturns"></rule>
    <rule ref="rulesets/java/design.xml/SimplifyBooleanExpressions"></rule>
    <rule ref="rulesets/java/design.xml/SwitchStmtsShouldHaveDefault"></rule>
    <rule ref="rulesets/java/design.xml/AvoidDeeplyNestedIfStmts">
        <properties>
            <property name="problemDepth" value="3"/>      <!-- The if statement depth reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/design.xml/AvoidReassigningParameters"></rule>
    <rule ref="rulesets/java/design.xml/SwitchDensity">
        <properties>
            <property name="minimum" value="10"/>      <!-- Minimum reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/design.xml/ConstructorCallsOverridableMethod"></rule>
    <rule ref="rulesets/java/design.xml/AccessorClassGeneration"></rule>
    <rule ref="rulesets/java/design.xml/FinalFieldCouldBeStatic"></rule>
    <rule ref="rulesets/java/design.xml/CloseResource"></rule>
    <rule ref="rulesets/java/design.xml/NonStaticInitializer"></rule>
    <rule ref="rulesets/java/design.xml/DefaultLabelNotLastInSwitchStmt"></rule>
    <rule ref="rulesets/java/design.xml/NonCaseLabelInSwitchStatement"></rule>
    <rule ref="rulesets/java/design.xml/OptimizableToArrayCall"></rule>
    <rule ref="rulesets/java/design.xml/BadComparison"></rule>
    <rule ref="rulesets/java/design.xml/EqualsNull"></rule>
    <rule ref="rulesets/java/design.xml/ConfusingTernary"></rule>
    <rule ref="rulesets/java/design.xml/InstantiationToGetClass"></rule>
    <rule ref="rulesets/java/design.xml/IdempotentOperations"></rule>
    <rule ref="rulesets/java/design.xml/SimpleDateFormatNeedsLocale"></rule>
    <rule ref="rulesets/java/design.xml/ImmutableField"></rule>
    <rule ref="rulesets/java/design.xml/UseLocaleWithCaseConversions"></rule>
    <rule ref="rulesets/java/design.xml/AvoidProtectedFieldInFinalClass"></rule>
    <rule ref="rulesets/java/design.xml/AssignmentToNonFinalStatic"></rule>
    <rule ref="rulesets/java/design.xml/MissingStaticMethodInNonInstantiatableClass"></rule>
    <rule ref="rulesets/java/design.xml/AvoidSynchronizedAtMethodLevel"></rule>
    <rule ref="rulesets/java/design.xml/MissingBreakInSwitch"></rule>
    <rule ref="rulesets/java/design.xml/UseNotifyAllInsteadOfNotify"></rule>
    <rule ref="rulesets/java/design.xml/AvoidInstanceofChecksInCatchClause"></rule>
    <rule ref="rulesets/java/design.xml/AbstractClassWithoutAbstractMethod"></rule>
    <rule ref="rulesets/java/design.xml/SimplifyConditional"></rule>
    <rule ref="rulesets/java/design.xml/CompareObjectsWithEquals"></rule>
    <rule ref="rulesets/java/design.xml/PositionLiteralsFirstInComparisons"></rule>
    <rule ref="rulesets/java/design.xml/UnnecessaryLocalBeforeReturn"></rule>
    <rule ref="rulesets/java/design.xml/NonThreadSafeSingleton"></rule>
    <rule ref="rulesets/java/design.xml/UncommentedEmptyMethod"></rule>
    <rule ref="rulesets/java/design.xml/UncommentedEmptyConstructor"></rule>
    <rule ref="rulesets/java/design.xml/AvoidConstantsInterface"></rule>
    <rule ref="rulesets/java/design.xml/UnsynchronizedStaticDateFormatter"></rule>
    <rule ref="rulesets/java/design.xml/PreserveStackTrace"></rule>
    <rule ref="rulesets/java/design.xml/UseCollectionIsEmpty"></rule>
    <rule ref="rulesets/java/design.xml/ClassWithOnlyPrivateConstructorsShouldBeFinal"></rule>
    <rule ref="rulesets/java/design.xml/EmptyMethodInAbstractClassShouldBeAbstract"></rule>
    <rule ref="rulesets/java/design.xml/SingularField"></rule>
    <rule ref="rulesets/java/design.xml/ReturnEmptyArrayRatherThanNull"></rule>
    <rule ref="rulesets/java/design.xml/AbstractClassWithoutAnyMethod"></rule>
    <rule ref="rulesets/java/design.xml/TooFewBranchesForASwitchStatement">
        <properties>
            <property name="minimumNumberCaseForASwitch" value="3"/>      <!-- Minimum number of branches for a switch -->
        </properties>
    </rule>
    <rule ref="rulesets/java/design.xml/LogicInversion"></rule>
    <rule ref="rulesets/java/design.xml/UseVarargs"></rule>
    <rule ref="rulesets/java/design.xml/FieldDeclarationsShouldBeAtStartOfClass"></rule>
    <rule ref="rulesets/java/design.xml/GodClass"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/empty.html -->
    <rule ref="rulesets/java/empty.xml/EmptyCatchBlock">
        <properties>
            <property name="allowCommentedBlocks" value="false"/>      <!-- Empty blocks containing comments will be skipped -->
        </properties>
    </rule>
    <rule ref="rulesets/java/empty.xml/EmptyIfStmt"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyWhileStmt"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyTryBlock"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyFinallyBlock"></rule>
    <rule ref="rulesets/java/empty.xml/EmptySwitchStatements"></rule>
    <rule ref="rulesets/java/empty.xml/EmptySynchronizedBlock"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyStatementNotInLoop"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyInitializer"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyStatementBlock"></rule>
    <rule ref="rulesets/java/empty.xml/EmptyStaticInitializer"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/finalizers.html -->
    <rule ref="rulesets/java/finalizers.xml/EmptyFinalizer"></rule>
    <rule ref="rulesets/java/finalizers.xml/FinalizeOnlyCallsSuperFinalize"></rule>
    <rule ref="rulesets/java/finalizers.xml/FinalizeOverloaded"></rule>
    <rule ref="rulesets/java/finalizers.xml/FinalizeDoesNotCallSuperFinalize"></rule>
    <rule ref="rulesets/java/finalizers.xml/FinalizeShouldBeProtected"></rule>
    <rule ref="rulesets/java/finalizers.xml/AvoidCallingFinalize"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/imports.html -->
    <rule ref="rulesets/java/imports.xml/DuplicateImports"></rule>
    <rule ref="rulesets/java/imports.xml/DontImportJavaLang"></rule>
    <rule ref="rulesets/java/imports.xml/UnusedImports"></rule>
    <rule ref="rulesets/java/imports.xml/ImportFromSamePackage"></rule>
    <rule ref="rulesets/java/imports.xml/TooManyStaticImports">
        <properties>
            <property name="maximumStaticImports" value="4"/>      <!-- All static imports can be disallowed by setting this to 0 -->
        </properties>
    </rule>
    <rule ref="rulesets/java/imports.xml/UnnecessaryFullyQualifiedName"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/javabeans.html -->
    <rule ref="rulesets/java/javabeans.xml/BeanMembersShouldSerialize">
        <properties>
            <property name="prefix" value=""/>      <!-- AA variable prefix to skip, i.e., m_ -->
        </properties>
    </rule>
    <rule ref="rulesets/java/javabeans.xml/MissingSerialVersionUID"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/junit.html -->
    <rule ref="rulesets/java/junit.xml/JUnitStaticSuite"></rule>
    <rule ref="rulesets/java/junit.xml/JUnitSpelling"></rule>
    <rule ref="rulesets/java/junit.xml/JUnitAssertionsShouldIncludeMessage"></rule>
    <rule ref="rulesets/java/junit.xml/JUnitTestsShouldIncludeAssert"></rule>
    <rule ref="rulesets/java/junit.xml/TestClassWithoutTestCases"></rule>
    <rule ref="rulesets/java/junit.xml/UnnecessaryBooleanAssertion"></rule>
    <rule ref="rulesets/java/junit.xml/UseAssertEqualsInsteadOfAssertTrue"></rule>
    <rule ref="rulesets/java/junit.xml/UseAssertSameInsteadOfAssertTrue"></rule>
    <rule ref="rulesets/java/junit.xml/UseAssertNullInsteadOfAssertTrue"></rule>
    <rule ref="rulesets/java/junit.xml/SimplifyBooleanAssertion"></rule>
    <rule ref="rulesets/java/junit.xml/JUnitTestContainsTooManyAsserts">
        <properties>
            <property name="maximumAsserts" value="1"/>      <!-- Maximum number of Asserts in a test method -->
        </properties>
    </rule>
    <rule ref="rulesets/java/junit.xml/UseAssertTrueInsteadOfAssertEquals"></rule>


    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/logging-jakarta-commons.html -->
    <rule ref="rulesets/java/logging-jakarta-commons.xml/UseCorrectExceptionLogging"></rule>
    <rule ref="rulesets/java/logging-jakarta-commons.xml/ProperLogger">
        <properties>
            <property name="staticLoggerName" value="LOG"/>      <!-- Name of the static Logger variable -->
        </properties>
    </rule>
    <rule ref="rulesets/java/logging-jakarta-commons.xml/GuardDebugLogging"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/logging-java.html -->
    <rule ref="rulesets/java/logging-java.xml/MoreThanOneLogger"></rule>
    <rule ref="rulesets/java/logging-java.xml/LoggerIsNotStaticFinal"></rule>
    <rule ref="rulesets/java/logging-java.xml/SystemPrintln"></rule>
    <rule ref="rulesets/java/logging-java.xml/AvoidPrintStackTrace"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/migrating.html -->
    <rule ref="rulesets/java/migrating.xml/ReplaceVectorWithList"></rule>
    <rule ref="rulesets/java/migrating.xml/ReplaceHashtableWithMap"></rule>
    <rule ref="rulesets/java/migrating.xml/ReplaceEnumerationWithIterator"></rule>
    <rule ref="rulesets/java/migrating.xml/AvoidEnumAsIdentifier"></rule>
    <rule ref="rulesets/java/migrating.xml/AvoidAssertAsIdentifier"></rule>
    <rule ref="rulesets/java/migrating.xml/IntegerInstantiation"></rule>
    <rule ref="rulesets/java/migrating.xml/ByteInstantiation"></rule>
    <rule ref="rulesets/java/migrating.xml/ShortInstantiation"></rule>
    <rule ref="rulesets/java/migrating.xml/LongInstantiation"></rule>
    <rule ref="rulesets/java/migrating.xml/JUnit4TestShouldUseBeforeAnnotation"></rule>
    <rule ref="rulesets/java/migrating.xml/JUnit4TestShouldUseAfterAnnotation"></rule>
    <rule ref="rulesets/java/migrating.xml/JUnit4TestShouldUseTestAnnotation"></rule>
    <rule ref="rulesets/java/migrating.xml/JUnit4SuitesShouldUseSuiteAnnotation"></rule>
    <rule ref="rulesets/java/migrating.xml/JUnitUseExpected"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/naming.html -->
    <rule ref="rulesets/java/naming.xml/ShortVariable"></rule>
    <rule ref="rulesets/java/naming.xml/LongVariable">
        <properties>
            <property name="minimum" value="17"/>      <!-- The variable length reporting threshold -->
        </properties>
    </rule>
    <rule ref="rulesets/java/naming.xml/ShortMethodName"></rule>
    <!--
    <rule ref="rulesets/java/naming.xml/VariableNamingConventions">
        <properties>
            <property name="parameterSuffix" value=""/>
            <property name="parameterPrefix" value=""/>
            <property name="localSuffix" value=""/>
            <property name="localPrefix" value=""/>
            <property name="memberSuffix" value=""/>
            <property name="memberPrefix" value=""/>
            <property name="staticSuffix" value=""/>
            <property name="staticPrefix" value=""/>
            <property name="checkParameters" value="true"/>
            <property name="checkLocals" value="true"/>
            <property name="checkMembers" value="true"/>
        </properties>
    </rule>
    -->
    <rule ref="rulesets/java/naming.xml/MethodNamingConventions"></rule>
    <rule ref="rulesets/java/naming.xml/ClassNamingConventions"></rule>
    <rule ref="rulesets/java/naming.xml/AbstractNaming"></rule>
    <rule ref="rulesets/java/naming.xml/AvoidDollarSigns"></rule>
    <rule ref="rulesets/java/naming.xml/MethodWithSameNameAsEnclosingClass"></rule>
    <rule ref="rulesets/java/naming.xml/SuspiciousHashcodeMethodName"></rule>
    <rule ref="rulesets/java/naming.xml/SuspiciousConstantFieldName"></rule>
    <rule ref="rulesets/java/naming.xml/SuspiciousEqualsMethodName"></rule>
    <rule ref="rulesets/java/naming.xml/AvoidFieldNameMatchingTypeName"></rule>
    <rule ref="rulesets/java/naming.xml/NoPackage"></rule>
    <rule ref="rulesets/java/naming.xml/PackageCase"></rule>
    <rule ref="rulesets/java/naming.xml/MisleadingVariableName"></rule>
    <rule ref="rulesets/java/naming.xml/BooleanGetMethodName">
        <properties>
            <property name="checkParameterizedMethods" value="false"/>      <!-- Check parameterized methods -->
        </properties>
    </rule>
    <rule ref="rulesets/java/naming.xml/ShortClassName"></rule>
    <rule ref="rulesets/java/naming.xml/GenericsNaming"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/optimizations.html -->
    <rule ref="rulesets/java/optimizations.xml/LocalVariableCouldBeFinal"></rule>
    <rule ref="rulesets/java/optimizations.xml/MethodArgumentCouldBeFinal"></rule>
    <rule ref="rulesets/java/optimizations.xml/AvoidInstantiatingObjectsInLoops"></rule>
    <rule ref="rulesets/java/optimizations.xml/UseArrayListInsteadOfVector"></rule>
    <rule ref="rulesets/java/optimizations.xml/SimplifyStartsWith"></rule>
    <rule ref="rulesets/java/optimizations.xml/UseStringBufferForStringAppends"></rule>
    <rule ref="rulesets/java/optimizations.xml/UseArraysAsList"></rule>
    <rule ref="rulesets/java/optimizations.xml/AvoidArrayLoops"></rule>
    <rule ref="rulesets/java/optimizations.xml/UnnecessaryWrapperObjectCreation"></rule>
    <rule ref="rulesets/java/optimizations.xml/AddEmptyString"></rule>
    <rule ref="rulesets/java/optimizations.xml/RedundantFieldInitializer"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/strictexception.html -->
    <rule ref="rulesets/java/strictexception.xml/AvoidCatchingThrowable"></rule>
    <rule ref="rulesets/java/strictexception.xml/SignatureDeclareThrowsException"></rule>
    <rule ref="rulesets/java/strictexception.xml/ExceptionAsFlowControl"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidCatchingNPE"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidThrowingRawExceptionTypes"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidThrowingNullPointerException"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidRethrowingException"></rule>
    <rule ref="rulesets/java/strictexception.xml/DoNotExtendJavaLangError"></rule>
    <rule ref="rulesets/java/strictexception.xml/DoNotThrowExceptionInFinally"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidThrowingNewInstanceOfSameException"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidCatchingGenericException"></rule>
    <rule ref="rulesets/java/strictexception.xml/AvoidLosingExceptionInformation"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/strings.html -->
    <rule ref="rulesets/java/strings.xml/AvoidDuplicateLiterals">
        <properties>
            <property name="maxDuplicateLiterals" value="0"/>      <!-- Max duplicate literals -->
            <property name="minimumLength" value="0"/>      <!-- Minimum string length to check -->
            <property name="skipAnnotations" value="true"/>      <!-- Skip literals within annotations -->
        </properties>
    </rule>
    <rule ref="rulesets/java/strings.xml/StringInstantiation"></rule>
    <rule ref="rulesets/java/strings.xml/StringToString"></rule>
    <rule ref="rulesets/java/strings.xml/InefficientStringBuffering"></rule>
    <rule ref="rulesets/java/strings.xml/UnnecessaryCaseChange"></rule>
    <rule ref="rulesets/java/strings.xml/UseStringBufferLength"></rule>
    <rule ref="rulesets/java/strings.xml/AppendCharacterWithChar"></rule>
    <rule ref="rulesets/java/strings.xml/ConsecutiveLiteralAppends">
        <properties>
            <property name="threshold" value="0"/>      <!-- Max consecutive appends -->
        </properties>
    </rule>
    <rule ref="rulesets/java/strings.xml/UseIndexOfChar"></rule>
    <rule ref="rulesets/java/strings.xml/InefficientEmptyStringCheck"></rule>
    <rule ref="rulesets/java/strings.xml/InsufficientStringBufferDeclaration"></rule>
    <rule ref="rulesets/java/strings.xml/UselessStringValueOf"></rule>
    <rule ref="rulesets/java/strings.xml/StringBufferInstantiationWithChar"></rule>
    <rule ref="rulesets/java/strings.xml/UseEqualsToCompareStrings"></rule>
    <rule ref="rulesets/java/strings.xml/AvoidStringBufferField"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/sunsecure.html -->
    <rule ref="rulesets/java/sunsecure.xml/MethodReturnsInternalArray"></rule>
    <rule ref="rulesets/java/sunsecure.xml/ArrayIsStoredDirectly"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/typeresolution.html -->
    <rule ref="rulesets/java/typeresolution.xml/LooseCoupling"></rule>
    <rule ref="rulesets/java/typeresolution.xml/CloneMethodMustImplementCloneable"></rule>
    <rule ref="rulesets/java/typeresolution.xml/UnusedImports"></rule>
    <rule ref="rulesets/java/typeresolution.xml/SignatureDeclareThrowsException">
        <properties>
            <property name="IgnoreJUnitCompletely" value="false"/>      <!-- If true, all methods in a JUnit testcase may throw Exception -->
        </properties>
    </rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/unnecessary.html -->
    <rule ref="rulesets/java/unnecessary.xml/UnnecessaryConversionTemporary"></rule>
    <rule ref="rulesets/java/unnecessary.xml/UnnecessaryReturn"></rule>
    <rule ref="rulesets/java/unnecessary.xml/UnnecessaryFinalModifier"></rule>
    <rule ref="rulesets/java/unnecessary.xml/UselessOverridingMethod">
        <properties>
            <property name="ignoreAnnotations" value="false"/>      <!-- Ignore annotations -->
        </properties>
    </rule>
    <rule ref="rulesets/java/unnecessary.xml/UselessOperationOnImmutable"></rule>
    <rule ref="rulesets/java/unnecessary.xml/UnusedNullCheckInEquals"></rule>
    <rule ref="rulesets/java/unnecessary.xml/UselessParentheses"></rule>

    <!-- See http://pmd.sourceforge.net/pmd-5.0.1/rules/java/unusedcode.html -->
    <rule ref="rulesets/java/unusedcode.xml/UnusedPrivateField"></rule>
    <rule ref="rulesets/java/unusedcode.xml/UnusedLocalVariable"></rule>
    <rule ref="rulesets/java/unusedcode.xml/UnusedFormalParameter">
        <properties>
            <property name="checkAll" value="true"/>      <!-- Ignore annotations -->
        </properties>
    </rule>
    <rule ref="rulesets/java/unusedcode.xml/UnusedModifier"></rule>


    <exclude-pattern>${project_namespace}.criterias.base.*</exclude-pattern>
    <exclude-pattern>${project_namespace}.data.base.*</exclude-pattern>
    <exclude-pattern>${project_namespace}.harmony.*</exclude-pattern>
    <exclude-pattern>${project_namespace}.provider.base.*</exclude-pattern>
    <exclude-pattern>${project_namespace}.provider.utils.base.*</exclude-pattern>
    <exclude-pattern>${project_namespace}.${project_name?cap_first}ApplicationBase.java</exclude-pattern>
    <exclude-pattern>com.google.*</exclude-pattern>
    <exclude-pattern>com.github.*</exclude-pattern>
</ruleset>
