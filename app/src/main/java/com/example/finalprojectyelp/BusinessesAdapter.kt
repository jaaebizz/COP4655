package com.example.finalprojectyelp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_business.view.*


class BusinessesAdapter(val context: Context, val businesses: List<YelpBusiness>) :
    RecyclerView.Adapter<BusinessesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business, parent, false))
    }

    override fun getItemCount() = businesses.size

    override fun onBindViewHolder(holder: BusinessesAdapter.ViewHolder, position: Int) {
        val business = businesses[position]
        holder.bind(business)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(business: YelpBusiness) {
            itemView.findViewById<TextView>(R.id.tvName).text = business.name
            itemView.findViewById<RatingBar>(R.id.ratingBar).rating = business.rating.toFloat()
            itemView.findViewById<TextView>(R.id.tvNumReviews).text = "${business.numReviews} Reviews"
            itemView.findViewById<TextView>(R.id.tvAddress).text = business.location.address
            itemView.findViewById<TextView>(R.id.tvCategory).text = business.categories[0].title
            itemView.findViewById<TextView>(R.id.tvDistance).text = business.displayDistance()
            itemView.findViewById<TextView>(R.id.tvPrice).text = business.price
            Glide.with(context).load(business.imageUrl).apply(RequestOptions().transform(
                CenterCrop(), RoundedCorners(20)
            )).into(itemView.imageView)
        }

    }

}
