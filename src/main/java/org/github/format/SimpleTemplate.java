package org.github.format;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Formatter;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * 
 * @author qijunbo
 * 
 *         SimpleTemplete load the string template form input, and replace the
 *         placeholder with the values found in map.
 * 
 *         example:
 * 
 *         if you have this file,
 * 
 *         hello, ${name}, how old are you ? Hi, I'm ${age, %d}, what about
 *         you? Well, I am ${age1, %d}. How lang have you been here? I came here
 *         on ${time, %tD}, so It's ${number, %d} years. but I don't like
 *         ${here} ; some developer like to use ${obj.name , %s}, all right, we
 *         support it anyway.
 * 
 *         //@formatter:off
 *         Map m = new HashMap();
 *         m.put("name", "tony"); 
 *         m.put("age", 36);
 *         m.put("age1", 34); 
 *         m.put("time", Calendar.getInstance());
 * 			
 *         System.out.print(Template.loadFrom(new
 *         File("D:\\templete")).format(m));
 *         
 *         //@formatter:on
 *         
 *         @see java.util.Formatter 
 *         
 *         The placeholder which cannot find its value in the map will be ignored.
 * 
 * 
 */
public class SimpleTemplate implements Template {

	private StringBuilder buffer = new StringBuilder();

	// something like this ${user.name, %8d}or ${name}
	private String placeholder = "\\$\\{([\\w\\.]+)( *, *(\\S+))?\\}";

	// if you want support other placeholder , you can customize this.
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public Template load(File file) throws FileNotFoundException {
		return load(new FileInputStream(file));
	}

	@Override
	public Template load(InputStream is) {
		try {
			return load(is, null);
		} catch (UnsupportedEncodingException e) {
			// use default charset, no exception here.
			return this;
		}
	}

	@Override
	public Template load(File f, String charset) throws FileNotFoundException, UnsupportedEncodingException {

		return load(new FileInputStream(f), charset);
	}

	@Override
	public Template load(InputStream is, String charset) throws UnsupportedEncodingException {

		String s;

		BufferedReader reader = new BufferedReader(
				charset == null ? new InputStreamReader(is) : new InputStreamReader(is, charset));
		try {
			while ((s = reader.readLine()) != null) {
				buffer.append(s).append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public StringBuilder format(Map<String, ?> values) {

		Pattern p = Pattern.compile(placeholder);
		Matcher m = p.matcher(buffer);
		// after the placeholder is relaced with target string, the location of
		// the string point will change.
		int next = 0;

		while (m.find(next)) {

			 IntStream.range(0, m.groupCount() + 1).forEach(i ->
			 System.out.print(i + ": " + m.group(i) + "\t"));
			 System.out.println(String.format("Start: %d, End: %d .\n",
			 m.start(), m.end()));

			Optional.ofNullable(values.get(m.group(1))).ifPresent(value -> {
				Formatter formatter = new Formatter();
				buffer.replace(m.start(), m.end(),
			//@formatter:off
					Optional.ofNullable(m.group(3))
						.map(format -> formatter.format(format, value).toString())
						.orElse(String.valueOf(value)));
				
				formatter.close();
			});
			//@formatter:on
			next = m.start();
		}

		return buffer;
	}

}
