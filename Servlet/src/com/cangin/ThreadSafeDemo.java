package com.cangin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Project: MyCode
 * @Author: ChagnXing 邮箱:515688558@qq.com
 * @Date: 2018/11/14 18:38
 * @Description: unsafe/safe: 9895/10000
 * unsafe/safe: 9945/10000
 * unsafe/safe: 9937/10000
 * unsafe/safe: 9925/10000
 * unsafe/safe: 9949/10000
 * unsafe/safe: 9940/10000
 * unsafe/safe: 9943/10000
 * unsafe/safe: 9961/10000
 * unsafe/safe: 9948/10000
 * unsafe/safe: 9918/10000
 * 之所以会造成不安全线程list数不足10000，主要有两点：
 * <p>
 * 首先我们来看ArrayList的源码：
 * <p>
 * <p>
 * <p>
 * public boolean add(E e) {
 * ensureCapacity(size + 1); // Increments modCount!!
 * elementData[size++] = e;
 * return true;
 * }
 * <p>
 * <p>
 * <p>
 * 主要问题出在size++这块，因为该方法不是线程安全的，所以就有可能出现以下两种情况：
 * <p>
 * 1）线程A和线程B同样取得size=10，然后在相同位置10插入了两遍值，然后在都执行size++，结果size变成了12，这样下次再有线程进来时会在12的位置继续插入值，而11则变成了null，实验下果然如此，但是其实这并不能解释为什么list1.size的值会减少，只能解释为什么list中有的值为null
 * <p>
 * 2) size++这个操作同样不是线程安全的，它分成两个步骤，第一，取size的位置，第二，size位置+1，这样就有可能A线程和B线程同时取到size的位置，然后+1，这样A，B线程执行完size++后,size的值为11而不是12，所以就会有不同的线程同时在一个位置赋值，导致list的数量不足。
 */
public class ThreadSafeDemo {

    public static int demo(final List list, final int testCount) throws InterruptedException {
        ThreadGroup group = new ThreadGroup(list.getClass().getName() + "@" + list.hashCode());
        final Random rand = new Random();

        Runnable listAppender = new Runnable() {  // 这里面实现了Runnable接口类，覆盖了它的run方法
            @Override
            public void run() {
                try {
                    Thread.sleep(rand.nextInt(2));
                } catch (InterruptedException e) {
                    return;
                }
                list.add("0");
            }
        };

        for (int i = 0; i < testCount; i++) {
            new Thread(group, listAppender, "InsertList-" + i).start();
            //java.lang.<a class="header">Thread</a>
//.Thread(<a class="header">ThreadGroup</a>
//                    group, <a class="header">Runnable</a>
//                    target, <a class="header">String</a>
//                    name)

        }

        while (group.activeCount() > 0) {
            Thread.sleep(10);
        }

        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {
        List unsafeList = new ArrayList();
        List safeList = Collections.synchronizedList(new ArrayList()); // 也可以换成new CopyToWriteArrayList
        final int N = 10000;
        for (int i = 0; i < 10; i++) {
            unsafeList.clear();
            safeList.clear();
            int unsafeSize = demo(unsafeList, N);
            int safeSize = demo(safeList, N);
            System.out.println("unsafe/safe: " + unsafeSize + "/" + safeSize);
        }
    }
}
