package de.asteromania.dgvk.dto;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.asteromania.dgvk.xmlBase.XmlExportable;

@Root(name = "score-list")
public class ScoreListDto implements XmlExportable
{

	@ElementList(inline = true)
	private List<ScoreDto> scores = new ArrayList<ScoreDto>();

	public ScoreListDto(Collection<? extends ScoreDto> scores)
	{
		if (scores != null)
			this.scores.addAll(scores);
	}

	@Deprecated
	ScoreListDto()
	{
		this(null);
	}

	public List<ScoreDto> getScores()
	{
		return scores;
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

	public static ScoreListDto fromXml(String xml)
	{
		Serializer persister = new Persister();
		try
		{
			return persister.read(ScoreListDto.class, xml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
