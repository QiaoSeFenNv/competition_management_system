package com.qiaose.competitionmanagementsystem.utils;

import java.util.BitSet;
import java.util.concurrent.locks.ReentrantLock;

public class IDFactory {

    /**
     * 使用Bitset标记所有已使用的id<br>
     * 不限定大小，以至于能达到 {@link Integer#MAX_VALUE}
     */
    private final BitSet idList;

    /**
     * 同步锁
     */
    private final ReentrantLock lock;

    /**
     * 标记下一个int的索引
     */
    private volatile int nextIndex = 0;

    private IDFactory() {
        idList = new BitSet();
        lock = new ReentrantLock();
        lockIds(0);

    }

    private static class SingletonHolder {
        protected static final IDFactory INSTANCE = new IDFactory();
    }

    public static final IDFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 下一个可用的int值
     *
     * @return
     */
    public int nextId() {
        try {
            lock.lock();

            int id;
            if (nextIndex == Integer.MIN_VALUE) {
                id = Integer.MIN_VALUE;
            } else {
                id = idList.nextClearBit(nextIndex);
            }

            idList.set(id);

            // It ok to have Integer OverFlow here, on next ID request IDFactory
            // will throw error
            nextIndex = id + 1;
            return id;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 告诉生成器已使用了那些int值
     *
     * @param ids
     *            ids to lock
     */
    private void lockIds(int... ids) {
        try {
            lock.lock();
            for (int id : ids) {
                boolean status = idList.get(id);
                idList.set(id);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 告诉生成器已使用了那些int值
     *
     * @param ids
     *            ids to lock
     */
    public void lockIds(Iterable<Integer> ids) {
        try {
            lock.lock();
            for (int id : ids) {
                boolean status = idList.get(id);
                idList.set(id);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 告诉生成器某int值可以重新使用
     *
     * @param id
     *            id to release
     */
    public void releaseId(int id) {
        try {
            lock.lock();
            boolean status = idList.get(id);
            idList.clear(id);
            if (id < nextIndex || nextIndex == Integer.MIN_VALUE) {
                nextIndex = id;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 已使用的int数
     *
     * @return 已使用的int数
     */
    public int getUsedCount() {
        try {
            lock.lock();
            return idList.cardinality();
        } finally {
            lock.unlock();
        }
    }

}