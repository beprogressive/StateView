package com.gmail.beprogressive.it.stateview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class StateView : LinearLayout {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var subtitleView: TextView
    private lateinit var retryView: TextView

    private var retryListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(ctx: Context, attr: AttributeSet?, defStyle: Int) : super(ctx, attr, defStyle) {
        init(ctx, attr, defStyle)
    }

    constructor(ctx: Context, attr: AttributeSet?, defStyle: Int, defStyleRes: Int)
            : super(ctx, attr, defStyle, defStyleRes) {
        init(ctx, attr, defStyle, defStyleRes)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int? = null,
        defStyleRes: Int? = null
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StateView,
            defStyle ?: 0,
            defStyleRes ?: 0
        )

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.state_view_layout, this, true)

        imageView = findViewById(R.id.sv_image)
        titleView = findViewById(R.id.sv_title)
        subtitleView = findViewById(R.id.sv_subtitle)
        retryView = findViewById(R.id.sv_retry)

        val imageRes: Int
        val imageTint: Int
        val imageVisible: Boolean
        val imageSize: Int

        val title: String?
        val titleColor: Int

        val subtitle: String?
        val subtitleColor: Int

        val titleVisible: Boolean
        val subtitleVisible: Boolean
        val retryVisible: Boolean

        val retryText: String?
        val retryBackground: Int
        val retryColor: Int

        try {
            imageRes =
                a.getResourceId(R.styleable.StateView_state_image, R.drawable.state_view_cloud)
            imageTint = a.getColor(R.styleable.StateView_state_imageTint, 0)
            imageVisible = a.getBoolean(R.styleable.StateView_state_imageVisible, true)
            imageSize = a.getDimensionPixelSize(R.styleable.StateView_state_imageSize, 0)
            title = a.getString(R.styleable.StateView_state_title)
            titleColor = a.getColor(
                R.styleable.StateView_state_titleColor,
                resources.getColor(R.color.state_view_title)
            )
            titleVisible = a.getBoolean(R.styleable.StateView_state_titleVisible, true)
            subtitle = a.getString(R.styleable.StateView_state_subtitle)
            subtitleColor = a.getColor(
                R.styleable.StateView_state_subtitleColor,
                resources.getColor(R.color.state_view_subtitle)
            )
            subtitleVisible = a.getBoolean(R.styleable.StateView_state_subtitleVisible, true)
            retryVisible = a.getBoolean(R.styleable.StateView_state_retryVisible, true)
            retryText = a.getString(R.styleable.StateView_state_retryText)
            retryBackground = a.getResourceId(
                R.styleable.StateView_state_retryBackground,
                R.drawable.state_view_retry_button_background
            )
            retryColor = a.getColor(
                R.styleable.StateView_state_retryColor,
                context.getColor(R.color.state_view_retry)
            )

            if (imageRes != 0) {
                setImage(imageRes)
            }
            if (imageTint != 0) {
                setImageTint(imageTint)
            }
            setImageVisible(imageVisible)
            if (imageSize != 0) {
                setImageSize(imageSize)
            }

            if (title != null) {
                setTitle(title)
            }

            if (subtitle != null) {
                setSubtitle(subtitle)
            }

            if (retryText != null) {
                retryView.text = retryText
            }

            if (!titleVisible) {
                titleView.visibility = View.GONE
            }

            if (!subtitleVisible) {
                subtitleView.visibility = View.GONE
            }

            if (!retryVisible) {
                retryView.visibility = View.GONE
            }

            titleView.setTextColor(titleColor)
            subtitleView.setTextColor(subtitleColor)

            retryView.setTextColor(retryColor)
            retryView.setBackgroundResource(retryBackground)

        } finally {
            a.recycle()
        }

        retryView.setOnClickListener {
            retryListener?.invoke()
        }
    }

    /**
     * Indicates whether the title is currently visible.
     */
    var isImageVisible: Boolean
        get() = imageView.visibility == View.VISIBLE
        set(value) = setImageVisible(value).unitify()

    /**
     * Returns the current title string.
     */
    var title: CharSequence
        get() = titleView.text
        set(value) = setTitle(value.toString()).unitify()

    /**
     * Indicates whether the title is currently visible.
     */
    var isTitleVisible: Boolean
        get() = titleView.visibility == View.VISIBLE
        set(value) = setTitleVisible(value).unitify()

    /**
     * Returns the current subtitle.
     */
    var subtitle: CharSequence
        get() = subtitleView.text
        set(value) = setSubtitle(value.toString()).unitify()

    /**
     * Indicates whether the subtitle is currently visible.
     */
    var isSubtitleVisible: Boolean
        get() = subtitleView.visibility == View.VISIBLE
        set(value) = setSubtitleVisible(value).unitify()

    /**
     * Returns the retry button text.
     */
    var retryText: CharSequence
        get() = retryView.text
        set(value) = setRetryText(value.toString()).unitify()

    /**
     * Indicates whether the retry button is visible.
     */
    var isRetryVisible: Boolean
        get() = retryView.visibility == View.VISIBLE
        set(value) = setRetryVisible(value).unitify()

    /**
     * Sets error image to a given drawable resource.
     */
    fun setImage(res: Int): StateView {
        imageView.setImageResource(res)
        return this
    }

    /**
     * Sets the error image to a given [android.graphics.drawable.Drawable].
     */
    fun setImage(drawable: Drawable): StateView {
        imageView.setImageDrawable(drawable)
        return this
    }

    /**
     * Sets the error image to a given [android.graphics.Bitmap].
     */
    fun setImage(bitmap: Bitmap): StateView {
        imageView.setImageBitmap(bitmap)
        return this
    }

    /**
     * Tints the error image with given color.
     */
    fun setImageTint(color: Int): StateView {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        return this
    }

    /**
     * Shows or hides the error image.
     */
    fun setImageVisible(visible: Boolean): StateView {
        imageView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets error image width to given pixel value. The ImageView adjusts its bounds to preserve the
     * aspect ratio of its drawable.
     */
    fun setImageSize(width: Int): StateView {
        imageView.layoutParams.width = width
        return this
    }

    /**
     * Sets the error title to a given [java.lang.String].
     */
    fun setTitle(text: String?): StateView {
        setTitleVisible(text != null)
        titleView.text = text
        return this
    }

    /**
     * Sets the error title to a given string resource.
     */
    fun setTitle(res: Int): StateView {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setTitleVisible(true)
        titleView.setText(res)
        return this
    }

    /**
     * Sets the error title text to a given color.
     */
    fun setTitleColor(color: Int): StateView {
        titleView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides the error title
     */
    fun setTitleVisible(visible: Boolean): StateView {
        titleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the error subtitle to a given [java.lang.String].
     */
    fun setSubtitle(subtitle: String?): StateView {
        setSubtitleVisible(subtitle != null)
        subtitleView.text = subtitle
        return this
    }

    /**
     * Sets the error subtitle to a given string resource.
     */
    fun setSubtitle(res: Int): StateView {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setSubtitleVisible(true)
        subtitleView.setText(res)
        return this
    }

    /**
     * Sets the error subtitle text to a given color
     */
    fun setSubtitleColor(color: Int): StateView {
        subtitleView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides error subtitle.
     */
    fun setSubtitleVisible(visible: Boolean): StateView {
        subtitleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the retry button text to a given string.
     */
    fun setRetryText(text: String): StateView {
        retryView.text = text
        return this
    }

    /**
     * Sets the retry button text to a given string resource.
     */
    fun setRetryText(res: Int): StateView {
        retryView.setText(res)
        return this
    }

    /**
     * Sets the retry button's text color to a given color.
     */
    fun setRetryColor(color: Int): StateView {
        retryView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides the retry button.
     */
    fun setRetryVisible(visible: Boolean): StateView {
        retryView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Attaches a listener to the view which will be notified when retry events occur.
     */
    fun setRetryListener(listener: () -> Unit): StateView {
        retryListener = listener
        return this
    }

    private fun Any.unitify(): Unit {}
}