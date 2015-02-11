package com.doorcii.messagecenter.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XStreamUtils {
	
	public static XStream getDefaultXStream() {
		XStream xstream = new XStream(new StaxDriver(new NoNameCoder()));
		xstream.autodetectAnnotations(true);
		xstream.ignoreUnknownElements();
		return xstream;
	}
	
}
