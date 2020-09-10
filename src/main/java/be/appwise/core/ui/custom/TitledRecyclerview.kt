package be.appwise.core.ui.custom


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import be.appwise.core.R
import kotlinx.android.synthetic.main.titled_recyclerview.view.*

class TitledRecyclerview : ConstraintLayout {


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
        rvItems.adapter = titledRecyclerViewAdapter
        rvItems.layoutManager = layoutManager
        liveData.observe(owner, Observer {
            val list = converter(it)
            titledRecyclerViewAdapter.updateData(list)
            rvItems.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
            llEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    fun <LT, VH : RecyclerView.ViewHolder?> setUp(
        owner: LifecycleOwner,
        liveData: LiveData<List<LT>>,
        titledRecyclerViewAdapter: TitledRecyclerViewAdapter<LT, VH>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        rvItems.adapter = titledRecyclerViewAdapter
        rvItems.layoutManager = layoutManager
        liveData.observe(owner, Observer {
            titledRecyclerViewAdapter.updateData(it)
            rvItems.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            llEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
    }


    fun init(attrs: AttributeSet? = null) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.titled_recyclerview, this, true)

        ivAction.setOnClickListener {
            mActionListener?.onAction()
        }

        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.TitledRecyclerview)
            with(attributes) {

                val title = getString(R.styleable.TitledRecyclerview_title)
                setTitle(title)
                val primaryEmptyText = getString(R.styleable.TitledRecyclerview_primaryEmptyText)
                tvPrimaryText.text = primaryEmptyText
                val secondaryEmptyText = getString(R.styleable.TitledRecyclerview_secondaryEmptyText)
                tvSecondaryText.text = secondaryEmptyText
                try {
                    val emptyIcon = getResourceIdOrThrow(R.styleable.TitledRecyclerview_emptyIcon)
                    ivEmptyIcon.setImageResource(emptyIcon)
                } catch (ex: Exception) {

                }

                try {
                    val actionIcon = getResourceIdOrThrow(R.styleable.TitledRecyclerview_actionIcon)
                    ivAction.setImageResource(actionIcon)
                } catch (ex: Exception) {

                }


                /* val textSize = getDimensionPixelSize(R.styleable.InitialsImageView_android_textSize, -1)
                 tvInitials.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize.toFloat())

                 val textColor = getResourceId(R.styleable.InitialsImageView_android_textColor, android.R.color.white)
                 setTextColor(textColor)*/
                recycle()
            }
        }
    }


    fun setTitle(title: String?) {
            tvTitle.text = title
    }


    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }


    private var mActionListener: ActionListener? = null

    fun setActionListener(actionListener: ActionListener) {
        mActionListener = actionListener
    }

    interface ActionListener {
        fun onAction()
    }

}