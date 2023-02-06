package be.appwise.core.validation.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult

/**
 * A validator that can contain multiple validators.
 * This validator will return a [ValidationResult] based on the results of the validators it contains.
 * @param validatorBuilder the builder that will be used to add validators to this validator
 */
class CompositeValidator(
    validatorBuilder: CompositeValidator.() -> Unit = {}
) : IValidator {

    /**
     * The list of validators that will be used to validate the input
     */
    private val validators: MutableList<IValidator> = mutableListOf()

    /**
     * The [LiveData] that will be used to observe the validation result
     */
    private val _validResultLive: MutableLiveData<ValidationResult> = MutableLiveData(ValidationResult.Valid)
    val validResultLive: LiveData<ValidationResult> = _validResultLive

    init {
        validatorBuilder()
    }

    /**
     * Validates the input and returns a [ValidationResult]
     * @param handleError is not used by this validator but is used by specific validators to show errors to the user
     * @return [ValidationResult] the result of the validation
     */
    override fun validate(handleError: Boolean): ValidationResult {
        return validators.foldRight(ValidationResult.Valid as ValidationResult) { iValidator, acc ->
            val result = iValidator.validate()
            return@foldRight acc combine result
        }.also {
            _validResultLive.postValue(it)
        }
    }

    /**
     * Adds a validator to the list of validators
     * @param validator the validator that will be added to the list of validators
     */
    fun add(validator: IValidator) {
        validators.add(validator)
        validate()
    }

    /**
     * Removes a validator from the list of validators
     * @param validator the validator that will be removed from the list of validators
     */
    fun remove(validator: IValidator) {
        validators.remove(validator)
        validate()
    }

    /**
     * Removes all validators from the list of validators
     */
    fun clear() {
        validators.clear()
        validate()
    }

}