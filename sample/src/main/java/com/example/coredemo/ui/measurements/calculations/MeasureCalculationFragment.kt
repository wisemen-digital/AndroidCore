package com.example.coredemo.ui.measurements.calculations

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import be.appwise.measurements.units.UnitLength
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentMeasureCalculationBinding
import com.example.coredemo.ui.measurements.conversion.list

class MeasureCalculationFragment : BaseBindingVMFragment<FragmentMeasureCalculationBinding>() {

    override fun getLayout() = R.layout.fragment_measure_calculation
    override val mViewModel: MeasureCalculationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initAdditionViews()
    }

    private fun initAdditionViews() {
        val items = UnitLength.list.map { it.symbol }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        (mBinding.tilFromUnit.editText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.firstLengthUnit.value = first
            }
        }

        (mBinding.tilToUnit.editText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.secondLengthUnit.value = first
            }
        }
    }
}