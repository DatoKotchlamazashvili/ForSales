package com.example.tbcexercises.presentation.cart_screen.companies_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemCompanyCartBinding
import com.example.tbcexercises.domain.model.cart.Company
import com.example.tbcexercises.utils.extension.loadImg

class CompanyListAdapter(val onCompanyClick: (Company) -> Unit) :
    ListAdapter<Company, CompanyListAdapter.CompanyViewHolder>(CompanyDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding =
            ItemCompanyCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.onBind()
    }


    inner class CompanyViewHolder(val binding: ItemCompanyCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

            val company = getItem(bindingAdapterPosition)

            binding.apply {
                imgCompany.loadImg(company.companyImgUrl)

                txtCompany.text = company.company

                root.setOnClickListener {
                    onCompanyClick(company)
                }
                if (company.isClicked) {
                    root.background = ContextCompat.getDrawable(
                        root.context,
                        R.drawable.item_company_cart_selected_background
                    )
                    txtCompany.setTextColor(root.context.getColor(R.color.darkWhite))
                } else {
                    root.background = ContextCompat.getDrawable(
                        root.context,
                        R.drawable.item_company_cart_unselected_background
                    )
                    txtCompany.setTextColor(root.context.getColor(R.color.black))

                }
            }
        }
    }
}