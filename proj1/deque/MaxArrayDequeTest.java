package deque;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {

    // 测试 Integer 自然顺序比较器
    @Test
    public void maxWithNaturalOrder() {
        Comparator<Integer> intComparator = Integer::compare;
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(intComparator);

        mad.addLast(3);
        mad.addFirst(1);
        mad.addLast(5);
        mad.addFirst(2);

        assertEquals("最大值应为 5", 5, (int) mad.max());
        assertEquals("自定义比较器（逆序）最大值应为 1", 1, (int) mad.max((a, b) -> b.compareTo(a)));
    }

    // 测试 String 长度比较器
    @Test
    public void maxWithStringLengthComparator() {
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(lengthComparator);

        mad.addFirst("a");
        mad.addLast("bb");
        mad.addFirst("ccc");
        mad.addLast("dddd");

        assertEquals("最长字符串应为 dddd", "dddd", mad.max());
        assertEquals("自定义比较器（长度逆序）最长应为 a", "a", mad.max((s1, s2) -> Integer.compare(s2.length(), s1.length())));
    }

    // 测试空队列
    @Test
    public void maxOnEmptyDeque() {
        Comparator<Double> doubleComparator = Double::compare;
        MaxArrayDeque<Double> mad = new MaxArrayDeque<>(doubleComparator);

        assertNull("空队列应返回 null", mad.max());
        assertNull("空队列自定义比较器也返回 null", mad.max(doubleComparator));
    }

    // 测试多个相同最大值
    @Test
    public void maxWithDuplicateMaxElements() {
        Comparator<Integer> intComparator = Integer::compare;
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(intComparator);

        mad.addLast(5);
        mad.addFirst(3);
        mad.addLast(5);
        mad.addFirst(5);

        int maxVal = mad.max();
        assertTrue("最大值应为 5", maxVal == 5);
    }
}