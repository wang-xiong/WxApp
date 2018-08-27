package com.example.rxjava_library.test;

/**
 * Created by wangxiong on 2018/8/9.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava异步框架
 */
public class RxJavaInroduce {
    private Context mContext;

    private void test() {
        final List<File> folders = new ArrayList<>();
        Activity mActivity = null;
        //传统的异步线程处理方法
        final Activity finalMActivity = mActivity;
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            //final Bitmap bitmap = getBitmapFromFile(file);
                            finalMActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //imageCollectorView.addImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();

        //RxJava的写法
        //遍历集合
        Observable.fromIterable(folders)
                //flatMap转换多个Observable，无序
                //concatMap转换多个Observable，有序
                .flatMap(new Function<File, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        //遍历数组
                        return Observable.fromArray(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        return file.getName().endsWith(".png");
                    }
                })
                //转换输入输出
                .map(new Function<File, Bitmap>() {
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {
                        //imageCollectorView.addImage(bitmap);
                    }
                });
    }

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observable observable = Observable.create(new ObservableOnSubscribe() {
        @Override
        public void subscribe(ObservableEmitter emitter) throws Exception {
            emitter.onNext("A");
            emitter.onNext("B");
            emitter.onNext("C");
            emitter.onComplete();
        }
    });


    Observable observable1 = Observable.just("A", "B", "C");

    String[] test = new String[]{"A", "B", "C"};

    Observable observable2 = Observable.fromArray(test);

    Disposable disposable;
    private void subscribe(Consumer<String> wx) {
        observable.subscribe(observer);
        disposable =observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }

    private void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 自动创建observer
     */


    // 自动创建 Subscriber
    private void test1() {
        observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    private void doTest() {
        String[] names = {};
        Observable.fromArray(names)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String name) {
                        Log.d("wx", "name:" + name);
                    }
                });

        final Context context = null;
        final int drawableRes = 0;

        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> emitter) throws Exception {
                Drawable drawable = context.getResources().getDrawable(drawableRes);
                emitter.onNext(drawable);

            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Drawable drawable) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    private void doTest2() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {

                    }
                });
    }

    private void doTest4() {
        List<Student> students = new ArrayList<>();

        Observable.fromIterable(students)
                .map(new Function<Student, String>() {

                    @Override
                    public String apply(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });

        Observable.fromIterable(students)
                .map(new Function<Student, List<Course>>() {
                    @Override
                    public List<Course> apply(Student student) {
                        return student.courseList;
                    }
                })
                .subscribe(new Consumer<List<Course>>() {
                    @Override
                    public void accept(List<Course> courses) {
                        for (Course course : courses) {
                            Log.d("wx", "name:" + course.getName());
                        }
                    }
                });

        Observable.fromIterable(students)
                .flatMap(new Function<Student, ObservableSource<Course>>() {
                    @Override
                    public ObservableSource<Course> apply(Student student) throws Exception {
                        return null;
                    }
                })
                //.throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Course>() {
                    @Override
                    public void accept(Course course) {
                        Log.d("wx", "name:" + course.getName());
                    }
                });

        Observable.fromIterable(students)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) {

                    }
                });

    }

    class Student {
        String name;
        List<Course> courseList;

        public String getName() {
            return name;
        }

        public List<Course> getCourseList() {
            return courseList;
        }
    }

    class Course {
        String name;

        public String getName() {
            return name;
        }
    }
}


/**
 * RxJava的异步实现是采用扩展的观察者模式
 * 观察者模式：
 * 1.观察者Observer
 * 2.被观察者Observable
 * 3.采用注册register或者订阅Subscribe的方式连接
 * <p>
 * RxJava回掉方法：
 * 1、普通事件onNext
 * 2、OnComplete
 * 3、OnError
 * <p>
 * 二、基本实现
 * 1、创建Observer
 * 2、创建Observable
 * 3、Subscribe
 * <p>
 * 三、线程控制Scheduler
 * 1、SchedulerApi
 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 * Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行
 * <p>
 * subscribeOn和observerOn:
 * subscribeOn指的subscribe发生的线程，即Observable.OnSubscribe被激活所处的线程
 * observerOn指定Subscriber所发生的线程
 * <p>
 * <p>
 * 四、变化
 */



