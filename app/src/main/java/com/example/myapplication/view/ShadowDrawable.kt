package com.jdc.bank.widet

import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import kotlin.math.min


class ShadowDrawable private constructor(
    private val mShape: Int,
    bgColor: IntArray,
    shapeRadius: Int,
    shadowColor: Int,
    shadowRadius: Int,
    offsetX: Int,
    offsetY: Int,
    startAngle: Float,
    sweepAngle: Float
) : Drawable() {
    private val mShadowPaint: Paint
    private val mBgPaint: Paint
    private val mShadowRadius: Int
    private val mShapeRadius: Int
    private val mOffsetX: Int
    private val mOffsetY: Int
    private val mBgColor: IntArray?
    private var mRect: RectF? = null
    private var mStartAngle:Float
    private var mSweepAngle:Float
    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        mRect = RectF(
            (left + mShadowRadius - mOffsetX).toFloat(),
            (top + mShadowRadius - mOffsetY).toFloat(),
            (right - mShadowRadius - mOffsetX).toFloat(),
            (bottom - mShadowRadius - mOffsetY).toFloat()
        )
    }

    override fun draw(canvas: Canvas) {
        if (mBgColor != null) {
            if (mBgColor.size == 1) {
                mBgPaint.color = mBgColor[0]
            } else {
                mBgPaint.shader = LinearGradient(
                    mRect!!.left, mRect!!.height() / 2, mRect!!.right,
                    mRect!!.height() / 2, mBgColor, null, Shader.TileMode.CLAMP
                )
            }
        }
        when (mShape) {
            SHAPE_ROUND -> {
                canvas.drawRoundRect(
                    mRect!!,
                    mShapeRadius.toFloat(),
                    mShapeRadius.toFloat(),
                    mShadowPaint
                )
                canvas.drawRoundRect(mRect!!, mShapeRadius.toFloat(), mShapeRadius.toFloat(), mBgPaint)
            }
            SHAPE_HALF_ROUND -> {
                canvas.drawArc(mRect!!, mStartAngle, mSweepAngle,true,mShadowPaint)
            }
            else -> {
                canvas.drawCircle(
                    mRect!!.centerX(),
                    mRect!!.centerY(),
                    min(mRect!!.width(), mRect!!.height()) / 2,
                    mShadowPaint
                )
                canvas.drawCircle(
                    mRect!!.centerX(),
                    mRect!!.centerY(),
                    min(mRect!!.width(), mRect!!.height()) / 2,
                    mBgPaint
                )
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        mShadowPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mShadowPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    class Builder {
        private var mShape: Int
        private var mShapeRadius: Int
        private var mShadowColor: Int
        private var mShadowRadius: Int
        private var mOffsetX: Int
        private var mOffsetY: Int
        private var mBgColor: IntArray
        private var mStartAngle:Float
        private var mSweepAngle:Float
        fun setShape(mShape: Int): Builder {
            this.mShape = mShape
            return this
        }

        fun setShapeRadius(ShapeRadius: Int): Builder {
            mShapeRadius = ShapeRadius
            return this
        }

        fun setShadowColor(shadowColor: Int): Builder {
            mShadowColor = shadowColor
            return this
        }

        fun setShadowRadius(shadowRadius: Int): Builder {
            mShadowRadius = shadowRadius
            return this
        }

        fun setOffsetX(OffsetX: Int): Builder {
            mOffsetX = OffsetX
            return this
        }

        fun setOffsetY(OffsetY: Int): Builder {
            mOffsetY = OffsetY
            return this
        }

        fun setBgColor(BgColor: Int): Builder {
            mBgColor[0] = BgColor
            return this
        }

        fun setBgColor(BgColor: IntArray): Builder {
            mBgColor = BgColor
            return this
        }
        fun setStartAngle(startAngle: Float): Builder {
            mStartAngle = startAngle
            return this
        }

        fun setSweepAngle(sweepAngle: Float): Builder {
            mSweepAngle = sweepAngle
            return this
        }
        fun builder(): ShadowDrawable {
            return ShadowDrawable(
                mShape,
                mBgColor,
                mShapeRadius,
                mShadowColor,
                mShadowRadius,
                mOffsetX,
                mOffsetY,
                mStartAngle,
                mSweepAngle
            )
        }



        init {
            mShape = SHAPE_ROUND
            mShapeRadius = 12
            mShadowColor = Color.parseColor("#4d000000")
            mShadowRadius = 18
            mOffsetX = 0
            mOffsetY = 0
            mBgColor = IntArray(1)
            mBgColor[0] = Color.TRANSPARENT
            mStartAngle = 40F
            mSweepAngle = 100F
        }
    }

    companion object {
        const val SHAPE_ROUND = 1
        const val SHAPE_CIRCLE = 2
        const val SHAPE_HALF_ROUND = 3
        fun setShadowDrawable(view: View, drawable: Drawable?) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }

        /**
         * 为指定View添加阴影
         * @param view 目标View
         * @param shapeRadius View的圆角
         * @param shadowColor 阴影的颜色
         * @param shadowRadius 阴影的宽度
         * @param offsetX 阴影水平方向的偏移量
         * @param offsetY 阴影垂直方向的偏移量
         */
        fun setShadowDrawable(
            view: View,
            shapeRadius: Int,
            shadowColor: Int,
            shadowRadius: Int,
            offsetX: Int,
            offsetY: Int
        ) {
            val drawable = Builder()
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .builder()
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }

        /**
         * 为指定View设置带阴影的背景
         * @param view 目标View
         * @param bgColor View背景色
         * @param shapeRadius View的圆角
         * @param shadowColor 阴影的颜色
         * @param shadowRadius 阴影的宽度
         * @param offsetX 阴影水平方向的偏移量
         * @param offsetY 阴影垂直方向的偏移量
         */
        fun setShadowDrawable(
            view: View,
            bgColor: Int,
            shapeRadius: Int,
            shadowColor: Int,
            shadowRadius: Int,
            offsetX: Int,
            offsetY: Int
        ) {
            val drawable = Builder()
                .setBgColor(bgColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .builder()
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }

        /**
         * 为指定View设置指定形状并带阴影的背景
         * @param view 目标View
         * @param shape View的形状 取值可为：GradientDrawable.RECTANGLE， GradientDrawable.OVAL， GradientDrawable.RING
         * @param bgColor View背景色
         * @param shapeRadius View的圆角
         * @param shadowColor 阴影的颜色
         * @param shadowRadius 阴影的宽度
         * @param offsetX 阴影水平方向的偏移量
         * @param offsetY 阴影垂直方向的偏移量
         */
        fun setShadowDrawable(
            view: View,
            shape: Int,
            bgColor: Int,
            shapeRadius: Int,
            shadowColor: Int,
            shadowRadius: Int,
            offsetX: Int,
            offsetY: Int
        ) {
            val drawable = Builder()
                .setShape(shape)
                .setBgColor(bgColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .builder()
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }

        /**
         * 为指定View设置指定形状角度阴影
         */

        fun setShadowDrawable(
            shape: Int,
            view: View,
            shapeRadius: Int,
            shadowColor: Int,
            shadowRadius: Int,
            offsetX: Int,
            offsetY: Int,
            startAngle:Float,
            sweepAngle:Float
        ) {
            val drawable = Builder()
                .setShapeRadius(shapeRadius)
                .setShape(shape)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .setStartAngle(startAngle)
                .setSweepAngle(sweepAngle)
                .builder()
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }

        /**
         * 为指定View设置带阴影的渐变背景
         * @param view
         * @param bgColor
         * @param shapeRadius
         * @param shadowColor
         * @param shadowRadius
         * @param offsetX
         * @param offsetY
         */
        fun setShadowDrawable(
            view: View,
            bgColor: IntArray,
            shapeRadius: Int,
            shadowColor: Int,
            shadowRadius: Int,
            offsetX: Int,
            offsetY: Int
        ) {
            val drawable = Builder()
                .setBgColor(bgColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .builder()
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            ViewCompat.setBackground(view, drawable)
        }
    }

    init {
        mBgColor = bgColor
        mShapeRadius = shapeRadius
        mShadowRadius = shadowRadius
        mOffsetX = offsetX
        mOffsetY = offsetY
        mShadowPaint = Paint()
        mShadowPaint.color = Color.TRANSPARENT
        mShadowPaint.isAntiAlias = true
        mStartAngle = startAngle
        mSweepAngle = sweepAngle
        mShadowPaint.setShadowLayer(
            shadowRadius.toFloat(),
            offsetX.toFloat(),
            offsetY.toFloat(),
            shadowColor
        )
        mShadowPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        mBgPaint = Paint()
        mBgPaint.isAntiAlias = true
    }
}