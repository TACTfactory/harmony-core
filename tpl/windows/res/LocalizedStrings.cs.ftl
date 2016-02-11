<@header?interpret />
using ${project_namespace}.Resources.Values;

namespace ${project_namespace}.Resources
{
    /// <summary>
    /// Provides access to string resources.
    /// </summary>
    public class LocalizedStrings
    {
        private static readonly StringsResources localizedResources =
            new StringsResources();

        public StringsResources LocalizedResources
        {
            get { return localizedResources; }
        }
    }
}