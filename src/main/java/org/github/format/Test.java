package org.github.format;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Test {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws FileNotFoundException {

        Map m = new HashMap();
        m.put("clientid", "tony");
        m.put("app.port", 12121);
        m.put("context.path", "iframework/");


		System.out.print(Template.loadFrom(Test.class.getResourceAsStream("template")).format(m));

	}
}
