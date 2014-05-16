<@header?interpret />

using System;
using System.IO;
using System.Windows;
using System.Windows.Resources;

namespace ${project_namespace}.Utils
{
    internal static class AssetManager
    {
        public static StreamReader Open(String file)
        {
            StreamReader result = null;

            StreamResourceInfo si = Application.GetResourceStream(new Uri(
                "Assets/" + file, UriKind.Relative));

            if (si != null)
            {
                result = new StreamReader(si.Stream);
            }

            return result;
        }
    }
}