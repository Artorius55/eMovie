package com.arthur.test.emovie.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import com.arthur.test.emovie.databinding.DataCardViewLayoutBinding

/**
 * Custom [CardView] class
 *
 * @author: Arturo Segura
 * @since: 1.0
 */
class DataCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var _binding: DataCardViewLayoutBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = DataCardViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * Method for setting [text] value into [textView] of this Custom View
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun setText(text: String) {
        binding.tcTextDataCardView.text = text
    }

    /**
     * Method for setting [drawable] res value into [imageView] of this Custom View
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun setIcon(@DrawableRes drawable: Int) {
        binding.ivIconDataCardView.setImageResource(drawable)
    }

    /**
     * Method for setting [imageView] visibility to VISIBLE
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun showIcon() {
        binding.ivIconDataCardView.visibility = VISIBLE
    }

    /**
     * Method for setting [imageView] visibility to GONE
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun hideIcon() {
        binding.ivIconDataCardView.visibility = GONE
    }
}