package com.example.androidpainting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Path drawPath; // 用于追踪用户触摸屏幕的路径
    private Paint drawPaint, canvasPaint; // 画笔和画布的画笔（用于显示绘画）
    private int paintColor = Color.BLACK; // 初始画笔颜色（黑色）
    private Canvas drawCanvas; // 内部的画布，实际的绘画都在这里进行
    private Bitmap canvasBitmap; // 在绘制过程中，我们的绘制会先在这个 Bitmap 上进行

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    // 设置和初始化绘画工具
    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    // 测量视图大小，初始化我们的 Bitmap 和画布
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    // 在这个视图上画一个路径
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    // 捕获用户触摸事件并将其转换为绘图路径
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                performClick();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    // 更改画笔颜色
    public void setPaintColor(int newColor) {
        invalidate();
        paintColor = newColor;
        drawPaint.setColor(paintColor);
    }

    public void setPaintSize(int newSize) {
        invalidate();
        drawPaint.setStrokeWidth(newSize);
    }

    // 清屏功能
    public void clear() {
        drawCanvas.drawColor(Color.WHITE);
        invalidate();
    }

    // 橡皮擦功能
    public void setErase() {
        drawPaint.setColor(Color.WHITE);
    }
}
