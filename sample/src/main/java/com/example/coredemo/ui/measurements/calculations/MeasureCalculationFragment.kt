package com.example.coredemo.ui.measurements.calculations

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import be.appwise.core.ui.base.BaseBindingVMFragment
import be.appwise.measurements.units.Dimension
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
        initDivideViews()
    }

    private fun initAdditionViews() {
        val items = UnitLength.list.map { it.symbol }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        (mBinding.cmcLength.fromEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.firstLengthUnit.value = first
            }
        }

        (mBinding.cmcLength.toEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first(), false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = UnitLength.list.first { it.symbol == items[i] }
                mViewModel.secondLengthUnit.value = first
            }
        }
    }

    private fun initDivideViews() {
        val items = UnitLength.list
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items.map { it.symbol })

        (mBinding.cmcDivideDifferent.fromEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(adapter)
            tv.setText(items.first().symbol, false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = items.first { it.symbol == items[i].symbol }
                mViewModel.firstDivideUnit.value = first
            }
        }

        val newList = Dimension.list
        val newAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, newList.map { it.symbol })

        (mBinding.cmcDivideDifferent.toEditText as? AutoCompleteTextView)?.let { tv ->
            tv.setAdapter(newAdapter)
            tv.setText(newList.first().symbol, false)

            tv.setOnItemClickListener { _, _, i, _ ->
                val first = newList.first { it.symbol == newList[i].symbol }
                mViewModel.secondDivideUnit.value = first
            }
        }
    }
}