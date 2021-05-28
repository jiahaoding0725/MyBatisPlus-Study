package com.ding;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ding.mapper.UserMapper;
import com.ding.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        System.out.println(("----- Insert method test ------"));
        User user = new User();
        user.setName("狂神说Java");
        user.setAge(3);
        user.setEmail("24736743@qq.com");
        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        // 通过条件自动拼接动态sql
        user.setId(6L);
        user.setName("关注公众号:狂神");
        user.setAge(18);
        // 注意:updateById 但是参数是一个 对象!
        int i = userMapper.updateById(user);
        System.out.println(i);

    }

    // 测试乐观锁成功
    @Test
    public void testOptimisticLocker1(){
        User user = userMapper.selectById(1);
        user.setName("kuangshen");
        user.setEmail("1023921169@qq.com");
        userMapper.updateById(user);
    }


    // 测试乐观所失败
    @Test
    public void testOptimisticLocker2(){
        User user1 = userMapper.selectById(1);
        user1.setName("kuangshen");
        user1.setEmail("1023921169@qq.com");

        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1);
        user2.setName("kaungshen222");
        user2.setEmail("1023921169@qq.com");
        userMapper.updateById(user2);

        // 自旋锁来多次尝试提交
        userMapper.updateById(user1);
    }


    // 查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }


    // 测试批量查询
    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        users.forEach(System.out::println);
    }

    // 条件查询 使用map操作
    @Test
    public void testSelectByCondition(){
        HashMap<String, Object> map = new HashMap<>();

        // 自定义查询条件
        map.put("name", "kuangshen");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    // 测试分页查询
    @Test
    public void testPage(){
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    // 测试删除
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1L);
    }
    // 通过id批量删除

    @Test
    public void testDeleteBatchId(){
        userMapper.deleteBatchIds(Arrays.asList(1240620674645544961L,1240620674645544962L));
    }

    // 通过map删除
    @Test
    public void testDeleteMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "狂神说Java");
        userMapper.deleteByMap(map);
    }
}
