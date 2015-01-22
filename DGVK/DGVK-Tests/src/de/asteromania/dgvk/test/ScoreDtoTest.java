package de.asteromania.dgvk.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import de.asteromania.dgvk.dto.ScoreDto;
import de.asteromania.dgvk.dto.ScoreListDto;

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

	public void testScoreListDtoXmlConversion()
	{
		List<ScoreDto> scores = new ArrayList<ScoreDto>();
		scores.add(new ScoreDto(testScore));
		ScoreListDto scoreListDto = new ScoreListDto(scores);
		assertEquals(testScore, scoreListDto.getScores().get(0).getScore());
		ScoreListDto scoreListDto2 = ScoreListDto.fromXml(scoreListDto.toXml());
		assertEquals(testScore, scoreListDto2.getScores().get(0).getScore());
	}

}
