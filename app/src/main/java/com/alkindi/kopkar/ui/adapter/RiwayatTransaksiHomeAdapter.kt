package com.alkindi.kopkar.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.kopkar.R
import com.alkindi.kopkar.data.remote.response.RiwayatTransaksiItem
import com.alkindi.kopkar.databinding.RvRiwayattransaksiHomeCardBinding
import com.bumptech.glide.Glide

class RiwayatTransaksiHomeAdapter :
    ListAdapter<RiwayatTransaksiItem, RiwayatTransaksiHomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvRiwayattransaksiHomeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatTransaksiItem) {
            val tglTransaksi = item.docDate.toString()
            val jenisTransaksi = item.jenis.toString()
            val tipeTransaksi = item.source.toString()
            val statusTransaksi = item.aprinfo.toString().first()

            if (tipeTransaksi == "PULL") {
                binding.tvJenisTransaksi.text = "Tarik Simpanan"
            } else if (tipeTransaksi == "PNJ") {
                binding.tvJenisTransaksi.text = "Pinjaman"
            }

            when (jenisTransaksi) {
                "SS" -> {
                    binding.tvTipeTransaksi.text = "Simpanan Sukarela"
                }

                "SKP" -> {
                    binding.tvTipeTransaksi.text = "Simpanan Khusus Pagu"
                }

                "SK" -> {
                    binding.tvTipeTransaksi.text = "Simpanan Khusus"
                }

                "JAPAN" -> {
                    binding.tvTipeTransaksi.text = "Jangka Panjang"
                }

                "RUMAH"->{
                    binding.tvTipeTransaksi.text = "Rumah"
                }
                "MOBIL"->{
                    binding.tvTipeTransaksi.text ="Mobil"
                }
                "BRANG"->{
                    binding.tvTipeTransaksi.text ="Kredit Barang"
                }
                "JAPEN"->{
                    binding.tvTipeTransaksi.text ="Jangka Pendek"
                }

            }
            when(statusTransaksi){
                'n' ->{
                    Glide.with(itemView.context).load(R.drawable.ic_pending).into(binding.icStatus)
                }
                'A'->{
                    Glide.with(itemView.context).load(R.drawable.ic_disetujui).into(binding.icStatus)
                }
                'R'->{
                    Glide.with(itemView.context).load(R.drawable.ic_ditolak).into(binding.icStatus)
                }
            }
            Log.d(
                TAG, "List status transaksi: $statusTransaksi"
            )
            binding.tvTanggal.text = tglTransaksi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvRiwayattransaksiHomeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyData = getItem(position)
        holder.bind(historyData)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RiwayatTransaksiItem>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RiwayatTransaksiItem,
                newItem: RiwayatTransaksiItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: RiwayatTransaksiItem,
                newItem: RiwayatTransaksiItem
            ): Boolean {
                return oldItem == newItem
            }
        }

        private val TAG = RiwayatTransaksiHomeAdapter::class.java.simpleName
    }
}