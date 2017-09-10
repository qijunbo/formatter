package org.github.format;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Test {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws FileNotFoundException {

		Map m = new HashMap();
		m.put("name", "tony");
		m.put("age", 36);
		m.put("age1", 34);
		m.put("time", Calendar.getInstance());
		m.put("number", 1000);

		System.out.print(Template.loadFrom(Test.class.getResourceAsStream("template")).format(m));

	}
}
