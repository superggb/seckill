package service;

import dto.Exposer;
import dto.SeckillExcecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseExcption;
import exception.SeckillException;

import java.util.List;

public interface SeckillService {
    /**
     * 查询所有秒杀记录
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启则输出秒杀接口，否则输出系统时间和秒杀时间
     */

    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     */

    SeckillExcecution executeSeckill(long seckillId,long userPhone,String md5)
    throws SeckillException, SeckillCloseExcption, RepeatKillException;
}
