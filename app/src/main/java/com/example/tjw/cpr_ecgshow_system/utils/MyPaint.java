package com.example.tjw.cpr_ecgshow_system.utils;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MyPaint {
    private int max = 66;
    private int min = 0;

    public void drawEPI(Canvas canvas,Paint paint,float time,float lengthEPI){
        time = time +20;//去掉左边一小块
        canvas.save();
        paint.setColor(Color.RED);
        canvas.drawRect(time,75,lengthEPI+time,115,paint);
        canvas.restore();
    }

    public void drawEPIText(Canvas canvas,Paint paint,String text,float time){
        time = time +20;//去掉左边一小块
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setTextSize(22);
        canvas.drawText(text,time,100,paint);
        canvas.restore();
    }

    public void drawCPRText(Canvas canvas,Paint paint,String text,float time){
        time = time +20;//去掉左边一小块
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setTextSize(22);
        canvas.drawText(text,time,145,paint);
        canvas.restore();
    }

    public void drawCPR(Canvas canvas,Paint paint,float time,float legthCPR){
        time = (float) (time +20);//去掉左边一小块
        canvas.save();
        paint.setColor(Color.GREEN);
        canvas.drawRect(time,120,legthCPR+time,160,paint);
        canvas.restore();
    }
//日志轴上 画VF图
    public void drawVF(Canvas canvas,Paint paint,int time) {
        float x1 = (float) (20 + time * 3.5);
        canvas.save();
        canvas.drawRect(x1, 340, x1 + 60, 377, paint);
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        canvas.drawText("VF\n"+time, x1 + 10, 370, paint);
        canvas.restore();
    }
    //日志轴上 画shock图
    public void drawShock(Canvas canvas,Paint paint,int time,String J,int num){
        float x1 = (float) (20 + time * 3.5);
        canvas.save();
        paint.setColor(Color.WHITE);
        canvas.drawRect(x1, 290, x1 + 200, 330, paint);
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        canvas.drawText("Shock ("+J+")"+num, x1 + 10, 315, paint);
        canvas.restore();
    }
    public void drawObserve(Canvas canvas,Paint paint,int time,int t){
        float x1 = (float) (20 + time * 3.5);
        canvas.save();
        paint.setColor(Color.WHITE);
        canvas.drawRect(x1, 290, x1 + 150, 330, paint);
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        canvas.drawText("等待观察 ("+t+")", x1, 315, paint);
        canvas.restore();
    }

    public void drawEnd(Canvas canvas,Paint paint,int time){
        float x1 = (float) (20 + time * 3.5);
        canvas.save();
        paint.setColor(Color.WHITE);
        canvas.drawRect(x1, 120, x1 + 55, 160, paint);
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        canvas.drawText("End", x1 + 10, 145, paint);
        canvas.restore();
    }

    //绘画日志轴
    public void drawBaseLine(Canvas canvas, Paint paint){
        canvas.save();
        canvas.drawLine(0, 207, 2300, 207, paint);
        int j = 0;
        for (int i = min; i < max; i++) {
            if (i % 3 == 0) {
                paint.setColor(Color.CYAN);
                canvas.drawLine(20, 187, 20, 240, paint);//画淡蓝色的竖线
                int temp = j++ * 30;
                @SuppressLint("DrawAllocation") StringBuilder min = new StringBuilder(String.valueOf((int) Math.ceil((double) (temp / 60))));
                @SuppressLint("DrawAllocation") StringBuilder sec = new StringBuilder(String.valueOf(temp % 60));
                if(min.length()==0){
                    min.append("00");
                }else if(min.length()==1){
                    min.insert(0,"0");
                }
                if(sec.length()==1){
                    sec.insert(0,"0");
                }
                String text = min + ":" + sec;


                @SuppressLint("DrawAllocation") Rect rect = new Rect();
                float textWidth = paint.measureText(text);
                //paint.getTextBounds(text, 0, text.length(), rect);
                paint.setTextSize(26);
                canvas.drawText(text, 18 - textWidth / 2, 240 + rect.height() + 30, paint);
            } else {
                paint.setColor(Color.BLUE);
                canvas.drawLine(20, 187, 20, 224, paint); //画深蓝色的竖线
            }
            canvas.translate(35, 0);
        }
        canvas.restore();
    }

}
