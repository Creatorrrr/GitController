package kosta.jgit.api.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GitStoreLogicTest {
	private GitStoreLogic gitCtrl;
	
	@Before
	public void setGit() {
		gitCtrl = new GitStoreLogic("C:/Users/Creator");
	}
	
//	@Test
//	public void testInitGit() {
//		gitCtrl.initGit("GitTest");
//	}
//
//	@Test
//	public void testSetLocalConfig() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.setConfig("user", null, "name", "cre");
//	}
//
//	@Test
//	public void testCloneRepository() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddFile() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.addFile("asdf.txt");
//	}
//
//	@Test
//	public void testCommitFile() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.commitFile("test message");
//	}
//
//	@Test
//	public void testAddBranch() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.addBranch("testbranch");
//	}
//
//	@Test
//	public void testGoToBranch() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.goToBranch("testbranch");
//	}
//
//	@Test
//	public void testMergeBranch() {
//		gitCtrl.initGit("GitTest");
//		gitCtrl.mergeBranch("master", "testbranch");
//	}
//
	@Test
	public void testGetDiff() {
		gitCtrl.initGit("GitTest");
		gitCtrl.getDiff("asdf.txt");
	}
//
//	@Test
//	public void testGetLog() {
//		fail("Not yet implemented");
//	}

}
