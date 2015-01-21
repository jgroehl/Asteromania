package de.asteromania.dgvk.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;
import de.asteromania.dgvk.xmlBase.BaseXmlObject;

public class ScoreDto extends BaseXmlObject
{
	private static final String XML_TAG = "score";

	private static final String TAG = ScoreDto.class.getSimpleName();

	private static final long DEFAULT_SCORE = 0;

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
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new ByteArrayInputStream(xml.getBytes()), null);
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, XML_TAG);
					if (parser.next() == XmlPullParser.TEXT)
					{
						score = Long.parseLong(parser.getText());
					}
					else 
					{
						score = DEFAULT_SCORE;
					}
		}
		catch (XmlPullParserException e)
		{
			Log.w(TAG, "Could not parse xml to create ScoreDto.");
			setDefault();
		}
		catch (IOException e)
		{
			Log.w(TAG, "Could not parse xml to create ScoreDto.");
			setDefault();
		}
	}

	private void setDefault()
	{
		score = DEFAULT_SCORE;
	}

	public long getScore()
	{
		return score;
	}
}
