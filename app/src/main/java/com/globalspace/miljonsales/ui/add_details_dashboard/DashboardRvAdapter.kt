package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutDashboardhospitalDetailsBinding

class DashboardRvAdapter(
    private val lstdata: List<DashboardData>,
    private val activity: FragmentActivity
) :
    RecyclerView.Adapter<DashboardRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutDashboardhospitalDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutDashboardhospitalDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.dashboardData = lstdata[position]
            val boardimg = lstdata[position].hospitalDetails!!.board_image
            if (boardimg !== null) {
                Glide.with(activity)
                    .load(boardimg).circleCrop()
                    .into(binding.boardimg)

            } else {
                binding.boardimg.setImageResource(R.drawable.ic_launcher_background)
            }
            /*val media = "http://13.127.182.214/mediapp/miljon_advance/uat/sales_reporting/DefaultImages/images.png"
            if (media !== null) {
                Glide.with(activity)
                    .load(media).circleCrop()
                    .into(binding.boardimg)

            } else {
                binding.boardimg.setImageResource(R.drawable.ic_launcher_background)
            }*/

            binding.executePendingBindings()
            holder.itemView.setOnClickListener {
                try {
                    val intent = Intent(activity, DashboardItemDetailsActivity::class.java)
                    val bundle = Bundle();
                    bundle.putSerializable("rv_selected_data", lstdata[position]);
                    intent.putExtras(bundle);
                    activity.startActivity(intent)
                } catch (e: Exception) {
                    val msg = e.message
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}

