package cs.ut.ee.mobilabtestassignment

import android.util.Log
import androidx.lifecycle.ViewModel

class ShoppingListViewModel: ViewModel(){
    var shoppingLists = ArrayList<ArrayList<ItemEntity>>()


    fun addItemToShoppingList(item: ItemEntity, listIndex: Int){
        shoppingLists[listIndex].add(item)
    }

    fun addNewShoppingList(){
        shoppingLists.add(ArrayList<ItemEntity>())
    }

    fun changeItemStatus(shoppingListIndex: Int, itemIndex: Int){
        val itemCurrentStatus = shoppingLists[shoppingListIndex][itemIndex].completed
        shoppingLists[shoppingListIndex][itemIndex].completed = itemCurrentStatus.not()
    }
}