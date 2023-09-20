package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.custombutton.databinding.PartButtonsBinding


enum class BottomButtonAction {
    POSITIVE, NEGATIVE
}

typealias  OnBottomButtonsActionListener = (BottomButtonAction) -> Unit


class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    desStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, desStyleRes) {


    private val binding: PartButtonsBinding

    private var listener: OnBottomButtonsActionListener? = null

    var isProgressMode: Boolean = false
        get() = field
        set(value) {
            field = value
            if (value) {
                binding.positiveButton.visibility = View.INVISIBLE
                binding.negativeButton.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.positiveButton.visibility = View.VISIBLE
                binding.negativeButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        R.style.MyBottomButtonsStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomButtonsStyle
    )

    constructor(context: Context) : this(context, null)


    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, desStyleRes)
        initListeners()
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, desStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            desStyleRes
        )
        with(binding) {

            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            setPositiveButtonText(positiveButtonText)

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

            val positiveButtonBgColor =
                typedArray.getColor(
                    R.styleable.BottomButtonsView_bottomPositiveBackgroundColor,
                    Color.BLACK
                )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBgColor)

            val negativeButtonBgColor =
                typedArray.getColor(
                    R.styleable.BottomButtonsView_bottomNegativeBackgroundColor,
                    Color.WHITE
                )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBgColor)

             isProgressMode = typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

        }

        typedArray.recycle()
    }

    private fun initListeners() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }

        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonsActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }
}