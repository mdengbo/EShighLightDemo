package kumo;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import org.dom4j.DocumentException;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.apache.tomcat.util.file.ConfigFileLoader.getInputStream;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-23
 */
public class kumo {

    public static void main(String[] args) throws IOException, DocumentException {
        //创建一个词语解析器,类似于分词
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        //这边要注意,引用了中文的解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

        //拿到文档里面分出的词,和词频,建立一个集合存储起来
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("C:/Users//ecarx/gitee/some_source_code/thread//src/main/resources/vina.txt"));

        for (int i = 0; i < 10 ; i++) {
            System.out.println(wordFrequencies.get(i).getFrequency());
        }
        Dimension dimension = new Dimension(600, 600);

        //设置图片相关的属性,这边是大小和形状,更多的形状属性,可以从CollisionMode源码里面查找
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);

        //这边要注意意思,是设置中文字体的,如果不设置,得到的将会是乱码,
        //这是官方给出的代码没有写的,我这边拓展写一下,字体,大小可以设置
        //具体可以参照Font源码
        Font font = new Font("STSong-Light", 2, 16);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
        //因为我这边是生成一个圆形,这边设置圆的半径
        wordCloud.setBackground(new CircleBackground(255));
        //设置颜色
        //wordCloud.setColorPalette(buildRandomColorPalette(1));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        //将文字写入图片
        wordCloud.build(wordFrequencies);
        //生成图片
        wordCloud.writeToFile("C:/Users//ecarx/gitee/some_source_code/thread/src/main/resources/chinese_language_circle.png");
    }

}
