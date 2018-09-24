package com.faltenreich.diaguard.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.util.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Faltenreich on 21.10.2015.
 */
public class CategoryCheckBoxList extends LinearLayout {

    private static final int PADDING = (int) Helper.getDPI(R.dimen.margin_between);

    private LinkedHashMap<Measurement.Category, Boolean> categories;

    public CategoryCheckBoxList(Context context) {
        super(context);
        init();
    }

    public CategoryCheckBoxList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            this.categories = new LinkedHashMap<>();
            Measurement.Category[] activeCategories = PreferenceHelper.getInstance().getActiveCategories();
            List<Measurement.Category> selectedCategories = Arrays.asList(PreferenceHelper.getInstance().getExportCategories());
            for (Measurement.Category category : activeCategories) {
                boolean isSelected = selectedCategories.contains(category);
                addCategory(category, isSelected);
            }
        }
    }

    private void addCategory(final Measurement.Category category, boolean isSelected) {
        categories.put(category, isSelected);
        addCheckBox(category.toLocalizedString(), categories.get(category), new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categories.put(category, isChecked);
            }
        });
    }

    private void addCheckBox(String text, boolean isChecked, CompoundButton.OnCheckedChangeListener listener) {
        CheckBox checkBox = new AppCompatCheckBox(getContext());
        checkBox.setMinimumHeight((int) getResources().getDimension(R.dimen.height_element));
        checkBox.setText(text);
        checkBox.setChecked(isChecked);
        checkBox.setPadding(PADDING, PADDING, PADDING, PADDING);
        checkBox.setOnCheckedChangeListener(listener);
        addView(checkBox);
    }

    public Measurement.Category[] getSelectedCategories() {
        ArrayList<Measurement.Category> selectedCategories = new ArrayList<>();
        for (Map.Entry<Measurement.Category, Boolean> mapEntry : categories.entrySet()) {
            if (mapEntry.getValue()) {
                selectedCategories.add(mapEntry.getKey());
            }
        }
        return selectedCategories.toArray(new Measurement.Category[selectedCategories.size()]);
    }
}
