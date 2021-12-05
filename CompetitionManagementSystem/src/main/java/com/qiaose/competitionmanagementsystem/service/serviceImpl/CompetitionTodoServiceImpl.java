package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CompetitionTodo;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionTodoMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionTodoService;

import java.util.List;

@Service
public class CompetitionTodoServiceImpl implements CompetitionTodoService{

    @Resource
    private CompetitionTodoMapper competitionTodoMapper;

    @Override
    public int deleteByPrimaryKey(Long todoId) {
        return competitionTodoMapper.deleteByPrimaryKey(todoId);
    }

    @Override
    public int insert(CompetitionTodo record) {
        return competitionTodoMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionTodo record) {
        return competitionTodoMapper.insertSelective(record);
    }

    @Override
    public CompetitionTodo selectByPrimaryKey(Long todoId) {
        return competitionTodoMapper.selectByPrimaryKey(todoId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionTodo record) {
        return competitionTodoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionTodo record) {
        return competitionTodoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionTodo> selectByApplicantId(String accountName) {
        return competitionTodoMapper.selectByApplicantId(accountName);
    }

    @Override
    public List<CompetitionTodo> selectByApprovalId(Long approvalId) {
        return competitionTodoMapper.selectByApprovalId(approvalId);
    }

}
