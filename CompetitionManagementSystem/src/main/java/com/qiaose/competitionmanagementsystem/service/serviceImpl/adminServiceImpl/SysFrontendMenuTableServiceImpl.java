package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendMenuTableService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFrontendMenuTableServiceImpl implements SysFrontendMenuTableService {

    @Resource
    private SysFrontendMenuTableMapper sysFrontendMenuTableMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysFrontendMenuTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.insertSelective(record);
    }

    @Override
    public SysFrontendMenuTable selectByPrimaryKey(Long id) {
        return sysFrontendMenuTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysFrontendMenuTable> selectByParentId(Long id) {
        return sysFrontendMenuTableMapper.selectByParentId(id);
    }


    @Override
    public SysFrontendMenuTable selectByPrimaryKeyTwo(long id) {
        return sysFrontendMenuTableMapper.selectByPrimaryKeyTwo(id);
    }


    @Override
    public List<SysFrontendMenuTable> listWithTree(Long id) {
        //获得父节点对象  1
        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableMapper.selectByPrimaryKey(id);
//        //先判断父类的meta
//        SysFrontendMenuTable.Meta meta = new SysFrontendMenuTable.Meta();
//        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getDescribe())) {
//            meta.setTitle(sysFrontendMenuTable.getLabel());
//        }
//        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getIcon())) {
//            meta.setIcon(sysFrontendMenuTable.getIcon());
//        }
//        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getRouteMeta())) {
//            JSONObject jsonObject =JSONObject.parseObject(sysFrontendMenuTable.getRouteMeta());
//            meta.setRouteMeta(jsonObject);
//        }
//        sysFrontendMenuTable.setMeta(meta);
        Map<String, Object> meta = new HashMap<>();
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getLabel())) {
            meta.put("title",sysFrontendMenuTable.getLabel());
        }
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getIcon())) {
            meta.put("icon",sysFrontendMenuTable.getIcon());
        }
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getRouteMeta())) {
            JSONObject jsonObject = JSONObject.parseObject(sysFrontendMenuTable.getRouteMeta());
            meta.putAll(jsonObject);
        }
        sysFrontendMenuTable.setMeta(meta);
        //在去拿子类
        List<SysFrontendMenuTable> children = getChildren(sysFrontendMenuTable);
        sysFrontendMenuTable.setChildren(children);

        return Collections.singletonList(sysFrontendMenuTable);
    }


    public List<SysFrontendMenuTable> getChildren(SysFrontendMenuTable sysFrontendMenuTable) {

        SysFrontendMenuTable baba = sysFrontendMenuTable;

        // 2 3
        List<SysFrontendMenuTable> children = sysFrontendMenuTableMapper.selectByParentId(baba.getId());
        for (SysFrontendMenuTable child : children) {
            List<SysFrontendMenuTable> children1 = getChildren(child);
            child.setChildren(children1);
        }
        // 将getDescribe getIcon 放进mate中
        for (SysFrontendMenuTable child : children) {
//            SysFrontendMenuTable.Meta meta = new SysFrontendMenuTable.Meta();
//            if (!StringUtil.isNullOrEmpty(child.getDescribe())) {
//                meta.setTitle(child.getLabel());
//            }
//            if (!StringUtil.isNullOrEmpty(child.getIcon())) {
//                meta.setIcon(child.getIcon());
//            }
//            if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getRouteMeta())) {
//                JSONObject jsonObject =JSONObject.parseObject(sysFrontendMenuTable.getRouteMeta());
//                meta.setRouteMeta(jsonObject);
//            }
//            child.setMeta(meta);
            Map<String, Object> meta = new HashMap<>();
            if (!StringUtil.isNullOrEmpty(child.getDescribe())) {
                meta.put("label", child.getLabel());
            }
            if (!StringUtil.isNullOrEmpty(child.getIcon())) {
                meta.put("icon", child.getIcon());
            }
            if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getRouteMeta())) {
                JSONObject jsonObject = JSONObject.parseObject(sysFrontendMenuTable.getRouteMeta());
                meta.putAll(jsonObject);
            }
            child.setMeta(meta);
        }
        return children;
    }

    @Override
    public SysFrontendMenuTable F_DtoToF_Po(SysFrontendDto sysFrontendDto) {
        SysFrontendMenuTable sysFrontendMenuTable = new SysFrontendMenuTable();

        //转换  可以设置默认值
        if (sysFrontendDto.getLabel() != null) {
            sysFrontendMenuTable.setLabel(sysFrontendDto.getLabel());
        }
        if (sysFrontendDto.getPath() != null)
            sysFrontendMenuTable.setPath(sysFrontendDto.getPath());
        if (sysFrontendDto.getState() != null)
            sysFrontendMenuTable.setState(Boolean.valueOf(sysFrontendDto.getState()));
        if (sysFrontendDto.getSortValue() != null)
            sysFrontendMenuTable.setSortValue(sysFrontendDto.getSortValue());
        if (sysFrontendDto.getParentId() != null) {
            sysFrontendMenuTable.setParentId(Long.valueOf(sysFrontendDto.getParentId()));
        }
        if (sysFrontendDto.getReadOnly() != null)
            sysFrontendMenuTable.setReadonly(sysFrontendDto.getReadOnly());
        if (sysFrontendDto.getKeepAlive() != null)
            sysFrontendMenuTable.setIsGeneral(sysFrontendDto.getKeepAlive());
        if (sysFrontendDto.getIcon() != null)
            sysFrontendMenuTable.setIcon(sysFrontendDto.getIcon());
        if (sysFrontendDto.getDescribe() != null)
            sysFrontendMenuTable.setDescribe(sysFrontendDto.getDescribe());

        return sysFrontendMenuTable;
    }

    @Override
    public List<SysFrontendMenuTable> selectByAll() {
        return sysFrontendMenuTableMapper.selectByAll();
    }

    @Override
    public List<SysFrontendMenuTable> findMenu(String name, Integer state) {
        return sysFrontendMenuTableMapper.findMenu(name, state);
    }

}
