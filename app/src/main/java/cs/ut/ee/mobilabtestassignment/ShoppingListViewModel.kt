package cs.ut.ee.mobilabtestassignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ShoppingListViewModel: ViewModel(){
    var shoppingLists = ArrayList<ArrayList<ItemEntity>>() //List of lists to store shopping lists
    var dataReceived = MutableLiveData<Boolean>() //Observable value that is changed when database data is received
    private var database: DatabaseReference = Firebase.database.reference

    init {
        dataReceived.value = false
    }

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

    fun saveToDataBase(){
        database.removeValue()
        database.setValue(shoppingLists)
    }

    fun readFromDataBase(){
        val singleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    shoppingLists.add(ArrayList<ItemEntity>())
                    it.children.forEach {
                        val itemCompleted = it.child("completed").getValue<Boolean>()!!
                        val itemName = it.child("item").getValue<String>()!!
                        val itemEntity = ItemEntity(itemName, itemCompleted)
                        shoppingLists[shoppingLists.lastIndex].add(itemEntity)
                    }
                }
                dataReceived.value = true
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        database.addListenerForSingleValueEvent(singleListener)
    }
}