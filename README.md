# seckill
一个基于SSM框架的JAVA后台练习项目；
用Maven搭建环境；
前端用了一些简单的bootstrap+jquery+ajax+js
遵循了比较严格的开发过程，每一步都手动配置，且包含注解
是一个不错的SSM项目模板

功能实现：
展现商品（商品ID，商品名称，商品库存，商品秒杀时间）
选择某一个商品后会，进入计时页面，或者抢购页面，通过cookie记录用户手机号；
成功购买后会更新商品数量和数据库里面的购买记录

优化：
1.singleton redis缓存秒杀接口
2.protostuff提高序列化性能


