CREATE DATABASE seckill;

use seckill;

CREATE TABLE seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number int NOT NULL COMMENT '库存数量',
start_time TIMESTAMP NOT NULL,
 end_time  TIMESTAMP NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY  idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存';

INSERT INTO
  seckill(name,number,start_time,end_time)
    VALUES
      ('1000元秒杀iphoneX',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
      ('10元秒杀MARS',200,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
      ('10000元秒杀gakki',500,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
      ('999999999元秒杀ronaldo',600,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
      ('100元秒杀Neymar',400,'2018-11-01 00:00:00','2018-11-02 00:00:00');

CREATE TABLE success_killed(
  seckill_id BIGINT NOT NULL,
  user_phone BIGINT not NULL ,
  state TINYINT NOT NULL DEFAULT -1 COMMENT '状态-1表示无效，0表示成功，1表示已付款',
  create_time timestamp not NULL ,
  PRIMARY KEY (seckill_id,user_phone),
  key idx_create_time(create_time)

)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='秒杀成功信息表';

INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('99元秒杀iphone50',100,'2018-01-01 00:00:00','2018-11-02 00:00:00');




