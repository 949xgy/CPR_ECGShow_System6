package com.example.tjw.cpr_ecgshow_system.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJW on 2019/3/20.
 */

public class DrawECGView {
    private SurfaceView sfv;
    private SurfaceHolder sfh;
    private int sfHeight;
    private int sfWidth;
    private Paint penOfGrid;//画网格的画笔
    private Paint penOfData;//画心电图波形的画笔
    private Paint penOfText;
    private Bitmap bitmap;
    private int scaleX = 1, scaleY = 100;

    public DrawECGView(SurfaceView msfv) {
        this.sfv = msfv;
    }

    public void InitCanvas() {
        sfh = sfv.getHolder();//得到Sacefure对象实例
        sfHeight = sfv.getHeight();
        sfWidth = sfv.getWidth();

        penOfGrid = new Paint();//创建背景网格画笔并设置画笔的一些属性
        penOfGrid.setColor(Color.BLUE); //设置画笔
        penOfGrid.setStrokeWidth(1);   //线宽
        penOfGrid.setAntiAlias(true);  //抗锯齿
        penOfGrid.setTextSize(22);     //文字大小，和线宽无关

        penOfData = new Paint();//创建心电图数据画笔并设置画笔的一些属性
        penOfData.setColor(Color.GREEN);
        penOfData.setStrokeWidth(2);
        penOfData.setAntiAlias(true);
        penOfData.setTextSize(60);

        penOfText = new Paint();
        penOfText.setColor(Color.RED);
        penOfText.setAntiAlias(true);
        penOfText.setTextSize(30);
    }

