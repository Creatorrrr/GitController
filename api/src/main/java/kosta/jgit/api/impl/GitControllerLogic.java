package kosta.jgit.api.impl;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import kosta.jgit.api.GitController;
import kosta.jgit.utils.AutoCloser;

public class GitControllerLogic implements GitController
{
	private String rootPath;
	
	private Git git;

	public GitControllerLogic() {
		this.rootPath = "/";
		this.git = null;
		
		System.out.println("RootPath : " + this.rootPath);
	}
	
	public GitControllerLogic(String rootPath) {
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
		File path = new File(rootPath + dir);
		
		try {
			git = Git.init().setDirectory(path).call();
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

	public boolean setConfig(String key, String info) {
		return false;
	}

	public boolean cloneRepository(String uri) {
		return false;
	}

	public boolean setRemoteRepository(String uri) {
		return false;
	}

	public boolean addFile(String file) {
		return false;
	}

	public boolean commitFile() {
		return false;
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
