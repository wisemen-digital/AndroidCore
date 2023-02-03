package be.appwise.core.validation.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult

class CompositeValidator(
    validatorBuilder: CompositeValidator.() -> Unit = {}
) : IValidator {

    private val validators: MutableList<IValidator> = mutableListOf()

    private val _validResultLive: MutableLiveData<ValidationResult> = MutableLiveData(ValidationResult.Valid)
    val validResultLive: LiveData<ValidationResult> = _validResultLive

    init {
        validatorBuilder()
    }

    override fun validate(handleError: Boolean): ValidationResult {
        return validators.foldRight(ValidationResult.Valid as ValidationResult) { iValidator, acc ->
            val result = iValidator.validate()
            return@foldRight acc combine result
        }.also {
            _validResultLive.postValue(it)
        }
    }


    fun add(validator: IValidator) {
        validators.add(validator)
    }

    fun remove(validator: IValidator) {
        validators.remove(validator)
    }

}