package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.SysFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SysFrontendMenuTableServiceImpl implements SysFrontendMenuTableService{

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
    public SysFrontendDto F_PoToDto(SysFrontendMenuTable sysFrontendMenuTable) {
        SysFrontendDto sysFrontendDto = new SysFrontendDto();
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getPath())){
            sysFrontendDto.setPath(sysFrontendMenuTable.getPath());
        }
        return sysFrontendDto;
    }

    @Override
    public SysFrontendMenuTable selectByPrimaryKeyTwo(long id) {
        return sysFrontendMenuTableMapper.selectByPrimaryKeyTwo(id);
    }


    @Override
    public List<SysFrontendMenuTable> listWithTree(Long id){
        //获得父节点对象  1
        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableMapper.selectByPrimaryKey(id);
        //先判断父类的meta
        SysFrontendMenuTable.Meta mate = new SysFrontendMenuTable.Meta();
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getDescribe())) {
            mate.setTitle(sysFrontendMenuTable.getDescribe());
        }
        if (!StringUtil.isNullOrEmpty(sysFrontendMenuTable.getIcon())) {
            mate.setIcon(sysFrontendMenuTable.getIcon());
        }
        sysFrontendMenuTable.setMeta(mate);

        //在去拿子类
        List<SysFrontendMenuTable> children = getChildren(sysFrontendMenuTable);
        sysFrontendMenuTable.setChildren(children);

        return Collections.singletonList(sysFrontendMenuTable);
    }


    public List<SysFrontendMenuTable> getChildren(SysFrontendMenuTable sysFrontendMenuTable){

        SysFrontendMenuTable baba =  sysFrontendMenuTable;

        // 2 3
        List<SysFrontendMenuTable> children = sysFrontendMenuTableMapper.selectByParentId(baba.getId());
        for (SysFrontendMenuTable child : children) {
            List<SysFrontendMenuTable> children1 = getChildren(child);
            child.setChildren(children1);
        }
        // 将getDescribe getIcon 放进mate中
        for (SysFrontendMenuTable child : children) {
            SysFrontendMenuTable.Meta meta = new SysFrontendMenuTable.Meta();
            if (!StringUtil.isNullOrEmpty(child.getDescribe())) {
                meta.setTitle(child.getDescribe());
            }
            if (!StringUtil.isNullOrEmpty(child.getIcon())) {
                meta.setIcon(child.getIcon());
            }
            child.setMeta(meta);
        }

        return children;
    }


    @Override
    public  SysFrontendDto PoToDto(SysFrontendMenuTable sysFrontendMenuTable){
        SysFrontendDto sysFrontendDto = new SysFrontendDto();
        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getPath())) {
            sysFrontendDto.setPath(sysFrontendMenuTable.getPath());
        }
        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getLabel())) {
            sysFrontendDto.setName(sysFrontendMenuTable.getLabel());
        }
        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getComponent())) {
            sysFrontendDto.setComponent(sysFrontendMenuTable.getComponent());
        }

        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getDescribe())) {
            sysFrontendDto.setMeta(Collections.singletonList(sysFrontendMenuTable.getDescribe()));
        }
        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getIcon())) {
            sysFrontendDto.setMeta(Collections.singletonList(sysFrontendMenuTable.getIcon()));
        }
        if (!StringUtil.isNullOrEmpty( sysFrontendMenuTable.getRedirect())) {
            sysFrontendDto.setRedirect(sysFrontendMenuTable.getRedirect());
        }
        return sysFrontendDto;
    }


}
