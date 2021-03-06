package com.faltenreich.diaguard.feature.export;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.shared.data.database.entity.Category;
import com.faltenreich.diaguard.shared.view.recyclerview.viewholder.BaseViewHolder;

import butterknife.BindView;

class ExportCategoryViewHolder extends BaseViewHolder<ExportCategoryListItem> {

    @BindView(R.id.category_image) ImageView categoryImageView;
    @BindView(R.id.category_checkbox) CheckBox categoryCheckBox;
    @BindView(R.id.extra_checkbox) CheckBox extraCheckBox;

    ExportCategoryViewHolder(ViewGroup parent) {
        super(parent, R.layout.list_item_export_category);
        initLayout();
    }

    private void initLayout() {
        categoryCheckBox.setOnCheckedChangeListener((checkBox, isChecked) -> {
            getItem().setCategorySelected(isChecked);
            extraCheckBox.setEnabled(isChecked);
        });
        extraCheckBox.setOnCheckedChangeListener((checkBox, isChecked) ->
            getItem().setExtraSelected(isChecked)
        );
    }

    @Override
    protected void onBind(ExportCategoryListItem item) {
        Category category = item.getCategory();

        categoryImageView.setImageResource(category.getIconImageResourceId());
        categoryCheckBox.setText(getContext().getString(category.getStringResId()));
        categoryCheckBox.setChecked(item.isCategorySelected());

        String extraTitle;
        switch (category) {
            case BLOODSUGAR:
                extraTitle = getContext().getString(R.string.highlight_limits);
                break;
            case INSULIN:
                extraTitle = getContext().getString(R.string.insulin_split);
                break;
            case MEAL:
                extraTitle = getContext().getString(R.string.food_append);
                break;
            default:
                extraTitle = null;
        }
        extraCheckBox.setText(extraTitle);
        extraCheckBox.setEnabled(item.isCategorySelected());
        extraCheckBox.setChecked(item.isExtraSelected());
        extraCheckBox.setVisibility(extraTitle != null ? View.VISIBLE : View.GONE);
    }
}
