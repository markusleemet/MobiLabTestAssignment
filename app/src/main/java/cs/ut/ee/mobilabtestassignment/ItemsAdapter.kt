package cs.ut.ee.mobilabtestassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val dataset: ArrayList<ItemEntity>, val onItemClickListener: OnItemClickListener, var onItemLongClickListener: OnItemLongClickListener): RecyclerView.Adapter<ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent, onItemClickListener, onItemLongClickListener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }
}



class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup, var onItemClickListener: OnItemClickListener, var onItemLongClickListener: OnItemLongClickListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_row, parent, false)), View.OnClickListener, View.OnLongClickListener{
    private var itemNameView: TextView? = null
    private var itemCompletedView: ImageView? = null
    private var itemConstraintLayout: ConstraintLayout? = null


    init {
        itemNameView = itemView.findViewById(R.id.text_view_item)
        itemCompletedView = itemView.findViewById(R.id.image_view_item)
        itemConstraintLayout = itemView.findViewById(R.id.constraint_layout_item)
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun bind(item: ItemEntity){
        itemNameView?.text = item.item
        if (item.completed.not()) {
            itemCompletedView?.visibility = View.INVISIBLE
        }else{
            itemCompletedView?.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        onItemClickListener.onItemClick(adapterPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        onItemLongClickListener.onItemLongClick(adapterPosition)
        return true
    }
}

interface OnItemClickListener{
    fun onItemClick(position: Int)
}

interface OnItemLongClickListener{
    fun onItemLongClick(position: Int)
}