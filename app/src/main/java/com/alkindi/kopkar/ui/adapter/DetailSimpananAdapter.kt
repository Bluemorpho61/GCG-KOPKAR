package com.alkindi.kopkar.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.kopkar.data.remote.response.DetailSimpananItem
import com.alkindi.kopkar.databinding.RvDetailsimpananCardBinding
import com.alkindi.kopkar.utils.FormatterAngka

class DetailSimpananAdapter :
    ListAdapter<DetailSimpananItem, DetailSimpananAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvDetailsimpananCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailSimpananItem) {
            val nominalDetail =
                FormatterAngka.formatterAngkaRibuanDouble(item.amount.toString().toDouble())
            val tipeSimpanan = item.txnCode

            when (tipeSimpanan) {
                "SS" -> {
                    binding.tvSimpananSukarela.text ="Simpanan Sukarela"
                }
                "SK" ->{
                    binding.tvSimpananSukarela.text ="Simpanan Khusus"
                }
                "SKP"->{
                    binding.tvSimpananSukarela.text ="Simpanan Khusus Pagu"
                }
                "SP"->{
                    binding.tvSimpananSukarela.text ="Simpanan Pokok"
                }
                "SW"->{
                    binding.tvSimpananSukarela.text ="Simpanan Wajib"
                }
            }

            binding.tglTransaksi.text = item.transDate
            binding.tvNominalDetail.text = nominalDetail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvDetailsimpananCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataDetail = getItem(position)
        holder.bind(dataDetail)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailSimpananItem>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DetailSimpananItem,
                newItem: DetailSimpananItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: DetailSimpananItem,
                newItem: DetailSimpananItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}