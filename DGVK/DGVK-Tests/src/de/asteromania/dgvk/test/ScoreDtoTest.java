package de.asteromania.dgvk.test;

import android.test.AndroidTestCase;
import de.asteromania.dgvk.dto.ScoreDto;

public class ScoreDtoTest extends AndroidTestCase
{
	private final static long testScore = 17;

	public void testScoreDtoNormalConstructor()
	{
		ScoreDto scoreDto = new ScoreDto(testScore);
		assertEquals(testScore, scoreDto.getScore());
	}

	public void testScoreDtoFullXmlConversion()
	{
		ScoreDto scoreDto = new ScoreDto(testScore);
		String xml = scoreDto.toFullXml();
		ScoreDto scoreDto2 = new ScoreDto(xml);
		assertEquals(testScore, scoreDto2.getScore());
	}

	public void testScoreDtoBodyXmlConversion()
	{
		ScoreDto scoreDto = new ScoreDto(testScore);
		String xml = scoreDto.toXmlBody();
		ScoreDto scoreDto2 = new ScoreDto(xml);
		assertEquals(testScore, scoreDto2.getScore());
	}

}
