{
  "locked": false,
  "version": 1,
  "targets": {
    "UAP,Version=v10.0": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "lib/netcore50/System.Core.dll": {},
          "lib/netcore50/System.dll": {},
          "lib/netcore50/System.Net.dll": {},
          "lib/netcore50/System.Numerics.dll": {},
          "lib/netcore50/System.Runtime.Serialization.dll": {},
          "lib/netcore50/System.ServiceModel.dll": {},
          "lib/netcore50/System.ServiceModel.Web.dll": {},
          "lib/netcore50/System.Windows.dll": {},
          "lib/netcore50/System.Xml.dll": {},
          "lib/netcore50/System.Xml.Linq.dll": {},
          "lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-arm": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "lib/netcore50/System.Core.dll": {},
          "lib/netcore50/System.dll": {},
          "lib/netcore50/System.Net.dll": {},
          "lib/netcore50/System.Numerics.dll": {},
          "lib/netcore50/System.Runtime.Serialization.dll": {},
          "lib/netcore50/System.ServiceModel.dll": {},
          "lib/netcore50/System.ServiceModel.Web.dll": {},
          "lib/netcore50/System.Windows.dll": {},
          "lib/netcore50/System.Xml.dll": {},
          "lib/netcore50/System.Xml.Linq.dll": {},
          "lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.CoreCLR-arm/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        },
        "compile": {
          "ref/dotnet/_._": {}
        },
        "runtime": {
          "runtimes/win8-arm/lib/dotnet/mscorlib.ni.dll": {}
        },
        "native": {
          "runtimes/win8-arm/native/clretwrc.dll": {},
          "runtimes/win8-arm/native/coreclr.dll": {},
          "runtimes/win8-arm/native/dbgshim.dll": {},
          "runtimes/win8-arm/native/mscordaccore.dll": {},
          "runtimes/win8-arm/native/mscordbi.dll": {},
          "runtimes/win8-arm/native/mscorrc.debug.dll": {},
          "runtimes/win8-arm/native/mscorrc.dll": {}
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-arm/4.0.0": {
        "native": {
          "runtimes/win10-arm/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Emit.Lightweight/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.Lightweight.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.Lightweight.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-arm-aot": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "runtimes/aot/lib/netcore50/mscorlib.dll": {},
          "runtimes/aot/lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "runtimes/aot/lib/netcore50/System.Core.dll": {},
          "runtimes/aot/lib/netcore50/System.dll": {},
          "runtimes/aot/lib/netcore50/System.Net.dll": {},
          "runtimes/aot/lib/netcore50/System.Numerics.dll": {},
          "runtimes/aot/lib/netcore50/System.Runtime.Serialization.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.Web.dll": {},
          "runtimes/aot/lib/netcore50/System.Windows.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Linq.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.Native/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-arm/4.0.0": {
        "native": {
          "runtimes/win10-arm/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-x64": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "lib/netcore50/System.Core.dll": {},
          "lib/netcore50/System.dll": {},
          "lib/netcore50/System.Net.dll": {},
          "lib/netcore50/System.Numerics.dll": {},
          "lib/netcore50/System.Runtime.Serialization.dll": {},
          "lib/netcore50/System.ServiceModel.dll": {},
          "lib/netcore50/System.ServiceModel.Web.dll": {},
          "lib/netcore50/System.Windows.dll": {},
          "lib/netcore50/System.Xml.dll": {},
          "lib/netcore50/System.Xml.Linq.dll": {},
          "lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.CoreCLR-x64/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        },
        "compile": {
          "ref/dotnet/_._": {}
        },
        "runtime": {
          "runtimes/win7-x64/lib/dotnet/mscorlib.ni.dll": {}
        },
        "native": {
          "runtimes/win7-x64/native/clretwrc.dll": {},
          "runtimes/win7-x64/native/coreclr.dll": {},
          "runtimes/win7-x64/native/dbgshim.dll": {},
          "runtimes/win7-x64/native/mscordaccore.dll": {},
          "runtimes/win7-x64/native/mscordbi.dll": {},
          "runtimes/win7-x64/native/mscorrc.debug.dll": {},
          "runtimes/win7-x64/native/mscorrc.dll": {}
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Windows.ApiSets-x64/1.0.0": {
        "native": {
          "runtimes/win10-x64/native/_._": {}
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-x64/4.0.0": {
        "native": {
          "runtimes/win10-x64/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Emit.Lightweight/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.Lightweight.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.Lightweight.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-x64-aot": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "runtimes/aot/lib/netcore50/mscorlib.dll": {},
          "runtimes/aot/lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "runtimes/aot/lib/netcore50/System.Core.dll": {},
          "runtimes/aot/lib/netcore50/System.dll": {},
          "runtimes/aot/lib/netcore50/System.Net.dll": {},
          "runtimes/aot/lib/netcore50/System.Numerics.dll": {},
          "runtimes/aot/lib/netcore50/System.Runtime.Serialization.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.Web.dll": {},
          "runtimes/aot/lib/netcore50/System.Windows.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Linq.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.Native/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-x64/4.0.0": {
        "native": {
          "runtimes/win10-x64/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-x86": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "lib/netcore50/System.Core.dll": {},
          "lib/netcore50/System.dll": {},
          "lib/netcore50/System.Net.dll": {},
          "lib/netcore50/System.Numerics.dll": {},
          "lib/netcore50/System.Runtime.Serialization.dll": {},
          "lib/netcore50/System.ServiceModel.dll": {},
          "lib/netcore50/System.ServiceModel.Web.dll": {},
          "lib/netcore50/System.Windows.dll": {},
          "lib/netcore50/System.Xml.dll": {},
          "lib/netcore50/System.Xml.Linq.dll": {},
          "lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.CoreCLR-x86/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        },
        "compile": {
          "ref/dotnet/_._": {}
        },
        "runtime": {
          "runtimes/win7-x86/lib/dotnet/mscorlib.ni.dll": {}
        },
        "native": {
          "runtimes/win7-x86/native/clretwrc.dll": {},
          "runtimes/win7-x86/native/coreclr.dll": {},
          "runtimes/win7-x86/native/dbgshim.dll": {},
          "runtimes/win7-x86/native/mscordaccore.dll": {},
          "runtimes/win7-x86/native/mscordbi.dll": {},
          "runtimes/win7-x86/native/mscorrc.debug.dll": {},
          "runtimes/win7-x86/native/mscorrc.dll": {}
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Windows.ApiSets-x86/1.0.0": {
        "native": {
          "runtimes/win10-x86/native/_._": {}
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-x86/4.0.0": {
        "native": {
          "runtimes/win10-x86/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Emit.Lightweight/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.Lightweight.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.Lightweight.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    },
    "UAP,Version=v10.0/win10-x86-aot": {
      "Microsoft.ApplicationInsights/1.2.3": {
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, )"
        },
        "compile": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        },
        "runtime": {
          "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll": {}
        }
      },
      "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
        "dependencies": {
          "Microsoft.ApplicationInsights": "[1.2.3, 1.2.3]",
          "Microsoft.ApplicationInsights.PersistenceChannel": "[1.2.3, 1.2.3]"
        },
        "compile": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        },
        "runtime": {
          "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll": {}
        }
      },
      "Microsoft.CSharp/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.CSharp.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.CSharp.dll": {}
        }
      },
      "Microsoft.NETCore/5.0.0": {
        "dependencies": {
          "Microsoft.CSharp": "[4.0.0, )",
          "Microsoft.NETCore.Targets": "[1.0.0, )",
          "Microsoft.VisualBasic": "[10.0.0, )",
          "System.AppContext": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.ComponentModel.Annotations": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tools": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Calendars": "[4.0.0, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.Compression.ZipFile": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.IO.UnmanagedMemoryStream": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Parallel": "[4.0.0, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.NetworkInformation": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Metadata": "[1.0.22, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Numerics": "[4.0.0, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Tasks.Dataflow": "[4.5.25, )",
          "System.Threading.Tasks.Parallel": "[4.0.0, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XDocument": "[4.0.10, )"
        }
      },
      "Microsoft.NETCore.Platforms/1.0.0": {},
      "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Runtime": "[1.0.0, )"
        },
        "compile": {
          "ref/netcore50/mscorlib.dll": {},
          "ref/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "ref/netcore50/System.Core.dll": {},
          "ref/netcore50/System.dll": {},
          "ref/netcore50/System.Net.dll": {},
          "ref/netcore50/System.Numerics.dll": {},
          "ref/netcore50/System.Runtime.Serialization.dll": {},
          "ref/netcore50/System.ServiceModel.dll": {},
          "ref/netcore50/System.ServiceModel.Web.dll": {},
          "ref/netcore50/System.Windows.dll": {},
          "ref/netcore50/System.Xml.dll": {},
          "ref/netcore50/System.Xml.Linq.dll": {},
          "ref/netcore50/System.Xml.Serialization.dll": {}
        },
        "runtime": {
          "runtimes/aot/lib/netcore50/mscorlib.dll": {},
          "runtimes/aot/lib/netcore50/System.ComponentModel.DataAnnotations.dll": {},
          "runtimes/aot/lib/netcore50/System.Core.dll": {},
          "runtimes/aot/lib/netcore50/System.dll": {},
          "runtimes/aot/lib/netcore50/System.Net.dll": {},
          "runtimes/aot/lib/netcore50/System.Numerics.dll": {},
          "runtimes/aot/lib/netcore50/System.Runtime.Serialization.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.dll": {},
          "runtimes/aot/lib/netcore50/System.ServiceModel.Web.dll": {},
          "runtimes/aot/lib/netcore50/System.Windows.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Linq.dll": {},
          "runtimes/aot/lib/netcore50/System.Xml.Serialization.dll": {}
        }
      },
      "Microsoft.NETCore.Runtime/1.0.0": {},
      "Microsoft.NETCore.Runtime.Native/1.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, 4.0.10]",
          "System.Diagnostics.Contracts": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Debug": "[4.0.10, 4.0.10]",
          "System.Diagnostics.StackTrace": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tools": "[4.0.0, 4.0.0]",
          "System.Diagnostics.Tracing": "[4.0.20, 4.0.20]",
          "System.Globalization": "[4.0.10, 4.0.10]",
          "System.Globalization.Calendars": "[4.0.0, 4.0.0]",
          "System.IO": "[4.0.10, 4.0.10]",
          "System.ObjectModel": "[4.0.10, 4.0.10]",
          "System.Private.Uri": "[4.0.0, 4.0.0]",
          "System.Reflection": "[4.0.10, 4.0.10]",
          "System.Reflection.Extensions": "[4.0.0, 4.0.0]",
          "System.Reflection.Primitives": "[4.0.0, 4.0.0]",
          "System.Resources.ResourceManager": "[4.0.0, 4.0.0]",
          "System.Runtime": "[4.0.20, 4.0.20]",
          "System.Runtime.Extensions": "[4.0.10, 4.0.10]",
          "System.Runtime.Handles": "[4.0.0, 4.0.0]",
          "System.Runtime.InteropServices": "[4.0.20, 4.0.20]",
          "System.Text.Encoding": "[4.0.10, 4.0.10]",
          "System.Text.Encoding.Extensions": "[4.0.10, 4.0.10]",
          "System.Threading": "[4.0.10, 4.0.10]",
          "System.Threading.Tasks": "[4.0.10, 4.0.10]",
          "System.Threading.Timer": "[4.0.0, 4.0.0]"
        }
      },
      "Microsoft.NETCore.Targets/1.0.0": {
        "dependencies": {
          "Microsoft.NETCore.Platforms": "[1.0.0, )",
          "Microsoft.NETCore.Targets.UniversalWindowsPlatform": "[5.0.0, )"
        }
      },
      "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {},
      "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
        "dependencies": {
          "Microsoft.NETCore": "[5.0.0, )",
          "Microsoft.NETCore.Portable.Compatibility": "[1.0.0, )",
          "Microsoft.NETCore.Runtime": "[1.0.0, )",
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Data.Common": "[4.0.0, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.StackTrace": "[4.0.0, )",
          "System.IO.IsolatedStorage": "[4.0.0, )",
          "System.Net.Http.Rtc": "[4.0.0, )",
          "System.Net.Requests": "[4.0.10, )",
          "System.Net.Sockets": "[4.0.0, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Numerics.Vectors.WindowsRuntime": "[4.0.0, )",
          "System.Reflection.Context": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Runtime.Serialization.Json": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Runtime.WindowsRuntime.UI.Xaml": "[4.0.0, )",
          "System.ServiceModel.Duplex": "[4.0.0, )",
          "System.ServiceModel.Http": "[4.0.10, )",
          "System.ServiceModel.NetTcp": "[4.0.0, )",
          "System.ServiceModel.Primitives": "[4.0.0, )",
          "System.ServiceModel.Security": "[4.0.0, )",
          "System.Text.Encoding.CodePages": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        }
      },
      "Microsoft.VisualBasic/10.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Dynamic.Runtime": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/Microsoft.VisualBasic.dll": {}
        },
        "runtime": {
          "lib/netcore50/Microsoft.VisualBasic.dll": {}
        }
      },
      "Microsoft.Win32.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/Microsoft.Win32.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/Microsoft.Win32.Primitives.dll": {}
        }
      },
      "Newtonsoft.Json/6.0.8": {
        "compile": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        },
        "runtime": {
          "lib/netcore45/Newtonsoft.Json.dll": {}
        }
      },
      "SQLite.Net-PCL/3.1.1": {
        "dependencies": {
          "SQLite.Net.Core-PCL": "[3.1.1, )"
        },
        "compile": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        },
        "runtime": {
          "lib/Windows8/SQLite.Net.Platform.WinRT.dll": {}
        }
      },
      "SQLite.Net.Core-PCL/3.1.1": {
        "compile": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        },
        "runtime": {
          "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll": {}
        }
      },
      "SQLiteNetExtensions/1.3.0": {
        "dependencies": {
          "Newtonsoft.Json": "[6.0.8, )",
          "SQLite.Net-PCL": "[3.0.5, )"
        },
        "compile": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        },
        "runtime": {
          "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll": {}
        }
      },
      "System.AppContext/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.AppContext.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.AppContext.dll": {}
        }
      },
      "System.Collections/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Collections.dll": {}
        }
      },
      "System.Collections.Concurrent/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Concurrent.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Concurrent.dll": {}
        }
      },
      "System.Collections.Immutable/1.1.37": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Immutable.dll": {}
        }
      },
      "System.Collections.NonGeneric/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.NonGeneric.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.NonGeneric.dll": {}
        }
      },
      "System.Collections.Specialized/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Globalization.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Collections.Specialized.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Collections.Specialized.dll": {}
        }
      },
      "System.ComponentModel/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ComponentModel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ComponentModel.dll": {}
        }
      },
      "System.ComponentModel.Annotations/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.ComponentModel": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.Annotations.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.Annotations.dll": {}
        }
      },
      "System.ComponentModel.EventBasedAsync/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ComponentModel.EventBasedAsync.dll": {}
        }
      },
      "System.Data.Common/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Data.Common.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Data.Common.dll": {}
        }
      },
      "System.Diagnostics.Contracts/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Contracts.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Contracts.dll": {}
        }
      },
      "System.Diagnostics.Debug/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Debug.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Debug.dll": {}
        }
      },
      "System.Diagnostics.StackTrace/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.StackTrace.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.StackTrace.dll": {}
        }
      },
      "System.Diagnostics.Tools/4.0.0": {
        "compile": {
          "ref/netcore50/System.Diagnostics.Tools.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tools.dll": {}
        }
      },
      "System.Diagnostics.Tracing/4.0.20": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Diagnostics.Tracing.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tracing.dll": {}
        }
      },
      "System.Dynamic.Runtime/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Dynamic.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Dynamic.Runtime.dll": {}
        }
      },
      "System.Globalization/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.dll": {}
        }
      },
      "System.Globalization.Calendars/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Calendars.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Globalization.Calendars.dll": {}
        }
      },
      "System.Globalization.Extensions/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Globalization.Extensions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Globalization.Extensions.dll": {}
        }
      },
      "System.IO/4.0.10": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.IO.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.IO.dll": {}
        }
      },
      "System.IO.Compression/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.IO.Compression.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.Compression.dll": {}
        }
      },
      "System.IO.Compression.clrcompression-x86/4.0.0": {
        "native": {
          "runtimes/win10-x86/native/ClrCompression.dll": {}
        }
      },
      "System.IO.Compression.ZipFile/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.Compression.ZipFile.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.Compression.ZipFile.dll": {}
        }
      },
      "System.IO.FileSystem/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.FileSystem.dll": {}
        }
      },
      "System.IO.FileSystem.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.IO.FileSystem.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.FileSystem.Primitives.dll": {}
        }
      },
      "System.IO.IsolatedStorage/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.IsolatedStorage.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.IO.IsolatedStorage.dll": {}
        }
      },
      "System.IO.UnmanagedMemoryStream/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.IO.UnmanagedMemoryStream.dll": {}
        }
      },
      "System.Linq/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.dll": {}
        }
      },
      "System.Linq.Expressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Linq.Expressions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Linq.Expressions.dll": {}
        }
      },
      "System.Linq.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Parallel.dll": {}
        }
      },
      "System.Linq.Queryable/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Linq.Queryable.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Linq.Queryable.dll": {}
        }
      },
      "System.Net.Http/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.dll": {}
        }
      },
      "System.Net.Http.Rtc/4.0.0": {
        "dependencies": {
          "System.Net.Http": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Net.Http.Rtc.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Http.Rtc.dll": {}
        }
      },
      "System.Net.NetworkInformation/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.InteropServices.WindowsRuntime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Net.NetworkInformation.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.NetworkInformation.dll": {}
        }
      },
      "System.Net.Primitives/4.0.10": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Primitives.dll": {}
        }
      },
      "System.Net.Requests/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Requests.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.Requests.dll": {}
        }
      },
      "System.Net.Sockets/4.0.0": {
        "dependencies": {
          "System.Private.Networking": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.Sockets.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Net.Sockets.dll": {}
        }
      },
      "System.Net.WebHeaderCollection/4.0.0": {
        "dependencies": {
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Net.WebHeaderCollection.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Net.WebHeaderCollection.dll": {}
        }
      },
      "System.Numerics.Vectors/4.1.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Numerics.Vectors.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.dll": {}
        }
      },
      "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
        "dependencies": {
          "System.Numerics.Vectors": "[4.1.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll": {}
        }
      },
      "System.ObjectModel/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.ObjectModel.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.ObjectModel.dll": {}
        }
      },
      "System.Private.DataContractSerialization/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.DataContractSerialization.dll": {}
        }
      },
      "System.Private.Networking/4.0.0": {
        "dependencies": {
          "Microsoft.Win32.Primitives": "[4.0.0, )",
          "System.Collections": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Overlapped": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.Networking.dll": {}
        }
      },
      "System.Private.ServiceModel/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Collections.NonGeneric": "[4.0.0, )",
          "System.Collections.Specialized": "[4.0.0, )",
          "System.ComponentModel.EventBasedAsync": "[4.0.10, )",
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.Compression": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Linq.Expressions": "[4.0.10, )",
          "System.Linq.Queryable": "[4.0.0, )",
          "System.Net.Http": "[4.0.0, )",
          "System.Net.Primitives": "[4.0.10, )",
          "System.Net.WebHeaderCollection": "[4.0.0, )",
          "System.ObjectModel": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.DispatchProxy": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )",
          "System.Runtime.Serialization.Xml": "[4.0.10, )",
          "System.Runtime.WindowsRuntime": "[4.0.10, )",
          "System.Security.Claims": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )",
          "System.Threading.Timer": "[4.0.0, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )",
          "System.Xml.XmlSerializer": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "lib/netcore50/System.Private.ServiceModel.dll": {}
        }
      },
      "System.Private.Uri/4.0.0": {
        "compile": {
          "ref/netcore50/_._": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Private.Uri.dll": {}
        }
      },
      "System.Reflection/4.0.10": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.dll": {}
        }
      },
      "System.Reflection.Context/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Context.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Context.dll": {}
        }
      },
      "System.Reflection.DispatchProxy/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.DispatchProxy.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.DispatchProxy.dll": {}
        }
      },
      "System.Reflection.Emit/4.0.0": {
        "dependencies": {
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Emit.ILGeneration": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.dll": {}
        }
      },
      "System.Reflection.Emit.ILGeneration/4.0.0": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.Emit.ILGeneration.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Reflection.Emit.ILGeneration.dll": {}
        }
      },
      "System.Reflection.Extensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Extensions.dll": {}
        }
      },
      "System.Reflection.Metadata/1.0.22": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Immutable": "[1.1.37, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.0, )",
          "System.Text.Encoding.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Reflection.Metadata.dll": {}
        }
      },
      "System.Reflection.Primitives/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Reflection.Primitives.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.Primitives.dll": {}
        }
      },
      "System.Reflection.TypeExtensions/4.0.0": {
        "dependencies": {
          "System.Diagnostics.Contracts": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Reflection.TypeExtensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Reflection.TypeExtensions.dll": {}
        }
      },
      "System.Resources.ResourceManager/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.Resources.ResourceManager.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Resources.ResourceManager.dll": {}
        }
      },
      "System.Runtime/4.0.20": {
        "dependencies": {
          "System.Private.Uri": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.dll": {}
        }
      },
      "System.Runtime.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Extensions.dll": {}
        }
      },
      "System.Runtime.Handles/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Handles.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Handles.dll": {}
        }
      },
      "System.Runtime.InteropServices/4.0.20": {
        "dependencies": {
          "System.Reflection": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.InteropServices.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.dll": {}
        }
      },
      "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
        "compile": {
          "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.Numerics/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Numerics.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.Numerics.dll": {}
        }
      },
      "System.Runtime.Serialization.Json/4.0.0": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.Serialization.Json.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Json.dll": {}
        }
      },
      "System.Runtime.Serialization.Primitives/4.0.10": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Runtime.Serialization.Primitives.dll": {}
        }
      },
      "System.Runtime.Serialization.Xml/4.0.10": {
        "dependencies": {
          "System.Private.DataContractSerialization": "[4.0.0, )",
          "System.Runtime.Serialization.Primitives": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Runtime.Serialization.Xml.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Xml.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime/4.0.10": {
        "dependencies": {
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.10, )",
          "System.ObjectModel": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Runtime.WindowsRuntime.dll": {}
        }
      },
      "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
        "dependencies": {
          "System.Globalization": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.WindowsRuntime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll": {}
        }
      },
      "System.Security.Claims/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Globalization": "[4.0.0, )",
          "System.IO": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Security.Principal": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Security.Claims.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Security.Claims.dll": {}
        }
      },
      "System.Security.Principal/4.0.0": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/netcore50/System.Security.Principal.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Security.Principal.dll": {}
        }
      },
      "System.ServiceModel.Duplex/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Duplex.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Duplex.dll": {}
        }
      },
      "System.ServiceModel.Http/4.0.10": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/dotnet/System.ServiceModel.Http.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Http.dll": {}
        }
      },
      "System.ServiceModel.NetTcp/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.NetTcp.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.NetTcp.dll": {}
        }
      },
      "System.ServiceModel.Primitives/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Primitives.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Primitives.dll": {}
        }
      },
      "System.ServiceModel.Security/4.0.0": {
        "dependencies": {
          "System.Private.ServiceModel": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )"
        },
        "compile": {
          "ref/netcore50/System.ServiceModel.Security.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.ServiceModel.Security.dll": {}
        }
      },
      "System.Text.Encoding/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.dll": {}
        }
      },
      "System.Text.Encoding.CodePages/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.CodePages.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.Encoding.CodePages.dll": {}
        }
      },
      "System.Text.Encoding.Extensions/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Text.Encoding": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.Encoding.Extensions.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.Extensions.dll": {}
        }
      },
      "System.Text.RegularExpressions/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Text.RegularExpressions.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Text.RegularExpressions.dll": {}
        }
      },
      "System.Threading/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.dll": {}
        }
      },
      "System.Threading.Overlapped/4.0.0": {
        "dependencies": {
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Runtime.Handles": "[4.0.0, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Threading": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Overlapped.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Overlapped.dll": {}
        }
      },
      "System.Threading.Tasks/4.0.10": {
        "dependencies": {
          "System.Runtime": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Threading.Tasks.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Tasks.dll": {}
        }
      },
      "System.Threading.Tasks.Dataflow/4.5.25": {
        "dependencies": {
          "System.Collections": "[4.0.0, )",
          "System.Collections.Concurrent": "[4.0.0, )",
          "System.Diagnostics.Debug": "[4.0.0, )",
          "System.Diagnostics.Tracing": "[4.0.0, )",
          "System.Dynamic.Runtime": "[4.0.0, )",
          "System.Linq": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.0, )",
          "System.Runtime.Extensions": "[4.0.0, )",
          "System.Threading": "[4.0.0, )",
          "System.Threading.Tasks": "[4.0.0, )"
        },
        "compile": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Threading.Tasks.Dataflow.dll": {}
        }
      },
      "System.Threading.Tasks.Parallel/4.0.0": {
        "dependencies": {
          "System.Collections.Concurrent": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Diagnostics.Tracing": "[4.0.20, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/netcore50/System.Threading.Tasks.Parallel.dll": {}
        },
        "runtime": {
          "lib/netcore50/System.Threading.Tasks.Parallel.dll": {}
        }
      },
      "System.Threading.Timer/4.0.0": {
        "compile": {
          "ref/netcore50/System.Threading.Timer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Threading.Timer.dll": {}
        }
      },
      "System.Xml.ReaderWriter/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.IO.FileSystem": "[4.0.0, )",
          "System.IO.FileSystem.Primitives": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Runtime.InteropServices": "[4.0.20, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Text.Encoding.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading.Tasks": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.ReaderWriter.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.ReaderWriter.dll": {}
        }
      },
      "System.Xml.XDocument/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Reflection": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XDocument.dll": {}
        }
      },
      "System.Xml.XmlDocument/4.0.0": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.Encoding": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlDocument.dll": {}
        },
        "runtime": {
          "lib/dotnet/System.Xml.XmlDocument.dll": {}
        }
      },
      "System.Xml.XmlSerializer/4.0.10": {
        "dependencies": {
          "System.Collections": "[4.0.10, )",
          "System.Diagnostics.Debug": "[4.0.10, )",
          "System.Globalization": "[4.0.10, )",
          "System.IO": "[4.0.10, )",
          "System.Linq": "[4.0.0, )",
          "System.Reflection": "[4.0.10, )",
          "System.Reflection.Extensions": "[4.0.0, )",
          "System.Reflection.Primitives": "[4.0.0, )",
          "System.Reflection.TypeExtensions": "[4.0.0, )",
          "System.Resources.ResourceManager": "[4.0.0, )",
          "System.Runtime": "[4.0.20, )",
          "System.Runtime.Extensions": "[4.0.10, )",
          "System.Text.RegularExpressions": "[4.0.10, )",
          "System.Threading": "[4.0.10, )",
          "System.Xml.ReaderWriter": "[4.0.10, )",
          "System.Xml.XmlDocument": "[4.0.0, )"
        },
        "compile": {
          "ref/dotnet/System.Xml.XmlSerializer.dll": {}
        },
        "runtime": {
          "runtimes/win8-aot/lib/netcore50/System.Xml.XmlSerializer.dll": {}
        }
      }
    }
  },
  "libraries": {
    "Microsoft.ApplicationInsights/1.2.3": {
      "sha512": "a34atobxpucCTTX9VCD7aIu6Ydzry+Va7TxK2tTQcfaMvefGW4kUKtg9GHKIn19IaXe+A4oZt/GBqsOt5vQrBA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/net40/Microsoft.ApplicationInsights.dll",
        "lib/net40/Microsoft.ApplicationInsights.XML",
        "lib/net45/Microsoft.ApplicationInsights.dll",
        "lib/net45/Microsoft.ApplicationInsights.XML",
        "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.dll",
        "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.XML",
        "lib/wp8/Microsoft.ApplicationInsights.dll",
        "lib/wp8/Microsoft.ApplicationInsights.XML",
        "Microsoft.ApplicationInsights.nuspec",
        "package/services/metadata/core-properties/3b36eef276524f5d883c618d5fd8c500.psmdcp"
      ]
    },
    "Microsoft.ApplicationInsights.PersistenceChannel/1.2.3": {
      "sha512": "5MGcvPQVaGlGyAVh9adg4sSkIVSYDKn+H3+YnZa9hi1gH/g6UsRt4HLc8vp/IxjlbmXbJyAB7dYJV/NHmAGAgA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/net40/Microsoft.ApplicationInsights.PersistenceChannel.dll",
        "lib/net40/Microsoft.ApplicationInsights.PersistenceChannel.XML",
        "lib/net45/Microsoft.ApplicationInsights.PersistenceChannel.dll",
        "lib/net45/Microsoft.ApplicationInsights.PersistenceChannel.XML",
        "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.dll",
        "lib/portable-win81+wpa81/Microsoft.ApplicationInsights.PersistenceChannel.XML",
        "lib/wp8/Microsoft.ApplicationInsights.PersistenceChannel.dll",
        "lib/wp8/Microsoft.ApplicationInsights.PersistenceChannel.XML",
        "Microsoft.ApplicationInsights.PersistenceChannel.nuspec",
        "package/services/metadata/core-properties/f973733421c14c3594986b3f2c22213a.psmdcp"
      ]
    },
    "Microsoft.ApplicationInsights.WindowsApps/1.1.1": {
      "sha512": "Orj0UQrjQ2z0aWZZoexl734Ge7Q4oVev3VFSsk6t8l2kImrfJ1eygkJWtQ1d7u0kF6MLLrvUvsMSxo+0rgPiMQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.dll",
        "lib/win81/Microsoft.ApplicationInsights.Extensibility.Windows.XML",
        "lib/wp8/Microsoft.ApplicationInsights.Extensibility.Windows.dll",
        "lib/wp8/Microsoft.ApplicationInsights.Extensibility.Windows.XML",
        "lib/wpa81/Microsoft.ApplicationInsights.Extensibility.Windows.dll",
        "lib/wpa81/Microsoft.ApplicationInsights.Extensibility.Windows.XML",
        "Microsoft.ApplicationInsights.WindowsApps.nuspec",
        "package/services/metadata/core-properties/8265b8c93c244c80b4e8bce27f31849d.psmdcp"
      ]
    },
    "Microsoft.CSharp/4.0.0": {
      "sha512": "oWqeKUxHXdK6dL2CFjgMcaBISbkk+AqEg+yvJHE4DElNzS4QaTsCflgkkqZwVlWby1Dg9zo9n+iCAMFefFdJ/A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/Microsoft.CSharp.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net45/_._",
        "lib/netcore50/Microsoft.CSharp.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "Microsoft.CSharp.nuspec",
        "package/services/metadata/core-properties/a8a7171824ab4656b3141cda0591ff66.psmdcp",
        "ref/dotnet/de/Microsoft.CSharp.xml",
        "ref/dotnet/es/Microsoft.CSharp.xml",
        "ref/dotnet/fr/Microsoft.CSharp.xml",
        "ref/dotnet/it/Microsoft.CSharp.xml",
        "ref/dotnet/ja/Microsoft.CSharp.xml",
        "ref/dotnet/ko/Microsoft.CSharp.xml",
        "ref/dotnet/Microsoft.CSharp.dll",
        "ref/dotnet/Microsoft.CSharp.xml",
        "ref/dotnet/ru/Microsoft.CSharp.xml",
        "ref/dotnet/zh-hans/Microsoft.CSharp.xml",
        "ref/dotnet/zh-hant/Microsoft.CSharp.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net45/_._",
        "ref/netcore50/Microsoft.CSharp.dll",
        "ref/netcore50/Microsoft.CSharp.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._"
      ]
    },
    "Microsoft.NETCore/5.0.0": {
      "sha512": "QQMp0yYQbIdfkKhdEE6Umh2Xonau7tasG36Trw/YlHoWgYQLp7T9L+ZD8EPvdj5ubRhtOuKEKwM7HMpkagB9ZA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_._",
        "_rels/.rels",
        "Microsoft.NETCore.nuspec",
        "package/services/metadata/core-properties/340ac37fb1224580ae47c59ebdd88964.psmdcp"
      ]
    },
    "Microsoft.NETCore.Platforms/1.0.0": {
      "sha512": "0N77OwGZpXqUco2C/ynv1os7HqdFYifvNIbveLDKqL5PZaz05Rl9enCwVBjF61aumHKueLWIJ3prnmdAXxww4A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Platforms.nuspec",
        "package/services/metadata/core-properties/36b51d4c6b524527902ff1a182a64e42.psmdcp",
        "runtime.json"
      ]
    },
    "Microsoft.NETCore.Portable.Compatibility/1.0.0": {
      "sha512": "5/IFqf2zN1jzktRJitxO+5kQ+0AilcIbPvSojSJwDG3cGNSMZg44LXLB5E9RkSETE0Wh4QoALdNh1koKoF7/mA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dnxcore50/System.ComponentModel.DataAnnotations.dll",
        "lib/dnxcore50/System.Core.dll",
        "lib/dnxcore50/System.dll",
        "lib/dnxcore50/System.Net.dll",
        "lib/dnxcore50/System.Numerics.dll",
        "lib/dnxcore50/System.Runtime.Serialization.dll",
        "lib/dnxcore50/System.ServiceModel.dll",
        "lib/dnxcore50/System.ServiceModel.Web.dll",
        "lib/dnxcore50/System.Windows.dll",
        "lib/dnxcore50/System.Xml.dll",
        "lib/dnxcore50/System.Xml.Linq.dll",
        "lib/dnxcore50/System.Xml.Serialization.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ComponentModel.DataAnnotations.dll",
        "lib/netcore50/System.Core.dll",
        "lib/netcore50/System.dll",
        "lib/netcore50/System.Net.dll",
        "lib/netcore50/System.Numerics.dll",
        "lib/netcore50/System.Runtime.Serialization.dll",
        "lib/netcore50/System.ServiceModel.dll",
        "lib/netcore50/System.ServiceModel.Web.dll",
        "lib/netcore50/System.Windows.dll",
        "lib/netcore50/System.Xml.dll",
        "lib/netcore50/System.Xml.Linq.dll",
        "lib/netcore50/System.Xml.Serialization.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "Microsoft.NETCore.Portable.Compatibility.nuspec",
        "package/services/metadata/core-properties/8131b8ae030a45e7986737a0c1d04ef5.psmdcp",
        "ref/dotnet/mscorlib.dll",
        "ref/dotnet/System.ComponentModel.DataAnnotations.dll",
        "ref/dotnet/System.Core.dll",
        "ref/dotnet/System.dll",
        "ref/dotnet/System.Net.dll",
        "ref/dotnet/System.Numerics.dll",
        "ref/dotnet/System.Runtime.Serialization.dll",
        "ref/dotnet/System.ServiceModel.dll",
        "ref/dotnet/System.ServiceModel.Web.dll",
        "ref/dotnet/System.Windows.dll",
        "ref/dotnet/System.Xml.dll",
        "ref/dotnet/System.Xml.Linq.dll",
        "ref/dotnet/System.Xml.Serialization.dll",
        "ref/net45/_._",
        "ref/netcore50/mscorlib.dll",
        "ref/netcore50/System.ComponentModel.DataAnnotations.dll",
        "ref/netcore50/System.Core.dll",
        "ref/netcore50/System.dll",
        "ref/netcore50/System.Net.dll",
        "ref/netcore50/System.Numerics.dll",
        "ref/netcore50/System.Runtime.Serialization.dll",
        "ref/netcore50/System.ServiceModel.dll",
        "ref/netcore50/System.ServiceModel.Web.dll",
        "ref/netcore50/System.Windows.dll",
        "ref/netcore50/System.Xml.dll",
        "ref/netcore50/System.Xml.Linq.dll",
        "ref/netcore50/System.Xml.Serialization.dll",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/aot/lib/netcore50/mscorlib.dll",
        "runtimes/aot/lib/netcore50/System.ComponentModel.DataAnnotations.dll",
        "runtimes/aot/lib/netcore50/System.Core.dll",
        "runtimes/aot/lib/netcore50/System.dll",
        "runtimes/aot/lib/netcore50/System.Net.dll",
        "runtimes/aot/lib/netcore50/System.Numerics.dll",
        "runtimes/aot/lib/netcore50/System.Runtime.Serialization.dll",
        "runtimes/aot/lib/netcore50/System.ServiceModel.dll",
        "runtimes/aot/lib/netcore50/System.ServiceModel.Web.dll",
        "runtimes/aot/lib/netcore50/System.Windows.dll",
        "runtimes/aot/lib/netcore50/System.Xml.dll",
        "runtimes/aot/lib/netcore50/System.Xml.Linq.dll",
        "runtimes/aot/lib/netcore50/System.Xml.Serialization.dll"
      ]
    },
    "Microsoft.NETCore.Runtime/1.0.0": {
      "sha512": "AjaMNpXLW4miEQorIqyn6iQ+BZBId6qXkhwyeh1vl6kXLqosZusbwmLNlvj/xllSQrd3aImJbvlHusam85g+xQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Runtime.nuspec",
        "package/services/metadata/core-properties/f289de2ffef9428684eca0c193bc8765.psmdcp",
        "runtime.json"
      ]
    },
    "Microsoft.NETCore.Runtime.CoreCLR-arm/1.0.0": {
      "sha512": "hoJfIl981eXwn9Tz8onO/J1xaYApIfp/YrhjSh9rRhml1U5Wj80LBgyp/6n+KI3VlvcAraThhnHnCTp+M3Uh+w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Runtime.CoreCLR-arm.nuspec",
        "package/services/metadata/core-properties/c1cbeaed81514106b6b7971ac193f132.psmdcp",
        "ref/dotnet/_._",
        "runtimes/win8-arm/lib/dotnet/mscorlib.ni.dll",
        "runtimes/win8-arm/native/clretwrc.dll",
        "runtimes/win8-arm/native/coreclr.dll",
        "runtimes/win8-arm/native/dbgshim.dll",
        "runtimes/win8-arm/native/mscordaccore.dll",
        "runtimes/win8-arm/native/mscordbi.dll",
        "runtimes/win8-arm/native/mscorrc.debug.dll",
        "runtimes/win8-arm/native/mscorrc.dll"
      ]
    },
    "Microsoft.NETCore.Runtime.CoreCLR-x64/1.0.0": {
      "sha512": "DaY5Z13xCZpnVIGluC5sCo4/0wy1rl6mptBH7v3RYi3guAmG88aSeFoQzyZepo0H0jEixUxNFM0+MB6Jc+j0bw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Runtime.CoreCLR-x64.nuspec",
        "package/services/metadata/core-properties/bd7bd26b6b8242179b5b8ca3d9ffeb95.psmdcp",
        "ref/dotnet/_._",
        "runtimes/win7-x64/lib/dotnet/mscorlib.ni.dll",
        "runtimes/win7-x64/native/clretwrc.dll",
        "runtimes/win7-x64/native/coreclr.dll",
        "runtimes/win7-x64/native/dbgshim.dll",
        "runtimes/win7-x64/native/mscordaccore.dll",
        "runtimes/win7-x64/native/mscordbi.dll",
        "runtimes/win7-x64/native/mscorrc.debug.dll",
        "runtimes/win7-x64/native/mscorrc.dll"
      ]
    },
    "Microsoft.NETCore.Runtime.CoreCLR-x86/1.0.0": {
      "sha512": "2LDffu5Is/X01GVPVuye4Wmz9/SyGDNq1Opgl5bXG3206cwNiCwsQgILOtfSWVp5mn4w401+8cjhBy3THW8HQQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Runtime.CoreCLR-x86.nuspec",
        "package/services/metadata/core-properties/dd7e29450ade4bdaab9794850cd11d7a.psmdcp",
        "ref/dotnet/_._",
        "runtimes/win7-x86/lib/dotnet/mscorlib.ni.dll",
        "runtimes/win7-x86/native/clretwrc.dll",
        "runtimes/win7-x86/native/coreclr.dll",
        "runtimes/win7-x86/native/dbgshim.dll",
        "runtimes/win7-x86/native/mscordaccore.dll",
        "runtimes/win7-x86/native/mscordbi.dll",
        "runtimes/win7-x86/native/mscorrc.debug.dll",
        "runtimes/win7-x86/native/mscorrc.dll"
      ]
    },
    "Microsoft.NETCore.Runtime.Native/1.0.0": {
      "sha512": "tMsWWrH1AJCguiM22zK/vr6COxqz62Q1F02B07IXAUN405R3HGk5SkD/DL0Hte+OTjNtW9LkKXpOggGBRwYFNg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_._",
        "_rels/.rels",
        "Microsoft.NETCore.Runtime.Native.nuspec",
        "package/services/metadata/core-properties/a985563978b547f984c16150ef73e353.psmdcp"
      ]
    },
    "Microsoft.NETCore.Targets/1.0.0": {
      "sha512": "XfITpPjYLYRmAeZtb9diw6P7ylLQsSC1U2a/xj10iQpnHxkiLEBXop/psw15qMPuNca7lqgxWvzZGpQxphuXaw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Targets.nuspec",
        "package/services/metadata/core-properties/5413a5ed3fde4121a1c9ee8feb12ba66.psmdcp",
        "runtime.json"
      ]
    },
    "Microsoft.NETCore.Targets.UniversalWindowsPlatform/5.0.0": {
      "sha512": "jszcJ6okLlhqF4OQbhSbixLOuLUyVT3BP7Y7/i7fcDMwnHBd1Pmdz6M1Al9SMDKVLA2oSaItg4tq6C0ydv8lYQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Targets.UniversalWindowsPlatform.nuspec",
        "package/services/metadata/core-properties/0d18100c9f8c491492d8ddeaa9581526.psmdcp",
        "runtime.json"
      ]
    },
    "Microsoft.NETCore.UniversalWindowsPlatform/5.0.0": {
      "sha512": "D0nsAm+yTk0oSSC7E6PcmuuEewBAQbGgIXNcCnRqQ4qLPdQLMjMHg8cilGs3xZgwTRQmMtEn45TAatrU1otWPQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_._",
        "_rels/.rels",
        "Microsoft.NETCore.UniversalWindowsPlatform.nuspec",
        "package/services/metadata/core-properties/5dffd3ce5b6640febe2db09251387062.psmdcp"
      ]
    },
    "Microsoft.NETCore.Windows.ApiSets-x64/1.0.0": {
      "sha512": "NC+dpFMdhujz2sWAdJ8EmBk07p1zOlNi0FCCnZEbzftABpw9xZ99EMP/bUJrPTgCxHfzJAiuLPOtAauzVINk0w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Windows.ApiSets-x64.nuspec",
        "package/services/metadata/core-properties/b25894a2a9234c329a98dc84006b2292.psmdcp",
        "runtimes/win10-x64/native/_._",
        "runtimes/win7-x64/native/API-MS-Win-Base-Util-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-com-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-comm-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-com-private-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-console-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-console-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-datetime-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-datetime-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-debug-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-debug-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-delayload-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-errorhandling-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-errorhandling-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-fibers-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-fibers-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-file-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-file-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-file-l1-2-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-file-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-file-l2-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-handle-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-heap-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-heap-obsolete-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-interlocked-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-io-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-io-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-kernel32-legacy-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-kernel32-legacy-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-1.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win7-x64/native/api-ms-win-core-libraryloader-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-libraryloader-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-localization-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-localization-l1-2-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-localization-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-localization-obsolete-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-memory-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-memory-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-memory-l1-1-2.dll",
        "runtimes/win7-x64/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win7-x64/native/api-ms-win-core-namedpipe-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-normalization-l1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-PrivateProfile-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-privateprofile-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processenvironment-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processenvironment-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processsecurity-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processthreads-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processthreads-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-processthreads-l1-1-2.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-ProcessTopology-Obsolete-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-profile-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-psapi-ansi-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-psapi-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-psapi-obsolete-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-realtime-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-registry-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-registry-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-rtlsupport-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-shlwapi-legacy-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-shlwapi-obsolete-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-shutdown-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-shutdown-l1-1-1.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-StringAnsi-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-string-l1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Core-String-L2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-stringloader-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-stringloader-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-string-obsolete-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-synch-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-synch-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-sysinfo-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-sysinfo-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-sysinfo-l1-2-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win7-x64/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win7-x64/native/api-ms-win-core-threadpool-l1-2-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-threadpool-legacy-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-threadpool-private-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-timezone-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-url-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-util-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-version-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-error-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-error-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-registration-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-robuffer-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-roparameterizediid-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-winrt-string-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-wow64-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-xstate-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-core-xstate-l2-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-devices-config-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-devices-config-L1-1-1.dll",
        "runtimes/win7-x64/native/API-MS-Win-Eventing-ClassicProvider-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Eventing-Consumer-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Eventing-Controller-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Eventing-Legacy-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-Eventing-Provider-L1-1-0.dll",
        "runtimes/win7-x64/native/API-MS-Win-EventLog-Legacy-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-ro-typeresolution-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-base-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-cryptoapi-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-lsalookup-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-lsalookup-l2-1-1.dll",
        "runtimes/win7-x64/native/API-MS-Win-Security-LsaPolicy-L1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-provider-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-security-sddl-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-service-core-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-service-core-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-service-management-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-service-management-l2-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-service-private-l1-1-0.dll",
        "runtimes/win7-x64/native/api-ms-win-service-private-l1-1-1.dll",
        "runtimes/win7-x64/native/api-ms-win-service-winsvc-l1-1-0.dll",
        "runtimes/win7-x64/native/ext-ms-win-advapi32-encryptedfile-l1-1-0.dll",
        "runtimes/win81-x64/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win81-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win81-x64/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win81-x64/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win81-x64/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win81-x64/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win81-x64/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win81-x64/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win8-x64/native/api-ms-win-core-file-l1-2-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-file-l2-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-kernel32-legacy-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win8-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-1.dll",
        "runtimes/win8-x64/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win8-x64/native/api-ms-win-core-localization-l1-2-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-localization-obsolete-l1-2-0.dll",
        "runtimes/win8-x64/native/api-ms-win-core-memory-l1-1-2.dll",
        "runtimes/win8-x64/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win8-x64/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-privateprofile-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-processthreads-l1-1-2.dll",
        "runtimes/win8-x64/native/api-ms-win-core-shutdown-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-stringloader-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-sysinfo-l1-2-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win8-x64/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win8-x64/native/api-ms-win-core-winrt-error-l1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-core-xstate-l2-1-0.dll",
        "runtimes/win8-x64/native/API-MS-Win-devices-config-L1-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win8-x64/native/api-ms-win-security-cryptoapi-l1-1-0.dll",
        "runtimes/win8-x64/native/api-ms-win-security-lsalookup-l2-1-1.dll",
        "runtimes/win8-x64/native/api-ms-win-service-private-l1-1-1.dll"
      ]
    },
    "Microsoft.NETCore.Windows.ApiSets-x86/1.0.0": {
      "sha512": "/HDRdhz5bZyhHwQ/uk/IbnDIX5VDTsHntEZYkTYo57dM+U3Ttel9/OJv0mjL64wTO/QKUJJNKp9XO+m7nSVjJQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "Microsoft.NETCore.Windows.ApiSets-x86.nuspec",
        "package/services/metadata/core-properties/b773d829b3664669b45b4b4e97bdb635.psmdcp",
        "runtimes/win10-x86/native/_._",
        "runtimes/win7-x86/native/API-MS-Win-Base-Util-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-com-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-comm-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-com-private-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-console-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-console-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-datetime-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-datetime-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-debug-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-debug-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-delayload-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-errorhandling-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-errorhandling-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-fibers-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-fibers-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-file-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-file-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-file-l1-2-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-file-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-file-l2-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-handle-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-heap-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-heap-obsolete-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-interlocked-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-io-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-io-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-kernel32-legacy-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-kernel32-legacy-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-1.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win7-x86/native/api-ms-win-core-libraryloader-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-libraryloader-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-localization-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-localization-l1-2-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-localization-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-localization-obsolete-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-memory-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-memory-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-memory-l1-1-2.dll",
        "runtimes/win7-x86/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win7-x86/native/api-ms-win-core-namedpipe-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-normalization-l1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-PrivateProfile-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-privateprofile-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processenvironment-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processenvironment-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processsecurity-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processthreads-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processthreads-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-processthreads-l1-1-2.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-ProcessTopology-Obsolete-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-profile-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-psapi-ansi-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-psapi-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-psapi-obsolete-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-realtime-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-registry-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-registry-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-rtlsupport-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-shlwapi-legacy-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-shlwapi-obsolete-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-shutdown-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-shutdown-l1-1-1.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-StringAnsi-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-string-l1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Core-String-L2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-stringloader-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-stringloader-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-string-obsolete-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-synch-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-synch-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-sysinfo-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-sysinfo-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-sysinfo-l1-2-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win7-x86/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win7-x86/native/api-ms-win-core-threadpool-l1-2-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-threadpool-legacy-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-threadpool-private-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-timezone-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-url-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-util-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-version-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-error-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-error-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-registration-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-robuffer-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-roparameterizediid-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-winrt-string-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-wow64-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-xstate-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-core-xstate-l2-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-devices-config-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-devices-config-L1-1-1.dll",
        "runtimes/win7-x86/native/API-MS-Win-Eventing-ClassicProvider-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Eventing-Consumer-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Eventing-Controller-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Eventing-Legacy-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-Eventing-Provider-L1-1-0.dll",
        "runtimes/win7-x86/native/API-MS-Win-EventLog-Legacy-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-ro-typeresolution-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-base-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-cryptoapi-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-lsalookup-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-lsalookup-l2-1-1.dll",
        "runtimes/win7-x86/native/API-MS-Win-Security-LsaPolicy-L1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-provider-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-security-sddl-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-service-core-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-service-core-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-service-management-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-service-management-l2-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-service-private-l1-1-0.dll",
        "runtimes/win7-x86/native/api-ms-win-service-private-l1-1-1.dll",
        "runtimes/win7-x86/native/api-ms-win-service-winsvc-l1-1-0.dll",
        "runtimes/win7-x86/native/ext-ms-win-advapi32-encryptedfile-l1-1-0.dll",
        "runtimes/win81-x86/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win81-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win81-x86/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win81-x86/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win81-x86/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win81-x86/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win81-x86/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win81-x86/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win8-x86/native/api-ms-win-core-file-l1-2-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-file-l2-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-kernel32-legacy-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-kernel32-legacy-l1-1-2.dll",
        "runtimes/win8-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-1.dll",
        "runtimes/win8-x86/native/API-MS-Win-Core-Kernel32-Private-L1-1-2.dll",
        "runtimes/win8-x86/native/api-ms-win-core-localization-l1-2-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-localization-obsolete-l1-2-0.dll",
        "runtimes/win8-x86/native/api-ms-win-core-memory-l1-1-2.dll",
        "runtimes/win8-x86/native/api-ms-win-core-memory-l1-1-3.dll",
        "runtimes/win8-x86/native/api-ms-win-core-namedpipe-l1-2-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-privateprofile-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-processthreads-l1-1-2.dll",
        "runtimes/win8-x86/native/api-ms-win-core-shutdown-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-stringloader-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-string-obsolete-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-sysinfo-l1-2-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-sysinfo-l1-2-2.dll",
        "runtimes/win8-x86/native/api-ms-win-core-sysinfo-l1-2-3.dll",
        "runtimes/win8-x86/native/api-ms-win-core-winrt-error-l1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-core-xstate-l2-1-0.dll",
        "runtimes/win8-x86/native/API-MS-Win-devices-config-L1-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-security-cpwl-l1-1-0.dll",
        "runtimes/win8-x86/native/api-ms-win-security-cryptoapi-l1-1-0.dll",
        "runtimes/win8-x86/native/api-ms-win-security-lsalookup-l2-1-1.dll",
        "runtimes/win8-x86/native/api-ms-win-service-private-l1-1-1.dll"
      ]
    },
    "Microsoft.VisualBasic/10.0.0": {
      "sha512": "5BEm2/HAVd97whRlCChU7rmSh/9cwGlZ/NTNe3Jl07zuPWfKQq5TUvVNUmdvmEe8QRecJLZ4/e7WF1i1O8V42g==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/Microsoft.VisualBasic.dll",
        "lib/net45/_._",
        "lib/netcore50/Microsoft.VisualBasic.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "Microsoft.VisualBasic.nuspec",
        "package/services/metadata/core-properties/5dbd3a7042354092a8b352b655cf4376.psmdcp",
        "ref/dotnet/de/Microsoft.VisualBasic.xml",
        "ref/dotnet/es/Microsoft.VisualBasic.xml",
        "ref/dotnet/fr/Microsoft.VisualBasic.xml",
        "ref/dotnet/it/Microsoft.VisualBasic.xml",
        "ref/dotnet/ja/Microsoft.VisualBasic.xml",
        "ref/dotnet/ko/Microsoft.VisualBasic.xml",
        "ref/dotnet/Microsoft.VisualBasic.dll",
        "ref/dotnet/Microsoft.VisualBasic.xml",
        "ref/dotnet/ru/Microsoft.VisualBasic.xml",
        "ref/dotnet/zh-hans/Microsoft.VisualBasic.xml",
        "ref/dotnet/zh-hant/Microsoft.VisualBasic.xml",
        "ref/net45/_._",
        "ref/netcore50/Microsoft.VisualBasic.dll",
        "ref/netcore50/Microsoft.VisualBasic.xml",
        "ref/win8/_._",
        "ref/wpa81/_._"
      ]
    },
    "Microsoft.Win32.Primitives/4.0.0": {
      "sha512": "CypEz9/lLOup8CEhiAmvr7aLs1zKPYyEU1sxQeEr6G0Ci8/F0Y6pYR1zzkROjM8j8Mq0typmbu676oYyvErQvg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/Microsoft.Win32.Primitives.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/Microsoft.Win32.Primitives.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "Microsoft.Win32.Primitives.nuspec",
        "package/services/metadata/core-properties/1d4eb9d0228b48b88d2df3822fba2d86.psmdcp",
        "ref/dotnet/de/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/es/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/fr/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/it/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/ja/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/ko/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/Microsoft.Win32.Primitives.dll",
        "ref/dotnet/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/ru/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/zh-hans/Microsoft.Win32.Primitives.xml",
        "ref/dotnet/zh-hant/Microsoft.Win32.Primitives.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/Microsoft.Win32.Primitives.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._"
      ]
    },
    "Newtonsoft.Json/6.0.8": {
      "sha512": "7ut47NDedTW19EbL0JpFDYUP62fcuz27hJrehCDNajdCS5NtqL+E39+7Um3OkNc2wl2ym7K8Ln5eNuLus6mVGQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/net20/Newtonsoft.Json.dll",
        "lib/net20/Newtonsoft.Json.xml",
        "lib/net35/Newtonsoft.Json.dll",
        "lib/net35/Newtonsoft.Json.xml",
        "lib/net40/Newtonsoft.Json.dll",
        "lib/net40/Newtonsoft.Json.xml",
        "lib/net45/Newtonsoft.Json.dll",
        "lib/net45/Newtonsoft.Json.xml",
        "lib/netcore45/Newtonsoft.Json.dll",
        "lib/netcore45/Newtonsoft.Json.xml",
        "lib/portable-net40+sl5+wp80+win8+wpa81/Newtonsoft.Json.dll",
        "lib/portable-net40+sl5+wp80+win8+wpa81/Newtonsoft.Json.xml",
        "lib/portable-net45+wp80+win8+wpa81+aspnetcore50/Newtonsoft.Json.dll",
        "lib/portable-net45+wp80+win8+wpa81+aspnetcore50/Newtonsoft.Json.xml",
        "Newtonsoft.Json.nuspec",
        "package/services/metadata/core-properties/d6cceb7474b245aab29598e10c6e65d6.psmdcp",
        "tools/install.ps1"
      ]
    },
    "SQLite.Net-PCL/3.1.1": {
      "sha512": "eIaIk+k4MwvBeKyzj1kgIegWXYZlaDAHNzsaYP7wNDgSqAdaza8FgCRun7mIZScJD/RIuKxN/b1V+bkC+/WIeA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/MonoAndroid/SQLite.Net.Platform.XamarinAndroid.dll",
        "lib/monotouch/SQLite.Net.Platform.XamarinIOS.dll",
        "lib/net4/SQLite.Net.Platform.Win32.dll",
        "lib/net40/SQLite.Net.Platform.Generic.dll",
        "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll",
        "lib/portable-win81+wpa81/SQLite.Net.Platform.WinRT.dll",
        "lib/Windows8/SQLite.Net.Platform.WinRT.dll",
        "lib/windowsphone8/ARM/SQLite.Net.Platform.WindowsPhone8.dll",
        "lib/windowsphone8/x86/SQLite.Net.Platform.WindowsPhone8.dll",
        "lib/Xamarin.iOS10/SQLite.Net.Platform.XamarinIOS.Unified.dll",
        "package/services/metadata/core-properties/42617fe08935462ea38a3073a9954c4b.psmdcp",
        "SQLite.Net-PCL.nuspec"
      ]
    },
    "SQLite.Net.Core-PCL/3.1.1": {
      "sha512": "/0VdXnyj2srEh5rM1RW/snXJrVGRsxGB3xjW4EkhZIig9N/8EocZZrEr0vwRJU8a9Lz4BrweoLyKwp2evmSIzg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/portable-win8+net45+wp8+wpa81+MonoAndroid1+MonoTouch1/SQLite.Net.dll",
        "package/services/metadata/core-properties/23cc4adcea6c40a7a0e82fb6b4a09ba7.psmdcp",
        "SQLite.Net.Core-PCL.nuspec"
      ]
    },
    "SQLiteNetExtensions/1.3.0": {
      "sha512": "XYt89yGciul0pPbZDxrl9Gnj3r8NmFbxC0dfjw7HqRCaSsCxjj9LGy3bBTW4/gykdQ7S3fvAx71f8orROfc9Hg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll",
        "lib/portable-net45+netcore45+wpa81+wp8+MonoAndroid1+MonoTouch1/SQLiteNetExtensions.dll.mdb",
        "package/services/metadata/core-properties/1.psmdcp",
        "SQLiteNetExtensions.nuspec",
        "src/Attributes/CascadeOperation.cs",
        "src/Attributes/ForeignKeyAttribute.cs",
        "src/Attributes/ManyToManyAttribute.cs",
        "src/Attributes/ManyToOneAttribute.cs",
        "src/Attributes/OneToManyAttribute.cs",
        "src/Attributes/OneToOneAttribute.cs",
        "src/Attributes/RelationshipAttribute.cs",
        "src/Attributes/TextBlobAttribute.cs",
        "src/Exceptions/IncorrectRelationshipException.cs",
        "src/Extensions/ReadOperations.cs",
        "src/Extensions/ReflectionExtensions.cs",
        "src/Extensions/TextBlob/ITextBlobSerializer.cs",
        "src/Extensions/TextBlob/Serializers/JsonBlobSerializer.cs",
        "src/Extensions/TextBlob/TextBlobOperations.cs",
        "src/Extensions/WriteOperations.cs",
        "src/obj/Debug/.NETPortable,Version=v4.0,Profile=Profile136.AssemblyAttribute.cs",
        "src/obj/Debug/.NETPortable,Version=v4.5,Profile=Profile259.AssemblyAttribute.cs",
        "src/obj/Release/.NETPortable,Version=v4.5,Profile=Profile259.AssemblyAttribute.cs",
        "src/Properties/AssemblyInfo.cs"
      ]
    },
    "System.AppContext/4.0.0": {
      "sha512": "gUoYgAWDC3+xhKeU5KSLbYDhTdBYk9GssrMSCcWUADzOglW+s0AmwVhOUGt2tL5xUl7ZXoYTPdA88zCgKrlG0A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.AppContext.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.AppContext.dll",
        "lib/netcore50/System.AppContext.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/3b390478e0cd42eb8818bbab19299738.psmdcp",
        "ref/dotnet/de/System.AppContext.xml",
        "ref/dotnet/es/System.AppContext.xml",
        "ref/dotnet/fr/System.AppContext.xml",
        "ref/dotnet/it/System.AppContext.xml",
        "ref/dotnet/ja/System.AppContext.xml",
        "ref/dotnet/ko/System.AppContext.xml",
        "ref/dotnet/ru/System.AppContext.xml",
        "ref/dotnet/System.AppContext.dll",
        "ref/dotnet/System.AppContext.xml",
        "ref/dotnet/zh-hans/System.AppContext.xml",
        "ref/dotnet/zh-hant/System.AppContext.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.AppContext.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.AppContext.nuspec"
      ]
    },
    "System.Collections/4.0.10": {
      "sha512": "ux6ilcZZjV/Gp7JEZpe+2V1eTueq6NuoGRM3eZCFuPM25hLVVgCRuea6STW8hvqreIOE59irJk5/ovpA5xQipw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Collections.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Collections.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/b4f8061406e54dbda8f11b23186be11a.psmdcp",
        "ref/dotnet/de/System.Collections.xml",
        "ref/dotnet/es/System.Collections.xml",
        "ref/dotnet/fr/System.Collections.xml",
        "ref/dotnet/it/System.Collections.xml",
        "ref/dotnet/ja/System.Collections.xml",
        "ref/dotnet/ko/System.Collections.xml",
        "ref/dotnet/ru/System.Collections.xml",
        "ref/dotnet/System.Collections.dll",
        "ref/dotnet/System.Collections.xml",
        "ref/dotnet/zh-hans/System.Collections.xml",
        "ref/dotnet/zh-hant/System.Collections.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Collections.dll",
        "System.Collections.nuspec"
      ]
    },
    "System.Collections.Concurrent/4.0.10": {
      "sha512": "ZtMEqOPAjAIqR8fqom9AOKRaB94a+emO2O8uOP6vyJoNswSPrbiwN7iH53rrVpvjMVx0wr4/OMpI7486uGZjbw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Collections.Concurrent.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/c982a1e1e1644b62952fc4d4dcbe0d42.psmdcp",
        "ref/dotnet/de/System.Collections.Concurrent.xml",
        "ref/dotnet/es/System.Collections.Concurrent.xml",
        "ref/dotnet/fr/System.Collections.Concurrent.xml",
        "ref/dotnet/it/System.Collections.Concurrent.xml",
        "ref/dotnet/ja/System.Collections.Concurrent.xml",
        "ref/dotnet/ko/System.Collections.Concurrent.xml",
        "ref/dotnet/ru/System.Collections.Concurrent.xml",
        "ref/dotnet/System.Collections.Concurrent.dll",
        "ref/dotnet/System.Collections.Concurrent.xml",
        "ref/dotnet/zh-hans/System.Collections.Concurrent.xml",
        "ref/dotnet/zh-hant/System.Collections.Concurrent.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Collections.Concurrent.nuspec"
      ]
    },
    "System.Collections.Immutable/1.1.37": {
      "sha512": "fTpqwZYBzoklTT+XjTRK8KxvmrGkYHzBiylCcKyQcxiOM8k+QvhNBxRvFHDWzy4OEP5f8/9n+xQ9mEgEXY+muA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Collections.Immutable.dll",
        "lib/dotnet/System.Collections.Immutable.xml",
        "lib/portable-net45+win8+wp8+wpa81/System.Collections.Immutable.dll",
        "lib/portable-net45+win8+wp8+wpa81/System.Collections.Immutable.xml",
        "package/services/metadata/core-properties/a02fdeabe1114a24bba55860b8703852.psmdcp",
        "System.Collections.Immutable.nuspec"
      ]
    },
    "System.Collections.NonGeneric/4.0.0": {
      "sha512": "rVgwrFBMkmp8LI6GhAYd6Bx+2uLIXjRfNg6Ie+ASfX8ESuh9e2HNxFy2yh1MPIXZq3OAYa+0mmULVwpnEC6UDA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Collections.NonGeneric.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Collections.NonGeneric.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/185704b1dc164b078b61038bde9ab31a.psmdcp",
        "ref/dotnet/de/System.Collections.NonGeneric.xml",
        "ref/dotnet/es/System.Collections.NonGeneric.xml",
        "ref/dotnet/fr/System.Collections.NonGeneric.xml",
        "ref/dotnet/it/System.Collections.NonGeneric.xml",
        "ref/dotnet/ja/System.Collections.NonGeneric.xml",
        "ref/dotnet/ko/System.Collections.NonGeneric.xml",
        "ref/dotnet/ru/System.Collections.NonGeneric.xml",
        "ref/dotnet/System.Collections.NonGeneric.dll",
        "ref/dotnet/System.Collections.NonGeneric.xml",
        "ref/dotnet/zh-hans/System.Collections.NonGeneric.xml",
        "ref/dotnet/zh-hant/System.Collections.NonGeneric.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Collections.NonGeneric.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Collections.NonGeneric.nuspec"
      ]
    },
    "System.Collections.Specialized/4.0.0": {
      "sha512": "poJFwQCUOoXqvdoGxx+3p8Z63yawcYKPBSFP67Z2jICeOINvEIQZN7mVOAnC7gsVF0WU+A2wtVwfhagC7UCgAg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Collections.Specialized.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Collections.Specialized.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/e7002e4ccd044c00a7cbd4a37efe3400.psmdcp",
        "ref/dotnet/de/System.Collections.Specialized.xml",
        "ref/dotnet/es/System.Collections.Specialized.xml",
        "ref/dotnet/fr/System.Collections.Specialized.xml",
        "ref/dotnet/it/System.Collections.Specialized.xml",
        "ref/dotnet/ja/System.Collections.Specialized.xml",
        "ref/dotnet/ko/System.Collections.Specialized.xml",
        "ref/dotnet/ru/System.Collections.Specialized.xml",
        "ref/dotnet/System.Collections.Specialized.dll",
        "ref/dotnet/System.Collections.Specialized.xml",
        "ref/dotnet/zh-hans/System.Collections.Specialized.xml",
        "ref/dotnet/zh-hant/System.Collections.Specialized.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Collections.Specialized.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Collections.Specialized.nuspec"
      ]
    },
    "System.ComponentModel/4.0.0": {
      "sha512": "BzpLdSi++ld7rJLOOt5f/G9GxujP202bBgKORsHcGV36rLB0mfSA2h8chTMoBzFhgN7TE14TmJ2J7Q1RyNCTAw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.ComponentModel.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ComponentModel.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/58b9abdedb3a4985a487cb8bf4bdcbd7.psmdcp",
        "ref/dotnet/de/System.ComponentModel.xml",
        "ref/dotnet/es/System.ComponentModel.xml",
        "ref/dotnet/fr/System.ComponentModel.xml",
        "ref/dotnet/it/System.ComponentModel.xml",
        "ref/dotnet/ja/System.ComponentModel.xml",
        "ref/dotnet/ko/System.ComponentModel.xml",
        "ref/dotnet/ru/System.ComponentModel.xml",
        "ref/dotnet/System.ComponentModel.dll",
        "ref/dotnet/System.ComponentModel.xml",
        "ref/dotnet/zh-hans/System.ComponentModel.xml",
        "ref/dotnet/zh-hant/System.ComponentModel.xml",
        "ref/net45/_._",
        "ref/netcore50/System.ComponentModel.dll",
        "ref/netcore50/System.ComponentModel.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "System.ComponentModel.nuspec"
      ]
    },
    "System.ComponentModel.Annotations/4.0.10": {
      "sha512": "7+XGyEZx24nP1kpHxCB9e+c6D0fdVDvFwE1xujE9BzlXyNVcy5J5aIO0H/ECupx21QpyRvzZibGAHfL/XLL6dw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.ComponentModel.Annotations.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/012e5fa97b3d450eb20342cd9ba88069.psmdcp",
        "ref/dotnet/de/System.ComponentModel.Annotations.xml",
        "ref/dotnet/es/System.ComponentModel.Annotations.xml",
        "ref/dotnet/fr/System.ComponentModel.Annotations.xml",
        "ref/dotnet/it/System.ComponentModel.Annotations.xml",
        "ref/dotnet/ja/System.ComponentModel.Annotations.xml",
        "ref/dotnet/ko/System.ComponentModel.Annotations.xml",
        "ref/dotnet/ru/System.ComponentModel.Annotations.xml",
        "ref/dotnet/System.ComponentModel.Annotations.dll",
        "ref/dotnet/System.ComponentModel.Annotations.xml",
        "ref/dotnet/zh-hans/System.ComponentModel.Annotations.xml",
        "ref/dotnet/zh-hant/System.ComponentModel.Annotations.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.ComponentModel.Annotations.nuspec"
      ]
    },
    "System.ComponentModel.EventBasedAsync/4.0.10": {
      "sha512": "d6kXcHUgP0jSPXEQ6hXJYCO6CzfoCi7t9vR3BfjSQLrj4HzpuATpx1gkN7itmTW1O+wjuw6rai4378Nj6N70yw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.ComponentModel.EventBasedAsync.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/5094900f1f7e4f4dae27507acc72f2a5.psmdcp",
        "ref/dotnet/de/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/es/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/fr/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/it/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/ja/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/ko/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/ru/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/System.ComponentModel.EventBasedAsync.dll",
        "ref/dotnet/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/zh-hans/System.ComponentModel.EventBasedAsync.xml",
        "ref/dotnet/zh-hant/System.ComponentModel.EventBasedAsync.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.ComponentModel.EventBasedAsync.nuspec"
      ]
    },
    "System.Data.Common/4.0.0": {
      "sha512": "SA7IdoTWiImVr0exDM68r0mKmR4f/qFGxZUrJQKu4YS7F+3afWzSOCezHxWdevQ0ONi4WRQsOiv+Zf9p8H0Feg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Data.Common.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Data.Common.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/aa5ad20c33d94c8e8016ba4fc64d8d66.psmdcp",
        "ref/dotnet/de/System.Data.Common.xml",
        "ref/dotnet/es/System.Data.Common.xml",
        "ref/dotnet/fr/System.Data.Common.xml",
        "ref/dotnet/it/System.Data.Common.xml",
        "ref/dotnet/ja/System.Data.Common.xml",
        "ref/dotnet/ko/System.Data.Common.xml",
        "ref/dotnet/ru/System.Data.Common.xml",
        "ref/dotnet/System.Data.Common.dll",
        "ref/dotnet/System.Data.Common.xml",
        "ref/dotnet/zh-hans/System.Data.Common.xml",
        "ref/dotnet/zh-hant/System.Data.Common.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Data.Common.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Data.Common.nuspec"
      ]
    },
    "System.Diagnostics.Contracts/4.0.0": {
      "sha512": "lMc7HNmyIsu0pKTdA4wf+FMq5jvouUd+oUpV4BdtyqoV0Pkbg9u/7lTKFGqpjZRQosWHq1+B32Lch2wf4AmloA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Diagnostics.Contracts.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Diagnostics.Contracts.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/c6cd3d0bbc304cbca14dc3d6bff6579c.psmdcp",
        "ref/dotnet/de/System.Diagnostics.Contracts.xml",
        "ref/dotnet/es/System.Diagnostics.Contracts.xml",
        "ref/dotnet/fr/System.Diagnostics.Contracts.xml",
        "ref/dotnet/it/System.Diagnostics.Contracts.xml",
        "ref/dotnet/ja/System.Diagnostics.Contracts.xml",
        "ref/dotnet/ko/System.Diagnostics.Contracts.xml",
        "ref/dotnet/ru/System.Diagnostics.Contracts.xml",
        "ref/dotnet/System.Diagnostics.Contracts.dll",
        "ref/dotnet/System.Diagnostics.Contracts.xml",
        "ref/dotnet/zh-hans/System.Diagnostics.Contracts.xml",
        "ref/dotnet/zh-hant/System.Diagnostics.Contracts.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Diagnostics.Contracts.dll",
        "ref/netcore50/System.Diagnostics.Contracts.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Contracts.dll",
        "System.Diagnostics.Contracts.nuspec"
      ]
    },
    "System.Diagnostics.Debug/4.0.10": {
      "sha512": "pi2KthuvI2LWV2c2V+fwReDsDiKpNl040h6DcwFOb59SafsPT/V1fCy0z66OKwysurJkBMmp5j5CBe3Um+ub0g==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Diagnostics.Debug.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Diagnostics.Debug.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/bfb05c26051f4a5f9015321db9cb045c.psmdcp",
        "ref/dotnet/de/System.Diagnostics.Debug.xml",
        "ref/dotnet/es/System.Diagnostics.Debug.xml",
        "ref/dotnet/fr/System.Diagnostics.Debug.xml",
        "ref/dotnet/it/System.Diagnostics.Debug.xml",
        "ref/dotnet/ja/System.Diagnostics.Debug.xml",
        "ref/dotnet/ko/System.Diagnostics.Debug.xml",
        "ref/dotnet/ru/System.Diagnostics.Debug.xml",
        "ref/dotnet/System.Diagnostics.Debug.dll",
        "ref/dotnet/System.Diagnostics.Debug.xml",
        "ref/dotnet/zh-hans/System.Diagnostics.Debug.xml",
        "ref/dotnet/zh-hant/System.Diagnostics.Debug.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Debug.dll",
        "System.Diagnostics.Debug.nuspec"
      ]
    },
    "System.Diagnostics.StackTrace/4.0.0": {
      "sha512": "PItgenqpRiMqErvQONBlfDwctKpWVrcDSW5pppNZPJ6Bpiyz+KjsWoSiaqs5dt03HEbBTMNCrZb8KCkh7YfXmw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Diagnostics.StackTrace.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Diagnostics.StackTrace.dll",
        "lib/netcore50/System.Diagnostics.StackTrace.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/5c7ca489a36944d895c628fced7e9107.psmdcp",
        "ref/dotnet/de/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/es/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/fr/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/it/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/ja/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/ko/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/ru/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/System.Diagnostics.StackTrace.dll",
        "ref/dotnet/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/zh-hans/System.Diagnostics.StackTrace.xml",
        "ref/dotnet/zh-hant/System.Diagnostics.StackTrace.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Diagnostics.StackTrace.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Diagnostics.StackTrace.dll",
        "System.Diagnostics.StackTrace.nuspec"
      ]
    },
    "System.Diagnostics.Tools/4.0.0": {
      "sha512": "uw5Qi2u5Cgtv4xv3+8DeB63iaprPcaEHfpeJqlJiLjIVy6v0La4ahJ6VW9oPbJNIjcavd24LKq0ctT9ssuQXsw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Diagnostics.Tools.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Diagnostics.Tools.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/20f622a1ae5b4e3992fc226d88d36d59.psmdcp",
        "ref/dotnet/de/System.Diagnostics.Tools.xml",
        "ref/dotnet/es/System.Diagnostics.Tools.xml",
        "ref/dotnet/fr/System.Diagnostics.Tools.xml",
        "ref/dotnet/it/System.Diagnostics.Tools.xml",
        "ref/dotnet/ja/System.Diagnostics.Tools.xml",
        "ref/dotnet/ko/System.Diagnostics.Tools.xml",
        "ref/dotnet/ru/System.Diagnostics.Tools.xml",
        "ref/dotnet/System.Diagnostics.Tools.dll",
        "ref/dotnet/System.Diagnostics.Tools.xml",
        "ref/dotnet/zh-hans/System.Diagnostics.Tools.xml",
        "ref/dotnet/zh-hant/System.Diagnostics.Tools.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Diagnostics.Tools.dll",
        "ref/netcore50/System.Diagnostics.Tools.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tools.dll",
        "System.Diagnostics.Tools.nuspec"
      ]
    },
    "System.Diagnostics.Tracing/4.0.20": {
      "sha512": "gn/wexGHc35Fv++5L1gYHMY5g25COfiZ0PGrL+3PfwzoJd4X2LbTAm/U8d385SI6BKQBI/z4dQfvneS9J27+Tw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Diagnostics.Tracing.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Diagnostics.Tracing.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/13423e75e6344b289b3779b51522737c.psmdcp",
        "ref/dotnet/de/System.Diagnostics.Tracing.xml",
        "ref/dotnet/es/System.Diagnostics.Tracing.xml",
        "ref/dotnet/fr/System.Diagnostics.Tracing.xml",
        "ref/dotnet/it/System.Diagnostics.Tracing.xml",
        "ref/dotnet/ja/System.Diagnostics.Tracing.xml",
        "ref/dotnet/ko/System.Diagnostics.Tracing.xml",
        "ref/dotnet/ru/System.Diagnostics.Tracing.xml",
        "ref/dotnet/System.Diagnostics.Tracing.dll",
        "ref/dotnet/System.Diagnostics.Tracing.xml",
        "ref/dotnet/zh-hans/System.Diagnostics.Tracing.xml",
        "ref/dotnet/zh-hant/System.Diagnostics.Tracing.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Diagnostics.Tracing.dll",
        "System.Diagnostics.Tracing.nuspec"
      ]
    },
    "System.Dynamic.Runtime/4.0.10": {
      "sha512": "r10VTLdlxtYp46BuxomHnwx7vIoMOr04CFoC/jJJfY22f7HQQ4P+cXY2Nxo6/rIxNNqOxwdbQQwv7Gl88Jsu1w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Dynamic.Runtime.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Dynamic.Runtime.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/b7571751b95d4952803c5011dab33c3b.psmdcp",
        "ref/dotnet/de/System.Dynamic.Runtime.xml",
        "ref/dotnet/es/System.Dynamic.Runtime.xml",
        "ref/dotnet/fr/System.Dynamic.Runtime.xml",
        "ref/dotnet/it/System.Dynamic.Runtime.xml",
        "ref/dotnet/ja/System.Dynamic.Runtime.xml",
        "ref/dotnet/ko/System.Dynamic.Runtime.xml",
        "ref/dotnet/ru/System.Dynamic.Runtime.xml",
        "ref/dotnet/System.Dynamic.Runtime.dll",
        "ref/dotnet/System.Dynamic.Runtime.xml",
        "ref/dotnet/zh-hans/System.Dynamic.Runtime.xml",
        "ref/dotnet/zh-hant/System.Dynamic.Runtime.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtime.json",
        "runtimes/win8-aot/lib/netcore50/System.Dynamic.Runtime.dll",
        "System.Dynamic.Runtime.nuspec"
      ]
    },
    "System.Globalization/4.0.10": {
      "sha512": "kzRtbbCNAxdafFBDogcM36ehA3th8c1PGiz8QRkZn8O5yMBorDHSK8/TGJPYOaCS5zdsGk0u9qXHnW91nqy7fw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Globalization.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Globalization.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/93bcad242a4e4ad7afd0b53244748763.psmdcp",
        "ref/dotnet/de/System.Globalization.xml",
        "ref/dotnet/es/System.Globalization.xml",
        "ref/dotnet/fr/System.Globalization.xml",
        "ref/dotnet/it/System.Globalization.xml",
        "ref/dotnet/ja/System.Globalization.xml",
        "ref/dotnet/ko/System.Globalization.xml",
        "ref/dotnet/ru/System.Globalization.xml",
        "ref/dotnet/System.Globalization.dll",
        "ref/dotnet/System.Globalization.xml",
        "ref/dotnet/zh-hans/System.Globalization.xml",
        "ref/dotnet/zh-hant/System.Globalization.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Globalization.dll",
        "System.Globalization.nuspec"
      ]
    },
    "System.Globalization.Calendars/4.0.0": {
      "sha512": "cL6WrdGKnNBx9W/iTr+jbffsEO4RLjEtOYcpVSzPNDoli6X5Q6bAfWtJYbJNOPi8Q0fXgBEvKK1ncFL/3FTqlA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Globalization.Calendars.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Globalization.Calendars.dll",
        "lib/netcore50/System.Globalization.Calendars.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/95fc8eb4808e4f31a967f407c94eba0f.psmdcp",
        "ref/dotnet/de/System.Globalization.Calendars.xml",
        "ref/dotnet/es/System.Globalization.Calendars.xml",
        "ref/dotnet/fr/System.Globalization.Calendars.xml",
        "ref/dotnet/it/System.Globalization.Calendars.xml",
        "ref/dotnet/ja/System.Globalization.Calendars.xml",
        "ref/dotnet/ko/System.Globalization.Calendars.xml",
        "ref/dotnet/ru/System.Globalization.Calendars.xml",
        "ref/dotnet/System.Globalization.Calendars.dll",
        "ref/dotnet/System.Globalization.Calendars.xml",
        "ref/dotnet/zh-hans/System.Globalization.Calendars.xml",
        "ref/dotnet/zh-hant/System.Globalization.Calendars.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Globalization.Calendars.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Globalization.Calendars.dll",
        "System.Globalization.Calendars.nuspec"
      ]
    },
    "System.Globalization.Extensions/4.0.0": {
      "sha512": "rqbUXiwpBCvJ18ySCsjh20zleazO+6fr3s5GihC2sVwhyS0MUl6+oc5Rzk0z6CKkS4kmxbZQSeZLsK7cFSO0ng==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Globalization.Extensions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Globalization.Extensions.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/a0490a34737f448fb53635b5210e48e4.psmdcp",
        "ref/dotnet/de/System.Globalization.Extensions.xml",
        "ref/dotnet/es/System.Globalization.Extensions.xml",
        "ref/dotnet/fr/System.Globalization.Extensions.xml",
        "ref/dotnet/it/System.Globalization.Extensions.xml",
        "ref/dotnet/ja/System.Globalization.Extensions.xml",
        "ref/dotnet/ko/System.Globalization.Extensions.xml",
        "ref/dotnet/ru/System.Globalization.Extensions.xml",
        "ref/dotnet/System.Globalization.Extensions.dll",
        "ref/dotnet/System.Globalization.Extensions.xml",
        "ref/dotnet/zh-hans/System.Globalization.Extensions.xml",
        "ref/dotnet/zh-hant/System.Globalization.Extensions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Globalization.Extensions.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Globalization.Extensions.nuspec"
      ]
    },
    "System.IO/4.0.10": {
      "sha512": "kghf1CeYT+W2lw8a50/GxFz5HR9t6RkL4BvjxtTp1NxtEFWywnMA9W8FH/KYXiDNThcw9u/GOViDON4iJFGXIQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.IO.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.IO.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/db72fd58a86b4d13a6d2858ebec46705.psmdcp",
        "ref/dotnet/de/System.IO.xml",
        "ref/dotnet/es/System.IO.xml",
        "ref/dotnet/fr/System.IO.xml",
        "ref/dotnet/it/System.IO.xml",
        "ref/dotnet/ja/System.IO.xml",
        "ref/dotnet/ko/System.IO.xml",
        "ref/dotnet/ru/System.IO.xml",
        "ref/dotnet/System.IO.dll",
        "ref/dotnet/System.IO.xml",
        "ref/dotnet/zh-hans/System.IO.xml",
        "ref/dotnet/zh-hant/System.IO.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.IO.dll",
        "System.IO.nuspec"
      ]
    },
    "System.IO.Compression/4.0.0": {
      "sha512": "S+ljBE3py8pujTrsOOYHtDg2cnAifn6kBu/pfh1hMWIXd8DoVh0ADTA6Puv4q+nYj+Msm6JoFLNwuRSmztbsDQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.IO.Compression.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net45/_._",
        "lib/netcore50/System.IO.Compression.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/cdbbc16eba65486f85d2caf9357894f3.psmdcp",
        "ref/dotnet/de/System.IO.Compression.xml",
        "ref/dotnet/es/System.IO.Compression.xml",
        "ref/dotnet/fr/System.IO.Compression.xml",
        "ref/dotnet/it/System.IO.Compression.xml",
        "ref/dotnet/ja/System.IO.Compression.xml",
        "ref/dotnet/ko/System.IO.Compression.xml",
        "ref/dotnet/ru/System.IO.Compression.xml",
        "ref/dotnet/System.IO.Compression.dll",
        "ref/dotnet/System.IO.Compression.xml",
        "ref/dotnet/zh-hans/System.IO.Compression.xml",
        "ref/dotnet/zh-hant/System.IO.Compression.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net45/_._",
        "ref/netcore50/System.IO.Compression.dll",
        "ref/netcore50/System.IO.Compression.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtime.json",
        "System.IO.Compression.nuspec"
      ]
    },
    "System.IO.Compression.clrcompression-arm/4.0.0": {
      "sha512": "Kk21GecAbI+H6tMP6/lMssGObbhoHwLiREiB5UkNMCypdxACuF+6gmrdDTousCUcbH28CJeo7tArrnUc+bchuw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "package/services/metadata/core-properties/e09228dcfd7b47adb2ddcf73e2eb6ddf.psmdcp",
        "runtimes/win10-arm/native/ClrCompression.dll",
        "runtimes/win7-arm/native/clrcompression.dll",
        "System.IO.Compression.clrcompression-arm.nuspec"
      ]
    },
    "System.IO.Compression.clrcompression-x64/4.0.0": {
      "sha512": "Lqr+URMwKzf+8HJF6YrqEqzKzDzFJTE4OekaxqdIns71r8Ufbd8SbZa0LKl9q+7nu6Em4SkIEXVMB7plSXekOw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "package/services/metadata/core-properties/416c3fd9fab749d484e0fed458de199f.psmdcp",
        "runtimes/win10-x64/native/ClrCompression.dll",
        "runtimes/win7-x64/native/clrcompression.dll",
        "System.IO.Compression.clrcompression-x64.nuspec"
      ]
    },
    "System.IO.Compression.clrcompression-x86/4.0.0": {
      "sha512": "GmevpuaMRzYDXHu+xuV10fxTO8DsP7OKweWxYtkaxwVnDSj9X6RBupSiXdiveq9yj/xjZ1NbG+oRRRb99kj+VQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "package/services/metadata/core-properties/cd12f86c8cc2449589dfbe349763f7b3.psmdcp",
        "runtimes/win10-x86/native/ClrCompression.dll",
        "runtimes/win7-x86/native/clrcompression.dll",
        "System.IO.Compression.clrcompression-x86.nuspec"
      ]
    },
    "System.IO.Compression.ZipFile/4.0.0": {
      "sha512": "pwntmtsJqtt6Lez4Iyv4GVGW6DaXUTo9Rnlsx0MFagRgX+8F/sxG5S/IzDJabBj68sUWViz1QJrRZL4V9ngWDg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.IO.Compression.ZipFile.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.IO.Compression.ZipFile.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/60dc66d592ac41008e1384536912dabf.psmdcp",
        "ref/dotnet/de/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/es/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/fr/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/it/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/ja/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/ko/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/ru/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/System.IO.Compression.ZipFile.dll",
        "ref/dotnet/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/zh-hans/System.IO.Compression.ZipFile.xml",
        "ref/dotnet/zh-hant/System.IO.Compression.ZipFile.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.IO.Compression.ZipFile.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.IO.Compression.ZipFile.nuspec"
      ]
    },
    "System.IO.FileSystem/4.0.0": {
      "sha512": "eo05SPWfG+54UA0wxgRIYOuOslq+2QrJLXZaJDDsfLXG15OLguaItW39NYZTqUb4DeGOkU4R0wpOLOW4ynMUDQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.IO.FileSystem.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.IO.FileSystem.dll",
        "lib/netcore50/System.IO.FileSystem.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/0405bad2bcdd403884f42a0a79534bc1.psmdcp",
        "ref/dotnet/de/System.IO.FileSystem.xml",
        "ref/dotnet/es/System.IO.FileSystem.xml",
        "ref/dotnet/fr/System.IO.FileSystem.xml",
        "ref/dotnet/it/System.IO.FileSystem.xml",
        "ref/dotnet/ja/System.IO.FileSystem.xml",
        "ref/dotnet/ko/System.IO.FileSystem.xml",
        "ref/dotnet/ru/System.IO.FileSystem.xml",
        "ref/dotnet/System.IO.FileSystem.dll",
        "ref/dotnet/System.IO.FileSystem.xml",
        "ref/dotnet/zh-hans/System.IO.FileSystem.xml",
        "ref/dotnet/zh-hant/System.IO.FileSystem.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.IO.FileSystem.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.IO.FileSystem.nuspec"
      ]
    },
    "System.IO.FileSystem.Primitives/4.0.0": {
      "sha512": "7pJUvYi/Yq3A5nagqCCiOw3+aJp3xXc/Cjr8dnJDnER3/6kX3LEencfqmXUcPl9+7OvRNyPMNhqsLAcMK6K/KA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.IO.FileSystem.Primitives.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.IO.FileSystem.Primitives.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/2cf3542156f0426483f92b9e37d8d381.psmdcp",
        "ref/dotnet/de/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/es/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/fr/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/it/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/ja/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/ko/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/ru/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/System.IO.FileSystem.Primitives.dll",
        "ref/dotnet/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/zh-hans/System.IO.FileSystem.Primitives.xml",
        "ref/dotnet/zh-hant/System.IO.FileSystem.Primitives.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.IO.FileSystem.Primitives.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.IO.FileSystem.Primitives.nuspec"
      ]
    },
    "System.IO.IsolatedStorage/4.0.0": {
      "sha512": "d5KimUbZ49Ki6A/uwU+Iodng+nhJvpRs7hr/828cfeXC02LxUiggnRnAu+COtWcKvJ2YbBmAGOcO4GLK4fX1+w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/netcore50/System.IO.IsolatedStorage.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/0d69e649eab84c3cad77d63bb460f7e7.psmdcp",
        "ref/dotnet/de/System.IO.IsolatedStorage.xml",
        "ref/dotnet/es/System.IO.IsolatedStorage.xml",
        "ref/dotnet/fr/System.IO.IsolatedStorage.xml",
        "ref/dotnet/it/System.IO.IsolatedStorage.xml",
        "ref/dotnet/ja/System.IO.IsolatedStorage.xml",
        "ref/dotnet/ko/System.IO.IsolatedStorage.xml",
        "ref/dotnet/ru/System.IO.IsolatedStorage.xml",
        "ref/dotnet/System.IO.IsolatedStorage.dll",
        "ref/dotnet/System.IO.IsolatedStorage.xml",
        "ref/dotnet/zh-hans/System.IO.IsolatedStorage.xml",
        "ref/dotnet/zh-hant/System.IO.IsolatedStorage.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.IO.IsolatedStorage.nuspec"
      ]
    },
    "System.IO.UnmanagedMemoryStream/4.0.0": {
      "sha512": "i2xczgQfwHmolORBNHxV9b5izP8VOBxgSA2gf+H55xBvwqtR+9r9adtzlc7at0MAwiLcsk6V1TZlv2vfRQr8Sw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.IO.UnmanagedMemoryStream.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.IO.UnmanagedMemoryStream.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/cce1d37d7dc24e5fb4170ead20101af0.psmdcp",
        "ref/dotnet/de/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/es/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/fr/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/it/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/ja/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/ko/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/ru/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/System.IO.UnmanagedMemoryStream.dll",
        "ref/dotnet/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/zh-hans/System.IO.UnmanagedMemoryStream.xml",
        "ref/dotnet/zh-hant/System.IO.UnmanagedMemoryStream.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.IO.UnmanagedMemoryStream.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.IO.UnmanagedMemoryStream.nuspec"
      ]
    },
    "System.Linq/4.0.0": {
      "sha512": "r6Hlc+ytE6m/9UBr+nNRRdoJEWjoeQiT3L3lXYFDHoXk3VYsRBCDNXrawcexw7KPLaH0zamQLiAb6avhZ50cGg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Linq.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Linq.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/6fcde56ce4094f6a8fff4b28267da532.psmdcp",
        "ref/dotnet/de/System.Linq.xml",
        "ref/dotnet/es/System.Linq.xml",
        "ref/dotnet/fr/System.Linq.xml",
        "ref/dotnet/it/System.Linq.xml",
        "ref/dotnet/ja/System.Linq.xml",
        "ref/dotnet/ko/System.Linq.xml",
        "ref/dotnet/ru/System.Linq.xml",
        "ref/dotnet/System.Linq.dll",
        "ref/dotnet/System.Linq.xml",
        "ref/dotnet/zh-hans/System.Linq.xml",
        "ref/dotnet/zh-hant/System.Linq.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Linq.dll",
        "ref/netcore50/System.Linq.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "System.Linq.nuspec"
      ]
    },
    "System.Linq.Expressions/4.0.10": {
      "sha512": "qhFkPqRsTfXBaacjQhxwwwUoU7TEtwlBIULj7nG7i4qAkvivil31VvOvDKppCSui5yGw0/325ZeNaMYRvTotXw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Linq.Expressions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Linq.Expressions.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/4e3c061f7c0a427fa5b65bd3d84e9bc3.psmdcp",
        "ref/dotnet/de/System.Linq.Expressions.xml",
        "ref/dotnet/es/System.Linq.Expressions.xml",
        "ref/dotnet/fr/System.Linq.Expressions.xml",
        "ref/dotnet/it/System.Linq.Expressions.xml",
        "ref/dotnet/ja/System.Linq.Expressions.xml",
        "ref/dotnet/ko/System.Linq.Expressions.xml",
        "ref/dotnet/ru/System.Linq.Expressions.xml",
        "ref/dotnet/System.Linq.Expressions.dll",
        "ref/dotnet/System.Linq.Expressions.xml",
        "ref/dotnet/zh-hans/System.Linq.Expressions.xml",
        "ref/dotnet/zh-hant/System.Linq.Expressions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtime.json",
        "runtimes/win8-aot/lib/netcore50/System.Linq.Expressions.dll",
        "System.Linq.Expressions.nuspec"
      ]
    },
    "System.Linq.Parallel/4.0.0": {
      "sha512": "PtH7KKh1BbzVow4XY17pnrn7Io63ApMdwzRE2o2HnzsKQD/0o7X5xe6mxrDUqTm9ZCR3/PNhAlP13VY1HnHsbA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Linq.Parallel.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Linq.Parallel.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/5cc7d35889814f73a239a1b7dcd33451.psmdcp",
        "ref/dotnet/de/System.Linq.Parallel.xml",
        "ref/dotnet/es/System.Linq.Parallel.xml",
        "ref/dotnet/fr/System.Linq.Parallel.xml",
        "ref/dotnet/it/System.Linq.Parallel.xml",
        "ref/dotnet/ja/System.Linq.Parallel.xml",
        "ref/dotnet/ko/System.Linq.Parallel.xml",
        "ref/dotnet/ru/System.Linq.Parallel.xml",
        "ref/dotnet/System.Linq.Parallel.dll",
        "ref/dotnet/System.Linq.Parallel.xml",
        "ref/dotnet/zh-hans/System.Linq.Parallel.xml",
        "ref/dotnet/zh-hant/System.Linq.Parallel.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Linq.Parallel.dll",
        "ref/netcore50/System.Linq.Parallel.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "System.Linq.Parallel.nuspec"
      ]
    },
    "System.Linq.Queryable/4.0.0": {
      "sha512": "DIlvCNn3ucFvwMMzXcag4aFnFJ1fdxkQ5NqwJe9Nh7y8ozzhDm07YakQL/yoF3P1dLzY1T2cTpuwbAmVSdXyBA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Linq.Queryable.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Linq.Queryable.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/24a380caa65148a7883629840bf0c343.psmdcp",
        "ref/dotnet/de/System.Linq.Queryable.xml",
        "ref/dotnet/es/System.Linq.Queryable.xml",
        "ref/dotnet/fr/System.Linq.Queryable.xml",
        "ref/dotnet/it/System.Linq.Queryable.xml",
        "ref/dotnet/ja/System.Linq.Queryable.xml",
        "ref/dotnet/ko/System.Linq.Queryable.xml",
        "ref/dotnet/ru/System.Linq.Queryable.xml",
        "ref/dotnet/System.Linq.Queryable.dll",
        "ref/dotnet/System.Linq.Queryable.xml",
        "ref/dotnet/zh-hans/System.Linq.Queryable.xml",
        "ref/dotnet/zh-hant/System.Linq.Queryable.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Linq.Queryable.dll",
        "ref/netcore50/System.Linq.Queryable.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "System.Linq.Queryable.nuspec"
      ]
    },
    "System.Net.Http/4.0.0": {
      "sha512": "mZuAl7jw/mFY8jUq4ITKECxVBh9a8SJt9BC/+lJbmo7cRKspxE3PsITz+KiaCEsexN5WYPzwBOx0oJH/0HlPyQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Net.Http.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Net.Http.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/62d64206d25643df9c8d01e867c05e27.psmdcp",
        "ref/dotnet/de/System.Net.Http.xml",
        "ref/dotnet/es/System.Net.Http.xml",
        "ref/dotnet/fr/System.Net.Http.xml",
        "ref/dotnet/it/System.Net.Http.xml",
        "ref/dotnet/ja/System.Net.Http.xml",
        "ref/dotnet/ko/System.Net.Http.xml",
        "ref/dotnet/ru/System.Net.Http.xml",
        "ref/dotnet/System.Net.Http.dll",
        "ref/dotnet/System.Net.Http.xml",
        "ref/dotnet/zh-hans/System.Net.Http.xml",
        "ref/dotnet/zh-hant/System.Net.Http.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Net.Http.dll",
        "ref/netcore50/System.Net.Http.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "System.Net.Http.nuspec"
      ]
    },
    "System.Net.Http.Rtc/4.0.0": {
      "sha512": "PlE+oJgXdbxPmZYR6GBywRkyIPovjB1Y0SYHizj2Iflgu80uJQC4szl9gue4rKI2FgXiEbj9JL7wL5K3mp9HAQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/netcore50/System.Net.Http.Rtc.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/5ae6b04142264f2abb319c7dccbfb69f.psmdcp",
        "ref/dotnet/de/System.Net.Http.Rtc.xml",
        "ref/dotnet/es/System.Net.Http.Rtc.xml",
        "ref/dotnet/fr/System.Net.Http.Rtc.xml",
        "ref/dotnet/it/System.Net.Http.Rtc.xml",
        "ref/dotnet/ja/System.Net.Http.Rtc.xml",
        "ref/dotnet/ko/System.Net.Http.Rtc.xml",
        "ref/dotnet/ru/System.Net.Http.Rtc.xml",
        "ref/dotnet/System.Net.Http.Rtc.dll",
        "ref/dotnet/System.Net.Http.Rtc.xml",
        "ref/dotnet/zh-hans/System.Net.Http.Rtc.xml",
        "ref/dotnet/zh-hant/System.Net.Http.Rtc.xml",
        "ref/netcore50/System.Net.Http.Rtc.dll",
        "ref/netcore50/System.Net.Http.Rtc.xml",
        "ref/win8/_._",
        "System.Net.Http.Rtc.nuspec"
      ]
    },
    "System.Net.NetworkInformation/4.0.0": {
      "sha512": "D68KCf5VK1G1GgFUwD901gU6cnMITksOdfdxUCt9ReCZfT1pigaDqjJ7XbiLAM4jm7TfZHB7g5mbOf1mbG3yBA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net45/_._",
        "lib/netcore50/System.Net.NetworkInformation.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/5daeae3f7319444d8efbd8a0c539559c.psmdcp",
        "ref/dotnet/de/System.Net.NetworkInformation.xml",
        "ref/dotnet/es/System.Net.NetworkInformation.xml",
        "ref/dotnet/fr/System.Net.NetworkInformation.xml",
        "ref/dotnet/it/System.Net.NetworkInformation.xml",
        "ref/dotnet/ja/System.Net.NetworkInformation.xml",
        "ref/dotnet/ko/System.Net.NetworkInformation.xml",
        "ref/dotnet/ru/System.Net.NetworkInformation.xml",
        "ref/dotnet/System.Net.NetworkInformation.dll",
        "ref/dotnet/System.Net.NetworkInformation.xml",
        "ref/dotnet/zh-hans/System.Net.NetworkInformation.xml",
        "ref/dotnet/zh-hant/System.Net.NetworkInformation.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net45/_._",
        "ref/netcore50/System.Net.NetworkInformation.dll",
        "ref/netcore50/System.Net.NetworkInformation.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Net.NetworkInformation.nuspec"
      ]
    },
    "System.Net.Primitives/4.0.10": {
      "sha512": "YQqIpmMhnKjIbT7rl6dlf7xM5DxaMR+whduZ9wKb9OhMLjoueAJO3HPPJI+Naf3v034kb+xZqdc3zo44o3HWcg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Net.Primitives.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Net.Primitives.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/3e2f49037d5645bdad757b3fd5b7c103.psmdcp",
        "ref/dotnet/de/System.Net.Primitives.xml",
        "ref/dotnet/es/System.Net.Primitives.xml",
        "ref/dotnet/fr/System.Net.Primitives.xml",
        "ref/dotnet/it/System.Net.Primitives.xml",
        "ref/dotnet/ja/System.Net.Primitives.xml",
        "ref/dotnet/ko/System.Net.Primitives.xml",
        "ref/dotnet/ru/System.Net.Primitives.xml",
        "ref/dotnet/System.Net.Primitives.dll",
        "ref/dotnet/System.Net.Primitives.xml",
        "ref/dotnet/zh-hans/System.Net.Primitives.xml",
        "ref/dotnet/zh-hant/System.Net.Primitives.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Net.Primitives.nuspec"
      ]
    },
    "System.Net.Requests/4.0.10": {
      "sha512": "A6XBR7TztiIQg6hx7VGfbBKmRTAavUERm2E7pmNz/gZeGvwyP0lcKHZxylJtNVKj7DPwr91bD87oLY6zZYntcg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Net.Requests.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/7a4e397882e44db3aa06d6d8c9dd3d66.psmdcp",
        "ref/dotnet/de/System.Net.Requests.xml",
        "ref/dotnet/es/System.Net.Requests.xml",
        "ref/dotnet/fr/System.Net.Requests.xml",
        "ref/dotnet/it/System.Net.Requests.xml",
        "ref/dotnet/ja/System.Net.Requests.xml",
        "ref/dotnet/ko/System.Net.Requests.xml",
        "ref/dotnet/ru/System.Net.Requests.xml",
        "ref/dotnet/System.Net.Requests.dll",
        "ref/dotnet/System.Net.Requests.xml",
        "ref/dotnet/zh-hans/System.Net.Requests.xml",
        "ref/dotnet/zh-hant/System.Net.Requests.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Net.Requests.nuspec"
      ]
    },
    "System.Net.Sockets/4.0.0": {
      "sha512": "7bBNLdO6Xw0BGyFVSxjloGXMvsc3qQmW+70bYMLwHEAVivMK8zx+E7XO8CeJnAko2mFj6R402E798EGYUksFcQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Net.Sockets.dll",
        "lib/netcore50/System.Net.Sockets.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/cca33bc0996f49c68976fa5bab1500ff.psmdcp",
        "ref/dotnet/de/System.Net.Sockets.xml",
        "ref/dotnet/es/System.Net.Sockets.xml",
        "ref/dotnet/fr/System.Net.Sockets.xml",
        "ref/dotnet/it/System.Net.Sockets.xml",
        "ref/dotnet/ja/System.Net.Sockets.xml",
        "ref/dotnet/ko/System.Net.Sockets.xml",
        "ref/dotnet/ru/System.Net.Sockets.xml",
        "ref/dotnet/System.Net.Sockets.dll",
        "ref/dotnet/System.Net.Sockets.xml",
        "ref/dotnet/zh-hans/System.Net.Sockets.xml",
        "ref/dotnet/zh-hant/System.Net.Sockets.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Net.Sockets.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Net.Sockets.nuspec"
      ]
    },
    "System.Net.WebHeaderCollection/4.0.0": {
      "sha512": "IsIZAsHm/yK7R/XASnEc4EMffFLIMgYchG3/zJv6B4LwMnXZwrVlSPpNbPgEVb0lSXyztsn7A6sIPAACQQ2vTQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Net.WebHeaderCollection.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/7ab0d7bde19b47548622bfa222a4eccb.psmdcp",
        "ref/dotnet/de/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/es/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/fr/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/it/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/ja/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/ko/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/ru/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/System.Net.WebHeaderCollection.dll",
        "ref/dotnet/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/zh-hans/System.Net.WebHeaderCollection.xml",
        "ref/dotnet/zh-hant/System.Net.WebHeaderCollection.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Net.WebHeaderCollection.nuspec"
      ]
    },
    "System.Numerics.Vectors/4.1.0": {
      "sha512": "jpubR06GWPoZA0oU5xLM7kHeV59/CKPBXZk4Jfhi0T3DafxbrdueHZ8kXlb+Fb5nd3DAyyMh2/eqEzLX0xv6Qg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Numerics.Vectors.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Numerics.Vectors.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/e501a8a91f4a4138bd1d134abcc769b0.psmdcp",
        "ref/dotnet/System.Numerics.Vectors.dll",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Numerics.Vectors.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Numerics.Vectors.nuspec"
      ]
    },
    "System.Numerics.Vectors.WindowsRuntime/4.0.0": {
      "sha512": "Ly7GvoPFZq6GyfZpfS0E7uCk1cinl5BANAngXVuau3lD2QqZJMHitzlPv6n1FlIn6krfv99X2IPkIaVzUwDHXA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Numerics.Vectors.WindowsRuntime.dll",
        "package/services/metadata/core-properties/6db0e2464a274e8eb688cd193eb37876.psmdcp",
        "System.Numerics.Vectors.WindowsRuntime.nuspec"
      ]
    },
    "System.ObjectModel/4.0.10": {
      "sha512": "Djn1wb0vP662zxbe+c3mOhvC4vkQGicsFs1Wi0/GJJpp3Eqp+oxbJ+p2Sx3O0efYueggAI5SW+BqEoczjfr1cA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.ObjectModel.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/36c2aaa0c5d24949a7707921f36ee13f.psmdcp",
        "ref/dotnet/de/System.ObjectModel.xml",
        "ref/dotnet/es/System.ObjectModel.xml",
        "ref/dotnet/fr/System.ObjectModel.xml",
        "ref/dotnet/it/System.ObjectModel.xml",
        "ref/dotnet/ja/System.ObjectModel.xml",
        "ref/dotnet/ko/System.ObjectModel.xml",
        "ref/dotnet/ru/System.ObjectModel.xml",
        "ref/dotnet/System.ObjectModel.dll",
        "ref/dotnet/System.ObjectModel.xml",
        "ref/dotnet/zh-hans/System.ObjectModel.xml",
        "ref/dotnet/zh-hant/System.ObjectModel.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.ObjectModel.nuspec"
      ]
    },
    "System.Private.DataContractSerialization/4.0.0": {
      "sha512": "uQvzoXHXHn/9YqUmPtgD8ZPJIlBuuL3QHegbuik97W/umoI28fOnGLnvjRHhju1VMWvFLRQoh7uZkBaoZ+KpVQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Private.DataContractSerialization.dll",
        "lib/netcore50/System.Private.DataContractSerialization.dll",
        "package/services/metadata/core-properties/124ac81dfe1e4d08942831c90a93a6ba.psmdcp",
        "ref/dnxcore50/_._",
        "ref/netcore50/_._",
        "runtime.json",
        "runtimes/win8-aot/lib/netcore50/System.Private.DataContractSerialization.dll",
        "System.Private.DataContractSerialization.nuspec"
      ]
    },
    "System.Private.Networking/4.0.0": {
      "sha512": "RUEqdBdJjISC65dO8l4LdN7vTdlXH+attUpKnauDUHVtLbIKdlDB9LKoLzCQsTQRP7vzUJHWYXznHJBkjAA7yA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Private.Networking.dll",
        "lib/netcore50/System.Private.Networking.dll",
        "package/services/metadata/core-properties/b57bed5f606b4402bbdf153fcf3df3ae.psmdcp",
        "ref/dnxcore50/_._",
        "ref/netcore50/_._",
        "System.Private.Networking.nuspec"
      ]
    },
    "System.Private.ServiceModel/4.0.0": {
      "sha512": "cm2wEa1f9kuUq/2k8uIwepgZJi5HdxXSnjGQIeXmAb7RaWfZPEC/iamv9GJ67b5LPnCZHR0KvtFqh82e8AAYSw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Private.ServiceModel.dll",
        "lib/netcore50/System.Private.ServiceModel.dll",
        "package/services/metadata/core-properties/5668af7c10764fafb51182a583dfb872.psmdcp",
        "ref/dnxcore50/_._",
        "ref/netcore50/_._",
        "System.Private.ServiceModel.nuspec"
      ]
    },
    "System.Private.Uri/4.0.0": {
      "sha512": "CtuxaCKcRIvPcsqquVl3mPp79EDZPMr2UogfiFCxCs+t2z1VjbpQsKNs1GHZ8VQetqbk1mr0V1yAfMe6y8CHDA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Private.Uri.dll",
        "lib/netcore50/System.Private.Uri.dll",
        "package/services/metadata/core-properties/86377e21a22d44bbba860094428d894c.psmdcp",
        "ref/dnxcore50/_._",
        "ref/netcore50/_._",
        "runtimes/win8-aot/lib/netcore50/System.Private.Uri.dll",
        "System.Private.Uri.nuspec"
      ]
    },
    "System.Reflection/4.0.10": {
      "sha512": "WZ+4lEE4gqGx6mrqLhSiW4oi6QLPWwdNjzhhTONmhELOrW8Cw9phlO9tltgvRUuQUqYtBiliFwhO5S5fCJElVw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Reflection.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/84d992ce164945bfa10835e447244fb1.psmdcp",
        "ref/dotnet/de/System.Reflection.xml",
        "ref/dotnet/es/System.Reflection.xml",
        "ref/dotnet/fr/System.Reflection.xml",
        "ref/dotnet/it/System.Reflection.xml",
        "ref/dotnet/ja/System.Reflection.xml",
        "ref/dotnet/ko/System.Reflection.xml",
        "ref/dotnet/ru/System.Reflection.xml",
        "ref/dotnet/System.Reflection.dll",
        "ref/dotnet/System.Reflection.xml",
        "ref/dotnet/zh-hans/System.Reflection.xml",
        "ref/dotnet/zh-hant/System.Reflection.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Reflection.dll",
        "System.Reflection.nuspec"
      ]
    },
    "System.Reflection.Context/4.0.0": {
      "sha512": "Gz4sUHHFd/52RjHccSHbOXdujJEWKfL3gIaA+ekxvQaQfJGbI2tPzA0Uv3WTCTDRGHgtoNq5WS9E007Dt4P/VQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Context.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/263ca61f1b594d9395e210a55a8fe7a7.psmdcp",
        "ref/dotnet/de/System.Reflection.Context.xml",
        "ref/dotnet/es/System.Reflection.Context.xml",
        "ref/dotnet/fr/System.Reflection.Context.xml",
        "ref/dotnet/it/System.Reflection.Context.xml",
        "ref/dotnet/ja/System.Reflection.Context.xml",
        "ref/dotnet/ko/System.Reflection.Context.xml",
        "ref/dotnet/ru/System.Reflection.Context.xml",
        "ref/dotnet/System.Reflection.Context.dll",
        "ref/dotnet/System.Reflection.Context.xml",
        "ref/dotnet/zh-hans/System.Reflection.Context.xml",
        "ref/dotnet/zh-hant/System.Reflection.Context.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Reflection.Context.dll",
        "ref/netcore50/System.Reflection.Context.xml",
        "ref/win8/_._",
        "System.Reflection.Context.nuspec"
      ]
    },
    "System.Reflection.DispatchProxy/4.0.0": {
      "sha512": "Kd/4o6DqBfJA4058X8oGEu1KlT8Ej0A+WGeoQgZU2h+3f2vC8NRbHxeOSZvxj9/MPZ1RYmZMGL1ApO9xG/4IVA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.DispatchProxy.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Reflection.DispatchProxy.dll",
        "lib/netcore50/System.Reflection.DispatchProxy.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/1e015137cc52490b9dcde73fb35dee23.psmdcp",
        "ref/dotnet/de/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/es/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/fr/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/it/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/ja/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/ko/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/ru/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/System.Reflection.DispatchProxy.dll",
        "ref/dotnet/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/zh-hans/System.Reflection.DispatchProxy.xml",
        "ref/dotnet/zh-hant/System.Reflection.DispatchProxy.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtime.json",
        "runtimes/win8-aot/lib/netcore50/System.Reflection.DispatchProxy.dll",
        "System.Reflection.DispatchProxy.nuspec"
      ]
    },
    "System.Reflection.Emit/4.0.0": {
      "sha512": "CqnQz5LbNbiSxN10cv3Ehnw3j1UZOBCxnE0OO0q/keGQ5ENjyFM6rIG4gm/i0dX6EjdpYkAgKcI/mhZZCaBq4A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.Emit.dll",
        "lib/MonoAndroid10/_._",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Emit.dll",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/f6dc998f8a6b43d7b08f33375407a384.psmdcp",
        "ref/dotnet/de/System.Reflection.Emit.xml",
        "ref/dotnet/es/System.Reflection.Emit.xml",
        "ref/dotnet/fr/System.Reflection.Emit.xml",
        "ref/dotnet/it/System.Reflection.Emit.xml",
        "ref/dotnet/ja/System.Reflection.Emit.xml",
        "ref/dotnet/ko/System.Reflection.Emit.xml",
        "ref/dotnet/ru/System.Reflection.Emit.xml",
        "ref/dotnet/System.Reflection.Emit.dll",
        "ref/dotnet/System.Reflection.Emit.xml",
        "ref/dotnet/zh-hans/System.Reflection.Emit.xml",
        "ref/dotnet/zh-hant/System.Reflection.Emit.xml",
        "ref/MonoAndroid10/_._",
        "ref/net45/_._",
        "ref/xamarinmac20/_._",
        "System.Reflection.Emit.nuspec"
      ]
    },
    "System.Reflection.Emit.ILGeneration/4.0.0": {
      "sha512": "02okuusJ0GZiHZSD2IOLIN41GIn6qOr7i5+86C98BPuhlwWqVABwebiGNvhDiXP1f9a6CxEigC7foQD42klcDg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.Emit.ILGeneration.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Emit.ILGeneration.dll",
        "lib/wp80/_._",
        "package/services/metadata/core-properties/d044dd882ed2456486ddb05f1dd0420f.psmdcp",
        "ref/dotnet/de/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/es/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/fr/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/it/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/ja/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/ko/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/ru/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/System.Reflection.Emit.ILGeneration.dll",
        "ref/dotnet/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/zh-hans/System.Reflection.Emit.ILGeneration.xml",
        "ref/dotnet/zh-hant/System.Reflection.Emit.ILGeneration.xml",
        "ref/net45/_._",
        "ref/wp80/_._",
        "System.Reflection.Emit.ILGeneration.nuspec"
      ]
    },
    "System.Reflection.Emit.Lightweight/4.0.0": {
      "sha512": "DJZhHiOdkN08xJgsJfDjkuOreLLmMcU8qkEEqEHqyhkPUZMMQs0lE8R+6+68BAFWgcdzxtNu0YmIOtEug8j00w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.Emit.Lightweight.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Emit.Lightweight.dll",
        "lib/wp80/_._",
        "package/services/metadata/core-properties/52abced289cd46eebf8599b9b4c1c67b.psmdcp",
        "ref/dotnet/de/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/es/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/fr/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/it/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/ja/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/ko/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/ru/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/System.Reflection.Emit.Lightweight.dll",
        "ref/dotnet/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/zh-hans/System.Reflection.Emit.Lightweight.xml",
        "ref/dotnet/zh-hant/System.Reflection.Emit.Lightweight.xml",
        "ref/net45/_._",
        "ref/wp80/_._",
        "System.Reflection.Emit.Lightweight.nuspec"
      ]
    },
    "System.Reflection.Extensions/4.0.0": {
      "sha512": "dbYaZWCyFAu1TGYUqR2n+Q+1casSHPR2vVW0WVNkXpZbrd2BXcZ7cpvpu9C98CTHtNmyfMWCLpCclDqly23t6A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.Extensions.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Extensions.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/0bcc335e1ef540948aef9032aca08bb2.psmdcp",
        "ref/dotnet/de/System.Reflection.Extensions.xml",
        "ref/dotnet/es/System.Reflection.Extensions.xml",
        "ref/dotnet/fr/System.Reflection.Extensions.xml",
        "ref/dotnet/it/System.Reflection.Extensions.xml",
        "ref/dotnet/ja/System.Reflection.Extensions.xml",
        "ref/dotnet/ko/System.Reflection.Extensions.xml",
        "ref/dotnet/ru/System.Reflection.Extensions.xml",
        "ref/dotnet/System.Reflection.Extensions.dll",
        "ref/dotnet/System.Reflection.Extensions.xml",
        "ref/dotnet/zh-hans/System.Reflection.Extensions.xml",
        "ref/dotnet/zh-hant/System.Reflection.Extensions.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Reflection.Extensions.dll",
        "ref/netcore50/System.Reflection.Extensions.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Reflection.Extensions.dll",
        "System.Reflection.Extensions.nuspec"
      ]
    },
    "System.Reflection.Metadata/1.0.22": {
      "sha512": "ltoL/teiEdy5W9fyYdtFr2xJ/4nHyksXLK9dkPWx3ubnj7BVfsSWxvWTg9EaJUXjhWvS/AeTtugZA1/IDQyaPQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Reflection.Metadata.dll",
        "lib/dotnet/System.Reflection.Metadata.xml",
        "lib/portable-net45+win8/System.Reflection.Metadata.dll",
        "lib/portable-net45+win8/System.Reflection.Metadata.xml",
        "package/services/metadata/core-properties/2ad78f291fda48d1847edf84e50139e6.psmdcp",
        "System.Reflection.Metadata.nuspec"
      ]
    },
    "System.Reflection.Primitives/4.0.0": {
      "sha512": "n9S0XpKv2ruc17FSnaiX6nV47VfHTZ1wLjKZlAirUZCvDQCH71mVp+Ohabn0xXLh5pK2PKp45HCxkqu5Fxn/lA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.Primitives.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Reflection.Primitives.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/7070509f3bfd418d859635361251dab0.psmdcp",
        "ref/dotnet/de/System.Reflection.Primitives.xml",
        "ref/dotnet/es/System.Reflection.Primitives.xml",
        "ref/dotnet/fr/System.Reflection.Primitives.xml",
        "ref/dotnet/it/System.Reflection.Primitives.xml",
        "ref/dotnet/ja/System.Reflection.Primitives.xml",
        "ref/dotnet/ko/System.Reflection.Primitives.xml",
        "ref/dotnet/ru/System.Reflection.Primitives.xml",
        "ref/dotnet/System.Reflection.Primitives.dll",
        "ref/dotnet/System.Reflection.Primitives.xml",
        "ref/dotnet/zh-hans/System.Reflection.Primitives.xml",
        "ref/dotnet/zh-hant/System.Reflection.Primitives.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Reflection.Primitives.dll",
        "ref/netcore50/System.Reflection.Primitives.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Reflection.Primitives.dll",
        "System.Reflection.Primitives.nuspec"
      ]
    },
    "System.Reflection.TypeExtensions/4.0.0": {
      "sha512": "YRM/msNAM86hdxPyXcuZSzmTO0RQFh7YMEPBLTY8cqXvFPYIx2x99bOyPkuU81wRYQem1c1HTkImQ2DjbOBfew==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Reflection.TypeExtensions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Reflection.TypeExtensions.dll",
        "lib/netcore50/System.Reflection.TypeExtensions.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/a37798ee61124eb7b6c56400aee24da1.psmdcp",
        "ref/dotnet/de/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/es/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/fr/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/it/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/ja/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/ko/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/ru/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/System.Reflection.TypeExtensions.dll",
        "ref/dotnet/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/zh-hans/System.Reflection.TypeExtensions.xml",
        "ref/dotnet/zh-hant/System.Reflection.TypeExtensions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Reflection.TypeExtensions.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Reflection.TypeExtensions.dll",
        "System.Reflection.TypeExtensions.nuspec"
      ]
    },
    "System.Resources.ResourceManager/4.0.0": {
      "sha512": "qmqeZ4BJgjfU+G2JbrZt4Dk1LsMxO4t+f/9HarNY6w8pBgweO6jT+cknUH7c3qIrGvyUqraBhU45Eo6UtA0fAw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Resources.ResourceManager.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Resources.ResourceManager.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/657a73ee3f09479c9fedb9538ade8eac.psmdcp",
        "ref/dotnet/de/System.Resources.ResourceManager.xml",
        "ref/dotnet/es/System.Resources.ResourceManager.xml",
        "ref/dotnet/fr/System.Resources.ResourceManager.xml",
        "ref/dotnet/it/System.Resources.ResourceManager.xml",
        "ref/dotnet/ja/System.Resources.ResourceManager.xml",
        "ref/dotnet/ko/System.Resources.ResourceManager.xml",
        "ref/dotnet/ru/System.Resources.ResourceManager.xml",
        "ref/dotnet/System.Resources.ResourceManager.dll",
        "ref/dotnet/System.Resources.ResourceManager.xml",
        "ref/dotnet/zh-hans/System.Resources.ResourceManager.xml",
        "ref/dotnet/zh-hant/System.Resources.ResourceManager.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Resources.ResourceManager.dll",
        "ref/netcore50/System.Resources.ResourceManager.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Resources.ResourceManager.dll",
        "System.Resources.ResourceManager.nuspec"
      ]
    },
    "System.Runtime/4.0.20": {
      "sha512": "X7N/9Bz7jVPorqdVFO86ns1sX6MlQM+WTxELtx+Z4VG45x9+LKmWH0GRqjgKprUnVuwmfB9EJ9DQng14Z7/zwg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Runtime.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/d1ded52f75da4446b1c962f9292aa3ef.psmdcp",
        "ref/dotnet/de/System.Runtime.xml",
        "ref/dotnet/es/System.Runtime.xml",
        "ref/dotnet/fr/System.Runtime.xml",
        "ref/dotnet/it/System.Runtime.xml",
        "ref/dotnet/ja/System.Runtime.xml",
        "ref/dotnet/ko/System.Runtime.xml",
        "ref/dotnet/ru/System.Runtime.xml",
        "ref/dotnet/System.Runtime.dll",
        "ref/dotnet/System.Runtime.xml",
        "ref/dotnet/zh-hans/System.Runtime.xml",
        "ref/dotnet/zh-hant/System.Runtime.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.dll",
        "System.Runtime.nuspec"
      ]
    },
    "System.Runtime.Extensions/4.0.10": {
      "sha512": "5dsEwf3Iml7d5OZeT20iyOjT+r+okWpN7xI2v+R4cgd3WSj4DeRPTvPFjDpacbVW4skCAZ8B9hxXJYgkCFKJ1A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.Extensions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Runtime.Extensions.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/c7fee76a13d04c7ea49fb1a24c184f37.psmdcp",
        "ref/dotnet/de/System.Runtime.Extensions.xml",
        "ref/dotnet/es/System.Runtime.Extensions.xml",
        "ref/dotnet/fr/System.Runtime.Extensions.xml",
        "ref/dotnet/it/System.Runtime.Extensions.xml",
        "ref/dotnet/ja/System.Runtime.Extensions.xml",
        "ref/dotnet/ko/System.Runtime.Extensions.xml",
        "ref/dotnet/ru/System.Runtime.Extensions.xml",
        "ref/dotnet/System.Runtime.Extensions.dll",
        "ref/dotnet/System.Runtime.Extensions.xml",
        "ref/dotnet/zh-hans/System.Runtime.Extensions.xml",
        "ref/dotnet/zh-hant/System.Runtime.Extensions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.Extensions.dll",
        "System.Runtime.Extensions.nuspec"
      ]
    },
    "System.Runtime.Handles/4.0.0": {
      "sha512": "638VhpRq63tVcQ6HDb3um3R/J2BtR1Sa96toHo6PcJGPXEPEsleCuqhBgX2gFCz0y0qkutANwW6VPPY5wQu1XQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.Handles.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Runtime.Handles.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/da57aa32ff2441d1acfe85bee4f101ab.psmdcp",
        "ref/dotnet/de/System.Runtime.Handles.xml",
        "ref/dotnet/es/System.Runtime.Handles.xml",
        "ref/dotnet/fr/System.Runtime.Handles.xml",
        "ref/dotnet/it/System.Runtime.Handles.xml",
        "ref/dotnet/ja/System.Runtime.Handles.xml",
        "ref/dotnet/ko/System.Runtime.Handles.xml",
        "ref/dotnet/ru/System.Runtime.Handles.xml",
        "ref/dotnet/System.Runtime.Handles.dll",
        "ref/dotnet/System.Runtime.Handles.xml",
        "ref/dotnet/zh-hans/System.Runtime.Handles.xml",
        "ref/dotnet/zh-hant/System.Runtime.Handles.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.Handles.dll",
        "System.Runtime.Handles.nuspec"
      ]
    },
    "System.Runtime.InteropServices/4.0.20": {
      "sha512": "ZgDyBYfEnjWoz/viS6VOswA6XOkDSH2DzgbpczbW50RywhnCgTl+w3JEvtAiOGyIh8cyx1NJq80jsNBSUr8Pig==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.InteropServices.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Runtime.InteropServices.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/78e7f61876374acba2a95834f272d262.psmdcp",
        "ref/dotnet/de/System.Runtime.InteropServices.xml",
        "ref/dotnet/es/System.Runtime.InteropServices.xml",
        "ref/dotnet/fr/System.Runtime.InteropServices.xml",
        "ref/dotnet/it/System.Runtime.InteropServices.xml",
        "ref/dotnet/ja/System.Runtime.InteropServices.xml",
        "ref/dotnet/ko/System.Runtime.InteropServices.xml",
        "ref/dotnet/ru/System.Runtime.InteropServices.xml",
        "ref/dotnet/System.Runtime.InteropServices.dll",
        "ref/dotnet/System.Runtime.InteropServices.xml",
        "ref/dotnet/zh-hans/System.Runtime.InteropServices.xml",
        "ref/dotnet/zh-hant/System.Runtime.InteropServices.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.dll",
        "System.Runtime.InteropServices.nuspec"
      ]
    },
    "System.Runtime.InteropServices.WindowsRuntime/4.0.0": {
      "sha512": "K5MGSvw/sGPKQYdOVqSpsVbHBE8HccHIDEhUNjM1lui65KGF/slNZfijGU87ggQiVXTI802ebKiOYBkwiLotow==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/net45/_._",
        "lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/3c944c6b4d6044d28ee80e49a09300c9.psmdcp",
        "ref/dotnet/de/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/es/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/fr/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/it/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/ja/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/ko/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/ru/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/System.Runtime.InteropServices.WindowsRuntime.dll",
        "ref/dotnet/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/zh-hans/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/dotnet/zh-hant/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll",
        "ref/netcore50/System.Runtime.InteropServices.WindowsRuntime.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.InteropServices.WindowsRuntime.dll",
        "System.Runtime.InteropServices.WindowsRuntime.nuspec"
      ]
    },
    "System.Runtime.Numerics/4.0.0": {
      "sha512": "aAYGEOE01nabQLufQ4YO8WuSyZzOqGcksi8m1BRW8ppkmssR7en8TqiXcBkB2gTkCnKG/Ai2NQY8CgdmgZw/fw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Runtime.Numerics.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Runtime.Numerics.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/2e43dbd3dfbf4af5bb74bedaf3a67bd5.psmdcp",
        "ref/dotnet/de/System.Runtime.Numerics.xml",
        "ref/dotnet/es/System.Runtime.Numerics.xml",
        "ref/dotnet/fr/System.Runtime.Numerics.xml",
        "ref/dotnet/it/System.Runtime.Numerics.xml",
        "ref/dotnet/ja/System.Runtime.Numerics.xml",
        "ref/dotnet/ko/System.Runtime.Numerics.xml",
        "ref/dotnet/ru/System.Runtime.Numerics.xml",
        "ref/dotnet/System.Runtime.Numerics.dll",
        "ref/dotnet/System.Runtime.Numerics.xml",
        "ref/dotnet/zh-hans/System.Runtime.Numerics.xml",
        "ref/dotnet/zh-hant/System.Runtime.Numerics.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Runtime.Numerics.dll",
        "ref/netcore50/System.Runtime.Numerics.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "System.Runtime.Numerics.nuspec"
      ]
    },
    "System.Runtime.Serialization.Json/4.0.0": {
      "sha512": "emhWMQP3sdtkAhD0TOeP3FfjS57sfQMQ2sqA6f2Yj5Gd9jkHV4KsQ2TsoJjghca6d8fur7+REQ6ILBXVdGf/0g==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.Serialization.Json.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Runtime.Serialization.Json.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/2c520ff333ad4bde986eb7a015ba6343.psmdcp",
        "ref/dotnet/de/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/es/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/fr/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/it/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/ja/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/ko/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/ru/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/System.Runtime.Serialization.Json.dll",
        "ref/dotnet/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/zh-hans/System.Runtime.Serialization.Json.xml",
        "ref/dotnet/zh-hant/System.Runtime.Serialization.Json.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Runtime.Serialization.Json.dll",
        "ref/netcore50/System.Runtime.Serialization.Json.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Json.dll",
        "System.Runtime.Serialization.Json.nuspec"
      ]
    },
    "System.Runtime.Serialization.Primitives/4.0.10": {
      "sha512": "NPc8DZIomf5tGjYtz/KTHI01IPcVlypfhCux32AbLPDjTotdvL8TpKRwMyQJ6Kh08yprRVH7uBD1PdJiuoFzag==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Runtime.Serialization.Primitives.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/92e70054da8743d68462736e85fe5580.psmdcp",
        "ref/dotnet/de/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/es/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/fr/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/it/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/ja/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/ko/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/ru/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/System.Runtime.Serialization.Primitives.dll",
        "ref/dotnet/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/zh-hans/System.Runtime.Serialization.Primitives.xml",
        "ref/dotnet/zh-hant/System.Runtime.Serialization.Primitives.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Runtime.Serialization.Primitives.nuspec"
      ]
    },
    "System.Runtime.Serialization.Xml/4.0.10": {
      "sha512": "xsy7XbH8RTpKoDPNcibSGCOpujsmwUmOWAby3PssqkZFpLBXUbDO2s6JKITRjxejET2g0PK8t+mdIvu3xmUuKA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Runtime.Serialization.Xml.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Runtime.Serialization.Xml.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/7d99189e9ae248c9a98d9fc3ccdc5130.psmdcp",
        "ref/dotnet/de/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/es/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/fr/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/it/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/ja/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/ko/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/ru/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/System.Runtime.Serialization.Xml.dll",
        "ref/dotnet/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/zh-hans/System.Runtime.Serialization.Xml.xml",
        "ref/dotnet/zh-hant/System.Runtime.Serialization.Xml.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.Serialization.Xml.dll",
        "System.Runtime.Serialization.Xml.nuspec"
      ]
    },
    "System.Runtime.WindowsRuntime/4.0.10": {
      "sha512": "9w6ypdnEw8RrLRlxTbLAYrap4eL1xIQeNoOaumQVOQ8TTD/5g9FGrBtY3KLiGxAPieN9AwAAEIDkugU85Cwuvg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/netcore50/System.Runtime.WindowsRuntime.dll",
        "lib/win81/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/a81cabb2b7e843ce801ecf91886941d4.psmdcp",
        "ref/dotnet/de/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/es/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/fr/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/it/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/ja/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/ko/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/ru/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/System.Runtime.WindowsRuntime.dll",
        "ref/dotnet/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/zh-hans/System.Runtime.WindowsRuntime.xml",
        "ref/dotnet/zh-hant/System.Runtime.WindowsRuntime.xml",
        "ref/netcore50/System.Runtime.WindowsRuntime.dll",
        "ref/netcore50/System.Runtime.WindowsRuntime.xml",
        "ref/win81/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Runtime.WindowsRuntime.dll",
        "System.Runtime.WindowsRuntime.nuspec"
      ]
    },
    "System.Runtime.WindowsRuntime.UI.Xaml/4.0.0": {
      "sha512": "2GY3fkXBMQOyyO9ovaH46CN6MD2ck/Gvk4VNAgVDvtmfO3HXYFNd+bB05WhVcJrHKbfKZNwfwZKpYZ+OsVFsLw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/0f3b84a81b7a4a97aa765ed058bf6c20.psmdcp",
        "ref/dotnet/de/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/es/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/fr/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/it/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/ja/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/ko/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/ru/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/System.Runtime.WindowsRuntime.UI.Xaml.dll",
        "ref/dotnet/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/zh-hans/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/dotnet/zh-hant/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.dll",
        "ref/netcore50/System.Runtime.WindowsRuntime.UI.Xaml.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "System.Runtime.WindowsRuntime.UI.Xaml.nuspec"
      ]
    },
    "System.Security.Claims/4.0.0": {
      "sha512": "94NFR/7JN3YdyTH7hl2iSvYmdA8aqShriTHectcK+EbizT71YczMaG6LuqJBQP/HWo66AQyikYYM9aw+4EzGXg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Security.Claims.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Security.Claims.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/b682071d85754e6793ca9777ffabaf8a.psmdcp",
        "ref/dotnet/de/System.Security.Claims.xml",
        "ref/dotnet/es/System.Security.Claims.xml",
        "ref/dotnet/fr/System.Security.Claims.xml",
        "ref/dotnet/it/System.Security.Claims.xml",
        "ref/dotnet/ja/System.Security.Claims.xml",
        "ref/dotnet/ko/System.Security.Claims.xml",
        "ref/dotnet/ru/System.Security.Claims.xml",
        "ref/dotnet/System.Security.Claims.dll",
        "ref/dotnet/System.Security.Claims.xml",
        "ref/dotnet/zh-hans/System.Security.Claims.xml",
        "ref/dotnet/zh-hant/System.Security.Claims.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Security.Claims.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Security.Claims.nuspec"
      ]
    },
    "System.Security.Principal/4.0.0": {
      "sha512": "FOhq3jUOONi6fp5j3nPYJMrKtSJlqAURpjiO3FaDIV4DJNEYymWW5uh1pfxySEB8dtAW+I66IypzNge/w9OzZQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Security.Principal.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Security.Principal.dll",
        "lib/win8/_._",
        "lib/wp80/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/5d44fbabc99d4204b6a2f76329d0a184.psmdcp",
        "ref/dotnet/de/System.Security.Principal.xml",
        "ref/dotnet/es/System.Security.Principal.xml",
        "ref/dotnet/fr/System.Security.Principal.xml",
        "ref/dotnet/it/System.Security.Principal.xml",
        "ref/dotnet/ja/System.Security.Principal.xml",
        "ref/dotnet/ko/System.Security.Principal.xml",
        "ref/dotnet/ru/System.Security.Principal.xml",
        "ref/dotnet/System.Security.Principal.dll",
        "ref/dotnet/System.Security.Principal.xml",
        "ref/dotnet/zh-hans/System.Security.Principal.xml",
        "ref/dotnet/zh-hant/System.Security.Principal.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Security.Principal.dll",
        "ref/netcore50/System.Security.Principal.xml",
        "ref/win8/_._",
        "ref/wp80/_._",
        "ref/wpa81/_._",
        "System.Security.Principal.nuspec"
      ]
    },
    "System.ServiceModel.Duplex/4.0.0": {
      "sha512": "JFeDn+IsiwAVJkNNnM7MLefJOnzYhovaHnjk3lzEnUWkYZJeAKrcgLdK6GE2GNjb5mEV8Pad/E0JcA8eCr3eWQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.ServiceModel.Duplex.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ServiceModel.Duplex.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/8a542ab34ffb4a13958ce3d7279d9dae.psmdcp",
        "ref/dotnet/de/System.ServiceModel.Duplex.xml",
        "ref/dotnet/es/System.ServiceModel.Duplex.xml",
        "ref/dotnet/fr/System.ServiceModel.Duplex.xml",
        "ref/dotnet/it/System.ServiceModel.Duplex.xml",
        "ref/dotnet/ja/System.ServiceModel.Duplex.xml",
        "ref/dotnet/ko/System.ServiceModel.Duplex.xml",
        "ref/dotnet/ru/System.ServiceModel.Duplex.xml",
        "ref/dotnet/System.ServiceModel.Duplex.dll",
        "ref/dotnet/System.ServiceModel.Duplex.xml",
        "ref/dotnet/zh-hans/System.ServiceModel.Duplex.xml",
        "ref/dotnet/zh-hant/System.ServiceModel.Duplex.xml",
        "ref/net45/_._",
        "ref/netcore50/System.ServiceModel.Duplex.dll",
        "ref/netcore50/System.ServiceModel.Duplex.xml",
        "ref/win8/_._",
        "System.ServiceModel.Duplex.nuspec"
      ]
    },
    "System.ServiceModel.Http/4.0.10": {
      "sha512": "Vyl7lmvMlXJamtnDugoXuAgAQGSqtA7omK3zDBYByhbYeBC2hRBchgyXox7e5vEO+29TeB1IpoLWQGb7tO9h6A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.ServiceModel.Http.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.ServiceModel.Http.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/da6bab8a73fb4ac9af198a5f70d8aa64.psmdcp",
        "ref/dotnet/de/System.ServiceModel.Http.xml",
        "ref/dotnet/es/System.ServiceModel.Http.xml",
        "ref/dotnet/fr/System.ServiceModel.Http.xml",
        "ref/dotnet/it/System.ServiceModel.Http.xml",
        "ref/dotnet/ja/System.ServiceModel.Http.xml",
        "ref/dotnet/ko/System.ServiceModel.Http.xml",
        "ref/dotnet/ru/System.ServiceModel.Http.xml",
        "ref/dotnet/System.ServiceModel.Http.dll",
        "ref/dotnet/System.ServiceModel.Http.xml",
        "ref/dotnet/zh-hans/System.ServiceModel.Http.xml",
        "ref/dotnet/zh-hant/System.ServiceModel.Http.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.ServiceModel.Http.nuspec"
      ]
    },
    "System.ServiceModel.NetTcp/4.0.0": {
      "sha512": "lV2Cdcso9jOS0KBtgHZHzTLe/Lx/ERdPcvF4dlepUie6/+BOMYTOgg2C7OdpIjp3fwUNXq8nhU+IilmEyjuf/A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.ServiceModel.NetTcp.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ServiceModel.NetTcp.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/024bb3a15d5444e2b8b485ce4cf44640.psmdcp",
        "ref/dotnet/de/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/es/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/fr/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/it/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/ja/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/ko/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/ru/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/System.ServiceModel.NetTcp.dll",
        "ref/dotnet/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/zh-hans/System.ServiceModel.NetTcp.xml",
        "ref/dotnet/zh-hant/System.ServiceModel.NetTcp.xml",
        "ref/net45/_._",
        "ref/netcore50/System.ServiceModel.NetTcp.dll",
        "ref/netcore50/System.ServiceModel.NetTcp.xml",
        "ref/win8/_._",
        "System.ServiceModel.NetTcp.nuspec"
      ]
    },
    "System.ServiceModel.Primitives/4.0.0": {
      "sha512": "uF5VYQWR07LgiZkzUr8qjwvqOaIAfwU566MneD4WuC14d8FLJNsAgCJUYhBGB7COjH7HTqnP9ZFmr6c+L83Stg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.ServiceModel.Primitives.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ServiceModel.Primitives.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/551694f534894508bee57aba617484c9.psmdcp",
        "ref/dotnet/de/System.ServiceModel.Primitives.xml",
        "ref/dotnet/es/System.ServiceModel.Primitives.xml",
        "ref/dotnet/fr/System.ServiceModel.Primitives.xml",
        "ref/dotnet/it/System.ServiceModel.Primitives.xml",
        "ref/dotnet/ja/System.ServiceModel.Primitives.xml",
        "ref/dotnet/ko/System.ServiceModel.Primitives.xml",
        "ref/dotnet/ru/System.ServiceModel.Primitives.xml",
        "ref/dotnet/System.ServiceModel.Primitives.dll",
        "ref/dotnet/System.ServiceModel.Primitives.xml",
        "ref/dotnet/zh-hans/System.ServiceModel.Primitives.xml",
        "ref/dotnet/zh-hant/System.ServiceModel.Primitives.xml",
        "ref/net45/_._",
        "ref/netcore50/System.ServiceModel.Primitives.dll",
        "ref/netcore50/System.ServiceModel.Primitives.xml",
        "ref/win8/_._",
        "System.ServiceModel.Primitives.nuspec"
      ]
    },
    "System.ServiceModel.Security/4.0.0": {
      "sha512": "sPVzsnd8w/TJsW/4sYA9eIGP+RtlpN0AhKLGKf9ywdGGmHPi0kkuX2mx412dM3GN0e4oifuISwvZqby/sI8Feg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.ServiceModel.Security.dll",
        "lib/net45/_._",
        "lib/netcore50/System.ServiceModel.Security.dll",
        "lib/win8/_._",
        "package/services/metadata/core-properties/724a153019f4439f95c814a98c7503f4.psmdcp",
        "ref/dotnet/de/System.ServiceModel.Security.xml",
        "ref/dotnet/es/System.ServiceModel.Security.xml",
        "ref/dotnet/fr/System.ServiceModel.Security.xml",
        "ref/dotnet/it/System.ServiceModel.Security.xml",
        "ref/dotnet/ja/System.ServiceModel.Security.xml",
        "ref/dotnet/ko/System.ServiceModel.Security.xml",
        "ref/dotnet/ru/System.ServiceModel.Security.xml",
        "ref/dotnet/System.ServiceModel.Security.dll",
        "ref/dotnet/System.ServiceModel.Security.xml",
        "ref/dotnet/zh-hans/System.ServiceModel.Security.xml",
        "ref/dotnet/zh-hant/System.ServiceModel.Security.xml",
        "ref/net45/_._",
        "ref/netcore50/System.ServiceModel.Security.dll",
        "ref/netcore50/System.ServiceModel.Security.xml",
        "ref/win8/_._",
        "System.ServiceModel.Security.nuspec"
      ]
    },
    "System.Text.Encoding/4.0.10": {
      "sha512": "fNlSFgy4OuDlJrP9SFFxMlaLazq6ipv15sU5TiEgg9UCVnA/OgoVUfymFp4AOk1jOkW5SVxWbeeIUptcM+m/Vw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Text.Encoding.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Text.Encoding.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/829e172aadac4937a5a6a4b386855282.psmdcp",
        "ref/dotnet/de/System.Text.Encoding.xml",
        "ref/dotnet/es/System.Text.Encoding.xml",
        "ref/dotnet/fr/System.Text.Encoding.xml",
        "ref/dotnet/it/System.Text.Encoding.xml",
        "ref/dotnet/ja/System.Text.Encoding.xml",
        "ref/dotnet/ko/System.Text.Encoding.xml",
        "ref/dotnet/ru/System.Text.Encoding.xml",
        "ref/dotnet/System.Text.Encoding.dll",
        "ref/dotnet/System.Text.Encoding.xml",
        "ref/dotnet/zh-hans/System.Text.Encoding.xml",
        "ref/dotnet/zh-hant/System.Text.Encoding.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.dll",
        "System.Text.Encoding.nuspec"
      ]
    },
    "System.Text.Encoding.CodePages/4.0.0": {
      "sha512": "ZHBTr1AXLjY9OuYR7pKx5xfN6QFye1kgd5QAbGrvfCOu7yxRnJs3VUaxERe1fOlnF0mi/xD/Dvb3T3x3HNuPWQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Text.Encoding.CodePages.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/8a616349cf5c4e6ba7634969c080759b.psmdcp",
        "ref/dotnet/de/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/es/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/fr/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/it/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/ja/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/ko/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/ru/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/System.Text.Encoding.CodePages.dll",
        "ref/dotnet/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/zh-hans/System.Text.Encoding.CodePages.xml",
        "ref/dotnet/zh-hant/System.Text.Encoding.CodePages.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Text.Encoding.CodePages.nuspec"
      ]
    },
    "System.Text.Encoding.Extensions/4.0.10": {
      "sha512": "TZvlwXMxKo3bSRIcsWZLCIzIhLbvlz+mGeKYRZv/zUiSoQzGOwkYeBu6hOw2XPQgKqT0F4Rv8zqKdvmp2fWKYg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Text.Encoding.Extensions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Text.Encoding.Extensions.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/894d51cf918c4bca91e81a732d958707.psmdcp",
        "ref/dotnet/de/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/es/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/fr/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/it/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/ja/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/ko/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/ru/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/System.Text.Encoding.Extensions.dll",
        "ref/dotnet/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/zh-hans/System.Text.Encoding.Extensions.xml",
        "ref/dotnet/zh-hant/System.Text.Encoding.Extensions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Text.Encoding.Extensions.dll",
        "System.Text.Encoding.Extensions.nuspec"
      ]
    },
    "System.Text.RegularExpressions/4.0.10": {
      "sha512": "0vDuHXJePpfMCecWBNOabOKCvzfTbFMNcGgklt3l5+RqHV5SzmF7RUVpuet8V0rJX30ROlL66xdehw2Rdsn2DA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Text.RegularExpressions.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/548eb1bd139e4c8cbc55e9f7f4f404dd.psmdcp",
        "ref/dotnet/de/System.Text.RegularExpressions.xml",
        "ref/dotnet/es/System.Text.RegularExpressions.xml",
        "ref/dotnet/fr/System.Text.RegularExpressions.xml",
        "ref/dotnet/it/System.Text.RegularExpressions.xml",
        "ref/dotnet/ja/System.Text.RegularExpressions.xml",
        "ref/dotnet/ko/System.Text.RegularExpressions.xml",
        "ref/dotnet/ru/System.Text.RegularExpressions.xml",
        "ref/dotnet/System.Text.RegularExpressions.dll",
        "ref/dotnet/System.Text.RegularExpressions.xml",
        "ref/dotnet/zh-hans/System.Text.RegularExpressions.xml",
        "ref/dotnet/zh-hant/System.Text.RegularExpressions.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Text.RegularExpressions.nuspec"
      ]
    },
    "System.Threading/4.0.10": {
      "sha512": "0w6pRxIEE7wuiOJeKabkDgeIKmqf4ER1VNrs6qFwHnooEE78yHwi/bKkg5Jo8/pzGLm0xQJw0nEmPXt1QBAIUA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Threading.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Threading.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/c17c3791d8fa4efbb8aded2ca8c71fbe.psmdcp",
        "ref/dotnet/de/System.Threading.xml",
        "ref/dotnet/es/System.Threading.xml",
        "ref/dotnet/fr/System.Threading.xml",
        "ref/dotnet/it/System.Threading.xml",
        "ref/dotnet/ja/System.Threading.xml",
        "ref/dotnet/ko/System.Threading.xml",
        "ref/dotnet/ru/System.Threading.xml",
        "ref/dotnet/System.Threading.dll",
        "ref/dotnet/System.Threading.xml",
        "ref/dotnet/zh-hans/System.Threading.xml",
        "ref/dotnet/zh-hant/System.Threading.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Threading.dll",
        "System.Threading.nuspec"
      ]
    },
    "System.Threading.Overlapped/4.0.0": {
      "sha512": "X5LuQFhM5FTqaez3eXKJ9CbfSGZ7wj6j4hSVtxct3zmwQXLqG95qoWdvILcgN7xtrDOBIFtpiyDg0vmoI0jE2A==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Threading.Overlapped.dll",
        "lib/net46/System.Threading.Overlapped.dll",
        "lib/netcore50/System.Threading.Overlapped.dll",
        "package/services/metadata/core-properties/e9846a81e829434aafa4ae2e8c3517d7.psmdcp",
        "ref/dotnet/de/System.Threading.Overlapped.xml",
        "ref/dotnet/es/System.Threading.Overlapped.xml",
        "ref/dotnet/fr/System.Threading.Overlapped.xml",
        "ref/dotnet/it/System.Threading.Overlapped.xml",
        "ref/dotnet/ja/System.Threading.Overlapped.xml",
        "ref/dotnet/ko/System.Threading.Overlapped.xml",
        "ref/dotnet/ru/System.Threading.Overlapped.xml",
        "ref/dotnet/System.Threading.Overlapped.dll",
        "ref/dotnet/System.Threading.Overlapped.xml",
        "ref/dotnet/zh-hans/System.Threading.Overlapped.xml",
        "ref/dotnet/zh-hant/System.Threading.Overlapped.xml",
        "ref/net46/System.Threading.Overlapped.dll",
        "System.Threading.Overlapped.nuspec"
      ]
    },
    "System.Threading.Tasks/4.0.10": {
      "sha512": "NOwJGDfk79jR0bnzosbXLVD/PdI8KzBeESoa3CofEM5v9R5EBfcI0Jyf18stx+0IYV9okmDIDxVtxq9TbnR9bQ==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Threading.Tasks.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Threading.Tasks.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/a4ed35f8764a4b68bb39ec8d13b3e730.psmdcp",
        "ref/dotnet/de/System.Threading.Tasks.xml",
        "ref/dotnet/es/System.Threading.Tasks.xml",
        "ref/dotnet/fr/System.Threading.Tasks.xml",
        "ref/dotnet/it/System.Threading.Tasks.xml",
        "ref/dotnet/ja/System.Threading.Tasks.xml",
        "ref/dotnet/ko/System.Threading.Tasks.xml",
        "ref/dotnet/ru/System.Threading.Tasks.xml",
        "ref/dotnet/System.Threading.Tasks.dll",
        "ref/dotnet/System.Threading.Tasks.xml",
        "ref/dotnet/zh-hans/System.Threading.Tasks.xml",
        "ref/dotnet/zh-hant/System.Threading.Tasks.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtimes/win8-aot/lib/netcore50/System.Threading.Tasks.dll",
        "System.Threading.Tasks.nuspec"
      ]
    },
    "System.Threading.Tasks.Dataflow/4.5.25": {
      "sha512": "Y5/Dj+tYlDxHBwie7bFKp3+1uSG4vqTJRF7Zs7kaUQ3ahYClffCTxvgjrJyPclC+Le55uE7bMLgjZQVOQr3Jfg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Threading.Tasks.Dataflow.dll",
        "lib/dotnet/System.Threading.Tasks.Dataflow.XML",
        "lib/portable-net45+win8+wp8+wpa81/System.Threading.Tasks.Dataflow.dll",
        "lib/portable-net45+win8+wp8+wpa81/System.Threading.Tasks.Dataflow.XML",
        "lib/portable-net45+win8+wpa81/System.Threading.Tasks.Dataflow.dll",
        "lib/portable-net45+win8+wpa81/System.Threading.Tasks.Dataflow.XML",
        "package/services/metadata/core-properties/b27f9e16f16b429f924c31eb4be21d09.psmdcp",
        "System.Threading.Tasks.Dataflow.nuspec"
      ]
    },
    "System.Threading.Tasks.Parallel/4.0.0": {
      "sha512": "GXDhjPhF3nE4RtDia0W6JR4UMdmhOyt9ibHmsNV6GLRT4HAGqU636Teo4tqvVQOFp2R6b1ffxPXiRaoqtzGxuA==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Threading.Tasks.Parallel.dll",
        "lib/net45/_._",
        "lib/netcore50/System.Threading.Tasks.Parallel.dll",
        "lib/win8/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/260c0741092249239a3182de21f409ef.psmdcp",
        "ref/dotnet/de/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/es/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/fr/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/it/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/ja/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/ko/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/ru/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/System.Threading.Tasks.Parallel.dll",
        "ref/dotnet/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/zh-hans/System.Threading.Tasks.Parallel.xml",
        "ref/dotnet/zh-hant/System.Threading.Tasks.Parallel.xml",
        "ref/net45/_._",
        "ref/netcore50/System.Threading.Tasks.Parallel.dll",
        "ref/netcore50/System.Threading.Tasks.Parallel.xml",
        "ref/win8/_._",
        "ref/wpa81/_._",
        "System.Threading.Tasks.Parallel.nuspec"
      ]
    },
    "System.Threading.Timer/4.0.0": {
      "sha512": "BIdJH5/e4FnVl7TkRUiE3pWytp7OYiRUGtwUbyLewS/PhKiLepFetdtlW+FvDYOVn60Q2NMTrhHhJ51q+sVW5g==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Threading.Timer.dll",
        "lib/net451/_._",
        "lib/netcore50/System.Threading.Timer.dll",
        "lib/win81/_._",
        "lib/wpa81/_._",
        "package/services/metadata/core-properties/c02c4d3d0eff43ec9b54de9f60bd68ad.psmdcp",
        "ref/dotnet/de/System.Threading.Timer.xml",
        "ref/dotnet/es/System.Threading.Timer.xml",
        "ref/dotnet/fr/System.Threading.Timer.xml",
        "ref/dotnet/it/System.Threading.Timer.xml",
        "ref/dotnet/ja/System.Threading.Timer.xml",
        "ref/dotnet/ko/System.Threading.Timer.xml",
        "ref/dotnet/ru/System.Threading.Timer.xml",
        "ref/dotnet/System.Threading.Timer.dll",
        "ref/dotnet/System.Threading.Timer.xml",
        "ref/dotnet/zh-hans/System.Threading.Timer.xml",
        "ref/dotnet/zh-hant/System.Threading.Timer.xml",
        "ref/net451/_._",
        "ref/netcore50/System.Threading.Timer.dll",
        "ref/netcore50/System.Threading.Timer.xml",
        "ref/win81/_._",
        "ref/wpa81/_._",
        "runtimes/win8-aot/lib/netcore50/System.Threading.Timer.dll",
        "System.Threading.Timer.nuspec"
      ]
    },
    "System.Xml.ReaderWriter/4.0.10": {
      "sha512": "VdmWWMH7otrYV7D+cviUo7XjX0jzDnD/lTGSZTlZqfIQ5PhXk85j+6P0TK9od3PnOd5ZIM+pOk01G/J+3nh9/w==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Xml.ReaderWriter.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/ef76b636720e4f2d8cfd570899d52df8.psmdcp",
        "ref/dotnet/de/System.Xml.ReaderWriter.xml",
        "ref/dotnet/es/System.Xml.ReaderWriter.xml",
        "ref/dotnet/fr/System.Xml.ReaderWriter.xml",
        "ref/dotnet/it/System.Xml.ReaderWriter.xml",
        "ref/dotnet/ja/System.Xml.ReaderWriter.xml",
        "ref/dotnet/ko/System.Xml.ReaderWriter.xml",
        "ref/dotnet/ru/System.Xml.ReaderWriter.xml",
        "ref/dotnet/System.Xml.ReaderWriter.dll",
        "ref/dotnet/System.Xml.ReaderWriter.xml",
        "ref/dotnet/zh-hans/System.Xml.ReaderWriter.xml",
        "ref/dotnet/zh-hant/System.Xml.ReaderWriter.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Xml.ReaderWriter.nuspec"
      ]
    },
    "System.Xml.XDocument/4.0.10": {
      "sha512": "+ej0g0INnXDjpS2tDJsLO7/BjyBzC+TeBXLeoGnvRrm4AuBH9PhBjjZ1IuKWOhCkxPkFognUOKhZHS2glIOlng==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Xml.XDocument.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/f5c45d6b065347dfaa1d90d06221623d.psmdcp",
        "ref/dotnet/de/System.Xml.XDocument.xml",
        "ref/dotnet/es/System.Xml.XDocument.xml",
        "ref/dotnet/fr/System.Xml.XDocument.xml",
        "ref/dotnet/it/System.Xml.XDocument.xml",
        "ref/dotnet/ja/System.Xml.XDocument.xml",
        "ref/dotnet/ko/System.Xml.XDocument.xml",
        "ref/dotnet/ru/System.Xml.XDocument.xml",
        "ref/dotnet/System.Xml.XDocument.dll",
        "ref/dotnet/System.Xml.XDocument.xml",
        "ref/dotnet/zh-hans/System.Xml.XDocument.xml",
        "ref/dotnet/zh-hant/System.Xml.XDocument.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Xml.XDocument.nuspec"
      ]
    },
    "System.Xml.XmlDocument/4.0.0": {
      "sha512": "H5qTx2+AXgaKE5wehU1ZYeYPFpp/rfFh69/937NvwCrDqbIkvJRmIFyKKpkoMI6gl9hGfuVizfIudVTMyowCXw==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/dotnet/System.Xml.XmlDocument.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/System.Xml.XmlDocument.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/89840371bf3f4e0d9ab7b6b34213c74c.psmdcp",
        "ref/dotnet/de/System.Xml.XmlDocument.xml",
        "ref/dotnet/es/System.Xml.XmlDocument.xml",
        "ref/dotnet/fr/System.Xml.XmlDocument.xml",
        "ref/dotnet/it/System.Xml.XmlDocument.xml",
        "ref/dotnet/ja/System.Xml.XmlDocument.xml",
        "ref/dotnet/ko/System.Xml.XmlDocument.xml",
        "ref/dotnet/ru/System.Xml.XmlDocument.xml",
        "ref/dotnet/System.Xml.XmlDocument.dll",
        "ref/dotnet/System.Xml.XmlDocument.xml",
        "ref/dotnet/zh-hans/System.Xml.XmlDocument.xml",
        "ref/dotnet/zh-hant/System.Xml.XmlDocument.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/System.Xml.XmlDocument.dll",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "System.Xml.XmlDocument.nuspec"
      ]
    },
    "System.Xml.XmlSerializer/4.0.10": {
      "sha512": "OKhE6vruk88z/hl0lmfrMvXteTASgJUagu6PT6S10i9uLbvDR3pTwB6jVgiwa2D2qtTB+eneZbS9jljhPXhTtg==",
      "type": "Package",
      "files": [
        "[Content_Types].xml",
        "_rels/.rels",
        "lib/DNXCore50/System.Xml.XmlSerializer.dll",
        "lib/MonoAndroid10/_._",
        "lib/MonoTouch10/_._",
        "lib/net46/_._",
        "lib/netcore50/System.Xml.XmlSerializer.dll",
        "lib/xamarinios10/_._",
        "lib/xamarinmac20/_._",
        "package/services/metadata/core-properties/1cffc42bca944f1d81ef3c3abdb0f0be.psmdcp",
        "ref/dotnet/de/System.Xml.XmlSerializer.xml",
        "ref/dotnet/es/System.Xml.XmlSerializer.xml",
        "ref/dotnet/fr/System.Xml.XmlSerializer.xml",
        "ref/dotnet/it/System.Xml.XmlSerializer.xml",
        "ref/dotnet/ja/System.Xml.XmlSerializer.xml",
        "ref/dotnet/ko/System.Xml.XmlSerializer.xml",
        "ref/dotnet/ru/System.Xml.XmlSerializer.xml",
        "ref/dotnet/System.Xml.XmlSerializer.dll",
        "ref/dotnet/System.Xml.XmlSerializer.xml",
        "ref/dotnet/zh-hans/System.Xml.XmlSerializer.xml",
        "ref/dotnet/zh-hant/System.Xml.XmlSerializer.xml",
        "ref/MonoAndroid10/_._",
        "ref/MonoTouch10/_._",
        "ref/net46/_._",
        "ref/xamarinios10/_._",
        "ref/xamarinmac20/_._",
        "runtime.json",
        "runtimes/win8-aot/lib/netcore50/System.Xml.XmlSerializer.dll",
        "System.Xml.XmlSerializer.nuspec"
      ]
    }
  },
  "projectFileDependencyGroups": {
    "": [
      "Microsoft.ApplicationInsights >= 1.2.3",
      "Microsoft.ApplicationInsights.PersistenceChannel >= 1.2.3",
      "Microsoft.ApplicationInsights.WindowsApps >= 1.1.1",
      "Microsoft.NETCore.UniversalWindowsPlatform >= 5.0.0",
      "SQLite.Net-PCL >= 3.1.1",
      "SQLiteNetExtensions >= 1.3.0"
    ],
    "UAP,Version=v10.0": []
  }
}