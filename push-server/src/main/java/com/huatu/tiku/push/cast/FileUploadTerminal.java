package com.huatu.tiku.push.cast;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-21 下午8:15
 **/

@Data
@NoArgsConstructor
public class FileUploadTerminal {

    private String androidFileId;

    private String iosFileId;

    @Builder
    public FileUploadTerminal(String androidFileId, String iosFileId) {
        this.androidFileId = androidFileId;
        this.iosFileId = iosFileId;
    }
}
