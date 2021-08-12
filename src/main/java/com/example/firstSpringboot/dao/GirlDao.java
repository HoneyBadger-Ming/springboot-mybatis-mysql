package com.example.firstSpringboot.dao;

import com.example.firstSpringboot.pojo.Girl;

import java.util.List;

public interface GirlDao {

    public List<Girl> findAll();

    public int save(Girl girl);

    public int addBatchGirl(List<Girl> girls);

    public int deleteOneGirl(Girl girl);

    public Girl getGirlById(Integer id);

    public int updateById(Girl girl);
}
