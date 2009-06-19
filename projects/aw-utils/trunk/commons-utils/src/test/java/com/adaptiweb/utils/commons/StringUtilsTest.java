package com.adaptiweb.utils.commons;

import static org.junit.Assert.*;
import static com.adaptiweb.utils.commons.StringUtils.*;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testParseIP() {
		assertEquals(0L, parseIP("0.0.0.0"));
		assertEquals(255L, parseIP("0.0.0.255")); /* 2^8 - 1 */
		assertEquals(4278190080L, parseIP("255.0.0.0")); /* 2^32 - 2^24 */
		assertEquals(2130706433L, parseIP("127.0.0.1")); /* 2^31 - 2^24 + 1*/
		assertEquals(4294967295L, parseIP("255.255.255.255")); /* 2^32 - 1 */
	}

	@Test
	public void testFormatIP() {
		assertEquals("0.0.0.0", formatIP(0L));
		assertEquals("0.0.0.255", formatIP(255L)); /* 2^8 - 1 */
		assertEquals("255.0.0.0", formatIP(4278190080L)); /* 2^32 - 2^24 */
		assertEquals("127.0.0.1", formatIP(2130706433L)); /* 2^31 - 2^24 + 1*/
		assertEquals("255.255.255.255", formatIP(4294967295L)); /* 2^32 - 1 */
	}

}
