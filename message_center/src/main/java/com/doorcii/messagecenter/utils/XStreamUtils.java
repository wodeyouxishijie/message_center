package com.doorcii.messagecenter.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamUtils {
	
	public static XStream getDefaultXStream() {
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.ignoreUnknownElements();
		return xstream;
	}
	
}
