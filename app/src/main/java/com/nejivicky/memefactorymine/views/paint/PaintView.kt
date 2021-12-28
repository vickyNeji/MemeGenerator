package com.nejivicky.memefactorymine.views.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.nejivicky.memefactorymine.utils.toMutableBitmap
import java.util.ArrayList

class PaintView : View {


    private var bitmapFromRes:Bitmap?=null
    private var BRUSH_SIZE=10
    private var mX = 0f
    private var mY = 0f
    private var mPath: Path? = null
    private var mPaint: Paint? = null
    private var currentColor = 0
    private val paths: ArrayList<Stroke> = ArrayList<Stroke>()
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)
    private var mCanvas: Canvas?=null
    var newAdded = false
    private val bitmap: ArrayList<Bitmap> = ArrayList()
    private val undoBitmap: ArrayList<Bitmap> = ArrayList()

    constructor(context: Context?):super(context)

    constructor(context: Context?,attrs:AttributeSet):super(context, attrs){
        mPaint=Paint().apply {
            isAntiAlias=true
            isDither=true
            color=COLOR_PEN
            style=Paint.Style.STROKE
            strokeJoin=Paint.Join.ROUND
            strokeCap=Paint.Cap.ROUND
            xfermode=null
            alpha= 0xff
        }
    }

    fun init(template:String){
        //bitmapFromRes=BitmapFactory.decodeResource(resources,template)
        bitmapFromRes=Glide.with(context).asBitmap()
            .load(template)
            .submit()
            .get()

        mCanvas = toMutableBitmap(bitmapFromRes!!)?.let { Canvas(it) }
        currentColor= COLOR_PEN
    }

    fun setColor(color:Int){
        currentColor=color
    }

    fun setBrushSize(size:Int){
        BRUSH_SIZE=size
    }

    fun clear(){
        paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        mCanvas=canvas
        val backgroundRect=Rect(0,0,mCanvas?.width!!,mCanvas?.height!!)

        bitmapFromRes?.let { mCanvas?.drawBitmap(it,null,backgroundRect,mBitmapPaint) }
        for(fp in paths){
            mPaint?.color=fp.color
            mPaint?.strokeWidth= fp.strokeWidth.toFloat()
            mPaint?.maskFilter=null
            mCanvas?.drawPath(fp.path,mPaint!!)
        }

        mCanvas?.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = Stroke(currentColor, BRUSH_SIZE, mPath!!)
        paths.add(fp)
        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x= event.x
        val y= event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                newAdded=true
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true

    }


    companion object {
        const val COLOR_PEN = Color.RED
        const val COLOR_ERASER = Color.TRANSPARENT
        const val DEFAULT_BG_COLOR = Color.WHITE
        private const val TOUCH_TOLERANCE = 4f
    }


}