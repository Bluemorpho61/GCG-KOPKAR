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
                "AUTD"->{
                    binding.tvTipeTransaksi.text ="Auto Debet"
                }
                "BAT"->{
                    binding.tvTipeTransaksi.text ="BAT"
                }
                "BNS"->{
                    binding.tvTipeTransaksi.text ="Bonus"
                }
                "COS"->{
                    binding.tvTipeTransaksi.text ="COS"
                }
                "CUTI"->{
                    binding.tvTipeTransaksi.text ="CUTI"
                }
                "DIS"->{
                    binding.tvTipeTransaksi.text ="DIS"
                }
                "FGS"->{
                    binding.tvTipeTransaksi.text ="Fungsional"
                }
                "GAJI"->{
                    binding.tvTipeTransaksi.text ="Gaji"
                }
                "GJ13"->{
                    binding.tvTipeTransaksi.text ="Gaji 13"
                }
                "INST"->{
                    binding.tvTipeTransaksi.text ="Insentif"
                }
                "MBL"->{
                    binding.tvTipeTransaksi.text ="Mobilitas"
                }
                "PMS"->{
                    binding.tvTipeTransaksi.text ="PMS"
                }
                "PPAN"->{
                    binding.tvTipeTransaksi.text ="Pinjaman Jangka Panjang"
                }
                "SHF"->{
                    binding.tvTipeTransaksi.text ="Shift"
                }
                "THR"->{
                    binding.tvTipeTransaksi.text ="THR"
                }
                "TNBL"->{
                    binding.tvTipeTransaksi.text ="Tunai Bulanan"
                }
                "TNTH"->{
                    binding.tvTipeTransaksi.text ="Tunai Tahunan"
                }
                "TRN"->{
                    binding.tvTipeTransaksi.text ="Transport"
                }
                "TSS"->{
                    binding.tvTipeTransaksi.text ="Simpanan Sukarela"
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