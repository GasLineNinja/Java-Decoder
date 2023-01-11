import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import junit.framework.TestCase;

public class TestClass extends TestCase{

	char[] normalArr = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	char[] neg5Ceasar = {'f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','a','b','c','d','e'};
	char[] plus10Ceasar = {'q','r','s','t','u','v','w','x','y','z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p'};
	
	char[] secrt = {'s','e','c','r','t','a','b','d','f','g','h','i','j','k','l','m','n','o','p','q','u','v','w','x','y','z'};
	char[] zxy = {'z','x','y','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w'};
	
	char[] secrtSalt = {'s','a','l','t','e','c','r','b','d','f','g','h','i','j','k','m','n','o','p','q','u','v','w','x','y','z'};
	char[] secrtSalt1 = {'z','s','a','l','t','e','c','r','b','d','f','g','h','i','j','k','m','n','o','p','q','u','v','w','x','y'};
	
	char[] qwertyFile = {'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
	
	Decoder control1, control2, control3, cc1,cc2,cc3,cc4, kc1,kc2,kc3,kc4;
	public void setUp() {
		control1 = new Decoder();
		
		control2 = new Decoder("abc");
		control3 = new Decoder("");
		
		cc1 = new Decoder(-5);
		cc2 = new Decoder(10);
		
		kc1 = new Decoder("secret");
		kc2 = new Decoder("zxy");
		kc3 = new Decoder("SeCREt");
		kc4 = new Decoder("   ..,42@%(#*   z   9897\n\n394878   x   /.,.>?>\"><:   y   ");
		
		
		
	}
	
	
	//testing Caesar Ciphers
	public void test01() {
		assertArrayEquals(normalArr, control1.getKey());
		assertArrayEquals(neg5Ceasar,cc1.getKey());
		assertArrayEquals(plus10Ceasar, cc2.getKey());
		
	}
	
	//testing keyword Ciphers
	public void test02() {
		assertArrayEquals(normalArr,control2.getKey());
		assertArrayEquals(normalArr,control3.getKey());
		
		assertArrayEquals(secrt, kc1.getKey());
		assertArrayEquals(zxy, kc2.getKey());

		assertArrayEquals(secrt, kc3.getKey());
		assertArrayEquals(zxy, kc4.getKey());
		
		
	}
	
	
	//testing translating lines
	public void test03() {
		assertEquals("this should come out the same.",control1.translateLine("this should come out the same."));
		
		assertEquals("jxuhui q idqau yd co reej!", cc2.translateLine("theres a snake in my boot!"));
		
		assertEquals("f misy mlq la bottr ql rosw qwl ktw csorp.",kc1.translateLine("i play pot of greed to draw two new cards."));
		
		assertEquals("f misy mlq la bottr ql rosw qwl ktw csorp.",kc1.translateLine("I play Pot of Greed to draw two new cards."));
	}
	
	public void test04() {
		boolean caught = false;
		try {
			cc2.translateLine(null);
		}catch(IllegalArgumentException e) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	
	//testing cipher from File
	
	public void test05() {
		Decoder cf1 = new Decoder(new File("inputFiles/TEST_qwerty"));
		assertArrayEquals(qwertyFile,cf1.getKey());
		String message = "";
		
		boolean caught = false;
		try {
			Decoder fail = new Decoder(new File("inputFiles/blank_for_testing"));
		}catch(IllegalArgumentException e) {
			caught = true;
			message = e.getMessage();
		}
		assertTrue(caught);
		assertEquals("File was blank", message);
		
		caught = false;
		try {
			Decoder fail = new Decoder(new File("inputFiles/this_doesnt_exist"));
		}catch(IllegalArgumentException e) {
			caught = true;
			message = e.getMessage();
		}
		assertTrue(caught);
		assertEquals("File cannot be located", message);
	}
	
	public void test06() {
		String message = "";
		
		boolean caught = false;
		try {
			Decoder fail = new Decoder(new File("inputFiles/InvalidKey"));
		}catch(IllegalArgumentException e) {
			caught = true;
			message = e.getMessage();
		}
		assertTrue(caught);
		assertEquals("Invalid key", message);
		
		caught = false;
		try {
			Decoder fail = new Decoder(new File("inputFiles/KeyTooShort"));
		}catch(IllegalArgumentException e) {
			caught = true;
			message = e.getMessage();
		}
		assertTrue(caught);
		assertEquals("Invalid key", message);
	}
		
	//testing saveFile
	
	public void test07() {
		Decoder dec = new Decoder(-5);
		
		File f = new File("inputFiles/TEST_savetest");
		
		dec.saveKey(f);
		
		dec = new Decoder(f);
		
		assertArrayEquals(dec.getKey(),neg5Ceasar);
		
		dec = new Decoder();
		
		dec.saveKey(f);
		
		dec = new Decoder(f);
		
		assertArrayEquals(dec.getKey(),normalArr);
	}
	
	public void test08() {
		Decoder dec = new Decoder(4);
		boolean caught = false;
		try {
			dec.saveKey(null);
		}catch(NullPointerException e) {
			caught = true;
		}
		assertTrue(caught);
	}
		
		
		
	
	
	
	
}
