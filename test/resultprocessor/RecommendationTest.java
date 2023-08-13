package resultprocessor;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RecommendationTest
{
    FileOperationsFileChooser chooser = new StubFileChooser();

    @Test
    public void shouldRecommend()
    {
        chooser.onFileChosen("/mnt/sdc1/Old/bandanna2k (0)/zoe.north.2013/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg", Collections.emptyList());

        Optional<String> recommendation = chooser.recommendation(List.of(
                "/mnt/sdc1/Old/16GB Micro SD card old/My Google Drive/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/16GB Micro SD card old/My Google Drive/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k/zoe.north.2013/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k/zoe.north.2013/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k (0)/zoe.north.2013/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k (0)/zoe.north.2013/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/Google Drive (15th Jan)/Media/Zoe/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/Google Drive (15th Jan)/Media/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/My Google Drive/Media/Zoe/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/My Google Drive/Media/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/TBD/Photos and Videos VAIO/Zoe/2013/Jan Feb/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/TBD/Photos and Videos VAIO/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg"
        ));

        Assert.assertTrue(recommendation.isPresent());
    }

    @Test
    @Ignore
    public void shouldNotRecommend()
    {
        chooser.onFileChosen("/mnt/sdc1/Old/bandanna2k/zoe.north.2013/2015/January/IMG_8633.jpg", Collections.emptyList());

        Optional<String> recommendation = chooser.recommendation(List.of(
                "/mnt/sdc1/Old/16GB Micro SD card old/My Google Drive/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/16GB Micro SD card old/My Google Drive/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k/zoe.north.2013/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k/zoe.north.2013/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k (0)/zoe.north.2013/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/bandanna2k (0)/zoe.north.2013/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/Google Drive (15th Jan)/Media/Zoe/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/Google Drive (15th Jan)/Media/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/My Google Drive/Media/Zoe/2013/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/My Google Drive/Media/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/TBD/Photos and Videos VAIO/Zoe/2013/Jan Feb/Before Hospital/OBSTETRIC ULTRASOUND 0024.jpg",
                "/mnt/sdc1/Old/TBD/Photos and Videos VAIO/Zoe/2013/Scans/2012 Aug 1st/OBSTETRIC ULTRASOUND 0024.jpg"
        ));

        Assert.assertFalse(recommendation.isPresent());
    }

    private static class StubFileChooser extends FileOperationsFileChooser
    {
        @Override
        void writeMovesToDisk(String chosenFile, List<String> otherFiles) {
        }
    }
}
