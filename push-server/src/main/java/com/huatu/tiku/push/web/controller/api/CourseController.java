package com.huatu.tiku.push.web.controller.api;

import com.huatu.common.SuccessMessage;
import com.huatu.common.exception.BizException;
import com.huatu.springboot.web.version.mapping.annotation.ApiVersion;
import com.huatu.tiku.push.request.CourseInfoReq;
import com.huatu.tiku.push.service.api.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * 描述： php端调用接口，存储课程信息
 *
 * @author biguodong
 * Create time 2018-11-06 下午7:15
 **/

@RestController
@RequestMapping(value = "course")
@ApiVersion("v2")
public class CourseController {



    @Autowired
    private CourseService courseService;

    /**
     * 保存直播信息
     * @param model
     * @return
     * @throws BizException
     */
    @PostMapping
    public Object saveCourseInfo(@Validated @RequestBody CourseInfoReq.Model model) throws BizException {
        courseService.saveCourseInfo(model);
        return SuccessMessage.create("保存成功！");
    }

    @PostMapping(value = "batch")
    public Object saveCourseInfoBatch(@Validated @RequestBody CourseInfoReq.Batch batch) throws BizException{
        courseService.saveCourseInfoBatch(batch);
        return SuccessMessage.create("操作成功");
    }

    @DeleteMapping(value = "{id}")
    public Object removeCourseInfo(@Validated @NotNull(message = "直播id不能为空！") @PathVariable(value = "id") long liveId)throws BizException{
        courseService.removeCourseInfo(liveId);
        return SuccessMessage.create("删除成功！");
    }

    @GetMapping(value = "list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "20") int size) throws BizException{
        return courseService.list(page, size);
    }


}
