package kosta.jgit.api.impl;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;

import kosta.jgit.api.GitStore;
import kosta.jgit.utils.AutoCloser;

public class GitStoreLogic implements GitStore
{
	/** rootPath is root of this service */
	private String rootPath;
	/** repoPath is a root of current repository */
	private String repoPath;
	
	private Git git;

	public GitStoreLogic() {
		this.rootPath = "/";
		this.git = null;
		
		System.out.println("RootPath : " + this.rootPath);
	}
	
	public GitStoreLogic(String rootPath) {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append(rootPath);
		if(!rootPath.endsWith("/")) {
			strBuilder.append("/");
		}
		this.rootPath = strBuilder.toString();
		this.git = null;
		
		System.out.println("RootPath : " + this.rootPath);
	}
	
	public void initGit(String dir) {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append(rootPath);
		strBuilder.append(dir);
		if(!dir.endsWith("/")) {
			strBuilder.append("/");
		}
		this.repoPath = strBuilder.toString();
		repoPath = rootPath + dir + "/";
		
		try {
			git = Git.init().setDirectory(new File(repoPath)).call();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
	}

	public void setLocalConfig(String section, String subSection, String name, String value) {
		StoredConfig config = null;
		
		try {
			config = git.getRepository().getConfig();
			config.setString(section, subSection, name, value);
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		System.out.println("Config Changed : " + config.getString(section, subSection, name));
	}

	public void cloneRepository(String uri, String dir) {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append(rootPath);
		strBuilder.append(dir);
		if(!dir.endsWith("/")) {
			strBuilder.append("/");
		}
		this.repoPath = strBuilder.toString();
		repoPath = rootPath + dir + "/";
		
		try {
			git = Git.cloneRepository().setURI(uri).setDirectory(new File(repoPath)).call();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
	}

	public boolean setRemoteRepository(String uri) {
		return false;
	}

	public void addFile(String file) {
		DirCache index = null;
		
		try {
			index = git.add().addFilepattern(file).call();
			System.out.println("RepoPath : " + repoPath + file);
		} catch (NoFilepatternException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		System.out.println("DirCache has " + index.getEntryCount() + " items");
        for (int i = 0; i < index.getEntryCount(); i++) {
            // the number after the AnyObjectId is the "stage", see the constants in DirCacheEntry
            System.out.println("Item " + i + ": " + index.getEntry(i));
        }
	}

	public void commitFile(String message) {
		RevCommit rev = null;
		
		try {
			rev = git.commit().setMessage(message).call();
		} catch (NoFilepatternException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		System.out.println("RevCommit message : " + rev.getFullMessage());
	}

	public boolean addBranch(String branch) {
		return false;
	}

	public boolean goToBranch(String branch) {
		return false;
	}

	public boolean mergeBranch(String from, String to) {
		return false;
	}

	public boolean addIgnore(String fileDir) {
		return false;
	}

	public String getDiff(String file) {
		return null;
	}

	public String getLog() {
		return null;
	}
	
}
