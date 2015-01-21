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

	public void testScoreDtoXmlConversion()
	{
		ScoreDto scoreDto = new ScoreDto(testScore);
		String xml = scoreDto.toXml();
		ScoreDto scoreDto2 = ScoreDto.fromXml(xml);
		assertEquals(testScore, scoreDto2.getScore());
	}

}
