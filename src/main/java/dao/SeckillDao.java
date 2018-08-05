package dao;

import entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     * 减库存，记录秒杀的时间
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    Seckill queryById(long seckillId);
//通过带有@Param注解使mapper.xml文件能通过别名识别两个及以上参数，单个参数不存在这问题
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
