package de.asteromania.dgvk.dto;

import java.io.StringWriter;

import org.simpleframework.xml.core.Persister;

public abstract class BaseDto<E>
{

	@SuppressWarnings("unchecked")
	public E fromXml(String xml) throws Exception
	{
		return new Persister().read((E) this, xml);
	}

	@SuppressWarnings("unchecked")
	public String toXml() throws Exception
	{
		StringWriter sw = new StringWriter();
		new Persister().write((E) this, sw);
		return sw.toString();
	}

}
