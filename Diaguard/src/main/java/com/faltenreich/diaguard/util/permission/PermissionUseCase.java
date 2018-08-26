package com.faltenreich.diaguard.util.permission;

import android.support.annotation.Nullable;

public enum PermissionUseCase {
    EXPORT,
    BACKUP_WRITE,
    BACKUP_READ;

    public int requestCode = ordinal() + 123;

    @Nullable
    public static PermissionUseCase fromRequestCode(int requestCode) {
        for (PermissionUseCase useCase : values()) {
            if (useCase.requestCode == requestCode) {
                return useCase;
            }
        }
        return null;
    }
}