package kosta.jgit.utils;

public class AutoCloser {

	public static void closeResource(AutoCloseable...autoCloseables) {
		for(AutoCloseable auto : autoCloseables) {
			try {
				if(auto != null) {
					auto.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
