package be.appwise.core.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import be.appwise.core.R

class TitledRecyclerview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.titled_recyclerview, this, true)
        findViewById<ImageView>(R.id.ivAction).setOnClickListener {
            mActionListener?.onAction()
        }
        findViewById<TextView>(R.id.tvAction).setOnClickListener {
            mActionListener?.onAction()
        }
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.TitledRecyclerview)
            with(attributes) {
                val title = getString(R.styleable.TitledRecyclerview_title)
                setTitle(title)
                val primaryEmptyText = getString(R.styleable.TitledRecyclerview_emptyTextTitle)
                findViewById<TextView>(R.id.tvEmptyTextTitle).text = primaryEmptyText
                val secondaryEmptyText =
                    getString(R.styleable.TitledRecyclerview_emptyTextDescription)
                findViewById<TextView>(R.id.tvEmptyTextDescription).text = secondaryEmptyText
                val emptyIcon = getResourceId(R.styleable.TitledRecyclerview_emptyIcon, -1)
                if (emptyIcon != -1)
                    findViewById<ImageView>(R.id.ivEmptyIcon).setImageResource(emptyIcon)
                findViewById<ImageView>(R.id.ivEmptyIcon).isVisible = emptyIcon != -1

                try {
                    val actionIcon = getResourceIdOrThrow(R.styleable.TitledRecyclerview_actionIcon)
                    findViewById<ImageView>(R.id.ivAction).setImageResource(actionIcon)
                } catch (ex: Exception) {
                }

                val actionText = getString(R.styleable.TitledRecyclerview_actionText)
                findViewById<TextView>(R.id.tvAction).text = actionText
                findViewById<TextView>(R.id.tvAction).isVisible = !actionText.isNullOrEmpty()
                findViewById<ImageView>(R.id.ivAction).isVisible = actionText.isNullOrEmpty()
                /* val textSize = getDimensionPixelSize(R.styleable.InitialsImageView_android_textSize, -1)
                 tvInitials.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize.toFloat())
                 val textColor = getResourceId(R.styleable.InitialsImageView_android_textColor, android.R.color.white)
                 setTextColor(textColor)*/
                recycle()
            }
        }
    }

    //todo add standard empty view + posibility to pass custom layout
    //todo diffutilcallback
    abstract class TitledRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder?> :
        RecyclerView.Adapter<VH>() {
        var data = emptyList<T>()
        fun updateData(data: List<T>) {
            this.data = data
            notifyDataSetChanged()
        }
    }

    /**
     * This function is used to give any kind of livedata (if not a list) example a user has a list of places
     *
     * LDT is User
     * LT is list type
     * VH is type of viewholder
     */
    fun <LDT, LT, VH : RecyclerView.ViewHolder?> setUp(
        owner: LifecycleOwner,
        liveData: LiveData<LDT>,
        converter: (LDT) -> List<LT>,
        titledRecyclerViewAdapter: TitledRecyclerViewAdapter<LT, VH>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        findViewById<RecyclerView>(R.id.rvItems).adapter = titledRecyclerViewAdapter
        findViewById<RecyclerView>(R.id.rvItems).layoutManager = layoutManager
        liveData.observe(owner, Observer {
            val list = converter(it)
            titledRecyclerViewAdapter.updateData(list)
            findViewById<RecyclerView>(R.id.rvItems).visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
            findViewById<LinearLayout>(R.id.llEmpty).visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    fun <LT, VH : RecyclerView.ViewHolder?> setUp(
        owner: LifecycleOwner,
        liveData: LiveData<List<LT>>,
        titledRecyclerViewAdapter: TitledRecyclerViewAdapter<LT, VH>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        findViewById<RecyclerView>(R.id.rvItems).adapter = titledRecyclerViewAdapter
        findViewById<RecyclerView>(R.id.rvItems).layoutManager = layoutManager
        liveData.observe(owner, Observer {
            titledRecyclerViewAdapter.updateData(it)
            findViewById<RecyclerView>(R.id.rvItems).visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            findViewById<LinearLayout>(R.id.llEmpty).visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    fun setTitle(title: String?) {
        findViewById<TextView>(R.id.tvTitle).text = title
    }

    private var mActionListener: ActionListener? = null

    fun setActionListener(actionListener: ActionListener) {
        mActionListener = actionListener
    }

    interface ActionListener {
        fun onAction()
    }
}