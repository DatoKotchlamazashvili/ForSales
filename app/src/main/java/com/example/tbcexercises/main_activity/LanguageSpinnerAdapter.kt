package com.example.tbcexercises.main_activity


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.tbcexercises.databinding.ItemLanguageSpinnerBinding
import com.example.tbcexercises.domain.model.Language

class LanguageSpinnerAdapter(
    context: Context,
    private val languages: List<Language>
) : ArrayAdapter<Language>(context, 0, languages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemLanguageSpinnerBinding = if (convertView == null) {
            ItemLanguageSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemLanguageSpinnerBinding.bind(convertView)
        }

        val languageItem = getItem(position)
        languageItem?.let {
            binding.imgLanguageFlag.setImageResource(it.flag)
            binding.txtLanguageName.text = it.name
        }

        return binding.root
    }
}

