package conorjmcq.github.com.myfirstapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import conorjmcq.github.com.firstAndroidApp.R
import conorjmcq.github.com.myfirstapp.models.Product
import kotlinx.android.synthetic.main.product_list_row.view.*


class ProductsAdapter(private val myDataset: ArrayList<Product>, private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productId = view.product_id
        val productName = view.product_name
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(context)
            .inflate(R.layout.product_list_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val product: Product = myDataset.get(position)
        holder.productId.text = product.id.toString()
        holder.productName.text = product.name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}