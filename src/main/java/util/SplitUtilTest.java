package util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SplitUtilTest {

	@Test
	public void splitStr() {
		// SplitUtil Test
		String[] str = {"hello", "world"};
		assertArrayEquals(str, SplitUtil.splitStr("hello world"));
	}
}
