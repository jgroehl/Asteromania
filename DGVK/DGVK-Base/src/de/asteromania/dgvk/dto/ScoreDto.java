package de.asteromania.dgvk.dto;

import java.io.ByteArrayOutputStream;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.asteromania.dgvk.xmlBase.XmlExportable;

@Root(name = "score")
public class ScoreDto implements XmlExportable
{
	@Element
	private long score;

	public ScoreDto(long score)
	{
		this.score = score;
	}

	@Deprecated
	ScoreDto()
	{
		// Needed for SimpleXml-Framework
		this(0);
	}

	public static ScoreDto fromXml(String xml)
	{
		Serializer persister = new Persister();
		try
		{
			return persister.read(ScoreDto.class, xml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toXml()
	{
		Serializer persister = new Persister();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			persister.write(this, baos);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return baos.toString();

	}

	public long getScore()
	{
		return score;
	}
}
