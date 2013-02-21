/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.io.File;

public abstract class OsUtil {
	private static final String GENERIC = "generic";
	private static final char BEGIN = 'c';
	private static final char END = 'z';


	/** Return Os name */
	public static String getOsName() {
		return System.getProperty("os.name", "unknown");
	}

	/** Return Platform type (win32, linux, solaris, mac, or generic) */
	public static String platform() {
		String ret = GENERIC;
		final String osname = 
				System.getProperty("os.name", GENERIC).toLowerCase();
		if (osname.startsWith("windows")) {
			ret = "win32";
		} else if (osname.startsWith("linux")) {
			ret = "linux";
		} else if (osname.startsWith("sunos")) {
			ret = "solaris";
		} else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
			ret = "mac";
		}
		return ret;
	}

	/** warning! only gives JRE architecture, 
	 * on x64 machine with x86 JRE installed, prints 'x86' */
	public static String getArch() {
		return System.getProperty("os.arch", GENERIC);
	}
	
	public static boolean isX64() {
		boolean is64bit = false;
		if (isWindows()) {
			if (System.getProperty("os.name").contains("Windows")) {
			    is64bit = System.getenv("ProgramFiles(x86)") != null;
			} else {
			    is64bit = System.getProperty("os.arch").indexOf("64") != -1;
			}
		} else {
			is64bit = false;
		}
		return is64bit;
	}
	
	public static boolean isWindows() {
		return getOsName().toLowerCase().contains("windows");
	}

	public static boolean isLinux() {
		return getOsName().toLowerCase().contains("linux");
	}

	public static boolean isMac() {
		final String os = getOsName().toLowerCase();
		return os.startsWith("mac") || os.startsWith("darwin");
	}

	public static boolean isSolaris() {
		final String os = getOsName().toLowerCase();
		return os.indexOf("sunos") >= 0;
	}

	/** return windows system drive */
	public static String findWindowsSystemDrive() {
		String sysdrive = null;
		if (isWindows() 
				&& System.getProperty("java.version", "").startsWith("1.5.")) {
			// System.getEnv(String name) 
			// is deprecated and throws java.lang.Error
			// in java 1.2 through 1.4. but un-deprecated in 1.5
			sysdrive = System.getenv("SYSTEMDRIVE");	
		}
		return sysdrive;
	}

	/** return windows system root folder */
	public static String findWindowsSystemRoot() {
		String sysRoot = null;
		if (isWindows()) { 
			if (System.getProperty("java.version", "").startsWith("1.5.")) {
				// System.getEnv(String name) 
				// is deprecated and throws java.lang.Error
				// in java 1.2 through 1.4. but un-deprecated in 1.5
				sysRoot = System.getenv("SYSTEMROOT");
			} else {
				// try to find it by looking at the file system
				for (char drive = OsUtil.BEGIN; drive < OsUtil.END; drive++) {
					File root = new File(drive + ":\\WINDOWS");
					if (root.exists() && root.isDirectory()) {
						sysRoot = root.getAbsolutePath().toString();
					}
		
					root = new File(drive + ":\\WINNT");
					if (root.exists() && root.isDirectory()) {
						sysRoot = root.getAbsolutePath().toString();
					}
				}
			}
		}
		return sysRoot;
	}
}

// Source: http://www.tolstoy.com/samizdat/sysprops.html

