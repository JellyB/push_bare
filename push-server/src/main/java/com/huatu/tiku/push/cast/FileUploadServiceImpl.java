package com.huatu.tiku.push.cast;

import com.google.common.base.Joiner;
import com.huatu.common.exception.BizException;
import com.huatu.tiku.push.constant.NoticePushRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-14 下午2:57
 **/
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private AndroidCustomCast androidCustomCast;

    @Autowired
    private IosCustomCast iosCustomCast;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 终端上传file_id
     *
     * @param classId
     * @return
     * @throws BizException
     */
    @Override
    public FileUploadTerminal uploadTerminal(long classId) throws BizException {

        String key = NoticePushRedisKey.getCourseClassId(classId);
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<Long> userIds = setOperations.members(key);
        String alias = Joiner.on("\n").join(userIds);
        String androidFileId = androidCustomCast.uploadContents(alias);
        String iosFileId = iosCustomCast.uploadContents(alias);
        return FileUploadTerminal
                .builder()
                .androidFileId(androidFileId)
                .iosFileId(iosFileId)
                .build();
    }
}
