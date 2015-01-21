import org.junit.Assert;
import org.junit.Test;

import de.asteromania.dgvk.dto.ScoreDto;

public class DtoXmlTests
{
	@Test
	public void testScoreDto()
	{
		ScoreDto scoreDto = new ScoreDto(17);
		ScoreDto scoreDto2 = ScoreDto.fromXml(scoreDto.toXml());
		Assert.assertEquals(scoreDto.getScore(), scoreDto2.getScore());
	}

}
