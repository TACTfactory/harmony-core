<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "ProviderUtilsTestBase.h"
#import "${curr.name}ProviderUtils.h"

/** ${curr.name} provider utils test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ${curr.name}ProviderUtilsTest class instead of this one or you will lose all your modifications.</i></b>
 */
@interface ${curr.name}ProviderUtilsTestBase : ProviderUtilsTestBase

@end