// Windows VMs
//
// ------------------------------------------------------
// OS: Windows95
// Processor: Pentium
// VM: SunJDK1.0.2
// Notes:
// Contributor: CK
//
// os.nam = "Windows 95" "windows 95"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.0.2" "1.0.2"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows95
// Processor: Pentium
// VM: SunJDK1.1.4
// Notes:
// Contributor: CK
//
// os.nam = "Windows 95" "windows 95"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.4" "1.1.4"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows NT
// Processor: x86
// VM: Microsoft1.1
// Notes:
// Contributor: AB
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Microsoft Corp." "microsoft corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1" "1.1"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows NT 4.0
// Processor: Pentium II
// VM: JDK 1.1.6
// Notes:
// Contributor: NB
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// My Windows NT 4.0 box with Java 1.1.6 reports:
// Contributor: RG
//
// osName = WindowsNT
// osArch = x86
// osVersion = 4.0
// vendor = Sun Microsystems Inc.
// APIVersion = 45.3
// interpeterVersion = 1.1.6
//
//
// ------------------------------------------------------
// OS: Windows CE 2.0
// Processor: SH3
// VM: Microsoft CE JDK Version 1.0
// Notes: This was the February release - The line separator is interesting for
// a windows machine...
// Contributor: AW
//
// os.nam = "Windows CE" "windows ce"
// os.arc = "Unknown" "unknown"
// os.versio = "2.0 Beta" "2.0 beta"
// java.vendo = "Microsoft" "microsoft"
// java.class.versio = "JDK1.1" "jdk1.1"
// java.versio = "JDK1.1" "jdk1.1"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: NT Workstation 4.0
// Processor: Pentium MMX 200Mhz
// VM: SuperCede 2.03
// Notes:
// Contributor: AL
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "SuperCede Inc." "supercede inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.4" "1.1.4"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows 95
// Processor: Pentium 166
// VM: Netscape Communications Corporation -- Java 1.1.2
// Notes: Obtained in Netscape Navigator 4.03.
// Contributor: DG
//
// os.nam = "Windows 95" "windows 95"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Netscape Communications Corporation""netscape
// communications corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.2" "1.1.2"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: Windows 95
// Processor: Pentium 166
// VM: Microsoft SDK for Java 2.01
// Notes: Obtained in Internet Explorer 4 (version 4.71.1712.6).
// Contributor: DG
//
// os.nam = "Windows 95" "windows 95"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Microsoft Corp." "microsoft corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1" "1.1"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows 95
// Processor: Pentium
// VM: Netscape
// Notes: Created in Netscape Navigator 3.01 for Win95.
// Contributor: DG
//
// os.nam = "Windows 95" "windows 95"
// os.arc = "Pentium" "pentium"
// os.versio = "4.0" "4.0"
// java.vendo = "Netscape Communications Corporation""netscape communications corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.02" "1.02"
// file.separato = "/" "/"
// path.separato = ";" ";"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: Windows NT Workstation 4.0 Service Pack 3
// Processor: Pentium II 266
// VM: Sun JDK 1.2 beta 4
// Notes:
// Contributor: JH2
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.2beta4" "1.2beta4"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: Windows NT Workstation 4.0 Service Pack 3
// Processor: Pentium II 266
// VM: Symantec Java! JustInTime Compiler Version 3.00.029(i) for JDK 1.1.x
// Notes:
// Contributor: JH2
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Symantec Corporation" "symantec corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.5" "1.1.5"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
// ------------------------------------------------------
// OS: WinNT 4.00.1381
// Processor: x86 Family 5 Model 6
// VM: IE 3.02
// Notes:
// Contributor: PB
//
// os.nam = "Windows NT" "windows nt"
// os.arc = "x86" "x86"
// os.versio = "4.0" "4.0"
// java.vendo = "Microsoft Corp." "microsoft corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.0.2" "1.0.2"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
//
//
// -------------------------------------------------------------------------
//
//
// Mac VMs
//
//
//
// ------------------------------------------------------
// OS: MacOS 7.5.1
// Processor: PowerMac
// VM: Metrowerks CodeWarrior Pro2, standard and JIT
// Notes:
// Contributor: CK
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "7.5.1" "7.5.1"
// java.vendo = "Metrowerks Corp." "metrowerks corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.3" "1.1.3"
// file.separato = "/" "/"
// path.separato = ";" ";"
// line.separato = "0xd" "0xd"
//
//
// ------------------------------------------------------
// OS: MacOS 7.5.1
// Processor: PowerMac
// VM: MRJ 1.0.2, 1.5, 2.0d2 (values are the same for all three)
// Notes:
// Contributor: CK
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "7.5.1" "7.5.1"
// java.vendo = "Apple Computer, Inc." "apple computer, inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.0.2" "1.0.2"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xd" "0xd"
//
//
// ------------------------------------------------------
// OS: MacOS 8.1
// Processor: PowerPC 604e
// VM: MRJ 2.0
// Notes:
// Contributor: BG
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "8.1" "8.1"
// java.vendo = "Apple Computer, Inc." "apple computer, inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.3" "1.1.3"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xd" "0xd"
//
//
// ------------------------------------------------------
// OS: MacOS 8.0
// Processor: PowerPC 603e
// VM: MRJ 2.1 ea1
// Notes:
// Contributor: MJ
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "8" "8"
// java.vendo = "Apple Computer, Inc." "apple computer, inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.5" "1.1.5"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xd" "0xd"
//
//
// ------------------------------------------------------
// OS: MacOS version 8.1
// Processor: PowerPC 750 (?)
// VM: Netscape Navigator
// Notes: Obtained in Netscape Navigator 4.05-98085.
// Contributor: DG
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "7.5" "7.5"
// java.vendo = "Netscape Communications Corporation""netscape communications corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.2" "1.1.2"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: MacOS version 8.1
// Processor: PowerPC 750 (?)
// VM: Microsoft Virtual Machine
// Notes: Obtained in Internet Explorer 4.01 (PowerPC) with "Java virtual Machine"
// pop-up preference set to "Microsoft Virtual Machine".
// Contributor: DG
//
// os.nam = "MacOS" "macos"
// os.arc = "PowerPC" "powerpc"
// os.versio = "8.1.0" "8.1.0"
// java.vendo = "Microsoft Corp." "microsoft corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.4" "1.1.4"
// file.separato = "/" "/"
// path.separato = ";" ";"
// line.separato = "0xd" "0xd"
//
//
// ------------------------------------------------------
// OS: MacOS version 8.1
// Processor: PowerPC 750 (?)
// VM: Apple MRJ
// Notes: Obtained in Internet Explorer 4.01 (PowerPC) with "Java virtual Machine"
// pop-up preference set to "Apple MRJ".
// Contributor: DG
//
// os.nam = "Mac OS" "mac os"
// os.arc = "PowerPC" "powerpc"
// os.versio = "8.1" "8.1"
// java.vendo = "Apple Computer, Inc." "apple computer, inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.3" "1.1.3"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xd" "0xd"
//
//
//
//
// --------------------------------------------------------------------------------
//
//
// Linux VMs
//
//
//
// ------------------------------------------------------
// OS: Redhat Linux 5.0
// Processor: Pentium
// VM: blackdown.org JDK1.1.6 v2
// Notes:
// Contributor: CK
//
// os.nam = "Linux" "linux"
// os.arc = "x86" "x86"
// os.versio = "2.0.31" "2.0.31"
// java.vendo = "Sun Microsystems Inc., ported by Randy Chapman and Steve Byrne""sun microsystems inc., ported by randy
// chapman and steve byrne"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
//
//
//
//
//
//
//
//
// --------------------------------------------------------------------------------
//
//
// Solaris
//
//
//
// ------------------------------------------------------
// OS: Solaris 2.5.1
// Processor: Ultra1
// VM: Sun JDK 1.1.6
// Notes:
// Contributor: MJ
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: Solaris 2.5.1
// Processor: Sparc Ultra 1
// VM: Sun JDK 1.1.6
// Notes:
// Contributor: JH
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: Solaris
// Processor: sparc
// VM: jdk1.1.6
// Notes:
// Contributor: AB
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: SunOS 5.6 (Solaris 2.6)
// Processor: sparc
// VM: JDK 1.1.3
// Notes:
// Contributor: NB
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.3" "1.1.3"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// On my Sun with JDK 1.1.6 I get -
// Contributor: CA
//
// osNam =Solaris
// osArc =sparc
// osVersio =2.x
// vendo =Sun Microsystems Inc.
// APIVersio =45.3
// interpreterVersio =1.1.6
//
//
// ------------------------------------------------------
// OS: Solaris 2.5.1
// Processor: UltraSparc
// VM: Javasoft JDK 1.0.2
// Notes:
// Contributor: CA
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.0.2" "1.0.2"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
//
//
//
//
//
//
// --------------------------------------------------------------------------------
//
//
// OS/2
//
//
//
// ------------------------------------------------------
// OS: OS/2
// Processor: Pentium
// VM: OS/2 JDK 1.1.6
// Notes: JDK 1.1.6 IBM build o116-19980728 (JIT: javax), OS/2 Warp 4, FP7
// Contributor: SP
//
// os.nam = "OS/2" "os/2"
// os.arc = "x86" "x86"
// os.versio = "20.40" "20.40"
// java.vendo = "IBM Corporation" "ibm corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
//
//
//
//
//
//
//
//
//
//
// ----------------------------------------------------------------------------
//
//
// Other Unix Systems
//
//
//
// ------------------------------------------------------
// OS: MPE/iX 5.5 (PowerPatch 3)
// Processor: HP 3000/968 (PA-RISC)
// VM: (unknown - HP provided - JDK 1.1.5)
// Notes:
// Contributor: SS
//
// os.nam = "MPE/iX" "mpe/ix"
// os.arc = "PA-RISC" "pa-risc"
// os.versio = "C.55.00" "c.55.00"
// java.vendo = "HP CSY (freeware)." "hp csy (freeware)."
// java.class.versio = "45.3" "45.3"
// java.versio = "JDK 1.1.5" "jdk 1.1.5"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// here are the results for the latest HP-UX JDK (HP-UX 10.20):
// AS told me (CK) that for HP-UX java.version 
// will always have the same general format;
// the letter ( "C" in the sample ) might change, and there may or may
// not be a date after the version number.
// Contributor: AS
//
// os.nam = "HP-UX" "hp-ux"
// os.arc = "PA-RISC" "pa-risc"
// os.versio = "B.10.20" "b.10.20"
// java.vendo = "Hewlett Packard Co." "hewlett packard co."
// java.class.versio = "45.3" "45.3"
// java.versio = "HP-UX Java C.01.15.03 07/07/98"
// 			"hp-ux java c.01.15.03 07/07/98"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: HP-UX
// Processor: PA-RISC
// VM: HP-UX Java C.01.15.01
// Notes: (see the notes in the previous entry)
// Contributor: NB
//
// os.nam = "HP-UX" "hp-ux"
// os.arc = "PA-RISC" "pa-risc"
// os.versio = "B.10.20" "b.10.20"
// java.vendo = "Hewlett Packard Co." "hewlett packard co."
// java.class.versio = "45.3" "45.3"
// java.versio = "HP-UX Java C.01.15.01" "hp-ux java c.01.15.01"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: AIX 4.3
// Processor: Power
// VM: JDK 1.1.2
// Notes:
// Contributor: NB
//
// os.nam = "AIX" "aix"
// os.arc = "Power" "power"
// os.versio = "4.3" "4.3"
// java.vendo = "IBM Corporation" "ibm corporation"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.2" "1.1.2"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// Contributor: RG
// My AIX 4.1.5 box with Java 1.1.4 reports:
//
// osName = AIX
// osArch = POWER_RS
// osVersion = 4.1
// vendor = IBM Corporation
// APIVersion = 45.3
// interpeterVersion = 1.1.4
//
//
// ------------------------------------------------------
// OS: FreeBSD 2.2.2
// Processor: Intel Pentium
// VM: FreeBSD port of JDK1.0.2 (Jeff Hsu?)
// Notes: This is actually for FreeBSD with the JDK 1.0.2. It probably says Solaris
// since it was a really early port.
// Contributor: CA
//
// os.nam = "Solaris" "solaris"
// os.arc = "sparc" "sparc"
// os.versio = "2.x" "2.x"
// java.vendo = "Sun Microsystems Inc." "sun microsystems inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "hsu:11/21/21-22:43" "hsu:11/21/21-22:43"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: FreeBSD 2.2.2
// Processor: Intel Pentium
// VM: JDK1.1.6 FreeBSD port
// Notes:
// Contributor: CA
//
// os.nam = "FreeBSD" "freebsd"
// os.arc = "x86" "x86"
// os.versio = "2.2.2-RELEASE" "2.2.2-release"
// java.vendo = "Sun Microsystems Inc., port by java-port@FreeBSD.org""sun microsystems inc., port by
// java-port@freebsd.org"
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.6" "1.1.6"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: IRIX 6.3
// Processor: MIPS R10000
// VM: JDK 1.1.6
// Notes:
// Contributor: YA
//
// os.nam = "Irix" "irix"
// os.arc = "mips" "mips"
// os.versio = "6.3" "6.3"
// java.vendo = "Silicon Graphics Inc." "silicon graphics inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "3.1.1 (Sun 1.1.6)" "3.1.1 (sun 1.1.6)"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
// ------------------------------------------------------
// OS: DIGITAL UNIX 4.0
// Processor: ALPHA
// VM: JDK 1.1.5
// Notes:
// Contributor: MK
//
// os.nam = "Digital Unix" "digital unix"
// os.arc = "alpha" "alpha"
// os.versio = "4.0" "4.0"
// java.vendo = "Digital Equipment Corp." "digital equipment corp."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.5" "1.1.5"
// file.separato = "/" "/"
// path.separato = ":" ":"
// line.separato = "0xa" "0xa"
//
//
//
//
//
//
// --------------------------------------------------------------------------------
//
//
// Other Platforms
//
//
//
// ------------------------------------------------------
// OS: NetWare 4.11
// Processor: Pentium 200
// VM: Novell JVM for NetWare 1.1.5
// Notes:
// Contributor: FJ
//
// os.nam = "NetWare 4.11" "netware 4.11"
// os.arc = "x86" "x86"
// os.versio = "4.11" "4.11"
// java.vendo = "Novell Inc." "novell inc."
// java.class.versio = "45.3" "45.3"
// java.versio = "1.1.5 " "1.1.5 "
// file.separato = "\" "\"
// path.separato = ";" ";"
// line.separato = "0xd,0xa" "0xd,0xa"
//
//
