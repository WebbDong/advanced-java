package com.webbdong.jvm.hotloadandunload.hotload;

import com.webbdong.jvm.util.FileUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * class 文件修改观察者
 * @author Webb Dong
 * @date 2021-04-18 10:14 PM
 */
public class ClassFileUpdatedObserver extends Observable {

    private ClassFileUpdatedObserverTaskThread taskThread;

    public ClassFileUpdatedObserver(String basePath, String className) {
        taskThread = new ClassFileUpdatedObserverTaskThread(basePath, className);
    }

    /**
     * 启动观察者
     */
    public void startObserver() {
        taskThread.start();
    }

    public void stopObserver() {
        taskThread.stop = true;
    }

    /**
     * 通知改变
     */
    public void notifyChanged() {
        notifyChanged(null);
    }

    public void notifyChanged(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    /**
     * 文件修改观察者任务
     */
    private class ClassFileUpdatedObserverTaskThread extends Thread {

        private File classFile;

        private boolean stop;

        private long oldLastModifiedTime;

        public ClassFileUpdatedObserverTaskThread(String basePath, String className) {
            classFile = new File(new StringBuilder()
                    .append(basePath)
                    .append(className.replaceAll("\\.", "/"))
                    .append(".class")
                    .toString());
            oldLastModifiedTime = -1;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (!stop) {
                long lastModifiedTime = FileUtils.getLastModifiedTime(classFile);
                if (oldLastModifiedTime != lastModifiedTime && lastModifiedTime != -1) {
                    ClassFileUpdatedObserver.this.notifyChanged();
                    oldLastModifiedTime = lastModifiedTime;
                }
                TimeUnit.SECONDS.sleep(2);
            }
        }

    }

}
