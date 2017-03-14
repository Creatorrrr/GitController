package kosta.jgit.api;

public interface GitController {
	
	// Initialize git to targeted directory
	public void initGit(String dir);
	
	// Set config
	public boolean setConfig(String key, String info);
	
	// Clone existing repository
	public boolean cloneRepository(String uri);

	// Set remote repository
	public boolean setRemoteRepository(String uri);
	
	// Stage file for commit it to Repository
	public boolean addFile(String file);
	
	// Commit staged files
	public boolean commitFile();
	
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
