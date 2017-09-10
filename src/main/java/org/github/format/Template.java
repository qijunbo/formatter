package org.github.format;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface Template {

	Template load(File f) throws FileNotFoundException;

	Template load(File f, String charset) throws FileNotFoundException, UnsupportedEncodingException;

	Template load(InputStream is);

	Template load(InputStream is, String charset) throws UnsupportedEncodingException;

	StringBuilder format(Map<String, ?> values);

	public static Template loadFrom(File f) throws FileNotFoundException {
		Template t = new SimpleTemplate();
		return t.load(f);
	}

	public static Template loadFrom(InputStream is) {
		Template t = new SimpleTemplate();
		return t.load(is);
	}

	public static Template loadFrom(File f, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		Template t = new SimpleTemplate();
		return t.load(f, charset);
	}

	public static Template loadFrom(InputStream is, String charset) throws UnsupportedEncodingException {
		Template t = new SimpleTemplate();
		return t.load(is, charset);
	}

	

}
