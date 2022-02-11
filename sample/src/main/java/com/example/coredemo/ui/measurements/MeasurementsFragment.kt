package com.example.coredemo.ui.measurements

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import be.appwise.measurements.units.UnitEnergy
import be.appwise.measurements.units.UnitLength
import com.example.coredemo.R
import com.example.coredemo.databinding.FragmentMeasurementsBinding

class MeasurementsFragment : BaseBindingVMFragment<FragmentMeasurementsBinding>() {

    override fun getLayout() = R.layout.fragment_measurements
    override val mViewModel: MeasurementsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.viewModel = mViewModel

        initEnergyViews()
        initLengthViews()
    }

    private fun initLengthViews() {
        val items = UnitLength.list.map { it.symbol }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        (mBinding.unitLength.fromEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.firstLengthUnit.value = first
            }
        }

        (mBinding.unitLength.toEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.secondLengthUnit.value = first
            }
        }
    }

    private fun initEnergyViews() {
        val items = UnitEnergy.list.map { it.symbol }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        (mBinding.unitEnergy.fromEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitEnergy.list.first { it.symbol == items[i] }
                mViewModel.firstEnergyUnit.value = first
            }
        }

        (mBinding.unitEnergy.toEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitEnergy.list.first { it.symbol == items[i] }
                mViewModel.secondEnergyUnit.value = first
            }
        }
    }
}