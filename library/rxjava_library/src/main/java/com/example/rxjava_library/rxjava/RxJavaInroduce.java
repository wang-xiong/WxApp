package com.example.rxjava_library.rxjava;

/**
 * Created by wangxiong on 2018/8/9.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava异步框架
 * RxJava的异步实现是采用扩展的观察者模式
 * 观察者模式：
 * 1.观察者Observer
 * 2.被观察者Observable
 * 3.采用注册register或者订阅Subscribe的方式连接
 * RxJava回掉方法：
 * 1、普通事件onNext
 * 2、OnComplete
 * 3、OnError
 * 二、基本实现
 * 1、创建Observer
 * 2、创建Observable
 * 3、Subscribe
 * 三、线程控制Scheduler
 * 1、SchedulerApi
 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 * Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行
 * subscribeOn和observerOn:
 * subscribeOn指的subscribe发生的线程，即Observable.OnSubscribe被激活所处的线程
 * observerOn指定Subscriber所发生的线程
 */


public class RxJavaInroduce {

    private void rxJava1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);

                //无法接收事件，但是会继续发送
                emitter.onComplete();
                emitter.onNext(4);

            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                if (integer == 2) {
                    //调用此方法会切断事件，Observer不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJava2() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            //通过map将一个Observable转换为另一种Observable
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "map";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                String result = s;
            }
        });
    }

    private void rxJava3() {
        //zip用于事件合并，该合并是两两配对，最终输出结果是A1，B2,C3,接收的事件数和最少的相同
        Disposable disposable = Observable.zip(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
            }
        }), Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        }), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    private void rxJava4() {
        //Concat用于事件连接，接受事件的输出结果：1，2，3，4，5，6
        Disposable disposable = Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava5() {
        //FlatMap,把一个Observable转换成多个Observables发送事件，无序的
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("add value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MICROSECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    private void rxJava6() {
        //concatMap,把一个Observable转换成多个Observables发送事件，有序的
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("add value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MICROSECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    private void rxJava7() {
        //distinct 去掉重复事件,例如：接收结果为1，2，3，4，5
        Disposable disposable = Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava8() {
        //filter过滤功能，过滤掉不满足条件的事件
        Disposable disposable = Observable.just(1, 20, 40, 8, 11)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava9() {
        //buffer 按照步长skip分成最大不超过count的buffer，生成一个Observable,如下输出1，2，3 3，4，5 5
        Disposable disposable = Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {

                    }
                });
    }

    private void rxJava10() {
        //timer默认在新的线程
        Disposable disposable = Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //切回到主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
        //退出时销毁
        disposable.dispose();
    }

    private void rxJava11() {
        //interval默认在新的线程
        Disposable disposable = Observable.interval(3,2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //切回到主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
        //退出时销毁
        disposable.dispose();
    }

    private void rxJava12() {
        //doOnNext在接收数据前,操作数据
        Disposable disposable = Observable.just(1,2,3,4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava13() {
        //just 就是发生器依次调用onNext方法
        Disposable disposable = Observable.just(1,2,3,4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava14() {
        //skip 跳过count个事件开始接收
        Disposable disposable = Observable.just(1,2,3,4)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void rxJava15() {
        //take 表示最多接受count个事件
        Disposable disposable = Observable.just(1,2,3,4)
                .take(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }
}