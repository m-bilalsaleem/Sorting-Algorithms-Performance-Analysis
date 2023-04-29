public abstract class AbstractSort {
    private long count;
    private long startTime;
    private long elapsedTime;

    public abstract void sort(int[] array);

    protected void startSort() {
        count = 0;
        startTime = System.nanoTime();
    }

    protected void endSort() {
        elapsedTime = System.nanoTime() - startTime;
    }

    protected void incrementCount() {
        count++;
    }

    public long getCount() {
        return count;
    }

    public long getTime() {
        return elapsedTime;
    }
}
