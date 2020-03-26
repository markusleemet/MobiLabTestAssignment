package cs.ut.ee.mobilabtestassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListsAdapter(private val dataset: ArrayList<ArrayList<ItemEntity>>, val onShoppingListClickListener: ShoppingListViewHolder.OnShoppingListClickListener): RecyclerView.Adapter<ShoppingListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ShoppingListViewHolder(inflater, parent, onShoppingListClickListener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }
}

class ShoppingListViewHolder(inflater: LayoutInflater, parent: ViewGroup, val onShoppingListClickListener: OnShoppingListClickListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.shopping_list_row, parent, false)), View.OnClickListener{
    private var itemsList: TextView? = null

    init {
        itemsList = itemView.findViewById(R.id.text_view_shopping_list)
        itemView.setOnClickListener(this)
    }


    fun bind(list: ArrayList<ItemEntity>){
        val stringBuilder = StringBuilder()
        list.forEach {
            stringBuilder.append(it.item + " • ")
        }
        try {
            itemsList?.text = stringBuilder.substring(0, stringBuilder.length - 3)
        } catch (e: StringIndexOutOfBoundsException) {
            itemsList?.text = "***empty list***"
        }
    }

    interface OnShoppingListClickListener{
        fun onShoppingListClick(position: Int)
    }

    override fun onClick(v: View?) {
        onShoppingListClickListener.onShoppingListClick(adapterPosition)
    }
}