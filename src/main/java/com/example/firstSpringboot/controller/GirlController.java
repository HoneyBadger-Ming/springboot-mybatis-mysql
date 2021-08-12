package com.example.firstSpringboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.firstSpringboot.dao.GirlDao;
import com.example.firstSpringboot.pojo.Girl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GirlController {

    private static final Log LOGGER = LogFactory.getLog(GirlController.class);

    @Autowired
    private GirlDao girlDao;

    /**
     * 查询所有女生列表
     * @return
     */
    @RequestMapping(value = "/girls", method = RequestMethod.GET)
    public List<Girl> getGirlList() {
        return girlDao.findAll();
    }

    @GetMapping(value = "/getGirl")
    public Girl girGirlById(@RequestParam("id") Integer id) {
        //省略调了service层
        return girlDao.getGirlById(id);
    }

    /**
     * 添加一个女生
     * @param name
     * @param age
     * @return
     */
    @RequestMapping(value = "/addOne", method = RequestMethod.POST)
    public int addGirl(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        Girl girl = new Girl();
        girl.setName(name);
        girl.setAge(age);
        return girlDao.save(girl);
    }

    @PostMapping(value = "/addBatch")
    public int addBatchGirl(@RequestBody List<Girl> girls) {
        LOGGER.info(String.format("List: %s.", girls));
        return girlDao.addBatchGirl(girls);
    }

    @PostMapping(value = "/addBatch2")
    public int addBatchGirl2(String data) {
        LOGGER.info(String.format("data: %s.", data));
        List<Girl> girls = JSONObject.parseArray(data, Girl.class);
        return girlDao.addBatchGirl(girls);
    }

    @DeleteMapping(value = "/deleteOne")
    public int deleteOneGirl(@RequestBody Girl girl) {
        //直接省略掉了service层
        return girlDao.deleteOneGirl(girl);
    }

    @PutMapping(value = "/updateById")
    public int updateById(@RequestBody Girl girl) {
        return girlDao.updateById(girl);
    }

}
