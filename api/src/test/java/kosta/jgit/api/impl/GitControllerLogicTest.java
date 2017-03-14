package kosta.jgit.api.impl;

import junit.framework.TestCase;

public class GitControllerLogicTest extends TestCase {

	public void testInitGit() {
		GitControllerLogic gitCtrl = new GitControllerLogic("C:/Users/Creator");
		
		gitCtrl.initGit("gittest");
	}

//	public void testSetConfig() {
//		fail("Not yet implemented");
//	}
//
//	public void testCloneRepository() {
//		fail("Not yet implemented");
//	}
//
//	public void testSetRemoteRepository() {
//		fail("Not yet implemented");
//	}
//
//	public void testAddFile() {
//		fail("Not yet implemented");
//	}
//
//	public void testCommitFile() {
//		fail("Not yet implemented");
//	}
//
//	public void testAddBranch() {
//		fail("Not yet implemented");
//	}
//
//	public void testGoToBranch() {
//		fail("Not yet implemented");
//	}
//
//	public void testMergeBranch() {
//		fail("Not yet implemented");
//	}
//
//	public void testAddIgnore() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetDiff() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetLog() {
//		fail("Not yet implemented");
//	}

}
