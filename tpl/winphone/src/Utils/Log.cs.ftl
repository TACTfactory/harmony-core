<@header?interpret />
﻿using System;
using System.Diagnostics;

namespace ${project_namespace}.Utils
{
    internal class Log
    {
        [Conditional("DEBUG")]
        public static void D(string tag, string msg)
        {
            Debug.WriteLine("{0} - {1}", tag, msg);
        }

        [Conditional("DEBUG")]
        public static void D(string tag, string msg, params object[] args)
        {
            string message = string.Format("{0} - {1}", tag, msg);
            Debug.WriteLine(message, args);
        }

        [Conditional("DEBUG")]
        public static void D(string tag, Exception msg)
        {
            D(tag, msg.ToString());
        }
    }
}