package com.faltenreich.diaguard.util.export;

import android.support.annotation.NonNull;

import com.faltenreich.diaguard.DiaguardApplication;
import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.util.FileUtils;
import com.faltenreich.diaguard.util.Helper;
import com.faltenreich.diaguard.util.IFileListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Faltenreich on 21.10.2015.
 */
public class Export {

    public static final String BACKUP_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String FILE_BACKUP_1_1_PREFIX = "backup";
    public static final String FILE_BACKUP_1_1_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String FILE_BACKUP_1_1_REGEX = "backup[0-9]{14}.csv";

    public static final String FILE_BACKUP_1_3_PREFIX = "diaguard_backup_";
    public static final String FILE_BACKUP_1_3_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String FILE_BACKUP_1_3_REGEX = "diaguard_backup_[0-9]{14}.csv";

    public enum FileType {
        CSV,
        PDF;

        public String getMimeType() {
            switch (this) {
                case CSV:
                    return "text/csv";
                case PDF:
                    return "application/pdf";
                default:
                    return null;
            }
        }

        public String getExtension() {
            switch (this) {
                case CSV:
                    return "csv";
                case PDF:
                    return "pdf";
                default:
                    return null;
            }
        }
    }

    public static final String PDF_MIME_TYPE = "application/pdf";

    public static final String CSV_MIME_TYPE = "text/csv";
    public static final char CSV_DELIMITER = ';';
    public static final String CSV_KEY_META = "meta";

    public static void exportPdf(IFileListener listener, @NonNull DateTime dateStart, @NonNull DateTime dateEnd, @NonNull Measurement.Category[] categories) {
        PdfExport pdfExport = new PdfExport(dateStart, dateEnd, categories);
        pdfExport.setListener(listener);
        pdfExport.execute();
    }

    public static void exportCsv(IFileListener listener, boolean isBackup, DateTime dateStart, DateTime dateEnd, Measurement.Category[] categories) {
        CsvExport csvExport = new CsvExport(isBackup, dateStart, dateEnd, categories);
        csvExport.setListener(listener);
        csvExport.execute();
    }

    public static void importCsv(IFileListener listener, File file) {
        CsvImport csvImport = new CsvImport(file);
        csvImport.setListener(listener);
        csvImport.execute();
    }

    public static DateTime dateTimeFromCsv(String dateString) {
        return Helper.getDateTimeFormatExport().parseDateTime(dateString);
    }

    public static File getExportFile(FileType fileType) {
        String fileName = String.format("%s%s%s_%s.%s",
                FileUtils.getPublicDirectory(),
                File.separator,
                DiaguardApplication.getContext().getString(R.string.app_name),
                DateTimeFormat.forPattern("yyyy-MM-dd_HH-mm").print(DateTime.now()),
                fileType.getExtension());
        return new File(fileName);
    }

    public static File getBackupFile(FileType fileType) {
        String fileName = String.format("%s%s%s%s.%s",
                FileUtils.getPublicDirectory(),
                File.separator,
                FILE_BACKUP_1_3_PREFIX,
                DateTimeFormat.forPattern(FILE_BACKUP_1_3_DATE_FORMAT).print(DateTime.now()),
                fileType.getExtension());
        return new File(fileName);
    }

    public static DateTime getBackupDate(File file) {
        String fileName = file.getName();
        if (fileName.matches(String.format(".*%s", FILE_BACKUP_1_1_REGEX))) {
            String dateString = getBackupDateString(fileName, FILE_BACKUP_1_1_PREFIX);
            return DateTimeFormat.forPattern(FILE_BACKUP_1_1_DATE_FORMAT).parseDateTime(dateString);
        } else if (fileName.matches(String.format(".*%s", FILE_BACKUP_1_3_REGEX))) {
            String dateString = getBackupDateString(fileName, FILE_BACKUP_1_3_PREFIX);
            return DateTimeFormat.forPattern(FILE_BACKUP_1_3_DATE_FORMAT).parseDateTime(dateString);
        } else {
            return null;
        }
    }

    private static String getBackupDateString(String fileName, String prefix) {
        int start = fileName.lastIndexOf(prefix) + prefix.length();
        int end = fileName.lastIndexOf(FileUtils.POINT);
        return fileName.substring(start, end);
    }

    public static List<File> getBackupFiles() {
        List<File> files = new ArrayList<>();

        File[] privateFiles = FileUtils.getPrivateDirectory().listFiles();
        File[] publicFiles = FileUtils.getPublicDirectory().listFiles();

        files.addAll(new ArrayList<>(Arrays.asList(privateFiles)));
        files.addAll(new ArrayList<>(Arrays.asList(publicFiles)));

        List<File> csvFiles = new ArrayList<>();
        for (File file : files) {
            if (isBackupFile(file)) {
                csvFiles.add(0, file);
            }
        }
        return csvFiles;
    }

    private static boolean isBackupFile(File file) {
        return file.getName().matches(String.format(".*%s", FILE_BACKUP_1_1_REGEX)) ||
                file.getName().matches(String.format(".*%s", FILE_BACKUP_1_3_REGEX));
    }
}
