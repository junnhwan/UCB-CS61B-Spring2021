package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Performs some basic ArrayDeque tests (following CS61B style).
 */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that deque is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

        // 额外测试addLast+removeLast
        ad1.addLast(20);
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());
        ad1.removeLast();
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<String>();
        ArrayDeque<Double> ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();

        // 额外验证取值正确性
        assertEquals("string", s);
        assertEquals(3.14159, d, 0.00001);
        assertTrue(b);
    }

    @Test
    /* check if null is returned when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order and values are correct. */
    public void bigArrayDequeTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // 添加100万个元素（触发多次扩容）
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        // 移除前50万，验证顺序和值
        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        // 移除后499999个，验证逆序
        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }

        // 最后剩余1个元素（500000），验证size和值
        assertEquals(1, ad1.size());
        assertEquals(500000, (int) ad1.get(0));
    }

    @Test
    /* Test get() method for correct values and index bounds. */
    public void getTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<>();
        ad1.addFirst("a");
        ad1.addLast("b");
        ad1.addLast("c");
        ad1.addFirst("d");

        // 队列顺序：d → a → b → c
        assertEquals("d", ad1.get(0));
        assertEquals("a", ad1.get(1));
        assertEquals("b", ad1.get(2));
        assertEquals("c", ad1.get(3));

        // 测试越界索引
        assertNull("get(-1) should return null", ad1.get(-1));
        assertNull("get(4) should return null", ad1.get(4));
    }
}