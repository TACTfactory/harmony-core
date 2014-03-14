/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.utils;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.storage.file.FileBasedConfig;

import com.google.common.base.Strings;

/**
 * Class utils for Git.
 * 
 * @author Erwan Le Huitouze (erwan.lehuitouze@tactfactory.com)
 *
 */
public class GitUtils {
	/**
	 * Clone a git repository.
	 * @param path Path where the repository will be clone
	 * @param url Url of the repository
	 * @throws GitException 
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 */
	public static void cloneRepository(String path, String url) throws GitException {
		cloneRepository(path, url, null);
	}
	
	/**
	 * Clone a git repository.
	 * @param path Path where the repository will be clone
	 * @param url Url of the repository
	 * @param branch Branch/Tag to checkout after clone, can be null
	 * @throws GitException
	 */
	public static void cloneRepository(String path, String url, String branch)
			throws GitException {
		CloneCommand command = Git.cloneRepository()
				.setProgressMonitor(new GitMonitor())
				.setURI(url)
				.setDirectory(new File(path));
		
		if (!Strings.isNullOrEmpty(branch)) {
			command.setBranch(branch);
		}
		
		try {
			command.call().close();
		} catch (InvalidRemoteException e) {
			throw new GitException(e);
		} catch (TransportException e) {
			throw new GitException(e);
		} catch (GitAPIException e) {
			throw new GitException(e);
		}
	}
	
	/**
	 * Add a submodule to .gitmodules file.
	 * @param repositoryPath Absolute path of the repository
	 * @param submodulePath Absolute path of the submodule repository
	 * @param submoduleUrl Url of the submodule
	 * @throws IOException
	 */
	public static void addSubmodule(
			String repositoryPath, String submodulePath, String submoduleUrl)
					throws IOException {
		// Get main repository
		RepositoryBuilder repoBuilder = new RepositoryBuilder();
		Repository repository = repoBuilder.setWorkTree(
				new File(repositoryPath))
				.setGitDir(new File(repositoryPath + "/.git"))
				.readEnvironment()
				.findGitDir()
				.build();
		
		// Get submodule relative path
		String path = TactFileUtils.absoluteToRelativePath(
				submodulePath,
				repositoryPath);
		path = path.substring(0, path.length() -1);
		
		// Update .gitmodules file
		try {
			FileBasedConfig modulesConfig = new FileBasedConfig(
					new File(
							repository.getWorkTree(),
							Constants.DOT_GIT_MODULES),
							repository.getFS());
		
			modulesConfig.load();
			
			modulesConfig.setString(
					ConfigConstants.CONFIG_SUBMODULE_SECTION,
					path, 
					ConfigConstants.CONFIG_KEY_PATH,
					path);
			modulesConfig.setString(
					ConfigConstants.CONFIG_SUBMODULE_SECTION,
					path,
					ConfigConstants.CONFIG_KEY_URL,
					submoduleUrl);
			
			modulesConfig.save();
		} catch (ConfigInvalidException e) {
			ConsoleUtils.displayError(e);
		}
		
		repository.close();
	}
	
	/**
	 * {@link ProgressMonitor} for Git download.
	 * 
	 * @author Erwan Le Huitouze (erwan.lehuitouze@tactfactory.com)
	 *
	 */
	private static class GitMonitor implements ProgressMonitor {
		/**
		 * Total unit progression for the current task.
		 */
		private double total = 0;
		/**
		 * Current progression of the current task.
		 */
		private double current = 0;
		/**
		 * Current progression of the current task in percent.
		 */
		private int percent = 0;
		
		@Override
		public void beginTask(String arg0, int arg1) {
			this.total = arg1;
			this.current = 0;
			this.percent = 0;
		}

		@Override
		public void endTask() {
			
		}

		@Override
		public boolean isCancelled() {
			return false;
		}

		@Override
		public void start(int arg0) {
			System.out.print("Clone git library...");
		}

		@Override
		public void update(int arg0) {
			if (this.total > 0) {
				this.current++;
				int newPercent = (int) (this.current / this.total * 100);
				
				if (this.percent != newPercent) {
					this.percent = newPercent;
					System.out.print(".");
				}
			}
		}
	}
	
	public static class GitException extends Exception {

		/**
		 * Serialization UID.
		 */
		private static final long serialVersionUID = -7401499264917401233L;

		/**
		 * Constructs a new exception with
		 * the specified cause and a detail message.
		 * @param throwable
		 */
		public GitException(Throwable throwable) {
			super(throwable);
		}
		
		/**
		 * Constructs a new exception with the specified detail message.
		 * @param message
		 */
		public GitException(String message) {
			super(message);
		}
		
		/**
		 * Constructs a new exception with
		 * the specified detail message and cause.
		 * @param message
		 * @param throwable
		 */
		public GitException(String message, Throwable throwable) {
			super(message, throwable);
		}
	}
}
