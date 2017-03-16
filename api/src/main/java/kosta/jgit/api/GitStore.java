package kosta.jgit.api;

public interface GitStore {
	
	// Initialize git to targeted directory
	public void initGit(String dir);
	
	// Set config
	public void setLocalConfig(String section, String subSection, String name, String value);
	
	// Clone existing repository
	public void cloneRepository(String uri, String dir);

	// Set remote repository
	public boolean setRemoteRepository(String uri);
	
	// Stage file for commit it to Repository
	public void addFile(String file);
	
	// Commit staged files
	public void commitFile(String message);
	
	// Add branch
	public boolean addBranch(String branch);
	
	// Go to specific branch
	public boolean goToBranch(String branch);
	
	// Merge status
	public boolean mergeBranch(String from, String to);
	
	// Add specified file to '.gitignore' list
	public boolean addIgnore(String fileDir);
	
	// Get difference of changed file
	public String getDiff(String file);
	
	// Get log
	public String getLog();
	
}
