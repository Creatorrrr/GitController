package kosta.jgit.api.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GitControllerLogicTest {
	private GitControllerLogic gitCtrl;
	
	@Before
	public void setGit() {
		gitCtrl = new GitControllerLogic("C:/Users/kosta");
	}
	
//	@Test
//	public void testInitGit() {
//		gitCtrl.initGit("GitTest");
//	}
//
//	@Test
//	public void testSetConfig() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCloneRepository() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetRemoteRepository() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testAddFile() {
		gitCtrl.initGit("GitTest");
		gitCtrl.addFile("ddd.txt");
	}
//
//	@Test
//	public void testCommitFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddBranch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGoToBranch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMergeBranch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddIgnore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDiff() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetLog() {
//		fail("Not yet implemented");
//	}

}
