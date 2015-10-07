package com.faltenreich.diaguard.ui.view;

import android.content.Context;
import android.widget.EditText;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.faltenreich.diaguard.data.entity.Insulin;
import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.util.StringUtils;

import butterknife.Bind;

/**
 * Created by Faltenreich on 20.09.2015.
 */
public class MeasurementInsulinView extends MeasurementAbstractView<Insulin> {

    @Bind(R.id.edittext_bolus)
    protected EditText editTextBolus;

    @Bind(R.id.edittext_correction)
    protected EditText editTextCorrection;

    @Bind(R.id.edittext_basal)
    protected EditText editTextBasal;

    public MeasurementInsulinView(Context context) {
        super(context, Measurement.Category.INSULIN);
    }

    public MeasurementInsulinView(Context context, Insulin insulin) {
        super(context, insulin);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.list_item_measurement_insulin;
    }

    @Override
    protected void initLayout() {
        editTextBolus.setHint(getContext().getString(R.string.bolus));
        editTextCorrection.setHint(getContext().getString(R.string.correction));
        editTextBasal.setHint(getContext().getString(R.string.basal));
    }

    @Override
    protected void setValues() {
        editTextBolus.setText(measurement.getValuesForUI()[0]);
        editTextCorrection.setText(measurement.getValuesForUI()[1]);
        editTextBasal.setText(measurement.getValuesForUI()[2]);
    }

    @Override
    protected boolean isValid() {
        boolean isValid = true;

        String bolus = editTextBolus.getText().toString().trim();
        String correction = editTextCorrection.getText().toString().trim();
        String basal = editTextBasal.getText().toString().trim();

        if (StringUtils.isBlank(bolus) && StringUtils.isBlank(correction) && StringUtils.isBlank(basal)) {
            editTextBolus.setError(getContext().getString(R.string.validator_value_empty));
            isValid = false;
        } else {
            if (!StringUtils.isBlank(bolus)) {
                isValid = isValueValid(editTextBolus);
            }
            if (!StringUtils.isBlank(correction)) {
                isValid = isValueValid(editTextCorrection);
            }
            if (!StringUtils.isBlank(basal)) {
                isValid = isValueValid(editTextBasal);
            }
        }
        return isValid;
    }

    private boolean isValueValid(EditText editText) {
        boolean isValid = true;
        editText.setError(null);
        try {
            float value = PreferenceHelper.getInstance().formatCustomToDefaultUnit(measurement.getCategory(), Float.parseFloat(editText.getText().toString()));
            if (!PreferenceHelper.getInstance().validateEventValue(measurement.getCategory(), value)) {
                editText.setError(getContext().getString(R.string.validator_value_unrealistic));
                isValid = false;
            }
        } catch (NumberFormatException exception) {
            editText.setError(getContext().getString(R.string.validator_value_number));
            isValid = false;
        }
        return isValid;
    }

    @Override
    public Measurement getMeasurement() {
        if (isValid()) {
            measurement.setValues(
                    editTextBolus.getText().toString().length() > 0 ?
                            PreferenceHelper.getInstance().formatCustomToDefaultUnit(
                                    measurement.getCategory(),
                                    Float.parseFloat(editTextBolus.getText().toString())) : 0,
                    editTextCorrection.getText().toString().length() > 0 ?
                            PreferenceHelper.getInstance().formatCustomToDefaultUnit(
                                    measurement.getCategory(),
                                    Float.parseFloat(editTextCorrection.getText().toString())) : 0,
                    editTextBasal.getText().toString().length() > 0 ?
                            PreferenceHelper.getInstance().formatCustomToDefaultUnit(
                                    measurement.getCategory(),
                                    Float.parseFloat(editTextBasal.getText().toString())) : 0);
            return measurement;
        } else {
            return null;
        }
    }
    
}