    public void CleanCanvas(Canvas mCanvas) {
        mCanvas.drawColor(Color.BLACK);//用黑色背景覆盖
        /**
         * 绘制网格
         */
        for (int i = 0; i < sfWidth / 30; i++) {
            mCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //纵
        }
        for (int i = 0; i < sfHeight / 30; i++) {
            mCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid); //横
        }
    }

    //画单导联心电图显示心率
    public void DrawOnetoView(List<Float> ECGCacheData, int value, List<String> leadList) {
        InitCanvas();
        Canvas canvas = sfh.lockCanvas(new Rect(0, 0, sfWidth, sfHeight)); // 锁定画布，开始绘图

        //bitmap：用于双缓冲画图，解决刷新闪烁问题
        if (bitmap == null) {//bitmap == null：第一次画图，bitmap没有初始化，就需要初始化，就是下面这一行
            bitmap = Bitmap.createBitmap(sfWidth, sfHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);//在bitmap中再新建一个画布Cancas，bitmap相当于SurfaceView了
            tempCanvas.drawColor(Color.BLACK);
            Log.d("Canvas大小", "该surfaceView的高度为：" + sfHeight + "宽度为：" + sfWidth);
            /**
             * 绘制网格
             */
            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }

            float cx, cy, dx, dy; //画线需要的起点坐标和终点坐标
            for (int i = 0, j = 0; i < ECGCacheData.size() - 1; i++) {//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i) * scaleY + sfHeight / 2;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i + 1) * scaleY + sfHeight / 2;
                //在起点和重点之间画一条线段
                if (dx >= sfWidth) {
                    j = 0;
                    cx = 0;
                    dx = (j + 1) * scaleX;
                    // System.out.println("清屏");
                    try {
                        if (tempCanvas != null) {
                            this.CleanCanvas(tempCanvas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                tempCanvas.drawLine(cx, cy, dx, dy, penOfData);
                j++;
            }
            tempCanvas.drawText(leadList.get(0) + ":", 20, sfHeight / 2, penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap, 0, 0, penOfData);
        } else { //如果不是第一次绘制，就没有必要重新初始化bitmap，节约资源，其他操作完全一样
            Canvas tempCanvas = new Canvas(bitmap);
            tempCanvas.drawColor(Color.BLACK); //Draw background color to BLACK

            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }
            float cx, cy, dx, dy;
            for (int i = 0, j = 0; i < ECGCacheData.size() - 1; i++) {//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i) * scaleY + sfHeight / 2;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i + 1) * scaleY + sfHeight / 2;
                if (dx >= sfWidth) {
                    j = 0;
                    cx = 0;
                    dx = (j + 1) * scaleX;
                    // System.out.println("清屏");
                    try {
                        if (tempCanvas != null) {
                            this.CleanCanvas(tempCanvas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //在起点和重点之间画一条线段
                tempCanvas.drawLine(cx, cy, dx, dy, penOfData);
                j++;
            }
            tempCanvas.drawText("心率：" + value, 20, 100, penOfData);
            tempCanvas.drawText(leadList.get(0) + ":", 20, sfHeight / 2, penOfText);
            canvas.drawBitmap(bitmap, 0, 0, penOfData);
        }
        //将双缓冲的图复制到SurfaceView上显示
        sfh.unlockCanvasAndPost(canvas);
    }

    //画单导联心电图不显示心率
    public void DrawOnetoView(List<Float> ECGCacheData, List<String> leadList) {
        InitCanvas();
        Canvas canvas = sfh.lockCanvas(new Rect(0, 0, sfWidth, sfHeight)); // 锁定画布，开始绘图

        //bitmap：用于双缓冲画图，解决刷新闪烁问题
        if (bitmap == null) {//bitmap == null：第一次画图，bitmap没有初始化，就需要初始化，就是下面这一行
            bitmap = Bitmap.createBitmap(sfWidth, sfHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);//在bitmap中再新建一个画布Cancas，bitmap相当于SurfaceView了
            tempCanvas.drawColor(Color.BLACK);
            Log.d("Canvas大小", "该surfaceView的高度为：" + sfHeight + "宽度为：" + sfWidth);
            /**
             * 绘制网格
             */
            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }

            float cx, cy, dx, dy; //画线需要的起点坐标和终点坐标
            for (int i = 0, j = 0; i < ECGCacheData.size() - 1; i++) {//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i) * scaleY + sfHeight / 2;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i + 1) * scaleY + sfHeight / 2;
                //在起点和重点之间画一条线段
                if (dx >= sfWidth) {
                    j = 0;
                    cx = 0;
                    dx = (j + 1) * scaleX;
                    // System.out.println("清屏");
                    try {
                        if (tempCanvas != null) {
                            this.CleanCanvas(tempCanvas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                tempCanvas.drawLine(cx, cy, dx, dy, penOfData);
                j++;
            }
            tempCanvas.drawText(leadList.get(0) + ":", 20, sfHeight / 2, penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap, 0, 0, penOfData);
        } else { //如果不是第一次绘制，就没有必要重新初始化bitmap，节约资源，其他操作完全一样
            Canvas tempCanvas = new Canvas(bitmap);
            tempCanvas.drawColor(Color.BLACK); //Draw background color to BLACK

            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }
            float cx, cy, dx, dy;
            for (int i = 0, j = 0; i < ECGCacheData.size() - 1; i++) {//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i) * scaleY + sfHeight / 2;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i + 1) * scaleY + sfHeight / 2;
                if (dx >= sfWidth) {
                    j = 0;
                    cx = 0;
                    dx = (j + 1) * scaleX;
                    // System.out.println("清屏");
                    try {
                        if (tempCanvas != null) {
                            this.CleanCanvas(tempCanvas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //在起点和重点之间画一条线段
                tempCanvas.drawLine(cx, cy, dx, dy, penOfData);
                j++;
            }
            tempCanvas.drawText(leadList.get(0) + ":", 20, sfHeight / 2, penOfText);
            canvas.drawBitmap(bitmap, 0, 0, penOfData);
        }
        //将双缓冲的图复制到SurfaceView上显示
        sfh.unlockCanvasAndPost(canvas);
    }

    //画双导联心电图并显示心率
    public void DrawTwotoView(List<Float> ECGCacheData, List<Float> ECGCacheData1, List<String> leadList, int value) {
        InitCanvas();
        Canvas canvas = sfh.lockCanvas(new Rect(0,0,sfWidth,sfHeight)); // 锁定画布，开始绘图

        //bitmap：用于双缓冲画图，解决刷新闪烁问题
        if(bitmap == null){//bitmap == null：第一次画图，bitmap没有初始化，就需要初始化，就是下面这一行
            bitmap = Bitmap.createBitmap(sfWidth,sfHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);//在bitmap中再新建一个画布Cancas，bitmap相当于SurfaceView了
            tempCanvas.drawColor(Color.BLACK);
            Log.d("Canvas大小","该surfaceView的高度为：" + sfHeight + "宽度为：" + sfWidth);
            /**
             * 绘制网格
             */
            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }

            float cx,cy,dx,dy; //画线需要的起点坐标和终点坐标
            float cx1,cy1,dx1,dy1;//第二导联的起点和终点坐标
            for (int i = 0,j=0;i<ECGCacheData.size()-2;i++){//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i)*scaleY + sfHeight/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i+1)*scaleY + sfHeight/3;

                //起点：
                cx1 = j * scaleX;
                cy1 = -ECGCacheData1.get(i)*scaleY + sfHeight*2/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx1 = (j+ 1) * scaleX;
                dy1 = -ECGCacheData1.get(i+1)*scaleY +sfHeight*2/3;


                //在起点和重点之间画一条线段
                if(dx>=sfWidth){
                    j=0;
                    cx=0;
                    cx1=0;
                    dx=(j+1)*scaleX;
                    dx1=(j+1)*scaleX;
                    // System.out.println("清屏");
                    try {
                        if(tempCanvas!=null){
                            this.CleanCanvas(tempCanvas);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                tempCanvas.drawLine(cx,cy,dx,dy,penOfData);
                tempCanvas.drawLine(cx1,cy1,dx1,dy1,penOfData);
                j++;
            }
            tempCanvas.drawText("心率："+value,20,100,penOfData);
            tempCanvas.drawText(""+leadList.get(0),20,sfHeight/3,penOfText);
            tempCanvas.drawText(""+leadList.get(1),20,sfHeight*2/3,penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap,0,0,penOfData);
        }else { //如果不是第一次绘制，就没有必要重新初始化bitmap，节约资源，其他操作完全一样
            Canvas tempCanvas = new Canvas(bitmap);
            tempCanvas.drawColor(Color.BLACK); //Draw background color to BLACK

            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }
            float cx,cy,dx,dy; //画线需要的起点坐标和终点坐标
            float cx1,cy1,dx1,dy1;//第二导联的起点和终点坐标
            for (int i = 0,j=0;i<ECGCacheData.size()-2;i++){//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i)*scaleY + sfHeight/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i+1)*scaleY + sfHeight/3;

                //起点：
                cx1 = j * scaleX;
                cy1 = -ECGCacheData1.get(i)*scaleY + sfHeight*2/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx1 = (j+ 1) * scaleX;
                dy1 = -ECGCacheData1.get(i+1)*scaleY +sfHeight*2/3;



                //在起点和重点之间画一条线段
                if(dx>=sfWidth){
                    j=0;
                    cx=0;
                    cx1=0;
                    dx=(j+1)*scaleX;
                    dx1=(j+1)*scaleX;
                    // System.out.println("清屏");
                    try {
                        if(tempCanvas!=null){
                            this.CleanCanvas(tempCanvas);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                tempCanvas.drawLine(cx,cy,dx,dy,penOfData);
                tempCanvas.drawLine(cx1,cy1,dx1,dy1,penOfData);
                j++;
            }
            tempCanvas.drawText("心率："+value,20,100,penOfData);
            tempCanvas.drawText(""+leadList.get(1),20,sfHeight/3,penOfText);
            tempCanvas.drawText(""+leadList.get(0),20,sfHeight*2/3,penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap,0,0,penOfData);
        }
        //将双缓冲的图复制到SurfaceView上显示
        sfh.unlockCanvasAndPost(canvas);
    }

    //画双导联心电图不显示心率
    //此方法是
    public void DrawTwotoView(List<Float> ECGCacheData, List<Float> ECGCacheData1, List<String> leadList) {
        InitCanvas();
        Canvas canvas = sfh.lockCanvas(new Rect(0,0,sfWidth,sfHeight)); // 锁定画布，开始绘图

        //bitmap：用于双缓冲画图，解决刷新闪烁问题
        if(bitmap == null){//bitmap == null：第一次画图，bitmap没有初始化，就需要初始化，就是下面这一行
            bitmap = Bitmap.createBitmap(sfWidth,sfHeight, Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);//在bitmap中再新建一个画布Cancas，bitmap相当于SurfaceView了
            tempCanvas.drawColor(Color.BLACK);
            Log.d("Canvas大小","该surfaceView的高度为：" + sfHeight + "宽度为：" + sfWidth);
            /**
             * 绘制网格
             */
            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }

            float cx,cy,dx,dy; //画线需要的起点坐标和终点坐标
            float cx1,cy1,dx1,dy1;//第二导联的起点和终点坐标
            for (int i = 0,j=0;i<ECGCacheData.size()-2;i++){//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i)*scaleY + sfHeight/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i+1)*scaleY + sfHeight/3;

                //起点：
                cx1 = j * scaleX;
                cy1 = -ECGCacheData1.get(i)*scaleY + sfHeight*2/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx1 = (j+ 1) * scaleX;
                dy1 = -ECGCacheData1.get(i+1)*scaleY +sfHeight*2/3;



                //在起点和重点之间画一条线段
                if(dx>=sfWidth){
                    j=0;
                    cx=0;
                    cx1=0;
                    dx=(j+1)*scaleX;
                    dx1=(j+1)*scaleX;
                    // System.out.println("清屏");
                    try {
                        if(tempCanvas!=null){
                            this.CleanCanvas(tempCanvas);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                tempCanvas.drawLine(cx,cy,dx,dy,penOfData);
                tempCanvas.drawLine(cx1,cy1,dx1,dy1,penOfData);
                j++;
            }
            //   tempCanvas.drawText("心率："+value,20,100,penOfData);
            tempCanvas.drawText(""+leadList.get(0),20,sfHeight/3,penOfText);
            tempCanvas.drawText(""+leadList.get(1),20,sfHeight*2/3,penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap,0,0,penOfData);
        }else { //如果不是第一次绘制，就没有必要重新初始化bitmap，节约资源，其他操作完全一样
            Canvas tempCanvas = new Canvas(bitmap);
            tempCanvas.drawColor(Color.BLACK); //Draw background color to BLACK

            for (int i = 0; i < sfWidth / 30; i++) {
                tempCanvas.drawLine(i * 30, 0, i * 30, sfHeight, penOfGrid); //vertical lines
            }
            for (int i = 0; i < sfHeight / 30; i++) {
                tempCanvas.drawLine(0, i * 30, sfWidth, i * 30, penOfGrid);
            }
            float cx,cy,dx,dy; //画线需要的起点坐标和终点坐标
            float cx1,cy1,dx1,dy1;//第二导联的起点和终点坐标
            for (int i = 0,j=0;i<ECGCacheData.size()-2;i++){//对数据集合中的数据点进行两个两个的绘制
                //起点：
                cx = j * scaleX;
                cy = -ECGCacheData.get(i)*scaleY + sfHeight/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx = (j + 1) * scaleX;
                dy = -ECGCacheData.get(i+1)*scaleY + sfHeight/3;

                //起点：
                cx1 = j * scaleX;
                cy1 = -ECGCacheData1.get(i)*scaleY + sfHeight*2/3;//由于左上角为(0,0)，需要对画出来的图进行上下颠倒，
                //并根据Canvas高度对数据进行一定比例的放大
                //终点：
                dx1 = (j+ 1) * scaleX;
                dy1 = -ECGCacheData1.get(i+1)*scaleY +sfHeight*2/3;


                //在起点和重点之间画一条线段
                if(dx>=sfWidth){
                    j=0;
                    cx=0;
                    cx1=0;
                    dx=(j+1)*scaleX;
                    dx1=(j+1)*scaleX;
                    // System.out.println("清屏");
                    try {
                        if(tempCanvas!=null){
                            this.CleanCanvas(tempCanvas);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                tempCanvas.drawLine(cx,cy,dx,dy,penOfData);
                tempCanvas.drawLine(cx1,cy1,dx1,dy1,penOfData);
                j++;
            }
            // tempCanvas.drawText("心率："+value,20,100,penOfData);
            tempCanvas.drawText(""+leadList.get(1),20,sfHeight/3,penOfText);
            tempCanvas.drawText(""+leadList.get(0),20,sfHeight*2/3,penOfText);
            //绘制完毕
            canvas.drawBitmap(bitmap,0,0,penOfData);
        }
        //将双缓冲的图复制到SurfaceView上显示
        sfh.unlockCanvasAndPost(canvas);
    }

}
