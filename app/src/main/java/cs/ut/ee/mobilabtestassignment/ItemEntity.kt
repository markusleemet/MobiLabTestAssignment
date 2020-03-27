package cs.ut.ee.mobilabtestassignment

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ItemEntity(val item: String, var completed: Boolean){}