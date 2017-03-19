package kosta.jgit.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;

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
		
		/* StoredConfig config = git.getRepository().getConfig();
		config.setString("remote", "origin", "url", "http://github.com/user/repo");
		config.save(); */
		
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

	public void addBranch(String branch) {
		Ref ref = null;
		
		try {
			ref = git.branchCreate().setName(branch).call();
		} catch (RefAlreadyExistsException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (RefNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (InvalidRefNameException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		System.out.println("Created branch : " + ref.getName() + "\n");
		
		try {
			List<Ref> refList = git.branchList().call();
			
			System.out.println("Branch list\n");
			
			for(Ref r : refList) {
				System.out.println(r.getName());
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		} finally {
			AutoCloser.closeResource(git);
		}
	}

	public void goToBranch(String branch) {
		Ref ref = null;
		
		try {
			ref = git.checkout().setName(branch).call();
		} catch (RefAlreadyExistsException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (RefNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (InvalidRefNameException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (CheckoutConflictException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		System.out.println("HEAD : " + ref.getName());
	}

	public void mergeBranch(String checkout, String mergeWith) {
		Ref ref = null;
		MergeResult mergeResult = null;
		
		try {
			ref = git.getRepository().findRef(mergeWith);
			
			git.checkout().setName(checkout).call();
			
			mergeResult = git.merge().include(ref).call();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (RefAlreadyExistsException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (RefNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (InvalidRefNameException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (CheckoutConflictException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}
		
		if (mergeResult.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)) {
			Map<String, int[][]> allConflicts = mergeResult.getConflicts();
			for (String path : allConflicts.keySet()) {
			 	int[][] c = allConflicts.get(path);
			 	System.out.println("Conflicts in file " + path);
			 	for (int i = 0; i < c.length; ++i) {
			 		System.out.println("  Conflict #" + i);
			 		for (int j = 0; j < (c[i].length) - 1; ++j) {
			 			if (c[i][j] >= 0)
			 				System.out.println("    Chunk for "
			 						+ mergeResult.getMergedCommits()[j] + " starts on line #"
			 						+ c[i][j]);
			 		}
			 	}
			 }
		   // inform the user he has to handle the conflicts
		}
	}

	public String getDiff(String file) {
		Repository repo = git.getRepository();
		
		ObjectId oldHead = null;
		ObjectId newHead = null;
		
		try {
			oldHead = repo.resolve("HEAD^^^^{tree}");
	        newHead = repo.resolve("HEAD^^{tree}");
	        
	        System.out.println("Printing diff between tree: " + oldHead.getName() + " and " + newHead.getName());
	        
	        AbstractTreeIterator oldTree = prepareTreeParser(repo, oldHead);
	        AbstractTreeIterator newTree = prepareTreeParser(repo, newHead);
		
			List<DiffEntry> diff = git.diff().setOldTree(oldTree).setNewTree(newTree).setPathFilter(PathFilter.create(file)).call();

			for(DiffEntry entry : diff) {
				System.out.println("Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId());
				
				DiffFormatter formatter = null;
				try {
                	formatter = new DiffFormatter(System.out);
                    formatter.setRepository(repo);
                    formatter.format(entry);
                } finally {
                	AutoCloser.closeResource(formatter);
                }
			}
		} catch (RevisionSyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (AmbiguousObjectException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(git);
		}

		return null;
	}
	
	private AbstractTreeIterator prepareTreeParser(Repository repo, ObjectId objectId) {
        RevWalk walk = new RevWalk(repo);
        RevCommit commit = null;
        RevTree tree = null;
        ObjectReader objectReader = repo.newObjectReader();
        CanonicalTreeParser treeParser = new CanonicalTreeParser();
        
		try {
//			commit = walk.parseCommit(objectId);
//			tree = walk.parseTree(commit.getTree().getId());
			tree = walk.parseTree(objectId);
			
			treeParser.reset(objectReader, tree.getId());

	        walk.dispose();
		} catch (MissingObjectException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			AutoCloser.closeResource(walk, objectReader);
		}
        
        return treeParser;
	}

	public String getLog() {
		return null;
	}
	
}
