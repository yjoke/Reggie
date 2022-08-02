package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.dto.SetMealDTO;
import com.itheima.reggie.entity.SetMeal;
import com.itheima.reggie.mapper.SetMealMapper;
import com.itheima.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal>
        implements SetMealService {

    @Resource
    SetMealMapper setMealMapper;

    @Override
    public R<Page<SetMealDTO>> listSetMeal(Integer page, Integer pageSize, String setMealName) {

        Page<SetMealDTO> pageInfo = new Page<>(page, pageSize);

        Page<SetMealDTO> result = setMealMapper.selectSetMealDTO(pageInfo, setMealName);

        return R.success(result);
    }

    @Override
    public R<String> modifySetMealStatusBatch(Integer status, String ids) {

        Object[] id = ids.split(",");

        boolean update = lambdaUpdate()
                .set(SetMeal::getStatus, status)
                .in(SetMeal::getId, id)
                .update();

        log.info("更新情况: {}", update);

        return R.success("更新成功");
    }
}
