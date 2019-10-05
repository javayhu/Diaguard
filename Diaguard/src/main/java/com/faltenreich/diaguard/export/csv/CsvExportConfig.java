package com.faltenreich.diaguard.export.csv;

import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.export.ExportCallback;
import com.faltenreich.diaguard.export.ExportConfig;

import org.joda.time.DateTime;

public class CsvExportConfig extends ExportConfig {

    private boolean isBackup;

    public CsvExportConfig(
        ExportCallback callback,
        DateTime dateStart,
        DateTime dateEnd,
        Measurement.Category[] categories,
        boolean isBackup
    ) {
        super(callback, dateStart, dateEnd, categories);
        this.isBackup = isBackup;
    }

    public boolean isBackup() {
        return isBackup;
    }
}