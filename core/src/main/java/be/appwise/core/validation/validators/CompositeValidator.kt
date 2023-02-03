package be.appwise.core.validation.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eevee.app.validators.sandbox.IValidator
import com.eevee.app.validators.sandbox.ValidationResult

class CompositeValidator(
    validatorBuilder: CompositeValidator.() -> Unit = {}
) : IValidator {

    private val validators: MutableList<IValidator> = mutableListOf()
    private val _validResult: MutableLiveData<ValidationResult> = MutableLiveData(ValidationResult.Valid)
    val validResult: LiveData<ValidationResult> = _validResult

    init {
        validatorBuilder()
    }

    override fun validate(showErrors: Boolean): ValidationResult {
        return validators.foldRight(ValidationResult.Valid as ValidationResult) { iValidator, acc ->
            val result = iValidator.validate()
            return@foldRight acc combine result
        }.also {
            _validResult.postValue(it)
        }
    }


    fun add(validator: IValidator) {
        validators.add(validator)
    }

    fun remove(validator: IValidator) {
        validators.remove(validator)
    }

}