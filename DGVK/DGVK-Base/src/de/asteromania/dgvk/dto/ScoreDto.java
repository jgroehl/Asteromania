package de.asteromania.dgvk.dto;

import de.asteromania.dgvk.xmlBase.BaseXmlObject;

public class ScoreDto extends BaseXmlObject
{
	private static final String XML_TAG = "score";

	private long score;

	public ScoreDto(long score)
	{
		this.score = score;
	}

	public ScoreDto(String xml)
	{
		super(xml);
	}

	@Override
	public String toFullXml()
	{
		return XML_PREAMBLE + toXmlBody();
	}

	@Override
	public String toXmlBody()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<" + XML_TAG + ">");
		sb.append(score);
		sb.append("</" + XML_TAG + ">");
		return sb.toString();
	}

	@Override
	protected String getXmlTag()
	{
		return XML_TAG;
	}

	@Override
	protected void initialize(String xml)
	{
		// TODO Auto-generated method stub

	}

	public long getScore()
	{
		return score;
	}
}
