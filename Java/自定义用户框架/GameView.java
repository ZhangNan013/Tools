package com.simple.zhangn.coins;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


//1、游戏的四层:游戏的初始化层init、游戏的表现层（绘制层、渲染层）、游戏的控制层
//游戏的控制层(onTouchEvent)、游戏的逻辑层logic（更新层update）
//2、线程
//3.获取画布

public class GameView extends SurfaceView implements Runnable, Callback {
    private Thread thread;
    private boolean flag;//控制线程
    private Canvas canvas;//画布
    private SurfaceHolder holder;//表面持有者

    public GameView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        init();
    }

    //启动线程
    private void start() {
        thread = new Thread(this);
        flag = true;
        thread.start();
    }

    //停止线程
    private void stop() {
        flag = false;
    }

    //游戏的初始化层
    private void init() {
        
    }

    //游戏的表现层
    private void paint(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    //游戏的控制层
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    //游戏的逻辑层
    private void logic() {
        
    }

    //运行线程（线程的工作）
    @Override
    public void run() {
        while (flag) {
            long t1, t2;
            t1 = System.currentTimeMillis();
            logic();
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();//锁定画布
                paint(canvas);
                holder.unlockCanvasAndPost(canvas);//解锁画布
            }
            t2 = System.currentTimeMillis();
            //Log.v("t2-t1:",""+(t2-t1));
            if (t2 - t1 < 100) {
                try {
                    Thread.sleep(100 - (t2 - t1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stop();
    }
}

