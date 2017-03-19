package kosta.jgit.api;

public interface GitController {
	
	// Initialize git to targeted directory
	public void initGit(String dir);
	
	// Set config
	public void setLocalConfig(String section, String subSection, String name, String value);
	
	// Clone existing repository
	public void cloneRepository(String uri, String dir);
	
	// Stage file for commit it to Repository
	public void addFile(String file);
	
	// Commit staged files
	public void commitFile(String message);
	
	// Add branch
	public void addBranch(String branch);
	
	// Go to specific branch
	public void goToBranch(String branch);
	
	// Merge status
	public void mergeBranch(String from, String to);
	
	// Get difference of changed file
	public String getDiff(String file, int rollbackCount);
	
	// Get previous version of file
	public String getPreviousFile(String file, int rollbackCount);
	
	// Get log
	public String getLog(String file);
	
}
