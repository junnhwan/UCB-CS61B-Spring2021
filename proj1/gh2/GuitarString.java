package gh2;


import deque.ArrayDeque;
import deque.Deque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayDeque<>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        // 记录缓冲区容量（清空前获取，确保填充后长度不变）
        int capacity = buffer.size();
        // 清空缓冲区
        while (!buffer.isEmpty()) {
            buffer.removeFirst();
        }
        // 填充随机噪声（-0.5到0.5之间）
        for (int i = 0; i < capacity; i++) {
            double randomValue = Math.random() - 0.5;
            buffer.addLast(randomValue);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        if (buffer.isEmpty()) {
            return; // 空缓冲区无需操作（实际场景中不会出现）
        }
        // 移除头部元素
        double oldFront = buffer.removeFirst();
        // 获取新的头部元素（原第二个元素）
        double newFront = buffer.get(0);
        // 计算新样本（衰减后的平均值）
        double newSample = (oldFront + newFront) / 2 * DECAY;
        // 加入尾部
        buffer.addLast(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // return 0;
        return buffer.get(0);
    }
}
