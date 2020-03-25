package cs.ut.ee.mobilabtestassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val dataset: ArrayList<ItemEntity>): RecyclerView.Adapter<ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }
}



class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_row, parent, false)){
    private var itemNameView: TextView? = null

    init {
        itemNameView = itemView.findViewById(R.id.text_view_item)
    }

    fun bind(item: ItemEntity){
        itemNameView?.text = item.item
    }
}