package com.alkindi.kopkar.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.kopkar.data.remote.response.HistoryPinjamanItem
import com.alkindi.kopkar.databinding.RvHistorypinjamanCardBinding
import com.alkindi.kopkar.ui.activity.DetailHistoryPinjamanActivity
import com.alkindi.kopkar.utils.FormatterAngka

class HistoryPinjamanAdapter :
    ListAdapter<HistoryPinjamanItem, HistoryPinjamanAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvHistorypinjamanCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryPinjamanItem) {
            binding.tvUniqueId.text = item.docNum
            val statusPinjaman = item.aprinfo.toString().first()
            val fetchedNominalGaji = item.amount.toString()
            val fetchedNominalPinjamanBulanan = item.remain.toString()
            val tipePotPinjaman =item.loanCode
            val formattedNominalGaji =
                FormatterAngka.formatterAngkaRibuanDouble(fetchedNominalGaji.toDouble())
            val formattedNominalPinjamanBulanan =
                FormatterAngka.formatterAngkaRibuanDouble(fetchedNominalPinjamanBulanan.toDouble())

            when (statusPinjaman) {
                'A' -> binding.tvStatus.text = "Diterima"
                'R' -> binding.tvStatus.text = "Ditolak"
                else -> binding.tvStatus.text = "Diproses"
            }
            val formattedTenor = FormatterAngka.penghilangNilaiKoma(item.term.toString())
            binding.tvNominalPinjamanBulanan.text = formattedNominalPinjamanBulanan

            when(tipePotPinjaman){
                "AUTD"->{
                    binding.tvPinjamanBulanan.text ="Auto Debet"
                }
                "BAT"->{
                    binding.tvPinjamanBulanan.text ="BAT"
                }
                "BNS"->{
                    binding.tvPinjamanBulanan.text ="Bonus"
                }
                "COS"->{
                    binding.tvPinjamanBulanan.text ="COS"
                }
                "CUTI"->{
                    binding.tvPinjamanBulanan.text ="CUTI"
                }
                "DIS"->{
                    binding.tvPinjamanBulanan.text ="DIS"
                }
                "FGS"->{
                    binding.tvPinjamanBulanan.text ="Fungsional"
                }
                "GAJI"->{
                    binding.tvPinjamanBulanan.text ="Gaji"
                }
                "GJ13"->{
                    binding.tvPinjamanBulanan.text ="Gaji 13"
                }
                "INST"->{
                    binding.tvPinjamanBulanan.text ="Insentif"
                }
                "MBL"->{
                    binding.tvPinjamanBulanan.text ="Mobilitas"
                }
                "PMS"->{
                    binding.tvPinjamanBulanan.text ="PMS"
                }
                "PPAN"->{
                    binding.tvPinjamanBulanan.text ="Pinjaman Jangka Panjang"
                }
                "SHF"->{
                    binding.tvPinjamanBulanan.text ="Shift"
                }
                "THR"->{
                    binding.tvPinjamanBulanan.text ="THR"
                }
                "TNBL"->{
                    binding.tvPinjamanBulanan.text ="Tunai Bulanan"
                }
                "TNTH"->{
                    binding.tvPinjamanBulanan.text ="Tunai Tahunan"
                }
                "TRN"->{
                    binding.tvPinjamanBulanan.text ="Transport"
                }
                "TSS"->{
                    binding.tvPinjamanBulanan.text ="Simpanan Sukarela"
                }
            }
            binding.tvNominalGaji.text = formattedNominalGaji
            binding.tvSisaTenor.text = formattedTenor
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailHistoryPinjamanActivity::class.java).apply {
                        putExtra(DetailHistoryPinjamanActivity.EXTRA_DOCNUM, item.docNum)
                        context.startActivity(this)
                    }
                }
            }
        }
    }


    companion object {
        @SuppressLint("DiffUtilEquals")
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryPinjamanItem>() {
            override fun areContentsTheSame(
                oldItem: HistoryPinjamanItem,
                newItem: HistoryPinjamanItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: HistoryPinjamanItem,
                newItem: HistoryPinjamanItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvHistorypinjamanCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyData = getItem(position)
        holder.bind(historyData)
    }


}